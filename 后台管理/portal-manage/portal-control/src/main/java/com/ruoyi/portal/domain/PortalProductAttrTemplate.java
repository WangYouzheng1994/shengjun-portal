package com.ruoyi.portal.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * 产品属性模板对象 portal_product_attr_template
 *
 * @author 王有政
 */
public class PortalProductAttrTemplate extends BaseEntity {
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
     * 模板编码
     */
    @Excel(name = "模板编码")
    private String templateCode;

    /**
     * 模板描述
     */
    @Excel(name = "模板描述")
    private String description;

    /**
     * 是否默认模板（0否 1是）
     */
    @Excel(name = "是否默认", readConverterExp = "0=否,1=是")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    /**
     * 属性定义列表
     */
    private List<PortalProductAttrDef> attrDefs;

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

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setAttrDefs(List<PortalProductAttrDef> attrDefs) {
        this.attrDefs = attrDefs;
    }

    public List<PortalProductAttrDef> getAttrDefs() {
        return attrDefs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("templateId", getTemplateId())
            .append("templateName", getTemplateName())
            .append("templateCode", getTemplateCode())
            .append("description", getDescription())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
