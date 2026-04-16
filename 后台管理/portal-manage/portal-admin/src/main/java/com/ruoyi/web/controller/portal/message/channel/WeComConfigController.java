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
import com.ruoyi.message.domain.config.PortalMessageChannelWecom;
import com.ruoyi.message.service.IMessageChannelService;

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