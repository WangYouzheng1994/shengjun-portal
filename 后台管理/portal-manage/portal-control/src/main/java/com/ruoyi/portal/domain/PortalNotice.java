package com.ruoyi.portal.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 门户公告对象 portal_notice
 *
 * @author 王有政
 */
public class PortalNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    @Excel(name = "公告标题")
    private String title;

    /**
     * 公告内容（富文本）
     */
    private String content;

    /**
     * 公告类型（notice系统公告 announcement重要通知 holiday节日问候 activity活动通告）
     */
    @Excel(name = "公告类型")
    private String noticeType;

    /**
     * 优先级（low低 normal普通 high高 urgent紧急 critical特急）
     */
    @Excel(name = "优先级", readConverterExp = "low=低,normal=普通,high=高,urgent=紧急,critical=特急")
    private String priorityLevel;

    /**
     * 展示方式（list列表 popup弹窗 banner横幅 scroll滚动 both列表+弹窗）
     */
    @Excel(name = "展示方式", readConverterExp = "list=列表,popup=弹窗,banner=横幅,scroll=滚动,both=列表+弹窗")
    private String displayMode;

    /**
     * 是否置顶（0否 1是）
     */
    @Excel(name = "是否置顶", readConverterExp = "0=否,1=是")
    private String isTop;

    /**
     * 是否常驻（0否 1是，常驻公告始终显示在公告栏顶部）
     */
    @Excel(name = "是否常驻", readConverterExp = "0=否,1=是")
    private String isSticky;

    /**
     * 状态（0草稿 1已发布 2已过期 3已停用）
     */
    @Excel(name = "状态", readConverterExp = "0=草稿,1=已发布,2=已过期,3=已停用")
    private String status;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /**
     * 有效开始时间（NULL表示立即生效）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 有效结束时间（NULL表示永不失效）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 排序权重（数值越大越靠前）
     */
    @Excel(name = "排序")
    private Integer sortOrder;

    /**
     * 查看次数
     */
    @Excel(name = "查看次数")
    private Integer viewCount;

    /**
     * 点击次数（点击"查看详情"的次数）
     */
    @Excel(name = "点击次数")
    private Integer clickCount;

    /**
     * 是否需要确认（0否 1是，用户需点击"我已知晓"）
     */
    @Excel(name = "需要确认", readConverterExp = "0=否,1=是")
    private String requireConfirm;

    /**
     * 确认按钮文字（如"我已知晓"、"确定"）
     */
    @Excel(name = "确认文字")
    private String confirmText;

    /**
     * 弹窗样式（default默认 warning警告 info信息 success成功）
     */
    @Excel(name = "弹窗样式")
    private String popupStyle;

    /**
     * 目标受众（all全部 user注册游客 visitor未登录游客 member会员 enterprise企业用户）
     */
    @Excel(name = "目标受众", readConverterExp = "all=全部,user=注册游客,visitor=未登录游客,member=会员,enterprise=企业用户")
    private String targetAudience;

    /**
     * 删除标志（0正常 1已删除）
     */
    private String delFlag;

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setDisplayMode(String displayMode) {
        this.displayMode = displayMode;
    }

    public String getDisplayMode() {
        return displayMode;
    }

    public void setIsTop(String isTop) {
        this.isTop = isTop;
    }

    public String getIsTop() {
        return isTop;
    }

    public void setIsSticky(String isSticky) {
        this.isSticky = isSticky;
    }

    public String getIsSticky() {
        return isSticky;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
    }

    public Integer getClickCount() {
        return clickCount;
    }

    public void setRequireConfirm(String requireConfirm) {
        this.requireConfirm = requireConfirm;
    }

    public String getRequireConfirm() {
        return requireConfirm;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public String getConfirmText() {
        return confirmText;
    }

    public void setPopupStyle(String popupStyle) {
        this.popupStyle = popupStyle;
    }

    public String getPopupStyle() {
        return popupStyle;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("noticeId", getNoticeId())
            .append("title", getTitle())
            .append("noticeType", getNoticeType())
            .append("priorityLevel", getPriorityLevel())
            .append("displayMode", getDisplayMode())
            .append("isTop", getIsTop())
            .append("isSticky", getIsSticky())
            .append("status", getStatus())
            .append("publishTime", getPublishTime())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("sortOrder", getSortOrder())
            .append("viewCount", getViewCount())
            .append("clickCount", getClickCount())
            .append("requireConfirm", getRequireConfirm())
            .append("confirmText", getConfirmText())
            .append("popupStyle", getPopupStyle())
            .append("targetAudience", getTargetAudience())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
