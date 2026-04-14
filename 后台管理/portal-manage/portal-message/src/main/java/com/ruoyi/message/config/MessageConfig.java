package com.ruoyi.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 消息模块配置类
 *
 * @author 王有政
 */
@Configuration
public class MessageConfig {

    /**
     * RestTemplate Bean（用于HTTP调用第三方API）
     *
     * @return RestTemplate实例
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
