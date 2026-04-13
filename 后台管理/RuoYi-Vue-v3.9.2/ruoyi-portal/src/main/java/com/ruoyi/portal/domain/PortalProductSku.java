package com.ruoyi.portal.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.annotation.Excel;

/**
 * 产品SKU对象 portal_product_sku
 *
 * @author 王有政
 */
public class PortalProductSku extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * SKU编码
     */
    @Excel(name = "SKU编码")
    private String skuCode;

    /**
     * SKU名称（如：红色-XL）
     */
    @Excel(name = "SKU名称")
    private String skuName;

    /**
     * SKU图片
     */
    @Excel(name = "SKU图片")
    private String skuImage;

    /**
     * SKU价格
     */
    @Excel(name = "SKU价格")
    private BigDecimal price;

    /**
     * SKU原价
     */
    @Excel(name = "SKU原价")
    private BigDecimal originalPrice;

    /**
     * SKU成本价
     */
    @Excel(name = "SKU成本价")
    private BigDecimal costPrice;

    /**
     * SKU库存数量
     */
    @Excel(name = "库存数量")
    private Integer stockQuantity;

    /**
     * SKU重量（kg）
     */
    @Excel(name = "SKU重量")
    private BigDecimal weight;

    /**
     * SKU属性组合（JSON格式）
     */
    private String skuAttrs;

    /**
     * 条形码
     */
    @Excel(name = "条形码")
    private String barCode;

    /**
     * 状态（0下架 1上架）
     */
    @Excel(name = "状态", readConverterExp = "0=下架,1=上架")
    private String status;

    /**
     * 是否默认SKU（0否 1是）
     */
    @Excel(name = "是否默认", readConverterExp = "0=否,1=是")
    private String isDefault;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setSkuAttrs(String skuAttrs) {
        this.skuAttrs = skuAttrs;
    }

    public String getSkuAttrs() {
        return skuAttrs;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDefault() {
        return isDefault;
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
            .append("skuId", getSkuId())
            .append("productId", getProductId())
            .append("skuCode", getSkuCode())
            .append("skuName", getSkuName())
            .append("price", getPrice())
            .append("stockQuantity", getStockQuantity())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
