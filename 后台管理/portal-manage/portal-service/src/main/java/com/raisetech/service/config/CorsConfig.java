package com.raisetech.service.config;

import com.raisetech.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CORS跨域资源配置（门户服务专用）
 * 采用严格的白名单机制，仅允许指定的域名访问
 * 
 * @author 王有政
 */
@Configuration
public class CorsConfig
{
    /**
     * 允许的域名列表（从配置文件读取）
     */
    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    /**
     * 允许的HTTP方法
     */
    @Value("${security.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;

    /**
     * 允许的请求头
     */
    @Value("${security.cors.allowed-headers:*}")
    private String allowedHeaders;

    /**
     * 预检请求缓存时间（秒）
     */
    @Value("${security.cors.max-age:3600}")
    private long maxAge;

    /**
     * 是否允许携带凭证（Cookie等）
     */
    @Value("${security.cors.allow-credentials:true}")
    private boolean allowCredentials;

    /**
     * 创建CORS过滤器
     * 
     * @return CORS过滤器实例
     */
    @Bean
    public CorsFilter corsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 设置允许的域名（严格白名单）
        if (StringUtils.isNotEmpty(allowedOrigins))
        {
            String[] origins = allowedOrigins.split(",");
            for (String origin : origins)
            {
                config.addAllowedOrigin(origin.trim());
            }
        }
        
        // 设置允许的HTTP方法
        if (StringUtils.isNotEmpty(allowedMethods))
        {
            String[] methods = allowedMethods.split(",");
            for (String method : methods)
            {
                config.addAllowedMethod(method.trim());
            }
        }
        
        // 设置允许的请求头
        if ("*".equals(allowedHeaders))
        {
            config.addAllowedHeader("*");
        }
        else if (StringUtils.isNotEmpty(allowedHeaders))
        {
            String[] headers = allowedHeaders.split(",");
            for (String header : headers)
            {
                config.addAllowedHeader(header.trim());
            }
        }
        
        // 允许携带凭证
        config.setAllowCredentials(allowCredentials);
        
        // 预检请求缓存时间
        config.setMaxAge(maxAge);
        
        // 暴露响应头（前端可访问的自定义头）
        config.addExposedHeader("X-Portal-Token");
        config.addExposedHeader("X-Request-Id");
        
        // 对所有路径应用CORS配置
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
