package com.ruoyi.message.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.message.domain.MessageResult;
import com.ruoyi.message.domain.PortalMessageSendLog;
import com.ruoyi.message.sender.MessageSender;
import com.ruoyi.message.sender.MessageSenderFactory;
import com.ruoyi.message.service.IMessageSendService;
import com.ruoyi.message.service.IMessageSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统一消息发送服务实现
 *
 * @author 王有政
 */
@Service
public class MessageSendServiceImpl implements IMessageSendService {

    private static final Logger log = LoggerFactory.getLogger(MessageSendServiceImpl.class);

    @Autowired
    private MessageSenderFactory messageSenderFactory;

    @Autowired
    private IMessageSendLogService messageSendLogService;

    @Override
    public MessageResult send(String channelType, String receiver, String title, String content) {
        MessageSender sender = messageSenderFactory.getSender(channelType);
        if (sender == null) {
            return MessageResult.fail("CHANNEL_NOT_FOUND", "未找到对应的消息渠道：" + channelType);
        }
        return sender.sendText(receiver, content);
    }

    @Override
    public MessageResult sendRichText(String channelType, String receiver, String title, String content, String url) {
        MessageSender sender = messageSenderFactory.getSender(channelType);
        if (sender == null) {
            return MessageResult.fail("CHANNEL_NOT_FOUND", "未找到对应的消息渠道：" + channelType);
        }
        return sender.sendRichText(receiver, title, content, url);
    }

    @Override
    public Long sendWithLog(String channelType, String receiver, String title, String content) {
        PortalMessageSendLog sendLog = new PortalMessageSendLog();
        sendLog.setChannelType(channelType);
        sendLog.setReceiver(receiver);
        sendLog.setTitle(title);
        sendLog.setContent(content);
        sendLog.setSendType("single");
        sendLog.setSendStatus("0");

        messageSendLogService.insertMessageSendLog(sendLog);

        MessageResult result = send(channelType, receiver, title, content);

        sendLog.setSendStatus(result.isSuccess() ? "1" : "2");
        sendLog.setSendTime(DateUtils.getNowDate());
        if (!result.isSuccess()) {
            sendLog.setErrorCode(result.getErrorCode());
            sendLog.setErrorMessage(result.getErrorMessage());
        }
        messageSendLogService.updateMessageSendLog(sendLog);

        return sendLog.getLogId();
    }

    @Override
    public Long sendRichTextWithLog(String channelType, String receiver, String title, String content, String url) {
        PortalMessageSendLog sendLog = new PortalMessageSendLog();
        sendLog.setChannelType(channelType);
        sendLog.setReceiver(receiver);
        sendLog.setTitle(title);
        sendLog.setContent(content);
        sendLog.setSendType("single");
        sendLog.setSendStatus("0");

        messageSendLogService.insertMessageSendLog(sendLog);

        MessageResult result = sendRichText(channelType, receiver, title, content, url);

        sendLog.setSendStatus(result.isSuccess() ? "1" : "2");
        sendLog.setSendTime(DateUtils.getNowDate());
        if (!result.isSuccess()) {
            sendLog.setErrorCode(result.getErrorCode());
            sendLog.setErrorMessage(result.getErrorMessage());
        }
        messageSendLogService.updateMessageSendLog(sendLog);

        return sendLog.getLogId();
    }
}
