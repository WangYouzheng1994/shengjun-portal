package com.ruoyi.message.mapper;

import java.util.List;
import com.ruoyi.message.domain.config.PortalMessageChannelFeishu;

/**
 * 飞书消息配置Mapper接口
 *
 * @author 王有政
 */
public interface MessageChannelFeishuMapper {
    public PortalMessageChannelFeishu selectMessageChannelFeishuByConfigId(Long configId);

    public PortalMessageChannelFeishu selectMessageChannelFeishuByChannelId(Long channelId);

    public List<PortalMessageChannelFeishu> selectMessageChannelFeishuList(PortalMessageChannelFeishu feishu);

    public int insertMessageChannelFeishu(PortalMessageChannelFeishu feishu);

    public int updateMessageChannelFeishu(PortalMessageChannelFeishu feishu);

    public int deleteMessageChannelFeishuByConfigId(Long configId);

    public int deleteMessageChannelFeishuByChannelId(Long channelId);
}
