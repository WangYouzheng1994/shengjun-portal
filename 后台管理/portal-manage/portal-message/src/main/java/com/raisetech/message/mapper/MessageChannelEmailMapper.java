package com.raisetech.message.mapper;

import java.util.List;
import com.raisetech.message.domain.config.PortalMessageChannelEmail;

/**
 * 邮件消息配置Mapper接口
 *
 * @author 王有政
 */
public interface MessageChannelEmailMapper {
    public PortalMessageChannelEmail selectMessageChannelEmailByConfigId(Long configId);

    public PortalMessageChannelEmail selectMessageChannelEmailByChannelId(Long channelId);

    public List<PortalMessageChannelEmail> selectMessageChannelEmailList(PortalMessageChannelEmail email);

    public int insertMessageChannelEmail(PortalMessageChannelEmail email);

    public int updateMessageChannelEmail(PortalMessageChannelEmail email);

    public int deleteMessageChannelEmailByConfigId(Long configId);

    public int deleteMessageChannelEmailByChannelId(Long channelId);
}
