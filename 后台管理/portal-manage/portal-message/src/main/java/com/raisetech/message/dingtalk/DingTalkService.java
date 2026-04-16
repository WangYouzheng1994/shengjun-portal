package com.raisetech.message.dingtalk;

import com.raisetech.message.domain.MessageResult;

/**
 * 钉钉消息服务接口
 *
 * @author 王有政
 */
public interface DingTalkService {

    /**
     * 发送工作通知（文本消息）
     *
     * @param userIdList 用户ID列表（多个用逗号分隔）
     * @param title 消息标题
     * @param content 消息内容
     * @return 发送结果
     */
    MessageResult sendWorkNotice(String userIdList, String title, String content);

    /**
     * 发送工作通知（Markdown消息）
     *
     * @param userIdList 用户ID列表（多个用逗号分隔）
     * @param title 消息标题
     * @param markdown Markdown格式内容
     * @return 发送结果
     */
    MessageResult sendWorkNoticeMarkdown(String userIdList, String title, String markdown);

    /**
     * 发送工作通知给全部用户
     *
     * @param title 消息标题
     * @param content 消息内容
     * @return 发送结果
     */
    MessageResult sendWorkNoticeToAll(String title, String content);
}
