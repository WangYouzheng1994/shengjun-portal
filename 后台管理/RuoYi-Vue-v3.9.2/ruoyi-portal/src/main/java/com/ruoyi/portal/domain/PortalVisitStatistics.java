package com.ruoyi.portal.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 门户访问统计对象 portal_visit_statistics
 *
 * @author 王有政
 */
public class PortalVisitStatistics extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 统计ID
     */
    private Long statId;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date statDate;

    /**
     * 总访问量
     */
    private Integer totalVisits;

    /**
     * 文章浏览量
     */
    private Integer articleViews;

    /**
     * 产品查阅量
     */
    private Integer productViews;

    /**
     * 独立访客数
     */
    private Integer uniqueVisitors;

    public void setStatId(Long statId) {
        this.statId = statId;
    }

    public Long getStatId() {
        return statId;
    }

    public void setStatDate(Date statDate) {
        this.statDate = statDate;
    }

    public Date getStatDate() {
        return statDate;
    }

    public void setTotalVisits(Integer totalVisits) {
        this.totalVisits = totalVisits;
    }

    public Integer getTotalVisits() {
        return totalVisits;
    }

    public void setArticleViews(Integer articleViews) {
        this.articleViews = articleViews;
    }

    public Integer getArticleViews() {
        return articleViews;
    }

    public void setProductViews(Integer productViews) {
        this.productViews = productViews;
    }

    public Integer getProductViews() {
        return productViews;
    }

    public void setUniqueVisitors(Integer uniqueVisitors) {
        this.uniqueVisitors = uniqueVisitors;
    }

    public Integer getUniqueVisitors() {
        return uniqueVisitors;
    }
}
