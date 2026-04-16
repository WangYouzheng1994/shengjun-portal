package com.raisetech.web.portal.controller.crm;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.portal.domain.PortalReplyTemplate;
import com.raisetech.portal.mapper.PortalReplyTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 回复模板管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/crm/template")
public class PortalReplyTemplateController extends BaseController {

    @Autowired
    private PortalReplyTemplateMapper portalReplyTemplateMapper;

    /**
     * 查询模板列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalReplyTemplate portalReplyTemplate) {
        startPage();
        List<PortalReplyTemplate> list = portalReplyTemplateMapper.selectPortalReplyTemplateList(portalReplyTemplate);
        return getDataTable(list);
    }

    /**
     * 获取模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId) {
        return success(portalReplyTemplateMapper.selectPortalReplyTemplateByTemplateId(templateId));
    }

    /**
     * 新增模板
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:add')")
    @Log(title = "回复模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalReplyTemplate portalReplyTemplate) {
        if (portalReplyTemplate.getUseCount() == null) {
            portalReplyTemplate.setUseCount(0);
        }
        return toAjax(portalReplyTemplateMapper.insertPortalReplyTemplate(portalReplyTemplate));
    }

    /**
     * 修改模板
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:edit')")
    @Log(title = "回复模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalReplyTemplate portalReplyTemplate) {
        return toAjax(portalReplyTemplateMapper.updatePortalReplyTemplate(portalReplyTemplate));
    }

    /**
     * 删除模板
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:template:remove')")
    @Log(title = "回复模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds) {
        return toAjax(portalReplyTemplateMapper.deletePortalReplyTemplateByTemplateIds(templateIds));
    }
}
