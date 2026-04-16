package com.raisetech.web.controller.portal.message.channel;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.message.domain.config.PortalMessageChannelWecom;
import com.raisetech.message.service.IMessageChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 企业微信消息配置Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/message/channel/wecom")
public class WeComConfigController extends BaseController {

    @Autowired
    private IMessageChannelService messageChannelService;

    /**
     * 查询企业微信消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:wecom:query')")
    @GetMapping("/config")
    public AjaxResult getWeComConfig() {
        PortalMessageChannelWecom config = messageChannelService.selectWeComConfig();
        return success(config);
    }

    /**
     * 新增企业微信消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:wecom:add')")
    @Log(title = "企业微信消息配置", businessType = BusinessType.INSERT)
    @PostMapping("/config")
    public AjaxResult addWeComConfig(@RequestBody PortalMessageChannelWecom wecom) {
        return toAjax(messageChannelService.insertWeComConfig(wecom));
    }

    /**
     * 修改企业微信消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:wecom:edit')")
    @Log(title = "企业微信消息配置", businessType = BusinessType.UPDATE)
    @PutMapping("/config")
    public AjaxResult editWeComConfig(@RequestBody PortalMessageChannelWecom wecom) {
        return toAjax(messageChannelService.updateWeComConfig(wecom));
    }

    /**
     * 删除企业微信消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:wecom:remove')")
    @Log(title = "企业微信消息配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/config/{configId}")
    public AjaxResult removeWeComConfig(@PathVariable Long configId) {
        return toAjax(messageChannelService.deleteWeComConfigByConfigId(configId));
    }
}
