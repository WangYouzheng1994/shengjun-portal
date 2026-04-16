package com.ruoyi.message.domain.config;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 钉钉消息配置对象 portal_message_channel_dingtalk
 *
 * @author 王有政
 */
public class PortalMessageChannelDingtalk extends BaseEntity {
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
     * 对接方式：internal企业内部应用/robot群机器人/isv第三方
     */
    @Excel(name = "对接方式")
    private String dingtalkType;

    /**
     * 企业ID
     */
    @Excel(name = "企业ID")
    private String corpId;

    /**
     * 应用AppKey（internal/isv）
     */
    @Excel(name = "应用AppKey")
    private String appKey;

    /**
     * 应用AppSecret（internal/isv）
     */
    private String appSecret;

    /**
     * 应用AgentID（internal）
     */
    @Excel(name = "应用AgentID")
    private String agentId;

    /**
     * Webhook地址（robot）
     */
    @Excel(name = "Webhook地址")
    private String webhookUrl;

    /**
     * 加签密钥（robot）
     */
    private String secretKey;

    /**
     * 套件ID（isv）
     */
    @Excel(name = "套件ID")
    private String suiteId;

    /**
     * 套件Secret（isv）
     */
    private String suiteSecret;

    /**
     * 调用凭证（isv）
     */
    private String token;

    /**
     * 加解密密钥（isv）
     */
    private String encodingAesKey;

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

    public String getDingtalkType() {
        return dingtalkType;
    }

    public void setDingtalkType(String dingtalkType) {
        this.dingtalkType = dingtalkType;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public void setWebhookUrl(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(String suiteId) {
        this.suiteId = suiteId;
    }

    public String getSuiteSecret() {
        return suiteSecret;
    }

    public void setSuiteSecret(String suiteSecret) {
        this.suiteSecret = suiteSecret;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}