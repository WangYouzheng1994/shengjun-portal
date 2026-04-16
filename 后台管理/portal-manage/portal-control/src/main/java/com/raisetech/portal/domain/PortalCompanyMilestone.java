package com.raisetech.portal.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;

/**
 * 企业发展历程里程碑对象 portal_company_milestone
 *
 * @author 王有政
 */
public class PortalCompanyMilestone extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 里程碑ID
     */
    private Long milestoneId;

    /**
     * 企业ID（关联portal_company_info.info_id）
     */
    @Excel(name = "企业ID")
    private Long companyId;

    /**
     * 里程碑标题
     */
    @Excel(name = "里程碑标题")
    private String milestoneTitle;

    /**
     * 年份
     */
    @Excel(name = "年份")
    private Integer milestoneYear;

    /**
     * 月份（1-12）
     */
    @Excel(name = "月份")
    private Integer milestoneMonth;

    /**
     * 日期（1-31）
     */
    @Excel(name = "日期")
    private Integer milestoneDay;

    /**
     * 节点级别（1重大里程碑 2重要事件 3一般事件）
     */
    @Excel(name = "节点级别", readConverterExp = "1=重大里程碑,2=重要事件,3=一般事件")
    private String milestoneLevel;

    /**
     * 事件描述（富文本）
     */
    private String description;

    /**
     * 缩略图地址
     */
    private String thumbnailImage;

    /**
     * 高清大图地址
     */
    private String hdImage;

    /**
     * 链接类型（0无链接 1详情页 2内部路由 3外部链接）
     */
    @Excel(name = "链接类型", readConverterExp = "0=无链接,1=详情页,2=内部路由,3=外部链接")
    private String linkType;

    /**
     * 链接地址（路由路径或外链URL）
     */
    @Excel(name = "链接地址")
    private String linkUrl;

    /**
     * 详情页内容（富文本，link_type=1时使用）
     */
    private String linkContent;

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

    public void setMilestoneId(Long milestoneId) {
        this.milestoneId = milestoneId;
    }

    public Long getMilestoneId() {
        return milestoneId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setMilestoneTitle(String milestoneTitle) {
        this.milestoneTitle = milestoneTitle;
    }

    public String getMilestoneTitle() {
        return milestoneTitle;
    }

    public void setMilestoneYear(Integer milestoneYear) {
        this.milestoneYear = milestoneYear;
    }

    public Integer getMilestoneYear() {
        return milestoneYear;
    }

    public void setMilestoneMonth(Integer milestoneMonth) {
        this.milestoneMonth = milestoneMonth;
    }

    public Integer getMilestoneMonth() {
        return milestoneMonth;
    }

    public void setMilestoneDay(Integer milestoneDay) {
        this.milestoneDay = milestoneDay;
    }

    public Integer getMilestoneDay() {
        return milestoneDay;
    }

    public void setMilestoneLevel(String milestoneLevel) {
        this.milestoneLevel = milestoneLevel;
    }

    public String getMilestoneLevel() {
        return milestoneLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setThumbnailImage(String thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public void setHdImage(String hdImage) {
        this.hdImage = hdImage;
    }

    public String getHdImage() {
        return hdImage;
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

    public void setLinkContent(String linkContent) {
        this.linkContent = linkContent;
    }

    public String getLinkContent() {
        return linkContent;
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
            .append("milestoneId", getMilestoneId())
            .append("companyId", getCompanyId())
            .append("milestoneTitle", getMilestoneTitle())
            .append("milestoneYear", getMilestoneYear())
            .append("milestoneMonth", getMilestoneMonth())
            .append("milestoneDay", getMilestoneDay())
            .append("milestoneLevel", getMilestoneLevel())
            .append("description", getDescription())
            .append("thumbnailImage", getThumbnailImage())
            .append("hdImage", getHdImage())
            .append("linkType", getLinkType())
            .append("linkUrl", getLinkUrl())
            .append("linkContent", getLinkContent())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
