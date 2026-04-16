package com.raisetech.portal.domain;

import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品分类对象 portal_product_category
 *
 * @author 王有政
 */
public class PortalProductCategory extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 分类编码
     */
    @Excel(name = "分类编码")
    private String categoryCode;

    /**
     * 父级ID（0为顶级）
     */
    @Excel(name = "父级ID")
    private Long parentId;

    /**
     * 祖级列表（如: 0,1,2）
     */
    @Excel(name = "祖级列表")
    private String ancestors;

    /**
     * 分类图标
     */
    @Excel(name = "分类图标")
    private String icon;

    /**
     * 分类图片
     */
    @Excel(name = "分类图片")
    private String image;

    /**
     * 关键词（SEO）
     */
    @Excel(name = "关键词")
    private String keywords;

    /**
     * 分类描述
     */
    @Excel(name = "分类描述")
    private String description;

    /**
     * 绑定的属性模板ID
     */
    @Excel(name = "属性模板ID")
    private Long templateId;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
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

    /**
     * 子分类列表
     */
    private List<PortalProductCategory> children = new ArrayList<PortalProductCategory>();

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
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

    public void setChildren(List<PortalProductCategory> children) {
        this.children = children;
    }

    public List<PortalProductCategory> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("categoryCode", getCategoryCode())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("icon", getIcon())
            .append("image", getImage())
            .append("keywords", getKeywords())
            .append("description", getDescription())
            .append("templateId", getTemplateId())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
