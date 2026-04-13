package com.ruoyi.portal.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.portal.domain.vo.PortalDashboardVO;
import com.ruoyi.portal.mapper.PortalDashboardMapper;
import com.ruoyi.portal.service.IPortalDashboardService;

/**
 * 门户Dashboard统计Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalDashboardServiceImpl implements IPortalDashboardService {

    @Autowired
    private PortalDashboardMapper portalDashboardMapper;

    /**
     * 获取Dashboard概览数据
     *
     * @return Dashboard数据VO
     */
    @Override
    public PortalDashboardVO getDashboardOverview() {
        PortalDashboardVO vo = new PortalDashboardVO();

        String today = DateUtils.getDate();
        String yesterday = DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.addDays(DateUtils.getNowDate(), -1));

        Map<String, Object> todayStat = portalDashboardMapper.selectVisitStatisticsByDate(today);
        if (todayStat != null) {
            vo.setTodayVisits(todayStat.get("totalVisits") != null ? ((Number) todayStat.get("totalVisits")).intValue() : 0);
        } else {
            vo.setTodayVisits(0);
        }

        Map<String, Object> yesterdayStat = portalDashboardMapper.selectVisitStatisticsByDate(yesterday);
        if (yesterdayStat != null) {
            vo.setYesterdayVisits(yesterdayStat.get("totalVisits") != null ? ((Number) yesterdayStat.get("totalVisits")).intValue() : 0);
        } else {
            vo.setYesterdayVisits(0);
        }

        Integer monthArticleViews = portalDashboardMapper.selectMonthArticleViews();
        vo.setMonthArticleViews(monthArticleViews != null ? monthArticleViews : 0);

        Integer monthProductViews = portalDashboardMapper.selectMonthProductViews();
        vo.setMonthProductViews(monthProductViews != null ? monthProductViews : 0);

        Integer pendingMessageCount = portalDashboardMapper.selectPendingMessageCount();
        vo.setPendingMessageCount(pendingMessageCount != null ? pendingMessageCount : 0);

        Integer totalArticles = portalDashboardMapper.selectTotalArticles();
        vo.setTotalArticles(totalArticles != null ? totalArticles : 0);

        Integer totalProducts = portalDashboardMapper.selectTotalProducts();
        vo.setTotalProducts(totalProducts != null ? totalProducts : 0);

        Integer totalCustomers = portalDashboardMapper.selectTotalCustomers();
        vo.setTotalCustomers(totalCustomers != null ? totalCustomers : 0);

        List<Map<String, Object>> trendData = portalDashboardMapper.selectLastDaysTrend(7);
        if (trendData == null || trendData.isEmpty()) {
            trendData = generateDefaultTrendData(7);
        } else {
            Collections.reverse(trendData);
        }
        vo.setTrendData(trendData);

        return vo;
    }

    /**
     * 生成默认趋势数据（当没有真实数据时使用）
     *
     * @param days 天数
     * @return 默认趋势数据
     */
    private List<Map<String, Object>> generateDefaultTrendData(int days) {
        List<Map<String, Object>> defaultData = new ArrayList<>();
        for (int i = days - 1; i >= 0; i--) {
            Map<String, Object> dayData = new HashMap<>();
            String dateStr = DateUtils.parseDateToStr("yyyy-MM-dd", DateUtils.addDays(DateUtils.getNowDate(), -i));
            String dateLabel = dateStr.substring(5);
            dayData.put("statDate", dateStr);
            dayData.put("dateLabel", dateLabel);
            dayData.put("totalVisits", 0);
            dayData.put("articleViews", 0);
            dayData.put("productViews", 0);
            dayData.put("uniqueVisitors", 0);
            defaultData.add(dayData);
        }
        return defaultData;
    }
}
