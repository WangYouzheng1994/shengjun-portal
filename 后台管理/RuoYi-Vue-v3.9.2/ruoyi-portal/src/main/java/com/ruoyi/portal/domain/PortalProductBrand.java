package com.ruoyi.portal.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.annotation.Excel;

/**
 * 产品品牌对象 portal_product_brand
 *
 * @author 王有政
 */
public class PortalProductBrand extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 品牌名称
     */
    @Excel(name = "品牌名称")
    private String brandName;

    /**
     * 品牌编码
     */
    @Excel(name = "品牌编码")
    private String brandCode;

    /**
     * 品牌LOGO
     */
    @Excel(name = "品牌LOGO")
    private String brandLogo;

    /**
     * 品牌图片
     */
    @Excel(name = "品牌图片")
    private String brandImage;

    /**
     * 品牌描述
     */
    @Excel(name = "品牌描述")
    private String description;

    /**
     * 品牌官网地址
     */
    @Excel(name = "官网地址")
    private String websiteUrl;

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

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandImage(String brandImage) {
        this.brandImage = brandImage;
    }

    public String getBrandImage() {
        return brandImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
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
            .append("brandId", getBrandId())
            .append("brandName", getBrandName())
            .append("brandCode", getBrandCode())
            .append("brandLogo", getBrandLogo())
            .append("brandImage", getBrandImage())
            .append("description", getDescription())
            .append("websiteUrl", getWebsiteUrl())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
