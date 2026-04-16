package com.raisetech.message.sender.impl;

import com.raisetech.message.domain.MessageResult;
import com.raisetech.message.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 飞书消息发送策略实现
 *
 * @author 王有政
 */
@Component
public class FeishuSender implements MessageSender {

    private static final Logger log = LoggerFactory.getLogger(FeishuSender.class);

    @Override
    public String getChannelType() {
        return "feishu";
    }

    @Override
    public MessageResult sendText(String receiver, String content) {
        log.info("飞书发送功能待实现，接收者：{}，内容：{}", receiver, content);
        return MessageResult.fail("NOT_IMPLEMENTED", "飞书发送功能待实现");
    }

    @Override
    public MessageResult sendRichText(String receiver, String title, String content, String url) {
        log.info("飞书发送功能待实现，接收者：{}，标题：{}，内容：{}，链接：{}", receiver, title, content, url);
        return MessageResult.fail("NOT_IMPLEMENTED", "飞书发送功能待实现");
    }
}
