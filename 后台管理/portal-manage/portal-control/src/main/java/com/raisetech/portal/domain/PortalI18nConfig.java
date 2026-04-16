package com.raisetech.portal.domain;

import com.raisetech.common.annotation.Excel;
import com.raisetech.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 多语言配置对象 portal_i18n_config
 *
 * @author 王有政
 */
public class PortalI18nConfig extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 语言代码（zh-CN/en-US/ja-JP等）
     */
    @Excel(name = "语言代码")
    private String langCode;

    /**
     * 语言显示名称
     */
    @Excel(name = "语言名称")
    private String langName;

    /**
     * 语言图标（emoji或图片URL）
     */
    @Excel(name = "语言图标")
    private String langIcon;

    /**
     * 是否默认语言（0否 1是）
     */
    @Excel(name = "是否默认", readConverterExp = "0=否,1=是")
    private String isDefault;

    /**
     * 状态（0启用 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=启用,1=停用")
    private String status;

    /**
     * 显示顺序（升序）
     */
    @Excel(name = "显示顺序")
    private Integer sortOrder;

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public Long getConfigId() {
        return configId;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangIcon(String langIcon) {
        this.langIcon = langIcon;
    }

    public String getLangIcon() {
        return langIcon;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("configId", getConfigId())
            .append("langCode", getLangCode())
            .append("langName", getLangName())
            .append("langIcon", getLangIcon())
            .append("isDefault", getIsDefault())
            .append("status", getStatus())
            .append("sortOrder", getSortOrder())
            .toString();
    }
}
