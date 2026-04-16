package com.ruoyi.message.mapper;

import java.util.List;
import com.ruoyi.message.domain.config.PortalMessageChannelWecom;

/**
 * 企业微信消息配置Mapper接口
 *
 * @author 王有政
 */
public interface MessageChannelWecomMapper {
    public PortalMessageChannelWecom selectMessageChannelWecomByConfigId(Long configId);

    public PortalMessageChannelWecom selectMessageChannelWecomByChannelId(Long channelId);

    public List<PortalMessageChannelWecom> selectMessageChannelWecomList(PortalMessageChannelWecom wecom);

    public int insertMessageChannelWecom(PortalMessageChannelWecom wecom);

    public int updateMessageChannelWecom(PortalMessageChannelWecom wecom);

    public int deleteMessageChannelWecomByConfigId(Long configId);

    public int deleteMessageChannelWecomByChannelId(Long channelId);
}
