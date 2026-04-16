package com.ruoyi.web.portal.controller.base;

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
import com.ruoyi.portal.domain.PortalCompanyHonor;
import com.ruoyi.portal.service.IPortalCompanyHonorService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业荣誉墙Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/base/honor")
public class PortalCompanyHonorController extends BaseController {

    @Autowired
    private IPortalCompanyHonorService portalCompanyHonorService;

    /**
     * 查询企业荣誉墙列表
     */
    @PreAuthorize("@ss.hasPermi('portal:base:honor:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalCompanyHonor portalCompanyHonor) {
        startPage();
        List<PortalCompanyHonor> list = portalCompanyHonorService.selectPortalCompanyHonorList(portalCompanyHonor);
        return getDataTable(list);
    }

    /**
     * 导出企业荣誉墙列表
     */
    @PreAuthorize("@ss.hasPermi('portal:base:honor:export')")
    @Log(title = "企业荣誉墙", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalCompanyHonor portalCompanyHonor) {
        List<PortalCompanyHonor> list = portalCompanyHonorService.selectPortalCompanyHonorList(portalCompanyHonor);
        ExcelUtil<PortalCompanyHonor> util = new ExcelUtil<PortalCompanyHonor>(PortalCompanyHonor.class);
        util.exportExcel(response, list, "企业荣誉墙数据");
    }

    /**
     * 获取企业荣誉墙详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:base:honor:query')")
    @GetMapping(value = "/{honorId}")
    public AjaxResult getInfo(@PathVariable("honorId") Long honorId) {
        return success(portalCompanyHonorService.selectPortalCompanyHonorByHonorId(honorId));
    }

    /**
     * 新增企业荣誉墙
     */
    @PreAuthorize("@ss.hasPermi('portal:base:honor:add')")
    @Log(title = "企业荣誉墙", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalCompanyHonor portalCompanyHonor) {
        return toAjax(portalCompanyHonorService.insertPortalCompanyHonor(portalCompanyHonor));
    }

    /**
     * 修改企业荣誉墙
     */
    @PreAuthorize("@ss.hasPermi('portal:base:honor:edit')")
    @Log(title = "企业荣誉墙", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalCompanyHonor portalCompanyHonor) {
        return toAjax(portalCompanyHonorService.updatePortalCompanyHonor(portalCompanyHonor));
    }

    /**
     * 删除企业荣誉墙
     */
    @PreAuthorize("@ss.hasPermi('portal:base:honor:remove')")
    @Log(title = "企业荣誉墙", businessType = BusinessType.DELETE)
    @DeleteMapping("/{honorIds}")
    public AjaxResult remove(@PathVariable Long[] honorIds) {
        return toAjax(portalCompanyHonorService.deletePortalCompanyHonorByHonorIds(honorIds));
    }
}
