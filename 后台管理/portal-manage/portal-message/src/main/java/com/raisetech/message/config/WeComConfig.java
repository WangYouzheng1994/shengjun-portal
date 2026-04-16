package com.raisetech.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 企业微信配置属性
 *
 * @author 王有政
 */
@Data
@Component
@ConfigurationProperties(prefix = "message.wecom")
public class WeComConfig {

    /**
     * 是否启用企业微信对接
     */
    private boolean enabled = false;

    /**
     * 企业ID（CorpID）
     */
    private String corpId;

    /**
     * 应用Secret（应用的凭证密钥）
     */
    private String agentSecret;

    /**
     * 应用ID（AgentId）
     */
    private Integer agentId;
}
