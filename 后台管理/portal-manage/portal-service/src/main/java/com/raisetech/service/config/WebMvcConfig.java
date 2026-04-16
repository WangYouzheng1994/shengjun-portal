package com.raisetech.service.config;

import com.raisetech.service.security.interceptor.SignatureInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置
 * 注册自定义拦截器，包括签名验证拦截器
 *
 * @author 王有政
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
    /**
     * 签名验证拦截器
     */
    @Autowired
    private SignatureInterceptor signatureInterceptor;

    /**
     * 添加拦截器配置
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        // 注册签名验证拦截器
        // 拦截所有请求，但只对标记了@SignatureRequired注解的方法生效
        registry.addInterceptor(signatureInterceptor)
                .addPathPatterns("/**")  // 拦截所有路径
                .excludePathPatterns(  // 排除不需要签名的路径
                    "/common/**",      // 公共接口（健康检查等）
                    "/portal/article/**",  // 文章读接口
                    "/portal/product/**",  // 产品读接口
                    "/portal/banner/**",   // 轮播图接口
                    "/portal/notice/**",   // 公告接口
                    "/portal/company/**",  // 企业信息接口
                    "/swagger-ui/**",      // API文档
                    "/v3/api-docs/**",
                    "/error"
                );
    }
}
