package com.ruoyi.message.service;

import java.util.List;

import com.ruoyi.message.domain.PortalMessageChannel;
import com.ruoyi.message.domain.config.PortalMessageChannelDingtalk;
import com.ruoyi.message.domain.config.PortalMessageChannelWecom;
import com.ruoyi.message.domain.config.PortalMessageChannelFeishu;
import com.ruoyi.message.domain.config.PortalMessageChannelEmail;

/**
 * 消息渠道配置Service接口
 *
 * @author 王有政
 */
public interface IMessageChannelService {
    /**
     * 查询消息渠道配置
     *
     * @param channelId 渠道ID
     * @return 消息渠道配置
     */
    public PortalMessageChannel selectMessageChannelByChannelId(Long channelId);

    /**
     * 根据渠道类型查询
     *
     * @param channelType 渠道类型
     * @return 消息渠道配置
     */
    public PortalMessageChannel selectMessageChannelByChannelType(String channelType);

    /**
     * 查询消息渠道配置列表
     *
     * @param channel 消息渠道配置
     * @return 消息渠道配置集合
     */
    public List<PortalMessageChannel> selectMessageChannelList(PortalMessageChannel channel);

    /**
     * 新增消息渠道配置
     *
     * @param channel 消息渠道配置
     * @return 结果
     */
    public int insertMessageChannel(PortalMessageChannel channel);

    /**
     * 修改消息渠道配置
     *
     * @param channel 消息渠道配置
     * @return 结果
     */
    public int updateMessageChannel(PortalMessageChannel channel);

    /**
     * 删除消息渠道配置
     *
     * @param channelId 渠道ID
     * @return 结果
     */
    public int deleteMessageChannelByChannelId(Long channelId);

    /**
     * 批量删除消息渠道配置
     *
     * @param channelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageChannelByChannelIds(Long[] channelIds);

    /**
     * 查询钉钉消息配置
     *
     * @return 钉钉消息配置
     */
    public PortalMessageChannelDingtalk selectDingtalkConfig();

    /**
     * 新增钉钉消息配置
     *
     * @param dingtalk 钉钉消息配置
     * @return 结果
     */
    public int insertDingtalkConfig(PortalMessageChannelDingtalk dingtalk);

    /**
     * 修改钉钉消息配置
     *
     * @param dingtalk 钉钉消息配置
     * @return 结果
     */
    public int updateDingtalkConfig(PortalMessageChannelDingtalk dingtalk);

    /**
     * 删除钉钉消息配置
     *
     * @param configId 配置ID
     * @return 结果
     */
    public int deleteDingtalkConfigByConfigId(Long configId);

    /**
     * 查询企业微信消息配置
     *
     * @return 企业微信消息配置
     */
    public PortalMessageChannelWecom selectWeComConfig();

    /**
     * 新增企业微信消息配置
     *
     * @param wecom 企业微信消息配置
     * @return 结果
     */
    public int insertWeComConfig(PortalMessageChannelWecom wecom);

    /**
     * 修改企业微信消息配置
     *
     * @param wecom 企业微信消息配置
     * @return 结果
     */
    public int updateWeComConfig(PortalMessageChannelWecom wecom);

    /**
     * 删除企业微信消息配置
     *
     * @param configId 配置ID
     * @return 结果
     */
    public int deleteWeComConfigByConfigId(Long configId);

    /**
     * 查询飞书消息配置
     *
     * @return 飞书消息配置
     */
    public PortalMessageChannelFeishu selectFeishuConfig();

    /**
     * 新增飞书消息配置
     *
     * @param feishu 飞书消息配置
     * @return 结果
     */
    public int insertFeishuConfig(PortalMessageChannelFeishu feishu);

    /**
     * 修改飞书消息配置
     *
     * @param feishu 飞书消息配置
     * @return 结果
     */
    public int updateFeishuConfig(PortalMessageChannelFeishu feishu);

    /**
     * 删除飞书消息配置
     *
     * @param configId 配置ID
     * @return 结果
     */
    public int deleteFeishuConfigByConfigId(Long configId);

    /**
     * 查询邮件消息配置
     *
     * @return 邮件消息配置
     */
    public PortalMessageChannelEmail selectEmailConfig();

    /**
     * 新增邮件消息配置
     *
     * @param email 邮件消息配置
     * @return 结果
     */
    public int insertEmailConfig(PortalMessageChannelEmail email);

    /**
     * 修改邮件消息配置
     *
     * @param email 邮件消息配置
     * @return 结果
     */
    public int updateEmailConfig(PortalMessageChannelEmail email);

    /**
     * 删除邮件消息配置
     *
     * @param configId 配置ID
     * @return 结果
     */
    public int deleteEmailConfigByConfigId(Long configId);
}