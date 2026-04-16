package com.raisetech.web.portal.controller.base;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.portal.domain.PortalBanner;
import com.raisetech.portal.service.IPortalBannerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 轮播图配置Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/base/banner")
public class PortalBannerController extends BaseController {

    @Autowired
    private IPortalBannerService portalBannerService;

    /**
     * 查询轮播图配置列表
     */
    @PreAuthorize("@ss.hasPermi('portal:base:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalBanner portalBanner) {
        startPage();
        List<PortalBanner> list = portalBannerService.selectPortalBannerList(portalBanner);
        return getDataTable(list);
    }

    /**
     * 导出轮播图配置列表
     */
    @PreAuthorize("@ss.hasPermi('portal:base:banner:export')")
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
    @PreAuthorize("@ss.hasPermi('portal:base:banner:query')")
    @GetMapping(value = "/{bannerId}")
    public AjaxResult getInfo(@PathVariable("bannerId") Long bannerId) {
        return success(portalBannerService.selectPortalBannerByBannerId(bannerId));
    }

    /**
     * 新增轮播图配置
     */
    @PreAuthorize("@ss.hasPermi('portal:base:banner:add')")
    @Log(title = "轮播图配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalBanner portalBanner) {
        return toAjax(portalBannerService.insertPortalBanner(portalBanner));
    }

    /**
     * 修改轮播图配置
     */
    @PreAuthorize("@ss.hasPermi('portal:base:banner:edit')")
    @Log(title = "轮播图配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalBanner portalBanner) {
        return toAjax(portalBannerService.updatePortalBanner(portalBanner));
    }

    /**
     * 删除轮播图配置
     */
    @PreAuthorize("@ss.hasPermi('portal:base:banner:remove')")
    @Log(title = "轮播图配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bannerIds}")
    public AjaxResult remove(@PathVariable Long[] bannerIds) {
        return toAjax(portalBannerService.deletePortalBannerByBannerIds(bannerIds));
    }
}
