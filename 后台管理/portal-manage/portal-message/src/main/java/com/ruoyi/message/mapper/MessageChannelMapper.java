package com.ruoyi.message.mapper;

import java.util.List;
import com.ruoyi.message.domain.PortalMessageChannel;

/**
 * 消息渠道配置Mapper接口
 *
 * @author 王有政
 */
public interface MessageChannelMapper {
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
}
