package com.raisetech.web.controller.portal.message.channel;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.message.domain.config.PortalMessageChannelFeishu;
import com.raisetech.message.service.IMessageChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 飞书消息配置Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/message/channel/feishu")
public class FeishuConfigController extends BaseController {

    @Autowired
    private IMessageChannelService messageChannelService;

    /**
     * 查询飞书消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:feishu:query')")
    @GetMapping("/config")
    public AjaxResult getFeishuConfig() {
        PortalMessageChannelFeishu config = messageChannelService.selectFeishuConfig();
        return success(config);
    }

    /**
     * 新增飞书消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:feishu:add')")
    @Log(title = "飞书消息配置", businessType = BusinessType.INSERT)
    @PostMapping("/config")
    public AjaxResult addFeishuConfig(@RequestBody PortalMessageChannelFeishu feishu) {
        return toAjax(messageChannelService.insertFeishuConfig(feishu));
    }

    /**
     * 修改飞书消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:feishu:edit')")
    @Log(title = "飞书消息配置", businessType = BusinessType.UPDATE)
    @PutMapping("/config")
    public AjaxResult editFeishuConfig(@RequestBody PortalMessageChannelFeishu feishu) {
        return toAjax(messageChannelService.updateFeishuConfig(feishu));
    }

    /**
     * 删除飞书消息配置
     */
    @PreAuthorize("@ss.hasPermi('message:channel:feishu:remove')")
    @Log(title = "飞书消息配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/config/{configId}")
    public AjaxResult removeFeishuConfig(@PathVariable Long configId) {
        return toAjax(messageChannelService.deleteFeishuConfigByConfigId(configId));
    }
}
