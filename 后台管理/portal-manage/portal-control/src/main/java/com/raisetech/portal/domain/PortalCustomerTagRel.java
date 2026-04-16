package com.raisetech.portal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 客户标签关联对象 portal_customer_tag_rel
 *
 * @author 王有政
 */
public class PortalCustomerTagRel {
    private static final long serialVersionUID = 1L;

    /**
     * 关联ID
     */
    private Long relId;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 标签ID
     */
    private Long tagId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public void setRelId(Long relId) {
        this.relId = relId;
    }

    public Long getRelId() {
        return relId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }
}
