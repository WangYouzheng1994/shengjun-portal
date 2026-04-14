package com.ruoyi.message.wecom.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.message.config.WeComConfig;
import com.ruoyi.message.domain.MessageResult;
import com.ruoyi.message.wecom.WeComService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 企业微信消息服务实现类
 *
 * @author 王有政
 */
@Service
public class WeComServiceImpl implements WeComService {

    private static final Logger log = LoggerFactory.getLogger(WeComServiceImpl.class);

    private static final String GET_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=%s&corpsecret=%s";

    private static final String SEND_MESSAGE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=%s";

    @Autowired
    private WeComConfig weComConfig;

    @Autowired
    private RestTemplate restTemplate;

    private String accessToken;

    private long tokenExpireTime;

    @Override
    public MessageResult sendText(String user, String content) {
        if (!weComConfig.isEnabled()) {
            return MessageResult.fail("NOT_ENABLED", "企业微信对接未启用");
        }

        try {
            String token = getAccessToken();

            JSONObject body = new JSONObject();
            body.put("touser", user);
            body.put("msgtype", "text");
            body.put("agentid", weComConfig.getAgentId());

            JSONObject textContent = new JSONObject();
            textContent.put("content", content);
            body.put("text", textContent);

            String url = String.format(SEND_MESSAGE_URL, token);
            String response = restTemplate.postForObject(url, body, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                log.info("企业微信文本消息发送成功，msgid：{}", result.getString("msgid"));
                return MessageResult.success(result.getString("msgid"), response);
            } else {
                log.error("企业微信文本消息发送失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                return MessageResult.fail(String.valueOf(errcode), result.getString("errmsg"), response);
            }
        } catch (Exception e) {
            log.error("发送企业微信文本消息异常", e);
            return MessageResult.fail("EXCEPTION", e.getMessage());
        }
    }

    @Override
    public MessageResult sendTextCard(String user, String title, String description, String url) {
        if (!weComConfig.isEnabled()) {
            return MessageResult.fail("NOT_ENABLED", "企业微信对接未启用");
        }

        try {
            String token = getAccessToken();

            JSONObject body = new JSONObject();
            body.put("touser", user);
            body.put("msgtype", "textcard");
            body.put("agentid", weComConfig.getAgentId());

            JSONObject cardContent = new JSONObject();
            cardContent.put("title", title);
            cardContent.put("description", description);
            cardContent.put("url", url);
            cardContent.put("btntext", "查看详情");
            body.put("textcard", cardContent);

            String apiUrl = String.format(SEND_MESSAGE_URL, token);
            String response = restTemplate.postForObject(apiUrl, body, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                log.info("企业微信文本卡片消息发送成功，msgid：{}", result.getString("msgid"));
                return MessageResult.success(result.getString("msgid"), response);
            } else {
                log.error("企业微信文本卡片消息发送失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                return MessageResult.fail(String.valueOf(errcode), result.getString("errmsg"), response);
            }
        } catch (Exception e) {
            log.error("发送企业微信文本卡片消息异常", e);
            return MessageResult.fail("EXCEPTION", e.getMessage());
        }
    }

    @Override
    public MessageResult sendMarkdown(String user, String content) {
        if (!weComConfig.isEnabled()) {
            return MessageResult.fail("NOT_ENABLED", "企业微信对接未启用");
        }

        try {
            String token = getAccessToken();

            JSONObject body = new JSONObject();
            body.put("touser", user);
            body.put("msgtype", "markdown");
            body.put("agentid", weComConfig.getAgentId());

            JSONObject markdownContent = new JSONObject();
            markdownContent.put("content", content);
            body.put("markdown", markdownContent);

            String url = String.format(SEND_MESSAGE_URL, token);
            String response = restTemplate.postForObject(url, body, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                log.info("企业微信Markdown消息发送成功，msgid：{}", result.getString("msgid"));
                return MessageResult.success(result.getString("msgid"), response);
            } else {
                log.error("企业微信Markdown消息发送失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                return MessageResult.fail(String.valueOf(errcode), result.getString("errmsg"), response);
            }
        } catch (Exception e) {
            log.error("发送企业微信Markdown消息异常", e);
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
            String url = String.format(GET_TOKEN_URL, weComConfig.getCorpId(), weComConfig.getAgentSecret());
            String response = restTemplate.getForObject(url, String.class);

            JSONObject result = JSON.parseObject(response);
            int errcode = result.getIntValue("errcode");

            if (errcode == 0) {
                accessToken = result.getString("access_token");
                int expiresIn = result.getIntValue("expires_in");
                tokenExpireTime = System.currentTimeMillis() + (expiresIn - 300) * 1000L;

                log.info("企业微信Access Token获取成功");
                return accessToken;
            } else {
                log.error("企业微信Access Token获取失败，错误码：{}，错误信息：{}", errcode, result.getString("errmsg"));
                throw new RuntimeException("获取企业微信Token失败：" + result.getString("errmsg"));
            }
        } catch (Exception e) {
            log.error("获取企业微信Access Token异常", e);
            throw new RuntimeException("获取企业微信Token异常", e);
        }
    }
}
