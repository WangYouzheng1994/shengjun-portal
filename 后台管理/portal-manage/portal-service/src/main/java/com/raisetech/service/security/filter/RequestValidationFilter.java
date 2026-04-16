package com.raisetech.service.security.filter;

import com.alibaba.fastjson2.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

/**
 * 请求校验过滤器（门户服务专用）
 * 防止SQL注入、XSS攻击、路径遍历等常见Web攻击
 * 对请求参数、Header、URL进行严格的安全检查
 * 
 * @author 王有政
 */
@Component
@Order(4)
public class RequestValidationFilter extends OncePerRequestFilter
{
    private static final Logger log = LoggerFactory.getLogger(RequestValidationFilter.class);

    /**
     * 是否启用SQL注入检测
     */
    @Value("${security.attack-prevention.sql-injection:true}")
    private boolean sqlInjectionCheckEnabled;

    /**
     * 是否启用XSS攻击检测
     */
    @Value("${security.attack-prevention.xss:true}")
    private boolean xssCheckEnabled;

    /**
     * SQL注入危险字符正则表达式
     */
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
        "(?i)(\\b(select|insert|update|delete|drop|truncate|exec|execute|union|load_file|intooutfile)\\b.*\\b(from|where|table|database)\\b)" +
        "|(\\b(OR|AND)\\b\\s+\\d+\\s*=\\s*\\d+)" +
        "|(\\b(OR|AND)\\b\\s+'[^']*'\\s*=\\s*'[^']*')" +
        "|(--|#|/\\*|\\*/)"
    );

    /**
     * XSS攻击危险字符正则表达式
     */
    private static final Pattern XSS_PATTERN = Pattern.compile(
        "(?i)(<script[^>]*>.*?</script>)" +
        "|(javascript:)" +
        "|(on(load|error|click|mouse|key|focus|blur)\\s*=)" +
        "|(expression\\s*\\()" +
        "|(vbscript:)" +
        "|(<iframe[^>]*>)"
    );

    /**
     * 路径遍历攻击正则表达式
     */
    private static final Pattern PATH_TRAVERSAL_PATTERN = Pattern.compile(
        "(\\.\\./)|(\\.\\\\)|(\\.{2,}[/\\\\])|(\\.[/\\\\])"
    );

    /**
     * 执行过滤逻辑
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        try
        {
            // 1. 检查URL安全性
            if (!validateUrl(request.getRequestURI()))
            {
                log.warn("检测到非法URL: {} - IP: {}", request.getRequestURI(), getRealIp(request));
                sendErrorResponse(response, 400, "非法的请求地址");
                return;
            }
            
            // 2. 检查Query参数
            if (!validateParameters(request))
            {
                log.warn("检测到非法参数: URI={} IP={}", request.getRequestURI(), getRealIp(request));
                sendErrorResponse(response, 400, "请求参数包含非法内容");
                return;
            }
            
            // 3. 检查请求头
            if (!validateHeaders(request))
            {
                log.warn("检测到非法请求头: URI={} IP={}", request.getRequestURI(), getRealIp(request));
                sendErrorResponse(response, 400, "请求头包含非法内容");
                return;
            }
            
            // 4. 对于POST/PUT请求，包装请求以读取Body并校验
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod()))
            {
                HttpServletRequest wrappedRequest = new RequestBodyValidationWrapper(request);
                filterChain.doFilter(wrappedRequest, response);
            }
            else
            {
                // GET/DELETE等请求直接放行
                filterChain.doFilter(request, response);
            }
        }
        catch (Exception e)
        {
            log.error("请求校验异常", e);
            sendErrorResponse(response, 500, "服务器内部错误");
        }
    }

    /**
     * 校验URL安全性
     * 
     * @param url 请求URL
     * @return 是否合法
     */
    private boolean validateUrl(String url)
    {
        if (url == null || url.isEmpty())
        {
            return false;
        }
        
        // 检查路径遍历攻击
        if (PATH_TRAVERSAL_PATTERN.matcher(url).find())
        {
            return false;
        }
        
        // URL长度限制（防止超长URL攻击）
        if (url.length() > 2048)
        {
            return false;
        }
        
        return true;
    }

    /**
     * 校验请求参数
     * 
     * @param request HTTP请求
     * @return 参数是否合法
     */
    private boolean validateParameters(HttpServletRequest request)
    {
        // 遍历所有参数
        java.util.Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            
            if (paramValues != null)
            {
                for (String value : paramValues)
                {
                    // SQL注入检查
                    if (sqlInjectionCheckEnabled && containsSqlInjection(value))
                    {
                        log.debug("检测到SQL注入风险: 参数={} 值={}", paramName, maskSensitiveData(value));
                        return false;
                    }
                    
                    // XSS攻击检查
                    if (xssCheckEnabled && containsXss(value))
                    {
                        log.debug("检测到XSS攻击风险: 参数={} 值={}", paramName, maskSensitiveData(value));
                        return false;
                    }
                    
                    // 单个参数值长度限制
                    if (value.length() > 10000)
                    {
                        log.warn("参数值过长: 参数={} 长度={}", paramName, value.length());
                        return false;
                    }
                }
            }
        }
        
        return true;
    }

    /**
     * 校验请求头
     * 
     * @param request HTTP请求
     * @return 请求头是否合法
     */
    private boolean validateHeaders(HttpServletRequest request)
    {
        // 检查User-Agent（防止空UA或可疑UA）
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.trim().isEmpty())
        {
            // 空User-Agent可能是爬虫或扫描器，记录但不拒绝（兼容性考虑）
            log.debug("空User-Agent请求: IP={}", getRealIp(request));
        }
        
        // 检查Content-Type（防止不合法的内容类型）
        String contentType = request.getContentType();
        if (contentType != null && !contentType.contains("application/json") && 
            !contentType.contains("application/x-www-form-urlencoded") &&
            !contentType.contains("multipart/form-data") &&
            !contentType.contains("text/plain"))
        {
            log.warn("可疑Content-Type: {} IP={}", contentType, getRealIp(request));
        }
        
        return true;
    }

    /**
     * 检测是否包含SQL注入特征
     * 
     * @param value 待检测字符串
     * @return 是否包含SQL注入特征
     */
    private boolean containsSqlInjection(String value)
    {
        if (value == null || value.isEmpty())
        {
            return false;
        }
        return SQL_INJECTION_PATTERN.matcher(value).find();
    }

    /**
     * 检测是否包含XSS攻击特征
     * 
     * @param value 待检测字符串
     * @return 是否包含XSS攻击特征
     */
    private boolean containsXss(String value)
    {
        if (value == null || value.isEmpty())
        {
            return false;
        }
        return XSS_PATTERN.matcher(value).find();
    }

    /**
     * 脱敏处理敏感数据（日志中使用）
     * 
     * @param data 原始数据
     * @return 脱敏后的数据
     */
    private String maskSensitiveData(String data)
    {
        if (data == null || data.length() <= 10)
        {
            return "***";
        }
        return data.substring(0, 5) + "***" + data.substring(data.length() - 5);
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

    /**
     * 请求体校验包装器
     * 用于读取POST/PUT请求的Body并进行安全校验
     */
    private static class RequestBodyValidationWrapper extends HttpServletRequestWrapper
    {
        private final byte[] body;

        public RequestBodyValidationWrapper(HttpServletRequest request) throws IOException
        {
            super(request);
            // 读取请求体
            body = readBytes(request.getInputStream());
            
            // 校验请求体内容
            String bodyStr = new String(body, StandardCharsets.UTF_8);
            if (bodyStr.length() > 1024 * 1024) // 1MB限制
            {
                throw new IllegalArgumentException("请求体过大");
            }
        }

        /**
         * 读取输入流为字节数组
         */
        private byte[] readBytes(ServletInputStream inputStream) throws IOException
        {
            java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int nRead;
            while ((nRead = inputStream.read(data, 0, data.length)) != -1)
            {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            return buffer.toByteArray();
        }

        @Override
        public BufferedReader getReader()
        {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream()
        {
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
            return new ServletInputStream()
            {
                @Override
                public boolean isFinished()
                {
                    return byteArrayInputStream.available() == 0;
                }

                @Override
                public boolean isReady()
                {
                    return true;
                }

                @Override
                public void setReadListener(ReadListener listener)
                {
                    throw new UnsupportedOperationException();
                }

                @Override
                public int read()
                {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
}
