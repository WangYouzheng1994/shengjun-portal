package com.ruoyi.message.sender;

import com.ruoyi.message.domain.MessageResult;

/**
 * 消息发送策略接口
 *
 * @author 王有政
 */
public interface MessageSender {

    /**
     * 获取渠道类型
     *
     * @return 渠道类型编码
     */
    String getChannelType();

    /**
     * 发送文本消息
     *
     * @param receiver 接收者
     * @param content 消息内容
     * @return 发送结果
     */
    MessageResult sendText(String receiver, String content);

    /**
     * 发送富文本消息
     *
     * @param receiver 接收者
     * @param title 标题
     * @param content 内容
     * @param url 点击跳转链接
     * @return 发送结果
     */
    MessageResult sendRichText(String receiver, String title, String content, String url);
}
