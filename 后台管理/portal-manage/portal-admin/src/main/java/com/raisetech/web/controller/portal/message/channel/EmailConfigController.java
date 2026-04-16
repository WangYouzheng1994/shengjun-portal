package com.raisetech.web.controller.portal.message.channel;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.message.domain.config.PortalMessageChannelEmail;
import com.raisetech.message.service.IMessageChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 邮件消息配置Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/message/channel/email")
public class EmailConfigController extends BaseController {

    @Autowired
    private IMessageChannelService messageChannelService;

    /**
     * 查询邮件消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:email:query')")
    @GetMapping("/config")
    public AjaxResult getEmailConfig() {
        PortalMessageChannelEmail config = messageChannelService.selectEmailConfig();
        return success(config);
    }

    /**
     * 新增邮件消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:email:add')")
    @Log(title = "邮件消息配置", businessType = BusinessType.INSERT)
    @PostMapping("/config")
    public AjaxResult addEmailConfig(@RequestBody PortalMessageChannelEmail email) {
        return toAjax(messageChannelService.insertEmailConfig(email));
    }

    /**
     * 修改邮件消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:email:edit')")
    @Log(title = "邮件消息配置", businessType = BusinessType.UPDATE)
    @PutMapping("/config")
    public AjaxResult editEmailConfig(@RequestBody PortalMessageChannelEmail email) {
        return toAjax(messageChannelService.updateEmailConfig(email));
    }

    /**
     * 删除邮件消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:email:remove')")
    @Log(title = "邮件消息配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/config/{configId}")
    public AjaxResult removeEmailConfig(@PathVariable Long configId) {
        return toAjax(messageChannelService.deleteEmailConfigByConfigId(configId));
    }
}
