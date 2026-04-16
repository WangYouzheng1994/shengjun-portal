package com.raisetech.message.service;

import com.raisetech.message.domain.MessageResult;

/**
 * 统一消息发送服务接口
 *
 * @author 王有政
 */
public interface IMessageSendService {

    /**
     * 发送文本消息
     *
     * @param channelType 渠道类型
     * @param receiver 接收者
     * @param title 标题
     * @param content 内容
     * @return 发送结果
     */
    MessageResult send(String channelType, String receiver, String title, String content);

    /**
     * 发送富文本消息
     *
     * @param channelType 渠道类型
     * @param receiver 接收者
     * @param title 标题
     * @param content 内容
     * @param url 点击跳转链接
     * @return 发送结果
     */
    MessageResult sendRichText(String channelType, String receiver, String title, String content, String url);

    /**
     * 发送消息并记录日志
     *
     * @param channelType 渠道类型
     * @param receiver 接收者
     * @param title 标题
     * @param content 内容
     * @return 发送日志ID
     */
    Long sendWithLog(String channelType, String receiver, String title, String content);

    /**
     * 发送富文本消息并记录日志
     *
     * @param channelType 渠道类型
     * @param receiver 接收者
     * @param title 标题
     * @param content 内容
     * @param url 点击跳转链接
     * @return 发送日志ID
     */
    Long sendRichTextWithLog(String channelType, String receiver, String title, String content, String url);
}
