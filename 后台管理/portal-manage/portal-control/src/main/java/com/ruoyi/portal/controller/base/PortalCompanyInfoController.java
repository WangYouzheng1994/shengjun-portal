package com.ruoyi.portal.controller.base;

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
import com.ruoyi.portal.domain.PortalCompanyInfo;
import com.ruoyi.portal.service.IPortalCompanyInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

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
}
