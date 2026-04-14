package com.ruoyi.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 产品对象 portal_product
 *
 * @author 王有政
 */
public class PortalProduct extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 产品编码
     */
    @Excel(name = "产品编码")
    private String productCode;

    /**
     * 产品名称
     */
    @Excel(name = "产品名称")
    private String productName;

    /**
     * 所属分类ID
     */
    @Excel(name = "分类ID")
    private Long categoryId;

    /**
     * 分类名称（非数据库字段，用于展示）
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 品牌ID
     */
    @Excel(name = "品牌ID")
    private Long brandId;

    /**
     * 品牌名称（非数据库字段，用于展示）
     */
    @Excel(name = "品牌名称")
    private String brandName;

    /**
     * 主图（大图）
     */
    @Excel(name = "主图")
    private String mainImage;

    /**
     * 缩略图（小图）
     */
    @Excel(name = "缩略图")
    private String thumbnailImage;

    /**
     * 视频地址
     */
    @Excel(name = "视频地址")
    private String videoUrl;

    /**
     * 副标题
     */
    @Excel(name = "副标题")
    private String subTitle;

    /**
     * 产品摘要
     */
    @Excel(name = "产品摘要")
    private String summary;

    /**
     * 详细介绍（富文本）
     */
    private String description;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 原价
     */
    @Excel(name = "原价")
    private BigDecimal originalPrice;

    /**
     * 成本价
     */
    @Excel(name = "成本价")
    private BigDecimal costPrice;

    /**
     * 计量单位
     */
    @Excel(name = "计量单位")
    private String unit;

    /**
     * 重量（kg）
     */
    @Excel(name = "重量")
    private BigDecimal weight;

    /**
     * 体积（长x宽x高）
     */
    @Excel(name = "体积")
    private String volume;

    /**
     * 销量
     */
    @Excel(name = "销量")
    private Integer salesCount;

    /**
     * 浏览次数
     */
    @Excel(name = "浏览次数")
    private Integer viewCount;

    /**
     * 收藏次数
     */
    @Excel(name = "收藏次数")
    private Integer collectCount;

    /**
     * 评论数
     */
    @Excel(name = "评论数")
    private Integer commentCount;

    /**
     * SKU数量
     */
    @Excel(name = "SKU数量")
    private Integer skuCount;

    /**
     * 是否推荐（0否 1是）
     */
    @Excel(name = "是否推荐", readConverterExp = "0=否,1=是")
    private String isRecommend;

    /**
     * 是否新品（0否 1是）
     */
    @Excel(name = "是否新品", readConverterExp = "0=否,1=是")
    private String isNew;

    /**
     * 是否热销（0否 1是）
     */
    @Excel(name = "是否热销", readConverterExp = "0=否,1=是")
    private String isHot;

    /**
     * 状态（0下架 1上架 2草稿）
     */
    @Excel(name = "状态", readConverterExp = "0=下架,1=上架,2=草稿")
    private String status;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sortOrder;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    /**
     * 产品属性值列表（非数据库字段）
     */
    private List<PortalProductAttrValue> attrValueList;

    /**
     * 产品SKU列表（非数据库字段）
     */
    private List<PortalProductSku> skuList;

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

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

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getVolume() {
        return volume;
    }

    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }

    public Integer getSalesCount() {
        return salesCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setAttrValueList(List<PortalProductAttrValue> attrValueList) {
        this.attrValueList = attrValueList;
    }

    public List<PortalProductAttrValue> getAttrValueList() {
        return attrValueList;
    }

    public void setSkuList(List<PortalProductSku> skuList) {
        this.skuList = skuList;
    }

    public List<PortalProductSku> getSkuList() {
        return skuList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("productId", getProductId())
            .append("productCode", getProductCode())
            .append("productName", getProductName())
            .append("categoryId", getCategoryId())
            .append("brandId", getBrandId())
            .append("mainImage", getMainImage())
            .append("price", getPrice())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
