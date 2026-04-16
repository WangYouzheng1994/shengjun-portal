package com.raisetech.web.portal.controller.base;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.portal.domain.PortalCompanyInfo;
import com.raisetech.portal.domain.PortalOfficeLocation;
import com.raisetech.portal.service.IPortalCompanyInfoService;
import com.raisetech.portal.service.IPortalOfficeLocationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 企业基础信息Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/base/company")
public class PortalCompanyInfoController extends BaseController {

    @Autowired
    private IPortalCompanyInfoService portalCompanyInfoService;

    @Autowired
    private IPortalOfficeLocationService portalOfficeLocationService;

    /**
     * 查询企业基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalCompanyInfo portalCompanyInfo) {
        startPage();
        List<PortalCompanyInfo> list = portalCompanyInfoService.selectPortalCompanyInfoList(portalCompanyInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:export')")
    @Log(title = "企业基础信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalCompanyInfo portalCompanyInfo) {
        List<PortalCompanyInfo> list = portalCompanyInfoService.selectPortalCompanyInfoList(portalCompanyInfo);
        ExcelUtil<PortalCompanyInfo> util = new ExcelUtil<PortalCompanyInfo>(PortalCompanyInfo.class);
        util.exportExcel(response, list, "企业基础信息数据");
    }

    /**
     * 获取企业基础信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:query')")
    @GetMapping(value = "/{infoId}")
    public AjaxResult getInfo(@PathVariable("infoId") Long infoId) {
        return success(portalCompanyInfoService.selectPortalCompanyInfoByInfoId(infoId));
    }

    /**
     * 新增企业基础信息
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:add')")
    @Log(title = "企业基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalCompanyInfo portalCompanyInfo) {
        return toAjax(portalCompanyInfoService.insertPortalCompanyInfo(portalCompanyInfo));
    }

    /**
     * 修改企业基础信息
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:edit')")
    @Log(title = "企业基础信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalCompanyInfo portalCompanyInfo) {
        return toAjax(portalCompanyInfoService.updatePortalCompanyInfo(portalCompanyInfo));
    }

    /**
     * 删除企业基础信息
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:remove')")
    @Log(title = "企业基础信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    public AjaxResult remove(@PathVariable Long[] infoIds) {
        return toAjax(portalCompanyInfoService.deletePortalCompanyInfoByInfoIds(infoIds));
    }

    // ==================== 办公点管理接口 ====================

    /**
     * 查询企业下的所有办公点
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:query')")
    @GetMapping("/office/list/{infoId}")
    public AjaxResult listOfficeLocations(@PathVariable("infoId") Long infoId) {
        List<PortalOfficeLocation> list = portalOfficeLocationService.selectPortalOfficeLocationByInfoId(infoId);
        return success(list);
    }

    /**
     * 获取办公点详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:query')")
    @GetMapping("/office/{locationId}")
    public AjaxResult getOfficeLocation(@PathVariable("locationId") Long locationId) {
        return success(portalOfficeLocationService.selectPortalOfficeLocationByLocationId(locationId));
    }

    /**
     * 新增办公点
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:add')")
    @Log(title = "企业办公点", businessType = BusinessType.INSERT)
    @PostMapping("/office")
    public AjaxResult addOffice(@RequestBody PortalOfficeLocation portalOfficeLocation) {
        return toAjax(portalOfficeLocationService.insertPortalOfficeLocation(portalOfficeLocation));
    }

    /**
     * 修改办公点
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:edit')")
    @Log(title = "企业办公点", businessType = BusinessType.UPDATE)
    @PutMapping("/office")
    public AjaxResult editOffice(@RequestBody PortalOfficeLocation portalOfficeLocation) {
        return toAjax(portalOfficeLocationService.updatePortalOfficeLocation(portalOfficeLocation));
    }

    /**
     * 删除办公点
     */
    @PreAuthorize("@ss.hasPermi('portal:base:company:remove')")
    @Log(title = "企业办公点", businessType = BusinessType.DELETE)
    @DeleteMapping("/office/{locationIds}")
    public AjaxResult removeOffice(@PathVariable Long[] locationIds) {
        return toAjax(portalOfficeLocationService.deletePortalOfficeLocationByLocationIds(locationIds));
    }
}
