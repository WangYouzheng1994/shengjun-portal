package com.ruoyi.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.portal.domain.vo.PortalDashboardVO;
import com.ruoyi.portal.service.IPortalDashboardService;

/**
 * 门户Dashboard首页Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/dashboard")
public class PortalDashboardController extends BaseController {

    @Autowired
    private IPortalDashboardService portalDashboardService;

    /**
     * 获取门户数据概览
     */
    @PreAuthorize("@ss.hasPermi('portal:dashboard:query')")
    @GetMapping("/overview")
    public AjaxResult overview() {
        PortalDashboardVO dashboardVO = portalDashboardService.getDashboardOverview();
        return success(dashboardVO);
    }
}
