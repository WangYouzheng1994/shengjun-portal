package com.raisetech.message.domain;

import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;

/**
 * 消息模板对象 portal_message_template
 *
 * @author 王有政
 */
public class PortalMessageTemplate extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
    private Long templateId;

    /**
     * 模板编码
     */
    @Excel(name = "模板编码")
    private String templateCode;

    /**
     * 模板名称
     */
    @Excel(name = "模板名称")
    private String templateName;

    /**
     * 渠道类型（dingtalk/wecom/feishu/email）
     */
    @Excel(name = "渠道类型")
    private String channelType;

    /**
     * 模板类型（notification/reminder/alert）
     */
    @Excel(name = "模板类型")
    private String templateType;

    /**
     * 标题模板
     */
    @Excel(name = "标题模板")
    private String titleTemplate;

    /**
     * 内容模板（支持变量占位符${xxx}）
     */
    @Excel(name = "内容模板")
    private String contentTemplate;

    /**
     * 是否启用（0否 1是）
     */
    @Excel(name = "是否启用", readConverterExp = "0=否,1=是")
    private String isEnabled;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTitleTemplate() {
        return titleTemplate;
    }

    public void setTitleTemplate(String titleTemplate) {
        this.titleTemplate = titleTemplate;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public void setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}