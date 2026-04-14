package com.ruoyi.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 跟进记录对象 portal_follow_up
 *
 * @author 王有政
 */
public class PortalFollowUp extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 跟进ID
     */
    private Long followId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 客户姓名（非数据库字段，用于展示）
     */
    @Excel(name = "客户姓名")
    private String customerName;

    /**
     * 关联留言ID（可选）
     */
    private Long messageId;

    /**
     * 跟进方式（0电话 1微信 2邮件 3上门 4其他）
     */
    @Excel(name = "跟进方式", readConverterExp = "0=电话,1=微信,2=邮件,3=上门,4=其他")
    private String followType;

    /**
     * 跟进内容
     */
    private String followContent;

    /**
     * 跟进结果
     */
    @Excel(name = "跟进结果")
    private String followResult;

    /**
     * 下一步计划
     */
    private String nextPlan;

    /**
     * 下次联系时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下次联系时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date nextContactTime;

    /**
     * 跟进人ID
     */
    private Long followUserId;

    /**
     * 跟进人姓名
     */
    @Excel(name = "跟进人")
    private String followUserName;

    /**
     * 接触感受（0无反馈 1有意向 2强烈意向 3无意向）
     */
    @Excel(name = "接触感受", readConverterExp = "0=无反馈,1=有意向,2=强烈意向,3=无意向")
    private String contactFeeling;

    /**
     * 附件路径（多个用逗号分隔）
     */
    private String attachments;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setFollowId(Long followId) {
        this.followId = followId;
    }

    public Long getFollowId() {
        return followId;
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

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowContent(String followContent) {
        this.followContent = followContent;
    }

    public String getFollowContent() {
        return followContent;
    }

    public void setFollowResult(String followResult) {
        this.followResult = followResult;
    }

    public String getFollowResult() {
        return followResult;
    }

    public void setNextPlan(String nextPlan) {
        this.nextPlan = nextPlan;
    }

    public String getNextPlan() {
        return nextPlan;
    }

    public void setNextContactTime(Date nextContactTime) {
        this.nextContactTime = nextContactTime;
    }

    public Date getNextContactTime() {
        return nextContactTime;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserName(String followUserName) {
        this.followUserName = followUserName;
    }

    public String getFollowUserName() {
        return followUserName;
    }

    public void setContactFeeling(String contactFeeling) {
        this.contactFeeling = contactFeeling;
    }

    public String getContactFeeling() {
        return contactFeeling;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
}
