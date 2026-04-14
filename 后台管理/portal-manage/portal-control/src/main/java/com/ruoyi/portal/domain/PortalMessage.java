package com.ruoyi.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 官网消息通知对象 portal_message
 *
 * @author 王有政
 */
public class PortalMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 消息通知ID
     */
    private Long messageId;

    /**
     * 关联客户ID（可为空，未匹配到客户时）
     */
    private Long customerId;

    /**
     * 客户姓名（非数据库字段，用于展示）
     */
    @Excel(name = "客户姓名")
    private String customerName;

    /**
     * 联系人姓名
     */
    @Excel(name = "联系人")
    private String name;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String phone;

    /**
     * 电子邮箱
     */
    @Excel(name = "电子邮箱")
    private String email;

    /**
     * 消息主题
     */
    @Excel(name = "消息主题")
    private String subject;

    /**
     * 消息内容
     */
    private String content;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 浏览器UA
     */
    private String userAgent;

    /**
     * 来源页面URL
     */
    private String sourcePage;

    /**
     * 处理状态（0待处理 1处理中 2已回复 3已关闭）
     */
    @Excel(name = "处理状态", readConverterExp = "0=待处理,1=处理中,2=已回复,3=已关闭")
    private String handleStatus;

    /**
     * 处理人ID
     */
    private Long handlerId;

    /**
     * 处理人姓名
     */
    @Excel(name = "处理人")
    private String handlerName;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "回复时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    /**
     * 是否已读（0未读 1已读）
     */
    @Excel(name = "是否已读", readConverterExp = "0=未读,1=已读")
    private String isRead;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setSourcePage(String sourcePage) {
        this.sourcePage = sourcePage;
    }

    public String getSourcePage() {
        return sourcePage;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandlerId(Long handlerId) {
        this.handlerId = handlerId;
    }

    public Long getHandlerId() {
        return handlerId;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
}
