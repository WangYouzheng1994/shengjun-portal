package com.ruoyi.portal.controller.crm;

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
import com.ruoyi.portal.domain.PortalMessage;
import com.ruoyi.portal.service.IPortalMessageService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.portal.domain.PortalCustomer;

/**
 * 官网消息通知Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/crm/message")
public class PortalMessageController extends BaseController {

    @Autowired
    private IPortalMessageService portalMessageService;

    /**
     * 查询消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalMessage portalMessage) {
        startPage();
        List<PortalMessage> list = portalMessageService.selectPortalMessageList(portalMessage);
        return getDataTable(list);
    }

    /**
     * 导出消息通知列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:export')")
    @Log(title = "消息通知", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalMessage portalMessage) {
        List<PortalMessage> list = portalMessageService.selectPortalMessageList(portalMessage);
        ExcelUtil<PortalMessage> util = new ExcelUtil<PortalMessage>(PortalMessage.class);
        util.exportExcel(response, list, "消息通知数据");
    }

    /**
     * 获取消息通知详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:query')")
    @GetMapping(value = "/{messageId}")
    public AjaxResult getInfo(@PathVariable("messageId") Long messageId) {
        return success(portalMessageService.selectPortalMessageByMessageId(messageId));
    }

    /**
     * 新增消息通知（前台提交）
     */
    @Log(title = "消息通知", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalMessage portalMessage) {
        return toAjax(portalMessageService.insertPortalMessage(portalMessage));
    }

    /**
     * 回复消息通知
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:reply')")
    @Log(title = "消息通知回复", businessType = BusinessType.UPDATE)
    @PutMapping("/reply")
    public AjaxResult reply(@RequestBody PortalMessage portalMessage) {
        if (portalMessage.getMessageId() == null) {
            return error("消息通知ID不能为空");
        }
        portalMessage.setHandleStatus("2");
        portalMessage.setReplyTime(new java.util.Date());
        return toAjax(portalMessageService.updatePortalMessage(portalMessage));
    }

    /**
     * 修改消息通知
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:edit')")
    @Log(title = "消息通知", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalMessage portalMessage) {
        return toAjax(portalMessageService.updatePortalMessage(portalMessage));
    }

    /**
     * 将消息通知转换为客户
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:convert')")
    @Log(title = "消息通知转客户", businessType = BusinessType.UPDATE)
    @PostMapping("/convert/{messageId}")
    public AjaxResult convertToCustomer(
            @PathVariable("messageId") Long messageId,
            @RequestBody PortalCustomer customerInfo) {
        return portalMessageService.convertToCustomer(messageId, customerInfo);
    }

    /**
     * 删除消息通知
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:message:remove')")
    @Log(title = "消息通知", businessType = BusinessType.DELETE)
    @DeleteMapping("/{messageIds}")
    public AjaxResult remove(@PathVariable Long[] messageIds) {
        return toAjax(portalMessageService.deletePortalMessageByMessageIds(messageIds));
    }
}
