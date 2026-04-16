package com.raisetech.service.security.filter;

import com.raisetech.common.utils.StringUtils;
import com.raisetech.common.utils.ip.IpUtils;
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
import java.util.Arrays;
import java.util.List;

/**
 * IP黑名单过滤器（门户服务专用）
 * 拦截来自黑名单IP的请求，支持通配符匹配
 * 
 * @author 王有政
 */
@Component
@Order(1)
public class IpBlacklistFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(IpBlacklistFilter.class);

    /**
     * IP黑名单列表（从配置文件读取）
     */
    @Value("${security.ip.blacklist:}")
    private String blacklistConfig;

    /**
     * 是否启用白名单模式（白名单模式下，仅允许白名单中的IP访问）
     */
    @Value("${security.ip.whitelist-enabled:false}")
    private boolean whitelistEnabled;

    /**
     * Redis模板（用于动态黑名单管理）
     */
    private final RedisTemplate<String, String> redisTemplate;

    public IpBlacklistFilter(RedisTemplate<String, String> redisTemplate)
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
        String clientIp = getRealIp(request);
        
        // 白名单模式检查
        if (whitelistEnabled)
        {
            if (!isWhitelisted(clientIp))
            {
                log.warn("IP不在白名单中，拒绝访问: {}", clientIp);
                sendErrorResponse(response, 403, "您的IP地址不在允许范围内");
                return;
            }
        }
        // 黑名单模式检查
        else
        {
            if (isBlacklisted(clientIp))
            {
                log.warn("检测到黑名单IP访问: {} - URI: {}", clientIp, request.getRequestURI());
                sendErrorResponse(response, 403, "访问被拒绝");
                return;
            }
        }
        
        // 通过检查，继续执行后续过滤器
        filterChain.doFilter(request, response);
    }

    /**
     * 判断IP是否在黑名单中
     * 支持配置文件静态黑名单 + Redis动态黑名单
     * 
     * @param ip 客户端IP地址
     * @return 是否在黑名单中
     */
    private boolean isBlacklisted(String ip)
    {
        // 1. 检查静态黑名单（配置文件）
        if (StringUtils.isNotEmpty(blacklistConfig))
        {
            List<String> staticBlacklist = Arrays.asList(blacklistConfig.split(","));
            for (String pattern : staticBlacklist)
            {
                if (matchPattern(ip, pattern.trim()))
                {
                    return true;
                }
            }
        }
        
        // 2. 检查动态黑名单（Redis存储）
        String dynamicBlacklistKey = "security:ip:blacklist";
        Boolean isDynamicBlacklisted = redisTemplate.hasKey(dynamicBlacklistKey + ":" + ip);
        if (Boolean.TRUE.equals(isDynamicBlacklisted))
        {
            return true;
        }
        
        return false;
    }

    /**
     * 判断IP是否在白名单中
     * 
     * @param ip 客户端IP地址
     * @return 是否在白名单中
     */
    private boolean isWhitelisted(String ip)
    {
        // 本地开发环境默认放行
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip))
        {
            return true;
        }
        
        // TODO: 可扩展为从配置或Redis读取白名单
        // 目前白名单模式主要用于内网部署场景
        
        return false;
    }

    /**
     * IP地址模式匹配
     * 支持精确匹配、通配符匹配（如 192.168.*）
     * 
     * @param ip 客户端IP
     * @param pattern 匹配模式
     * @return 是否匹配
     */
    private boolean matchPattern(String ip, String pattern)
    {
        if (StringUtils.isEmpty(pattern) || StringUtils.isEmpty(ip))
        {
            return false;
        }
        
        // 精确匹配
        if (pattern.equals(ip))
        {
            return true;
        }
        
        // 通配符匹配（简单实现：只支持末尾*）
        if (pattern.endsWith(".*"))
        {
            String prefix = pattern.substring(0, pattern.length() - 1);
            return ip.startsWith(prefix);
        }
        
        // 通配符匹配（支持末尾*）
        if (pattern.endsWith("*"))
        {
            String prefix = pattern.substring(0, pattern.length() - 1);
            return ip.startsWith(prefix);
        }
        
        return false;
    }

    /**
     * 获取客户端真实IP地址
     * 支持多级代理场景
     * 
     * @param request HTTP请求
     * @return 真实IP地址
     */
    private String getRealIp(HttpServletRequest request)
    {
        return IpUtils.getIpAddr(request);
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
        String json = "{\"code\":" + statusCode + ",\"msg\":\"" + message + "\"}";
        response.getWriter().write(json);
    }
}
