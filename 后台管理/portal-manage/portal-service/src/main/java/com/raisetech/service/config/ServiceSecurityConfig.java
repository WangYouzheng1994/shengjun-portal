package com.raisetech.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import com.raisetech.service.security.handle.ServiceAuthenticationEntryPoint;
import com.raisetech.service.security.filter.IpBlacklistFilter;
import com.raisetech.service.security.filter.RateLimitFilter;
import com.raisetech.service.security.filter.RequestValidationFilter;
import com.raisetech.service.security.filter.AttackPreventionFilter;

/**
 * 门户服务Spring Security配置
 * 采用严格的安全策略，所有接口默认需要认证
 * 
 * @author 王有政
 */
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class ServiceSecurityConfig
{
    /**
     * 认证失败处理类
     */
    @Autowired
    private ServiceAuthenticationEntryPoint unauthorizedHandler;

    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;

    /**
     * IP黑名单过滤器
     */
    @Autowired
    private IpBlacklistFilter ipBlacklistFilter;

    /**
     * 全局限流过滤器
     */
    @Autowired
    private RateLimitFilter rateLimitFilter;

    /**
     * 请求校验过滤器（防注入、防XSS）
     */
    @Autowired
    private RequestValidationFilter requestValidationFilter;

    /**
     * 攻击预防过滤器
     */
    @Autowired
    private AttackPreventionFilter attackPreventionFilter;

	/**
	 * 身份验证实现
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception 
	{
		return authenticationConfiguration.getAuthenticationManager();
	}

    /**
     * 配置安全过滤链
     * 门户服务的安全策略：默认允许匿名访问公开接口，但通过多层过滤器进行防护
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception
    {
        return httpSecurity
            // CSRF禁用，因为不使用session且采用token认证
            .csrf(csrf -> csrf.disable())
            // 安全响应头配置
            .headers((headersCustomizer) -> {
                headersCustomizer
                    .cacheControl(cache -> cache.disable())
                    .frameOptions(options -> options.deny())
                    .httpStrictTransportSecurity(hsts -> hsts.maxAgeInSeconds(31536000));
            })
            // 认证失败处理类
            .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
            // 基于token，所以不需要session
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // URL授权配置
            .authorizeHttpRequests((requests) -> {
                // 允许匿名访问的公开接口（门户前端站使用）
                requests.requestMatchers("/portal/**", "/open/**", "/common/**").permitAll()
                    // 健康检查端点
                    .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                    // 静态资源
                    .requestMatchers(HttpMethod.GET, "/", "/*.html", "/**.html", "/**.css", "/**.js", "/profile/**").permitAll()
                    // API文档（仅开发环境）
                    .requestMatchers("/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    // 除上面外的所有请求全部需要鉴权认证
                    .anyRequest().authenticated();
            })
            // 添加自定义过滤器链（顺序很重要）
            // 1. CORS过滤器（最外层）
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            // 2. IP黑名单过滤器
            .addFilterBefore(ipBlacklistFilter, CorsFilter.class)
            // 3. 攻击预防过滤器
            .addFilterBefore(attackPreventionFilter, IpBlacklistFilter.class)
            // 4. 请求校验过滤器
            .addFilterBefore(requestValidationFilter, AttackPreventionFilter.class)
            // 5. 全局限流过滤器
            .addFilterBefore(rateLimitFilter, RequestValidationFilter.class)
            .build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
