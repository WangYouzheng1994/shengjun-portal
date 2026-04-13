package com.ruoyi.portal.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 产品属性值对象 portal_product_attr_value
 *
 * @author 王有政
 */
public class PortalProductAttrValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 属性值ID
     */
    private Long valueId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 属性定义ID
     */
    private Long attrDefId;

    /**
     * 属性名称（非数据库字段，用于展示）
     */
    private String attrName;

    /**
     * 属性值（原始值）
     */
    private String attrValue;

    /**
     * 属性值文本（用于搜索）
     */
    private String attrValueText;

    /**
     * 属性值数值（仅数字类型）
     */
    private BigDecimal attrValueNum;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setValueId(Long valueId) {
        this.valueId = valueId;
    }

    public Long getValueId() {
        return valueId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setAttrDefId(Long attrDefId) {
        this.attrDefId = attrDefId;
    }

    public Long getAttrDefId() {
        return attrDefId;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValueText(String attrValueText) {
        this.attrValueText = attrValueText;
    }

    public String getAttrValueText() {
        return attrValueText;
    }

    public void setAttrValueNum(BigDecimal attrValueNum) {
        this.attrValueNum = attrValueNum;
    }

    public BigDecimal getAttrValueNum() {
        return attrValueNum;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
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
            .append("valueId", getValueId())
            .append("productId", getProductId())
            .append("attrDefId", getAttrDefId())
            .append("attrValue", getAttrValue())
            .append("attrValueText", getAttrValueText())
            .append("attrValueNum", getAttrValueNum())
            .append("sortOrder", getSortOrder())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
