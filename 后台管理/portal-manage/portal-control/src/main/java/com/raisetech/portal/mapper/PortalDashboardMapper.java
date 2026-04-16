package com.raisetech.portal.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 门户Dashboard统计Mapper接口
 *
 * @author 王有政
 */
public interface PortalDashboardMapper {

    /**
     * 获取指定日期的访问统计数据
     *
     * @param statDate 统计日期
     * @return 统计数据
     */
    public Map<String, Object> selectVisitStatisticsByDate(@Param("statDate") String statDate);

    /**
     * 获取近N天的访问趋势数据
     *
     * @param days 天数
     * @return 趋势数据列表
     */
    public List<Map<String, Object>> selectLastDaysTrend(@Param("days") int days);

    /**
     * 获取本月文章浏览总量
     *
     * @return 浏览量
     */
    public Integer selectMonthArticleViews();

    /**
     * 获取本月产品查阅总量（从访问日志表统计）
     *
     * @return 查阅量
     */
    public Integer selectMonthProductViews();

    /**
     * 获取待处理留言数量
     *
     * @return 数量
     */
    public Integer selectPendingMessageCount();

    /**
     * 获取已发布文章总数
     *
     * @return 数量
     */
    public Integer selectTotalArticles();

    /**
     * 获取上架产品总数
     *
     * @return 数量
     */
    public Integer selectTotalProducts();

    /**
     * 获取客户总数
     *
     * @return 数量
     */
    public Integer selectTotalCustomers();
}
