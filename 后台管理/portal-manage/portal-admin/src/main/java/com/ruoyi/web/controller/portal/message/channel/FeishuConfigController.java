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
import com.ruoyi.message.domain.config.PortalMessageChannelFeishu;
import com.ruoyi.message.service.IMessageChannelService;

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