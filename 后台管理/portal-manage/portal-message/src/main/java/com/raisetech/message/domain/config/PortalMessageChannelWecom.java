package com.raisetech.message.domain.config;

import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;

/**
 * 企业微信消息配置对象 portal_message_channel_wecom
 *
 * @author 王有政
 */
public class PortalMessageChannelWecom extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 关联渠道主表
     */
    private Long channelId;

    /**
     * 对接方式：internal企业自建应用/robot群机器人
     */
    @Excel(name = "对接方式")
    private String wecomType;

    /**
     * 企业ID
     */
    @Excel(name = "企业ID")
    private String corpId;

    /**
     * 应用AgentID（internal）
     */
    @Excel(name = "应用AgentID")
    private String agentId;

    /**
     * 应用Secret（internal）
     */
    private String agentSecret;

    /**
     * Webhook地址（robot）
     */
    @Excel(name = "Webhook地址")
    private String webhookUrl;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getWecomType() {
        return wecomType;
    }

    public void setWecomType(String wecomType) {
        this.wecomType = wecomType;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentSecret() {
        return agentSecret;
    }

    public void setAgentSecret(String agentSecret) {
        this.agentSecret = agentSecret;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}