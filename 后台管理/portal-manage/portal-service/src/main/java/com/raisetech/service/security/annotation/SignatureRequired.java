package com.raisetech.service.security.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口签名验证注解
 * 标记在Controller方法上，表示该接口需要验证请求签名
 * 适用于所有写操作（POST/PUT/DELETE）和敏感的读操作
 *
 * 签名规则：
 * 1. 前端需要生成HMAC-SHA256签名
 * 2. 签名内容 = HTTP方法 + URL路径 + 时间戳 + 请求体（如有）
 * 3. 签名密钥由服务端配置，前端需妥善保管
 *
 * 使用示例：
 * <pre>
 * &#64;PostMapping("/submit")
 * &#64;SignatureRequired
 * public AjaxResult submitData(&#64;RequestBody DataDTO dto) {
 *     // 业务逻辑
 * }
 * </pre>
 *
 * @author 王有政
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SignatureRequired
{
    /**
     * 是否必须携带时间戳（默认true）
     * 如果为false，则不校验时间戳的有效性
     */
    boolean requireTimestamp() default true;

    /**
     * 时间戳有效窗口（秒），默认300秒（5分钟）
     * 超过此时间范围的请求将被拒绝
     */
    long timestampWindow() default 300L;

    /**
     * 是否校验请求体（默认true）
     * 对于GET请求可以设置为false
     */
    boolean validateBody() default true;
}
