package com.ruoyi.portal.controller.crm;

import java.util.List;

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
import com.ruoyi.portal.domain.PortalMessageTemplate;
import com.ruoyi.portal.mapper.PortalMessageTemplateMapper;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 回复模板管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/crm/template")
public class PortalMessageTemplateController extends BaseController {

    @Autowired
    private PortalMessageTemplateMapper portalMessageTemplateMapper;

    /**
     * 查询模板列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalMessageTemplate portalMessageTemplate) {
        startPage();
        List<PortalMessageTemplate> list = portalMessageTemplateMapper.selectPortalMessageTemplateList(portalMessageTemplate);
        return getDataTable(list);
    }

    /**
     * 获取模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId) {
        return success(portalMessageTemplateMapper.selectPortalMessageTemplateByTemplateId(templateId));
    }

    /**
     * 新增模板
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:add')")
    @Log(title = "回复模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalMessageTemplate portalMessageTemplate) {
        if (portalMessageTemplate.getUseCount() == null) {
            portalMessageTemplate.setUseCount(0);
        }
        return toAjax(portalMessageTemplateMapper.insertPortalMessageTemplate(portalMessageTemplate));
    }

    /**
     * 修改模板
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:edit')")
    @Log(title = "回复模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalMessageTemplate portalMessageTemplate) {
        return toAjax(portalMessageTemplateMapper.updatePortalMessageTemplate(portalMessageTemplate));
    }

    /**
     * 删除模板
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:remove')")
    @Log(title = "回复模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds) {
        return toAjax(portalMessageTemplateMapper.deletePortalMessageTemplateByTemplateIds(templateIds));
    }
}
