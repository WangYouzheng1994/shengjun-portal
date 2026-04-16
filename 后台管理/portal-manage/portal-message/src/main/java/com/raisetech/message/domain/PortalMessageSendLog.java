package com.raisetech.message.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 消息发送记录对象 portal_message_send_log
 *
 * @author 王有政
 */
public class PortalMessageSendLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 渠道类型
     */
    @Excel(name = "渠道类型")
    private String channelType;

    /**
     * 关联模板ID
     */
    @Excel(name = "模板ID")
    private Long templateId;

    /**
     * 模板名称
     */
    @Excel(name = "模板名称")
    private String templateName;

    /**
     * 发送类型（broadcast/group/personal）
     */
    @Excel(name = "发送类型")
    private String sendType;

    /**
     * 接收者
     */
    @Excel(name = "接收者")
    private String receiver;

    /**
     * 消息标题
     */
    @Excel(name = "消息标题")
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送状态（0待发送 1成功 2失败）
     */
    @Excel(name = "发送状态", readConverterExp = "0=待发送,1=成功,2=失败")
    private String sendStatus;

    /**
     * 错误码
     */
    @Excel(name = "错误码")
    private String errorCode;

    /**
     * 错误信息
     */
    @Excel(name = "错误信息")
    private String errorMessage;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date sendTime;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
