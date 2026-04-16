package com.raisetech.service.security.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * 全局限流过滤器（门户服务专用）
 * 基于Redis + Lua脚本实现高性能分布式限流
 * 支持IP级别和URL级别的多维限流策略
 * 
 * @author 王有政
 */
@Component
@Order(3)
public class RateLimitFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(RateLimitFilter.class);

    /**
     * 是否启用限流
     */
    @Value("${security.rate-limit.enabled:true}")
    private boolean enabled;

    /**
     * 默认限制：每分钟请求数
     */
    @Value("${security.rate-limit.default-limit:100}")
    private int defaultLimit;

    /**
     * 突发流量容忍数
     */
    @Value("${security.rate-limit.burst:20}")
    private int burst;

    /**
     * 限流时间窗口（秒）
     */
    private static final long TIME_WINDOW = 60;

    /**
     * Redis模板
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * Lua脚本（原子性操作，保证限流准确性）
     */
    private final DefaultRedisScript<Long> limitScript;

    public RateLimitFilter(RedisTemplate<String, String> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
        
        // 初始化Lua脚本：令牌桶算法实现
        // 返回值：-1 表示拒绝，>=0 表示剩余可用请求数
        this.limitScript = new DefaultRedisScript<>();
        this.limitScript.setScriptText(
            "local key = KEYS[1] " +
            "local limit = tonumber(ARGV[1]) " +
            "local burst = tonumber(ARGV[2]) " +
            "local window = tonumber(ARGV[3]) " +
            "local current = redis.call('get', key) " +
            "if current == false then " +
            "    redis.call('set', key, 1, 'EX', window) " +
            "    return limit - 1 " +
            "else " +
            "    local count = tonumber(current) " +
            "    if count < limit + burst then " +
            "        redis.call('incr', key) " +
            "        return math.max(0, limit - count - 1) " +
            "    else " +
            "        return -1 " +
            "    end " +
            "end"
        );
        this.limitScript.setResultType(Long.class);
    }

    /**
     * 执行过滤逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        // 如果未启用限流，直接放行
        if (!enabled)
        {
            filterChain.doFilter(request, response);
            return;
        }
        
        // OPTIONS预检请求不限流
        if ("OPTIONS".equalsIgnoreCase(request.getMethod()))
        {
            filterChain.doFilter(request, response);
            return;
        }
        
        try
        {
            // 构建限流Key（IP + URL组合）
            String rateLimitKey = buildRateLimitKey(request);
            
            // 执行限流检查
            Long remaining = redisTemplate.execute(
                limitScript,
                Collections.singletonList(rateLimitKey),
                String.valueOf(defaultLimit),
                String.valueOf(burst),
                String.valueOf(TIME_WINDOW)
            );
            
            if (remaining != null && remaining < 0)
            {
                // 触发限流，记录日志并拒绝请求
                log.warn("触发限流: IP={} URI={} 限制={}/min", 
                    getRealIp(request), request.getRequestURI(), defaultLimit);
                
                sendRateLimitResponse(response);
                return;
            }
            
            // 设置响应头（告知客户端剩余配额）
            response.setHeader("X-RateLimit-Limit", String.valueOf(defaultLimit));
            response.setHeader("X-RateLimit-Remaining", String.valueOf(Math.max(0, remaining)));
            response.setHeader("X-RateLimit-Reset", String.valueOf(System.currentTimeMillis() / 1000 + TIME_WINDOW));
            
            // 通过限流检查，继续执行后续过滤器
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            log.error("限流检查异常", e);
            // 异常情况放行（避免影响正常业务）
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 构建限流Key
     * 采用 IP + HTTP方法 + URL 的组合方式
     * 
     * @param request HTTP请求
     * @return 限流Key
     */
    private String buildRateLimitKey(HttpServletRequest request)
    {
        String ip = getRealIp(request);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        
        // 格式：rate_limit:{IP}:{METHOD}:{URI}
        return String.format("rate_limit:%s:%s:%s", ip, method, uri);
    }

    /**
     * 获取客户端真实IP地址
     * 
     * @param request HTTP请求
     * @return IP地址
     */
    private String getRealIp(HttpServletRequest request)
    {
        return com.raisetech.common.utils.ip.IpUtils.getIpAddr(request);
    }

    /**
     * 发送限流响应
     * 符合RFC 6585标准（429 Too Many Requests）
     * 
     * @param response HTTP响应
     * @throws IOException IO异常
     */
    private void sendRateLimitResponse(HttpServletResponse response) throws IOException
    {
        response.setStatus(429); // Too Many Requests
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Retry-After", String.valueOf(TIME_WINDOW));
        
        // 统一的错误响应格式
        String json = JSON.toJSONString(com.alibaba.fastjson2.JSONObject.of(
            "code", 429,
            "msg", "访问过于频繁，请稍后再试",
            "retryAfter", TIME_WINDOW
        ));
        response.getWriter().write(json);
    }
}
