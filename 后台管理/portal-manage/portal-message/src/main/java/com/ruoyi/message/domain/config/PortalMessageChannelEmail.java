package com.ruoyi.message.domain.config;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 邮件消息配置对象 portal_message_channel_email
 *
 * @author 王有政
 */
public class PortalMessageChannelEmail extends BaseEntity {
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
     * SMTP服务器
     */
    @Excel(name = "SMTP服务器")
    private String smtpHost;

    /**
     * SMTP端口
     */
    @Excel(name = "SMTP端口")
    private Integer smtpPort;

    /**
     * 用户名
     */
    @Excel(name = "用户名")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 发件人地址
     */
    @Excel(name = "发件人地址")
    private String fromAddress;

    /**
     * 发件人名称
     */
    @Excel(name = "发件人名称")
    private String fromName;

    /**
     * 是否使用SSL
     */
    @Excel(name = "是否使用SSL", readConverterExp = "0=否,1=是")
    private String useSsl;

    /**
     * 是否使用TLS
     */
    @Excel(name = "是否使用TLS", readConverterExp = "0=否,1=是")
    private String useTls;

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

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public Integer getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(Integer smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getUseSsl() {
        return useSsl;
    }

    public void setUseSsl(String useSsl) {
        this.useSsl = useSsl;
    }

    public String getUseTls() {
        return useTls;
    }

    public void setUseTls(String useTls) {
        this.useTls = useTls;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
