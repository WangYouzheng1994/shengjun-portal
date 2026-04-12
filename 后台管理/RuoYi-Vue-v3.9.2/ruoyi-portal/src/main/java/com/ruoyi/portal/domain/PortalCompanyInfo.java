package com.ruoyi.portal.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.annotation.Excel;

/**
 * 企业基础信息对象 portal_company_info
 *
 * @author 王有政
 */
public class PortalCompanyInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 信息ID
     */
    private Long infoId;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 公司Logo
     */
    private String companyLogo;

    /**
     * 公司地址
     */
    @Excel(name = "公司地址")
    private String address;

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
     * 传真号码
     */
    @Excel(name = "传真号码")
    private String fax;

    /**
     * 微信公众号
     */
    @Excel(name = "微信公众号")
    private String wechat;

    /**
     * 新浪微博
     */
    @Excel(name = "新浪微博")
    private String weibo;

    /**
     * 抖音账号
     */
    @Excel(name = "抖音账号")
    private String douyin;

    /**
     * QQ号
     */
    @Excel(name = "QQ号")
    private String qq;

    /**
     * 公司简介（富文本）
     */
    private String introduction;

    /**
     * 营业执照
     */
    private String businessLicense;

    /**
     * ICP备案号
     */
    @Excel(name = "ICP备案号")
    private String icpNumber;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
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

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setDouyin(String douyin) {
        this.douyin = douyin;
    }

    public String getDouyin() {
        return douyin;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq() {
        return qq;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setIcpNumber(String icpNumber) {
        this.icpNumber = icpNumber;
    }

    public String getIcpNumber() {
        return icpNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("infoId", getInfoId())
            .append("companyName", getCompanyName())
            .append("companyLogo", getCompanyLogo())
            .append("address", getAddress())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("fax", getFax())
            .append("wechat", getWechat())
            .append("weibo", getWeibo())
            .append("douyin", getDouyin())
            .append("qq", getQq())
            .append("introduction", getIntroduction())
            .append("businessLicense", getBusinessLicense())
            .append("icpNumber", getIcpNumber())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
