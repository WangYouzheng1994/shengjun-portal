package com.raisetech.service.security.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 签名工具类
 * 提供HMAC-SHA256签名生成和验证功能
 * 用于接口请求的完整性和真实性验证
 *
 * @author 王有政
 */
public class SignatureUtils
{
    /**
     * HMAC-SHA256算法名称
     */
    private static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * 生成HMAC-SHA256签名
     *
     * @param data 待签名的数据
     * @param secret 签名密钥
     * @return Base64编码的签名结果
     * @throws RuntimeException 签名失败时抛出
     */
    public static String generateSignature(String data, String secret)
    {
        try
        {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e)
        {
            throw new RuntimeException("签名生成失败", e);
        }
    }

    /**
     * 验证签名是否有效
     *
     * @param data 原始数据
     * @param secret 签名密钥
     * @param signature 待验证的签名
     * @return 签名是否有效
     */
    public static boolean verifySignature(String data, String secret, String signature)
    {
        if (data == null || secret == null || signature == null)
        {
            return false;
        }

        String computedSignature = generateSignature(data, secret);

        // 使用时间恒定的比较方式，防止时序攻击
        return MessageDigest.isEqual(computedSignature, signature);
    }

    /**
     * 构建待签名的字符串
     * 按照约定的格式拼接：HTTP方法 + URL + 时间戳 + 请求体
     *
     * @param method HTTP方法（GET/POST/PUT/DELETE）
     * @param uri 请求URI路径
     * @param timestamp 时间戳（毫秒）
     * @param body 请求体内容（可为空）
     * @return 待签名的字符串
     */
    public static String buildSignData(String method, String uri, long timestamp, String body)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(method.toUpperCase()).append("\n");
        sb.append(uri).append("\n");
        sb.append(timestamp).append("\n");

        if (body != null && !body.isEmpty())
        {
            sb.append(body);
        }

        return sb.toString();
    }

    /**
     * 时间恒定比较方法
     * 防止时序攻击（Timing Attack）
     */
    private static class MessageDigest
    {
        /**
         * 安全比较两个字节数组
         * 无论是否匹配，执行时间都相同
         */
        public static boolean isEqual(String a, String b)
        {
            if (a.length() != b.length())
            {
                return false;
            }

            int result = 0;
            for (int i = 0; i < a.length(); i++)
            {
                result |= a.charAt(i) ^ b.charAt(i);
            }

            return result == 0;
        }
    }
}
