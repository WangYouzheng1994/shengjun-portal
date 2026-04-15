package com.ruoyi.portal.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 企业荣誉墙对象 portal_company_honor
 *
 * @author 王有政
 */
public class PortalCompanyHonor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 荣誉ID
     */
    private Long honorId;

    /**
     * 荣誉标题
     */
    @Excel(name = "荣誉标题")
    private String honorTitle;

    /**
     * 荣誉图片地址
     */
    @Excel(name = "荣誉图片")
    private String honorImage;

    /**
     * 荣誉说明
     */
    @Excel(name = "荣誉说明")
    private String honorDescription;

    /**
     * 授予机构
     */
    @Excel(name = "授予机构")
    private String awardOrg;

    /**
     * 获得时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获得时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date awardDate;

    /**
     * 荣誉类型（0资质认证 1行业奖项 2荣誉称号 3其他）
     */
    @Excel(name = "荣誉类型", readConverterExp = "0=资质认证,1=行业奖项,2=荣誉称号,3=其他")
    private String honorType;

    /**
     * 排序（升序）
     */
    @Excel(name = "排序")
    private Integer sortOrder;

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

    public void setHonorId(Long honorId) {
        this.honorId = honorId;
    }

    public Long getHonorId() {
        return honorId;
    }

    public void setHonorTitle(String honorTitle) {
        this.honorTitle = honorTitle;
    }

    public String getHonorTitle() {
        return honorTitle;
    }

    public void setHonorImage(String honorImage) {
        this.honorImage = honorImage;
    }

    public String getHonorImage() {
        return honorImage;
    }

    public void setHonorDescription(String honorDescription) {
        this.honorDescription = honorDescription;
    }

    public String getHonorDescription() {
        return honorDescription;
    }

    public void setAwardOrg(String awardOrg) {
        this.awardOrg = awardOrg;
    }

    public String getAwardOrg() {
        return awardOrg;
    }

    public void setAwardDate(Date awardDate) {
        this.awardDate = awardDate;
    }

    public Date getAwardDate() {
        return awardDate;
    }

    public void setHonorType(String honorType) {
        this.honorType = honorType;
    }

    public String getHonorType() {
        return honorType;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
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
            .append("honorId", getHonorId())
            .append("honorTitle", getHonorTitle())
            .append("honorImage", getHonorImage())
            .append("honorDescription", getHonorDescription())
            .append("awardOrg", getAwardOrg())
            .append("awardDate", getAwardDate())
            .append("honorType", getHonorType())
            .append("sortOrder", getSortOrder())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
