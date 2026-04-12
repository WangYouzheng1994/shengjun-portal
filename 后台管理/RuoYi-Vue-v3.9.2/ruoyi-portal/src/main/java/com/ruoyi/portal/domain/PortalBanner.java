package com.ruoyi.portal.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.annotation.Excel;

/**
 * 轮播图配置对象 portal_banner
 *
 * @author 王有政
 */
public class PortalBanner extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 轮播图ID
     */
    private Long bannerId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * PC端图片地址
     */
    @Excel(name = "PC端图片")
    private String pcImage;

    /**
     * 移动端图片地址
     */
    @Excel(name = "移动端图片")
    private String mobileImage;

    /**
     * PC端图片宽度
     */
    private Integer pcWidth;

    /**
     * PC端图片高度
     */
    private Integer pcHeight;

    /**
     * 移动端图片宽度
     */
    private Integer mobileWidth;

    /**
     * 移动端图片高度
     */
    private Integer mobileHeight;

    /**
     * 跳转链接类型（0无跳转 1内部链接 2外部链接）
     */
    @Excel(name = "链接类型", readConverterExp = "0=无跳转,1=内部链接,2=外部链接")
    private String linkType;

    /**
     * 跳转链接地址
     */
    @Excel(name = "跳转链接")
    private String linkUrl;

    /**
     * 排序（升序）
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

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setBannerId(Long bannerId) {
        this.bannerId = bannerId;
    }

    public Long getBannerId() {
        return bannerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setPcImage(String pcImage) {
        this.pcImage = pcImage;
    }

    public String getPcImage() {
        return pcImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    public String getMobileImage() {
        return mobileImage;
    }

    public void setPcWidth(Integer pcWidth) {
        this.pcWidth = pcWidth;
    }

    public Integer getPcWidth() {
        return pcWidth;
    }

    public void setPcHeight(Integer pcHeight) {
        this.pcHeight = pcHeight;
    }

    public Integer getPcHeight() {
        return pcHeight;
    }

    public void setMobileWidth(Integer mobileWidth) {
        this.mobileWidth = mobileWidth;
    }

    public Integer getMobileWidth() {
        return mobileWidth;
    }

    public void setMobileHeight(Integer mobileHeight) {
        this.mobileHeight = mobileHeight;
    }

    public Integer getMobileHeight() {
        return mobileHeight;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("bannerId", getBannerId())
            .append("title", getTitle())
            .append("pcImage", getPcImage())
            .append("mobileImage", getMobileImage())
            .append("pcWidth", getPcWidth())
            .append("pcHeight", getPcHeight())
            .append("mobileWidth", getMobileWidth())
            .append("mobileHeight", getMobileHeight())
            .append("linkType", getLinkType())
            .append("linkUrl", getLinkUrl())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
