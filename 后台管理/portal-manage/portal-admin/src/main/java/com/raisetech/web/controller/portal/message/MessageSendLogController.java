package com.raisetech.web.controller.portal.message;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.message.domain.PortalMessageSendLog;
import com.raisetech.message.service.IMessageSendLogService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息发送记录Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/message/sendLog")
public class MessageSendLogController extends BaseController {

    @Autowired
    private IMessageSendLogService messageSendLogService;

    /**
     * 查询消息发送记录列表
     */
    @PreAuthorize("@ss.hasPermi('message:sendLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalMessageSendLog log) {
        startPage();
        List<PortalMessageSendLog> list = messageSendLogService.selectMessageSendLogList(log);
        return getDataTable(list);
    }

    /**
     * 导出消息发送记录列表
     */
    @PreAuthorize("@ss.hasPermi('message:sendLog:export')")
    @Log(title = "消息发送记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalMessageSendLog log) {
        List<PortalMessageSendLog> list = messageSendLogService.selectMessageSendLogList(log);
        ExcelUtil<PortalMessageSendLog> util = new ExcelUtil<PortalMessageSendLog>(PortalMessageSendLog.class);
        util.exportExcel(response, list, "消息发送记录数据");
    }

    /**
     * 获取消息发送记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('message:sendLog:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId) {
        return success(messageSendLogService.selectMessageSendLogByLogId(logId));
    }

    /**
     * 删除消息发送记录
     */
    @PreAuthorize("@ss.hasPermi('message:sendLog:remove')")
    @Log(title = "消息发送记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable Long[] logIds) {
        return toAjax(messageSendLogService.deleteMessageSendLogByLogIds(logIds));
    }
}
