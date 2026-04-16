package com.raisetech.portal.domain;

import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 产品属性定义对象 portal_product_attr_def
 *
 * @author 王有政
 */
public class PortalProductAttrDef extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 属性定义ID
     */
    private Long attrDefId;

    /**
     * 所属模板ID
     */
    @Excel(name = "模板ID")
    private Long templateId;

    /**
     * 属性名称
     */
    @Excel(name = "属性名称")
    private String attrName;

    /**
     * 属性编码
     */
    @Excel(name = "属性编码")
    private String attrCode;

    /**
     * 属性分组（规格参数/基本属性/详细信息等）
     */
    @Excel(name = "属性分组")
    private String attrGroup;

    /**
     * 属性类型（1文本 2数字 3单选 4多选 5日期 6布尔 7富文本 8图片 9附件）
     */
    @Excel(name = "属性类型")
    private Integer attrType;

    /**
     * 输入控件类型（input/select/checkbox/radio/textarea/date/switch等）
     */
    @Excel(name = "输入控件类型")
    private String inputType;

    /**
     * 选项值（JSON数组格式）
     */
    @Excel(name = "选项值")
    private String options;

    /**
     * 默认值
     */
    @Excel(name = "默认值")
    private String defaultValue;

    /**
     * 验证规则（正则表达式）
     */
    private String validationRule;

    /**
     * 单位（仅数字类型）
     */
    @Excel(name = "单位")
    private String unit;

    /**
     * 是否必填（0否 1是）
     */
    @Excel(name = "是否必填")
    private String isRequired;

    /**
     * 是否可搜索（0否 1是）
     */
    @Excel(name = "是否可搜索")
    private String isSearchable;

    /**
     * 是否可筛选（0否 1是）
     */
    @Excel(name = "是否可筛选")
    private String isFilterable;

    /**
     * 是否SKU属性（用于生成SKU组合）
     */
    @Excel(name = "是否SKU属性")
    private String isSkuAttr;

    /**
     * 是否在列表页显示（0否 1是）
     */
    @Excel(name = "列表显示")
    private String showInList;

    /**
     * 是否在详情页显示（0否 1是）
     */
    @Excel(name = "详情显示")
    private String showInDetail;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortOrder;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setAttrDefId(Long attrDefId) {
        this.attrDefId = attrDefId;
    }

    public Long getAttrDefId() {
        return attrDefId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrGroup(String attrGroup) {
        this.attrGroup = attrGroup;
    }

    public String getAttrGroup() {
        return attrGroup;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInputType() {
        return inputType;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getOptions() {
        return options;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setValidationRule(String validationRule) {
        this.validationRule = validationRule;
    }

    public String getValidationRule() {
        return validationRule;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setIsRequired(String isRequired) {
        this.isRequired = isRequired;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public void setIsSearchable(String isSearchable) {
        this.isSearchable = isSearchable;
    }

    public String getIsSearchable() {
        return isSearchable;
    }

    public void setIsFilterable(String isFilterable) {
        this.isFilterable = isFilterable;
    }

    public String getIsFilterable() {
        return isFilterable;
    }

    public void setIsSkuAttr(String isSkuAttr) {
        this.isSkuAttr = isSkuAttr;
    }

    public String getIsSkuAttr() {
        return isSkuAttr;
    }

    public void setShowInList(String showInList) {
        this.showInList = showInList;
    }

    public String getShowInList() {
        return showInList;
    }

    public void setShowInDetail(String showInDetail) {
        this.showInDetail = showInDetail;
    }

    public String getShowInDetail() {
        return showInDetail;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("attrDefId", getAttrDefId())
            .append("templateId", getTemplateId())
            .append("attrName", getAttrName())
            .append("attrCode", getAttrCode())
            .append("attrGroup", getAttrGroup())
            .append("attrType", getAttrType())
            .append("inputType", getInputType())
            .append("options", getOptions())
            .append("defaultValue", getDefaultValue())
            .append("unit", getUnit())
            .append("isRequired", getIsRequired())
            .append("isSearchable", getIsSearchable())
            .append("isFilterable", getIsFilterable())
            .append("isSkuAttr", getIsSkuAttr())
            .append("showInList", getShowInList())
            .append("showInDetail", getShowInDetail())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
