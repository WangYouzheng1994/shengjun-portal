package com.ruoyi.portal.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.annotation.Excel;

/**
 * 留言回复模板对象 portal_message_template
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
     * 模板名称
     */
    @Excel(name = "模板名称")
    private String templateName;

    /**
     * 模板内容（支持变量：{name} {phone} {date}等）
     */
    private String templateContent;

    /**
     * 模板类型（0通用 1产品咨询 2合作洽谈 3投诉建议）
     */
    @Excel(name = "模板类型", readConverterExp = "0=通用,1=产品咨询,2=合作洽谈,3=投诉建议")
    private String templateType;

    /**
     * 状态（0正常 1停用）
     */
    private String status;

    /**
     * 使用次数
     */
    @Excel(name = "使用次数")
    private Integer useCount;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
}
