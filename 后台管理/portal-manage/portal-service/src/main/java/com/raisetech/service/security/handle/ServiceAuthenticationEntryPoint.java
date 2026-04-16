package com.raisetech.service.security.handle;

import com.alibaba.fastjson2.JSON;
import com.raisetech.common.constant.HttpStatus;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类（门户服务专用）
 * 返回未认证的JSON响应，不暴露内部错误信息
 * 
 * @author 王有政
 */
@Component
public class ServiceAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(ServiceAuthenticationEntryPoint.class);

    /**
     * 处理认证失败请求
     * 
     * @param request 当前请求
     * @param response 响应对象
     * @param authException 认证异常
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException
    {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        
        // 记录安全日志（不记录异常堆栈，防止信息泄露）
        log.warn("认证失败请求: {} - IP: {}", request.getRequestURI(), getIpAddr(request));
        
        // 设置响应状态和内容类型
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        
        // 返回统一的错误响应（脱敏处理）
        AjaxResult result = AjaxResult.error(code, "访问被拒绝，请先进行身份验证");
        response.getWriter().print(JSON.toJSONString(result));
    }

    /**
     * 获取客户端真实IP地址
     * 
     * @param request HTTP请求
     * @return IP地址字符串
     */
    private String getIpAddr(HttpServletRequest request)
    {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }
        // 多级代理时，取第一个IP
        if (ip != null && ip.contains(","))
        {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }
}
