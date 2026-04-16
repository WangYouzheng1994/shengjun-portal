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
import com.ruoyi.message.domain.config.PortalMessageChannelDingtalk;
import com.ruoyi.message.service.IMessageChannelService;

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