package com.raisetech.web.controller.portal.message.channel;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.message.domain.config.PortalMessageChannelDingtalk;
import com.raisetech.message.service.IMessageChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 钉钉消息配置Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/message/channel/dingtalk")
public class DingTalkConfigController extends BaseController {

    @Autowired
    private IMessageChannelService messageChannelService;

    /**
     * 查询钉钉消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:dingtalk:query')")
    @GetMapping("/config")
    public AjaxResult getDingtalkConfig() {
        PortalMessageChannelDingtalk config = messageChannelService.selectDingtalkConfig();
        return success(config);
    }

    /**
     * 新增钉钉消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:dingtalk:add')")
    @Log(title = "钉钉消息配置", businessType = BusinessType.INSERT)
    @PostMapping("/config")
    public AjaxResult addDingtalkConfig(@RequestBody PortalMessageChannelDingtalk dingtalk) {
        return toAjax(messageChannelService.insertDingtalkConfig(dingtalk));
    }

    /**
     * 修改钉钉消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:dingtalk:edit')")
    @Log(title = "钉钉消息配置", businessType = BusinessType.UPDATE)
    @PutMapping("/config")
    public AjaxResult editDingtalkConfig(@RequestBody PortalMessageChannelDingtalk dingtalk) {
        return toAjax(messageChannelService.updateDingtalkConfig(dingtalk));
    }

    /**
     * 删除钉钉消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:dingtalk:remove')")
    @Log(title = "钉钉消息配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/config/{configId}")
    public AjaxResult removeDingtalkConfig(@PathVariable Long configId) {
        return toAjax(messageChannelService.deleteDingtalkConfigByConfigId(configId));
    }
}
