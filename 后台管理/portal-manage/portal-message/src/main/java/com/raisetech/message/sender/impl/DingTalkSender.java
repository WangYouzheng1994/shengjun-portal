package com.raisetech.message.sender.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.raisetech.message.config.DingTalkConfig;
import com.raisetech.message.domain.MessageResult;
import com.raisetech.message.sender.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 钉钉消息发送策略实现
 *
 * @author 王有政
 */
@Component
public class DingTalkSender implements MessageSender {

    private static final Logger log = LoggerFactory.getLogger(DingTalkSender.class);

    private static final String GET_TOKEN_URL = "https://oapi.dingtalk.com/gettoken?appkey=%s&appsecret=%s";

    private static final String SEND_WORK_NOTICE_URL = "https://oapi.dingtalk/topapi/message/corpconversation/asyncsend_v2?access_token=%s";

    @Autowired
    private DingTalkConfig dingTalkConfig;

    @Autowired
    private RestTemplate restTemplate;

    private String accessToken;

    private long tokenExpireTime;

    @Override
    public String getChannelType() {
        return "dingtalk";
    }

    @Override
    public MessageResult sendText(String receiver, String content) {
        if (!dingTalkConfig.isEnabled()) {
            return MessageResult.fail("NOT_ENABLED", "钉钉对接未启用");
        }

        try {
            String token = getAccessToken();

            JSONObject body = new JSONObject();
            body.put("agent_id", dingTalkConfig.getAgentId());
            body.put("userid_list", receiver);

            JSONObject msg = new JSONObject();
            msg.put("msgtype", "text");
            JSONObject textContent = new JSONObject();
            textContent.put("content", content);
            msg.put("text", textContent);
            body.put("msg", msg);

            String url = String.format(SEND_WORK_NOTICE_URL, token);
            String response = restTemplate.postForObject(url, body, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                log.info("钉钉工作通知发送成功，taskId：{}", result.getString("task_id"));
                return MessageResult.success(result.getString("task_id"), response);
            } else {
                log.error("钉钉工作通知发送失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                return MessageResult.fail(String.valueOf(errcode), result.getString("errmsg"), response);
            }
        } catch (Exception e) {
            log.error("发送钉钉工作通知异常", e);
            return MessageResult.fail("EXCEPTION", e.getMessage());
        }
    }

    @Override
    public MessageResult sendRichText(String receiver, String title, String content, String url) {
        if (!dingTalkConfig.isEnabled()) {
            return MessageResult.fail("NOT_ENABLED", "钉钉对接未启用");
        }

        try {
            String token = getAccessToken();

            JSONObject body = new JSONObject();
            body.put("agent_id", dingTalkConfig.getAgentId());
            body.put("userid_list", receiver);

            JSONObject msg = new JSONObject();
            msg.put("msgtype", "markdown");
            JSONObject markdownContent = new JSONObject();
            markdownContent.put("title", title);
            markdownContent.put("text", content);
            msg.put("markdown", markdownContent);
            body.put("msg", msg);

            String apiUrl = String.format(SEND_WORK_NOTICE_URL, token);
            String response = restTemplate.postForObject(apiUrl, body, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                log.info("钉钉Markdown工作通知发送成功，taskId：{}", result.getString("task_id"));
                return MessageResult.success(result.getString("task_id"), response);
            } else {
                log.error("钉钉Markdown工作通知发送失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                return MessageResult.fail(String.valueOf(errcode), result.getString("errmsg"), response);
            }
        } catch (Exception e) {
            log.error("发送钉钉Markdown工作通知异常", e);
            return MessageResult.fail("EXCEPTION", e.getMessage());
        }
    }

    /**
     * 获取Access Token（带缓存机制）
     *
     * @return Access Token
     */
    private synchronized String getAccessToken() {
        if (accessToken != null && System.currentTimeMillis() < tokenExpireTime) {
            return accessToken;
        }

        try {
            String url = String.format(GET_TOKEN_URL, dingTalkConfig.getAppKey(), dingTalkConfig.getAppSecret());
            String response = restTemplate.getForObject(url, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                accessToken = result.getString("access_token");
                int expiresIn = result.getIntValue("expires_in");
                tokenExpireTime = System.currentTimeMillis() + (expiresIn - 300) * 1000L;

                log.info("钉钉Access Token获取成功");
                return accessToken;
            } else {
                log.error("钉钉Access Token获取失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                throw new RuntimeException("获取钉钉Token失败：" + result.getString("errmsg"));
            }
        } catch (Exception e) {
            log.error("获取钉钉Access Token异常", e);
            throw new RuntimeException("获取钉钉Token异常", e);
        }
    }
}
