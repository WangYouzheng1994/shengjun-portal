package com.ruoyi.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * 文章对象 portal_article
 *
 * @author 王有政
 */
public class PortalArticle extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private Long categoryId;

    /**
     * 分类名称（非数据库字段，用于展示）
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 文章标题
     */
    @Excel(name = "文章标题")
    private String title;

    /**
     * 副标题
     */
    @Excel(name = "副标题")
    private String subtitle;

    /**
     * 文章摘要
     */
    @Excel(name = "文章摘要")
    private String summary;

    /**
     * 正文内容（富文本）
     */
    private String content;

    /**
     * 封面图片地址
     */
    @Excel(name = "封面图")
    private String coverImage;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 来源
     */
    @Excel(name = "来源")
    private String source;

    /**
     * 浏览次数
     */
    @Excel(name = "浏览次数")
    private Integer viewCount;

    /**
     * 是否置顶（0否 1是）
     */
    @Excel(name = "是否置顶", readConverterExp = "0=否,1=是")
    private String isTop;

    /**
     * 是否推荐（0否 1是）
     */
    @Excel(name = "是否推荐", readConverterExp = "0=否,1=是")
    private String isRecommend;

    /**
     * 状态（0草稿 1已发布 2已下架）
     */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=已发布,2=已下架")
    private String status;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 排序（升序）
     */
    @Excel(name = "排序")
    private Integer sortOrder;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleId() {
        return articleId;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() {
        return publishTime;
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
            .append("articleId", getArticleId())
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("title", getTitle())
            .append("subtitle", getSubtitle())
            .append("summary", getSummary())
            .append("content", getContent())
            .append("coverImage", getCoverImage())
            .append("author", getAuthor())
            .append("source", getSource())
            .append("viewCount", getViewCount())
            .append("isTop", getIsTop())
            .append("isRecommend", getIsRecommend())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("sortOrder", getSortOrder())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
