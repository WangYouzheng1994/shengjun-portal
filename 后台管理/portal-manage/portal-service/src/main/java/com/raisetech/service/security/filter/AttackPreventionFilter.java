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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 攻击预防过滤器（门户服务专用）
 * 综合检测和防御多种网络攻击行为
 * 包括：恶意爬虫检测、扫描器识别、异常行为分析、自动封禁机制
 * 
 * @author 王有政
 */
@Component
@Order(2)
public class AttackPreventionFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(AttackPreventionFilter.class);

    /**
     * 是否启用攻击防护
     */
    @Value("${security.attack-prevention.enabled:true}")
    private boolean enabled;

    /**
     * 是否启用防重放攻击
     */
    @Value("${security.attack-prevention.replay-attack:true}")
    private boolean replayAttackCheckEnabled;

    /**
     * 短时间内请求阈值（超过此值视为可疑）
     */
    @Value("#{${security.attack-prevention.suspicious-threshold:50}}")
    private int suspiciousThreshold;

    /**
     * 短时间窗口（秒）
     */
    private static final long SUSPICIOUS_TIME_WINDOW = 10;

    /**
     * 自动封禁时长（秒）
     */
    private static final long BAN_DURATION = 3600; // 1小时

    /**
     * Redis模板（用于存储攻击行为统计和封禁信息）
     */
    private final RedisTemplate<String, String> redisTemplate;

    public AttackPreventionFilter(RedisTemplate<String, String> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 执行过滤逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        if (!enabled)
        {
            filterChain.doFilter(request, response);
            return;
        }

        String clientIp = getRealIp(request);
        String requestUri = request.getRequestURI();

        try
        {
            // 1. 检查IP是否已被自动封禁
            if (isIpBanned(clientIp))
            {
                log.warn("封禁IP尝试访问: IP={} URI={}", clientIp, requestUri);
                sendBanResponse(response);
                return;
            }

            // 2. 检测恶意User-Agent（扫描器、爬虫）
            if (isMaliciousUserAgent(request.getHeader("User-Agent")))
            {
                log.warn("检测到恶意User-Agent: IP={} UA={}", clientIp, request.getHeader("User-Agent"));
                // 记录但不立即拒绝（兼容性考虑，可配置为拒绝）
                recordSuspiciousBehavior(clientIp, "malicious_ua");
            }

            // 3. 检测异常请求频率（短时间大量请求可能是攻击）
            if (detectAbnormalFrequency(clientIp))
            {
                log.warn("检测到异常请求频率: IP={} URI={}", clientIp, requestUri);
                
                // 增加可疑计数
                long suspiciousCount = incrementSuspiciousCount(clientIp);
                
                // 如果可疑次数过多，自动封禁
                if (suspiciousCount > suspiciousThreshold)
                {
                    banIpAutomatically(clientIp, "abnormal_frequency");
                    sendBanResponse(response);
                    return;
                }
            }

            // 4. 防重放攻击检查（针对写操作）
            if (replayAttackCheckEnabled && isWriteOperation(request.getMethod()))
            {
                if (!checkReplayAttack(request))
                {
                    log.warn("检测到重放攻击: IP={} URI={}", clientIp, requestUri);
                    sendErrorResponse(response, 400, "请求已过期或重复提交");
                    return;
                }
            }

            // 5. 记录正常访问（用于统计分析）
            recordNormalAccess(clientIp);

            // 通过所有检查，继续执行后续过滤器
            filterChain.doFilter(request, response);
        }
        catch (Exception e)
        {
            log.error("攻击预防检查异常", e);
            // 异常情况放行（避免影响正常业务）
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 检查IP是否已被封禁
     * 
     * @param ip 客户端IP
     * @return 是否被封禁
     */
    private boolean isIpBanned(String ip)
    {
        String banKey = "security:ip:banned:" + ip;
        return Boolean.TRUE.equals(redisTemplate.hasKey(banKey));
    }

    /**
     * 自动封禁IP
     * 
     * @param ip 客户端IP
     * @param reason 封禁原因
     */
    private void banIpAutomatically(String ip, String reason)
    {
        String banKey = "security:ip:banned:" + ip;
        redisTemplate.opsForValue().set(banKey, reason, BAN_DURATION, TimeUnit.SECONDS);
        
        // 同时添加到动态黑名单（供 IpBlacklistFilter 使用）
        String blacklistKey = "security:ip:blacklist:" + ip;
        redisTemplate.opsForValue().set(blacklistKey, "auto_banned", BAN_DURATION, TimeUnit.SECONDS);
        
        log.warn("IP已被自动封禁: IP={} 原因={} 时长={}秒", ip, reason, BAN_DURATION);
        
        // TODO: 可扩展为发送告警通知给运维人员
    }

    /**
     * 检测恶意User-Agent
     * 识别常见的扫描器、爬虫、自动化工具
     * 
     * @param userAgent User-Agent字符串
     * @return 是否为恶意UA
     */
    private boolean isMaliciousUserAgent(String userAgent)
    {
        if (userAgent == null || userAgent.trim().isEmpty())
        {
            return true; // 空UA通常是脚本或工具
        }

        String uaLower = userAgent.toLowerCase();
        
        // 常见恶意UA关键词列表
        String[] maliciousKeywords = {
            "bot", "crawler", "spider", "scanner", "curl", "wget",
            "python-requests", "httpclient", "java/", "nikto", "sqlmap",
            "nmap", "masscan", "zgrab", "go-http-client",
            "python-urllib", "libwww-perl", "lwp-trivial"
        };

        for (String keyword : maliciousKeywords)
        {
            if (uaLower.contains(keyword))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * 检测异常请求频率
     * 
     * @param ip 客户端IP
     * @return 是否存在异常
     */
    private boolean detectAbnormalFrequency(String ip)
    {
        String freqKey = "security:request:freq:" + ip;
        
        // 增加请求计数（使用滑动窗口）
        Long count = redisTemplate.opsForValue().increment(freqKey);
        if (count != null && count == 1)
        {
            redisTemplate.expire(freqKey, SUSPICIOUS_TIME_WINDOW, TimeUnit.SECONDS);
        }
        
        // 如果短时间内请求数超过阈值，返回true
        return count != null && count > suspiciousThreshold;
    }

    /**
     * 增加可疑行为计数
     * 
     * @param ip 客户端IP
     * @return 当前可疑计数
     */
    private long incrementSuspiciousCount(String ip)
    {
        String suspiciousKey = "security:suspicious:" + ip;
        Long count = redisTemplate.opsForValue().increment(suspiciousKey);
        if (count != null && count == 1)
        {
            redisTemplate.expire(suspiciousKey, 300, TimeUnit.SECONDS); // 5分钟窗口
        }
        return count != null ? count : 0;
    }

    /**
     * 检查重放攻击
     * 通过检查请求的时间戳和唯一标识符来防止重放
     * 
     * @param request HTTP请求
     * @return 是否通过检查
     */
    private boolean checkReplayAttack(HttpServletRequest request)
    {
        // 获取请求时间戳
        String timestamp = request.getHeader("X-Timestamp");
        if (timestamp == null || timestamp.isEmpty())
        {
            // 对于非签名接口，不强制要求时间戳
            return true;
        }

        try
        {
            long requestTime = Long.parseLong(timestamp);
            long currentTime = System.currentTimeMillis() / 1000;
            
            // 时间差超过5分钟，视为过期请求
            if (Math.abs(currentTime - requestTime) > 300)
            {
                return false;
            }

            // 检查是否已经处理过该请求（基于时间戳+IP的唯一性）
            String nonceKey = "security:nonce:" + getRealIp(request) + ":" + timestamp;
            if (Boolean.TRUE.equals(redisTemplate.hasKey(nonceKey)))
            {
                return false; // 重复请求
            }
            
            // 标记该请求已处理（5分钟有效期）
            redisTemplate.opsForValue().set(nonceKey, "1", 300, TimeUnit.SECONDS);
            
            return true;
        }
        catch (NumberFormatException e)
        {
            // 时间戳格式错误，拒绝请求
            return false;
        }
    }

    /**
     * 判断是否为写操作
     * 
     * @param method HTTP方法
     * @return 是否为写操作
     */
    private boolean isWriteOperation(String method)
    {
        return "POST".equalsIgnoreCase(method) ||
               "PUT".equalsIgnoreCase(method) ||
               "DELETE".equalsIgnoreCase(method) ||
               "PATCH".equalsIgnoreCase(method);
    }

    /**
     * 记录正常访问（用于统计和分析）
     * 
     * @param ip 客户端IP
     */
    private void recordNormalAccess(String ip)
    {
        // 轻量级记录，不影响性能
        String accessKey = "security:access:total:" + ip;
        redisTemplate.opsForValue().increment(accessKey);
    }

    /**
     * 记录可疑行为
     * 
     * @param ip 客户端IP
     * @param type 可疑类型
     */
    private void recordSuspiciousBehavior(String ip, String type)
    {
        String key = "security:suspicious:detail:" + ip;
        Map<String, String> data = new HashMap<>();
        data.put("type", type);
        data.put("timestamp", String.valueOf(System.currentTimeMillis()));
        data.put("uri", "");
        redisTemplate.opsForList().leftPush(key, JSON.toJSONString(data));
        redisTemplate.expire(key, 3600, TimeUnit.SECONDS); // 保留1小时
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
     * 发送封禁响应
     * 
     * @param response HTTP响应
     * @throws IOException IO异常
     */
    private void sendBanResponse(HttpServletResponse response) throws IOException
    {
        response.setStatus(403);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String json = JSON.toJSONString(com.alibaba.fastjson2.JSONObject.of(
            "code", 403,
            "msg", "您的IP已被临时封禁，如有疑问请联系管理员"
        ));
        response.getWriter().write(json);
    }

    /**
     * 发送错误响应
     * 
     * @param response HTTP响应
     * @param statusCode 状态码
     * @param message 错误消息
     * @throws IOException IO异常
     */
    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException
    {
        response.setStatus(statusCode);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String json = JSON.toJSONString(com.alibaba.fastjson2.JSONObject.of(
            "code", statusCode,
            "msg", message
        ));
        response.getWriter().write(json);
    }
}
