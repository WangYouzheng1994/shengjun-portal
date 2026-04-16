package com.raisetech.message.mapper;

import java.util.List;
import com.raisetech.message.domain.config.PortalMessageChannelDingtalk;

/**
 * 钉钉消息配置Mapper接口
 *
 * @author 王有政
 */
public interface MessageChannelDingtalkMapper {
    /**
     * 查询钉钉消息配置
     *
     * @param configId 配置ID
     * @return 钉钉消息配置
     */
    public PortalMessageChannelDingtalk selectMessageChannelDingtalkByConfigId(Long configId);

    /**
     * 根据渠道ID查询钉钉消息配置
     *
     * @param channelId 渠道ID
     * @return 钉钉消息配置
     */
    public PortalMessageChannelDingtalk selectMessageChannelDingtalkByChannelId(Long channelId);

    /**
     * 查询钉钉消息配置列表
     *
     * @param dingtalk 钉钉消息配置
     * @return 钉钉消息配置集合
     */
    public List<PortalMessageChannelDingtalk> selectMessageChannelDingtalkList(PortalMessageChannelDingtalk dingtalk);

    /**
     * 新增钉钉消息配置
     *
     * @param dingtalk 钉钉消息配置
     * @return 结果
     */
    public int insertMessageChannelDingtalk(PortalMessageChannelDingtalk dingtalk);

    /**
     * 修改钉钉消息配置
     *
     * @param dingtalk 钉钉消息配置
     * @return 结果
     */
    public int updateMessageChannelDingtalk(PortalMessageChannelDingtalk dingtalk);

    /**
     * 删除钉钉消息配置
     *
     * @param configId 配置ID
     * @return 结果
     */
    public int deleteMessageChannelDingtalkByConfigId(Long configId);

    /**
     * 删除钉钉消息配置
     *
     * @param channelId 渠道ID
     * @return 结果
     */
    public int deleteMessageChannelDingtalkByChannelId(Long channelId);
}
