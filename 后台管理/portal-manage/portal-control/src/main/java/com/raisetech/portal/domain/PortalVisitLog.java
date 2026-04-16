package com.raisetech.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.raisetech.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 门户访问日志对象 portal_visit_log
 *
 * @author 王有政
 */
public class PortalVisitLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    private Long logId;

    /**
     * 访问日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitTime;

    /**
     * 页面类型（home首页/article文章/product产品/other其他）
     */
    private String pageType;

    /**
     * 目标ID（文章ID或产品ID）
     */
    private Long targetId;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 浏览器信息
     */
    private String userAgent;

    /**
     * 来源页面
     */
    private String referer;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Long getLogId() {
        return logId;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitTime(Date visitTime) {
        this.visitTime = visitTime;
    }

    public Date getVisitTime() {
        return visitTime;
    }

    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPageType() {
        return pageType;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getTargetId() {
        return targetId;
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

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getReferer() {
        return referer;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
}
