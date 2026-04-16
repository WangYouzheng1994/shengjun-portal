package com.raisetech.web.portal.controller.product;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.portal.domain.PortalProductAttrTemplate;
import com.raisetech.portal.service.IPortalProductAttrTemplateService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 产品属性模板管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/product/attrTemplate")
public class PortalProductAttrTemplateController extends BaseController {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(PortalProductAttrTemplateController.class);

    /**
     * 产品属性模板Service接口
     */
    @Autowired
    private IPortalProductAttrTemplateService portalProductAttrTemplateService;

    /**
     * 查询产品属性模板列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:attrTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalProductAttrTemplate portalProductAttrTemplate) {
        startPage();
        List<PortalProductAttrTemplate> list = portalProductAttrTemplateService.selectPortalProductAttrTemplateList(portalProductAttrTemplate);
        return getDataTable(list);
    }

    /**
     * 导出产品属性模板列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:attrTemplate:export')")
    @Log(title = "产品属性模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalProductAttrTemplate portalProductAttrTemplate) {
        List<PortalProductAttrTemplate> list = portalProductAttrTemplateService.selectPortalProductAttrTemplateList(portalProductAttrTemplate);
        ExcelUtil<PortalProductAttrTemplate> util = new ExcelUtil<PortalProductAttrTemplate>(PortalProductAttrTemplate.class);
        util.exportExcel(response, list, "产品属性模板");
    }

    /**
     * 获取产品属性模板详细信息（包含属性定义）
     */
    @PreAuthorize("@ss.hasPermi('portal:product:attrTemplate:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId) {
        return success(portalProductAttrTemplateService.selectPortalProductAttrTemplateByTemplateId(templateId));
    }

    /**
     * 新增产品属性模板
     */
    @PreAuthorize("@ss.hasPermi('portal:product:attrTemplate:add')")
    @Log(title = "产品属性模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalProductAttrTemplate portalProductAttrTemplate) {
        portalProductAttrTemplate.setCreateBy(getUsername());
        return toAjax(portalProductAttrTemplateService.insertPortalProductAttrTemplate(portalProductAttrTemplate));
    }

    /**
     * 修改产品属性模板
     */
    @PreAuthorize("@ss.hasPermi('portal:product:attrTemplate:edit')")
    @Log(title = "产品属性模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalProductAttrTemplate portalProductAttrTemplate) {
        portalProductAttrTemplate.setUpdateBy(getUsername());
        return toAjax(portalProductAttrTemplateService.updatePortalProductAttrTemplate(portalProductAttrTemplate));
    }

    /**
     * 删除产品属性模板
     */
    @PreAuthorize("@ss.hasPermi('portal:product:attrTemplate:remove')")
    @Log(title = "产品属性模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds) {
        return toAjax(portalProductAttrTemplateService.deletePortalProductAttrTemplateByTemplateIds(templateIds));
    }
}
