package com.raisetech.web.controller.portal.message;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.message.domain.PortalMessageTemplate;
import com.raisetech.message.service.IMessageTemplateService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息模板Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/message/template")
public class MessageTemplateController extends BaseController {

    @Autowired
    private IMessageTemplateService messageTemplateService;

    /**
     * 查询消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('message:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalMessageTemplate template) {
        startPage();
        List<PortalMessageTemplate> list = messageTemplateService.selectMessageTemplateList(template);
        return getDataTable(list);
    }

    /**
     * 导出消息模板列表
     */
    @PreAuthorize("@ss.hasPermi('message:template:export')")
    @Log(title = "消息模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalMessageTemplate template) {
        List<PortalMessageTemplate> list = messageTemplateService.selectMessageTemplateList(template);
        ExcelUtil<PortalMessageTemplate> util = new ExcelUtil<>(PortalMessageTemplate.class);
        util.exportExcel(response, list, "消息模板数据");
    }

    /**
     * 获取消息模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId) {
        return success(messageTemplateService.selectMessageTemplateByTemplateId(templateId));
    }

    /**
     * 新增消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:add')")
    @Log(title = "消息模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody PortalMessageTemplate template) {
        if (template.getIsEnabled() == null) {
            template.setIsEnabled("1");
        }
        return success(messageTemplateService.insertMessageTemplate(template));
    }

    /**
     * 修改消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:edit')")
    @Log(title = "消息模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody PortalMessageTemplate template) {
        return success(messageTemplateService.updateMessageTemplate(template));
    }

    /**
     * 删除消息模板
     */
    @PreAuthorize("@ss.hasPermi('message:template:remove')")
    @Log(title = "消息模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds) {
        return success(messageTemplateService.deleteMessageTemplateByTemplateIds(templateIds));
    }
}
