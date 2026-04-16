package com.raisetech.message.wecom;

import com.raisetech.message.domain.MessageResult;

/**
 * 企业微信消息服务接口
 *
 * @author 王有政
 */
public interface WeComService {

    /**
     * 发送文本消息
     *
     * @param user 用户ID（多个用|分隔）
     * @param content 消息内容
     * @return 发送结果
     */
    MessageResult sendText(String user, String content);

    /**
     * 发送文本卡片消息
     *
     * @param user 用户ID（多个用|分隔）
     * @param title 标题
     * @param description 描述（支持HTML标签）
     * @param url 点击跳转URL
     * @return 发送结果
     */
    MessageResult sendTextCard(String user, String title, String description, String url);

    /**
     * 发送Markdown消息
     *
     * @param user 用户ID（多个用|分隔）
     * @param content Markdown内容
     * @return 发送结果
     */
    MessageResult sendMarkdown(String user, String content);
}
