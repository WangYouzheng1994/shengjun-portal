package com.ruoyi.portal.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.portal.domain.PortalBanner;
import com.ruoyi.portal.service.IPortalBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 轮播图配置Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/banner")
public class PortalBannerController extends BaseController {

    @Autowired
    private IPortalBannerService portalBannerService;

    /**
     * 查询轮播图配置列表
     */
    @PreAuthorize("@ss.hasPermi('portal:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalBanner portalBanner) {
        startPage();
        List<PortalBanner> list = portalBannerService.selectPortalBannerList(portalBanner);
        return getDataTable(list);
    }

    /**
     * 导出轮播图配置列表
     */
    @PreAuthorize("@ss.hasPermi('portal:banner:export')")
    @Log(title = "轮播图配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalBanner portalBanner) {
        List<PortalBanner> list = portalBannerService.selectPortalBannerList(portalBanner);
        ExcelUtil<PortalBanner> util = new ExcelUtil<PortalBanner>(PortalBanner.class);
        util.exportExcel(response, list, "轮播图配置数据");
    }

    /**
     * 获取轮播图配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:banner:query')")
    @GetMapping(value = "/{bannerId}")
    public AjaxResult getInfo(@PathVariable("bannerId") Long bannerId) {
        return success(portalBannerService.selectPortalBannerByBannerId(bannerId));
    }

    /**
     * 新增轮播图配置
     */
    @PreAuthorize("@ss.hasPermi('portal:banner:add')")
    @Log(title = "轮播图配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalBanner portalBanner) {
        return toAjax(portalBannerService.insertPortalBanner(portalBanner));
    }

    /**
     * 修改轮播图配置
     */
    @PreAuthorize("@ss.hasPermi('portal:banner:edit')")
    @Log(title = "轮播图配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalBanner portalBanner) {
        return toAjax(portalBannerService.updatePortalBanner(portalBanner));
    }

    /**
     * 删除轮播图配置
     */
    @PreAuthorize("@ss.hasPermi('portal:banner:remove')")
    @Log(title = "轮播图配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bannerIds}")
    public AjaxResult remove(@PathVariable Long[] bannerIds) {
        return toAjax(portalBannerService.deletePortalBannerByBannerIds(bannerIds));
    }
}
