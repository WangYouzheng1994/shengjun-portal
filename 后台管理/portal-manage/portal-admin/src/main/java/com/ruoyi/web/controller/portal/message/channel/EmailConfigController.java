package com.ruoyi.web.controller.portal.message.channel;

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
import com.ruoyi.message.domain.config.PortalMessageChannelEmail;
import com.ruoyi.message.service.IMessageChannelService;

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