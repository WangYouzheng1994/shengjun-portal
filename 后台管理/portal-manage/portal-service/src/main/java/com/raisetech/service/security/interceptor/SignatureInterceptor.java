package com.raisetech.service.security.interceptor;

import com.alibaba.fastjson2.JSON;
import com.raisetech.service.security.annotation.SignatureRequired;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.service.security.utils.SignatureUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 签名验证拦截器（门户服务专用）
 * 拦截所有标记了@SignatureRequired注解的接口请求
 * 验证请求签名的完整性和时效性
 *
 * 工作流程：
 * 1. 检查是否携带必要的签名头信息
 * 2. 提取时间戳并校验时效性
 * 3. 读取请求体并构建待签名字符串
 * 4. 使用HMAC-SHA256算法验证签名
 * 5. 通过验证后放行，否则返回401错误
 *
 * @author 王有政
 */
@Component
public class SignatureInterceptor implements HandlerInterceptor
{
    private static final Logger log = LoggerFactory.getLogger(SignatureInterceptor.class);

    /**
     * 签名密钥（从配置文件读取，生产环境建议使用环境变量或密钥管理服务）
     */
    @Value("${security.signature.secret:portal_service_secret_key_2026}")
    private String signatureSecret;

    /**
     * 签名头名称
     */
    private static final String SIGNATURE_HEADER = "X-Signature";

    /**
     * 时间戳头名称
     */
    private static final String TIMESTAMP_HEADER = "X-Timestamp";

    /**
     * 应用ID头名称（可选，用于多端区分）
     */
    private static final String APP_ID_HEADER = "X-App-Id";

    /**
     * 预处理请求：验证签名
     *
     * @param request HTTP请求
     * @param response HTTP响应
     * @param handler 处理器对象
     * @return true表示通过验证，false表示验证失败
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception
    {
        // 仅处理方法级别的处理器
        if (!(handler instanceof HandlerMethod))
        {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 检查方法是否有@SignatureRequired注解
        SignatureRequired signatureRequired = handlerMethod.getMethodAnnotation(SignatureRequired.class);
        if (signatureRequired == null)
        {
            // 没有注解，放行
            return true;
        }

        try
        {
            // 1. 获取签名相关头信息
            String signature = request.getHeader(SIGNATURE_HEADER);
            String timestampStr = request.getHeader(TIMESTAMP_HEADER);

            // 2. 校验必要头是否存在
            if (StringUtils.isEmpty(signature))
            {
                log.warn("缺少签名头: URI={} IP={}", request.getRequestURI(), getRealIp(request));
                sendSignatureErrorResponse(response, "缺少签名信息");
                return false;
            }

            if (signatureRequired.requireTimestamp() && StringUtils.isEmpty(timestampStr))
            {
                log.warn("缺少时间戳头: URI={} IP={}", request.getRequestURI(), getRealIp(request));
                sendSignatureErrorResponse(response, "缺少时间戳信息");
                return false;
            }

            // 3. 校验时间戳有效性（防重放攻击）
            long timestamp = 0L;
            if (StringUtils.isNotEmpty(timestampStr))
            {
                try
                {
                    timestamp = Long.parseLong(timestampStr);
                    long currentTime = System.currentTimeMillis();

                    // 检查时间戳是否在有效窗口内
                    long timeDiff = Math.abs(currentTime - timestamp);
                    long windowMillis = signatureRequired.timestampWindow() * 1000L;

                    if (timeDiff > windowMillis)
                    {
                        log.warn("时间戳过期: URI={} 时间差={}ms IP={}",
                            request.getRequestURI(), timeDiff, getRealIp(request));
                        sendSignatureErrorResponse(response, "请求已过期，请重新发起");
                        return false;
                    }
                }
                catch (NumberFormatException e)
                {
                    log.warn("时间戳格式错误: URI={} timestamp={} IP={}",
                        request.getRequestURI(), timestampStr, getRealIp(request));
                    sendSignatureErrorResponse(response, "时间戳格式错误");
                    return false;
                }
            }

            // 4. 读取请求体（用于签名计算）
            String body = "";
            if (signatureRequired.validateBody() && isBodyRequest(request.getMethod()))
            {
                body = readRequestBody(request);
            }

            // 5. 构建待签名字符串
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String signData = SignatureUtils.buildSignData(method, uri, timestamp, body);

            // 6. 验证签名
            boolean isValid = SignatureUtils.verifySignature(signData, signatureSecret, signature);

            if (!isValid)
            {
                log.warn("签名验证失败: URI={} Method={} IP={}",
                    uri, method, getRealIp(request));

                // 记录详细的调试信息（不记录敏感数据）
                log.debug("签名验证详情: signDataLength={} signatureLength={}",
                    signData.length(), signature.length());

                sendSignatureErrorResponse(response, "签名验证失败");
                return false;
            }

            // 签名验证通过
            log.debug("签名验证通过: URI={} AppId={}", uri, request.getHeader(APP_ID_HEADER));
            return true;
        }
        catch (Exception e)
        {
            log.error("签名验证异常: URI=" + request.getRequestURI(), e);
            sendSignatureErrorResponse(response, "服务器内部错误");
            return false;
        }
    }

    /**
     * 判断HTTP方法是否会携带请求体
     *
     * @param method HTTP方法
     * @return 是否为写操作方法
     */
    private boolean isBodyRequest(String method)
    {
        return "POST".equalsIgnoreCase(method) ||
               "PUT".equalsIgnoreCase(method) ||
               "PATCH".equalsIgnoreCase(method);
    }

    /**
     * 读取请求体内容
     * 注意：此操作会消耗输入流，需要配合ContentCachingRequestWrapper使用
     * 当前简化实现，仅适用于小体积请求体
     *
     * @param request HTTP请求
     * @return 请求体字符串
     * @throws IOException IO异常
     */
    private String readRequestBody(HttpServletRequest request) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader())
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    /**
     * 发送签名验证失败响应
     * 符合RFC 7805标准（401 Unauthorized）
     *
     * @param response HTTP响应
     * @param message 错误消息
     * @throws IOException IO异常
     */
    private void sendSignatureErrorResponse(HttpServletResponse response, String message) throws IOException
    {
        response.setStatus(401); // Unauthorized
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        // 告知客户端如何正确生成签名
        response.setHeader("X-Signature-Required", "true");
        response.setHeader("X-Signature-Algorithm", "HMAC-SHA256");
        response.setHeader("X-Timestamp-Required", "true");

        String json = JSON.toJSONString(com.alibaba.fastjson2.JSONObject.of(
            "code", 401,
            "msg", message,
            "signType", "HMAC-SHA256",
            "requiredHeaders", new String[]{"X-Signature", "X-Timestamp"}
        ));
        response.getWriter().write(json);
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
}
