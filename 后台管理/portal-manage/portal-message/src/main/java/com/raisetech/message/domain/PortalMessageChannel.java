package com.raisetech.message.domain;

import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;

/**
 * 消息渠道配置对象 portal_message_channel
 *
 * @author 王有政
 */
public class PortalMessageChannel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道ID
     */
    private Long channelId;

    /**
     * 渠道类型（dingtalk/wecom/email/feishu）
     */
    @Excel(name = "渠道类型")
    private String channelType;

    /**
     * 渠道名称
     */
    @Excel(name = "渠道名称")
    private String channelName;

    /**
     * 是否启用（0否 1是）
     */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private String isEnabled;

    /**
     * 配置内容（JSON格式存储各渠道配置）
     */
    private String configContent;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortOrder;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getConfigContent() {
        return configContent;
    }

    public void setConfigContent(String configContent) {
        this.configContent = configContent;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
