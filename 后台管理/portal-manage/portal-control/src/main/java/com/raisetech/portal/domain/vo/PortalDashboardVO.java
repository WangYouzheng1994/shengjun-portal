package com.raisetech.portal.domain.vo;

import java.util.List;
import java.util.Map;

/**
 * 门户Dashboard数据VO
 *
 * @author 王有政
 */
public class PortalDashboardVO {

    /**
     * 今日访问量
     */
    private Integer todayVisits;

    /**
     * 昨日访问量
     */
    private Integer yesterdayVisits;

    /**
     * 本月文章浏览总量
     */
    private Integer monthArticleViews;

    /**
     * 本月产品查阅总量
     */
    private Integer monthProductViews;

    /**
     * 待处理留言数量
     */
    private Integer pendingMessageCount;

    /**
     * 总文章数
     */
    private Integer totalArticles;

    /**
     * 总产品数
     */
    private Integer totalProducts;

    /**
     * 总客户数
     */
    private Integer totalCustomers;

    /**
     * 近七天访问趋势数据
     */
    private List<Map<String, Object>> trendData;

    public void setTodayVisits(Integer todayVisits) {
        this.todayVisits = todayVisits;
    }

    public Integer getTodayVisits() {
        return todayVisits;
    }

    public void setYesterdayVisits(Integer yesterdayVisits) {
        this.yesterdayVisits = yesterdayVisits;
    }

    public Integer getYesterdayVisits() {
        return yesterdayVisits;
    }

    public void setMonthArticleViews(Integer monthArticleViews) {
        this.monthArticleViews = monthArticleViews;
    }

    public Integer getMonthArticleViews() {
        return monthArticleViews;
    }

    public void setMonthProductViews(Integer monthProductViews) {
        this.monthProductViews = monthProductViews;
    }

    public Integer getMonthProductViews() {
        return monthProductViews;
    }

    public void setPendingMessageCount(Integer pendingMessageCount) {
        this.pendingMessageCount = pendingMessageCount;
    }

    public Integer getPendingMessageCount() {
        return pendingMessageCount;
    }

    public void setTotalArticles(Integer totalArticles) {
        this.totalArticles = totalArticles;
    }

    public Integer getTotalArticles() {
        return totalArticles;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public void setTotalCustomers(Integer totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Integer getTotalCustomers() {
        return totalCustomers;
    }

    public void setTrendData(List<Map<String, Object>> trendData) {
        this.trendData = trendData;
    }

    public List<Map<String, Object>> getTrendData() {
        return trendData;
    }
}
