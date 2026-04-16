package com.raisetech.message.service.impl;

import java.util.List;
import com.raisetech.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.message.mapper.MessageChannelMapper;
import com.raisetech.message.mapper.MessageChannelDingtalkMapper;
import com.raisetech.message.mapper.MessageChannelWecomMapper;
import com.raisetech.message.mapper.MessageChannelFeishuMapper;
import com.raisetech.message.mapper.MessageChannelEmailMapper;
import com.raisetech.message.domain.PortalMessageChannel;
import com.raisetech.message.domain.config.PortalMessageChannelDingtalk;
import com.raisetech.message.domain.config.PortalMessageChannelWecom;
import com.raisetech.message.domain.config.PortalMessageChannelFeishu;
import com.raisetech.message.domain.config.PortalMessageChannelEmail;
import com.raisetech.message.service.IMessageChannelService;

/**
 * 消息渠道配置Service业务层处理
 *
 * @author 王有政
 */
@Service
public class MessageChannelServiceImpl implements IMessageChannelService {

    @Autowired
    private MessageChannelMapper messageChannelMapper;

    @Autowired
    private MessageChannelDingtalkMapper dingtalkMapper;

    @Autowired
    private MessageChannelWecomMapper wecomMapper;

    @Autowired
    private MessageChannelFeishuMapper feishuMapper;

    @Autowired
    private MessageChannelEmailMapper emailMapper;

    @Override
    public PortalMessageChannel selectMessageChannelByChannelId(Long channelId) {
        return messageChannelMapper.selectMessageChannelByChannelId(channelId);
    }

    @Override
    public PortalMessageChannel selectMessageChannelByChannelType(String channelType) {
        return messageChannelMapper.selectMessageChannelByChannelType(channelType);
    }

    @Override
    public List<PortalMessageChannel> selectMessageChannelList(PortalMessageChannel channel) {
        return messageChannelMapper.selectMessageChannelList(channel);
    }

    @Override
    public int insertMessageChannel(PortalMessageChannel channel) {
        channel.setCreateTime(DateUtils.getNowDate());
        return messageChannelMapper.insertMessageChannel(channel);
    }

    @Override
    public int updateMessageChannel(PortalMessageChannel channel) {
        channel.setUpdateTime(DateUtils.getNowDate());
        return messageChannelMapper.updateMessageChannel(channel);
    }

    @Override
    public int deleteMessageChannelByChannelId(Long channelId) {
        return messageChannelMapper.deleteMessageChannelByChannelId(channelId);
    }

    @Override
    public int deleteMessageChannelByChannelIds(Long[] channelIds) {
        return messageChannelMapper.deleteMessageChannelByChannelIds(channelIds);
    }

    @Override
    public PortalMessageChannelDingtalk selectDingtalkConfig() {
        List<PortalMessageChannelDingtalk> list = dingtalkMapper.selectMessageChannelDingtalkList(new PortalMessageChannelDingtalk());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertDingtalkConfig(PortalMessageChannelDingtalk dingtalk) {
        dingtalk.setCreateTime(DateUtils.getNowDate());
        dingtalk.setDelFlag("0");
        return dingtalkMapper.insertMessageChannelDingtalk(dingtalk);
    }

    @Override
    public int updateDingtalkConfig(PortalMessageChannelDingtalk dingtalk) {
        dingtalk.setUpdateTime(DateUtils.getNowDate());
        return dingtalkMapper.updateMessageChannelDingtalk(dingtalk);
    }

    @Override
    public int deleteDingtalkConfigByConfigId(Long configId) {
        return dingtalkMapper.deleteMessageChannelDingtalkByConfigId(configId);
    }

    @Override
    public PortalMessageChannelWecom selectWeComConfig() {
        List<PortalMessageChannelWecom> list = wecomMapper.selectMessageChannelWecomList(new PortalMessageChannelWecom());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertWeComConfig(PortalMessageChannelWecom wecom) {
        wecom.setCreateTime(DateUtils.getNowDate());
        wecom.setDelFlag("0");
        return wecomMapper.insertMessageChannelWecom(wecom);
    }

    @Override
    public int updateWeComConfig(PortalMessageChannelWecom wecom) {
        wecom.setUpdateTime(DateUtils.getNowDate());
        return wecomMapper.updateMessageChannelWecom(wecom);
    }

    @Override
    public int deleteWeComConfigByConfigId(Long configId) {
        return wecomMapper.deleteMessageChannelWecomByConfigId(configId);
    }

    @Override
    public PortalMessageChannelFeishu selectFeishuConfig() {
        List<PortalMessageChannelFeishu> list = feishuMapper.selectMessageChannelFeishuList(new PortalMessageChannelFeishu());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertFeishuConfig(PortalMessageChannelFeishu feishu) {
        feishu.setCreateTime(DateUtils.getNowDate());
        feishu.setDelFlag("0");
        return feishuMapper.insertMessageChannelFeishu(feishu);
    }

    @Override
    public int updateFeishuConfig(PortalMessageChannelFeishu feishu) {
        feishu.setUpdateTime(DateUtils.getNowDate());
        return feishuMapper.updateMessageChannelFeishu(feishu);
    }

    @Override
    public int deleteFeishuConfigByConfigId(Long configId) {
        return feishuMapper.deleteMessageChannelFeishuByConfigId(configId);
    }

    @Override
    public PortalMessageChannelEmail selectEmailConfig() {
        List<PortalMessageChannelEmail> list = emailMapper.selectMessageChannelEmailList(new PortalMessageChannelEmail());
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insertEmailConfig(PortalMessageChannelEmail email) {
        email.setCreateTime(DateUtils.getNowDate());
        email.setDelFlag("0");
        return emailMapper.insertMessageChannelEmail(email);
    }

    @Override
    public int updateEmailConfig(PortalMessageChannelEmail email) {
        email.setUpdateTime(DateUtils.getNowDate());
        return emailMapper.updateMessageChannelEmail(email);
    }

    @Override
    public int deleteEmailConfigByConfigId(Long configId) {
        return emailMapper.deleteMessageChannelEmailByConfigId(configId);
    }
}