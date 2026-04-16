package com.raisetech.message.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息发送工厂类
 *
 * @author 王有政
 */
@Component
public class MessageSenderFactory {

    @Autowired
    private List<MessageSender> messageSenders;

    private final Map<String, MessageSender> senderMap = new ConcurrentHashMap<>();

    public MessageSender getSender(String channelType) {
        if (senderMap.isEmpty()) {
            synchronized (this) {
                if (senderMap.isEmpty()) {
                    for (MessageSender sender : messageSenders) {
                        senderMap.put(sender.getChannelType(), sender);
                    }
                }
            }
        }
        return senderMap.get(channelType);
    }
}
