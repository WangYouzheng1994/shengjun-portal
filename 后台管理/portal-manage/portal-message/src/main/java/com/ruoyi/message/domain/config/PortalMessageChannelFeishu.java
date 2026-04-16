package com.ruoyi.message.domain.config;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 飞书消息配置对象 portal_message_channel_feishu
 *
 * @author 王有政
 */
public class PortalMessageChannelFeishu extends BaseEntity {
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
     * 对接方式：internal企业自建应用/webhook WebHook机器人/thirdparty第三方
     */
    @Excel(name = "对接方式")
    private String feishuType;

    /**
     * 应用AppID
     */
    @Excel(name = "应用AppID")
    private String appId;

    /**
     * 应用AppSecret
     */
    private String appSecret;

    /**
     * 租户标识（thirdparty）
     */
    @Excel(name = "租户标识")
    private String tenantKey;

    /**
     * Webhook地址（webhook）
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

    public String getFeishuType() {
        return feishuType;
    }

    public void setFeishuType(String feishuType) {
        this.feishuType = feishuType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTenantKey() {
        return tenantKey;
    }

    public void setTenantKey(String tenantKey) {
        this.tenantKey = tenantKey;
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