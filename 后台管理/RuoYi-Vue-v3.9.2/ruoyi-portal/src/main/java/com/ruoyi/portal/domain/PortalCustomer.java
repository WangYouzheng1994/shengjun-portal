package com.ruoyi.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 客户对象 portal_customer
 *
 * @author 王有政
 */
public class PortalCustomer extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 客户姓名
     */
    @Excel(name = "客户姓名")
    private String customerName;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 电子邮箱
     */
    @Excel(name = "电子邮箱")
    private String email;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 职位
     */
    @Excel(name = "职位")
    private String position;

    /**
     * 性别（0未知 1男 2女）
     */
    @Excel(name = "性别", readConverterExp = "0=未知,1=男,2=女")
    private String gender;

    /**
     * 客户来源（0官网留言 1电话咨询 2朋友推荐 3展会 4其他）
     */
    @Excel(name = "客户来源", readConverterExp = "0=官网留言,1=电话咨询,2=朋友推荐,3=展会,4=其他")
    private String source;

    /**
     * 来源详情
     */
    @Excel(name = "来源详情")
    private String sourceDetail;

    /**
     * 客户状态（0潜在客户 1意向客户 2成交客户 3流失客户）
     */
    @Excel(name = "客户状态", readConverterExp = "0=潜在客户,1=意向客户,2=成交客户,3=流失客户")
    private String status;

    /**
     * 客户等级（0普通 1重要 2VIP）
     */
    @Excel(name = "客户等级", readConverterExp = "0=普通,1=重要,2=VIP")
    private String level;

    /**
     * 负责人ID（关联sys_user.user_id）
     */
    private Long ownerId;

    /**
     * 负责人姓名（冗余字段）
     */
    @Excel(name = "负责人")
    private String ownerName;

    /**
     * 是否公海客户（0否 1是）
     */
    @Excel(name = "是否公海", readConverterExp = "0=否,1=是")
    private String isPublic;

    /**
     * 最后联系时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后联系时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastContactTime;

    /**
     * 下次联系时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "下次联系时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date nextContactTime;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String address;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    /**
     * 标签列表（非数据库字段，用于展示）
     */
    private java.util.List<PortalCustomerTag> tags;

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

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSourceDetail(String sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    public String getSourceDetail() {
        return sourceDetail;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setLastContactTime(Date lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public Date getLastContactTime() {
        return lastContactTime;
    }

    public void setNextContactTime(Date nextContactTime) {
        this.nextContactTime = nextContactTime;
    }

    public Date getNextContactTime() {
        return nextContactTime;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setTags(java.util.List<PortalCustomerTag> tags) {
        this.tags = tags;
    }

    public java.util.List<PortalCustomerTag> getTags() {
        return tags;
    }
}
