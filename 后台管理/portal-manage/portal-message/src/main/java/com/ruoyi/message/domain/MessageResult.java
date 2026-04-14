package com.ruoyi.message.domain;

import lombok.Data;

/**
 * 第三方消息发送结果对象
 *
 * @author 王有政
 */
@Data
public class MessageResult {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 原始返回数据（JSON格式）
     */
    private String rawData;

    /**
     * 业务ID（如消息ID、任务ID等）
     */
    private String bizId;

    public static MessageResult success(String bizId, String rawData) {
        MessageResult result = new MessageResult();
        result.setSuccess(true);
        result.setBizId(bizId);
        result.setRawData(rawData);
        return result;
    }

    public static MessageResult fail(String errorCode, String errorMessage) {
        MessageResult result = new MessageResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        return result;
    }

    public static MessageResult fail(String errorCode, String errorMessage, String rawData) {
        MessageResult result = new MessageResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(errorMessage);
        result.setRawData(rawData);
        return result;
    }
}
