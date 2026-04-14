package com.ruoyi.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 钉钉配置属性
 *
 * @author 王有政
 */
@Data
@Component
@ConfigurationProperties(prefix = "message.dingtalk")
public class DingTalkConfig {

    /**
     * 是否启用钉钉对接
     */
    private boolean enabled = false;

    /**
     * 应用AppKey
     */
    private String appKey;

    /**
     * 应用AppSecret
     */
    private String appSecret;

    /**
     * 代理应用AgentId（工作通知使用）
     */
    private Long agentId;
}
