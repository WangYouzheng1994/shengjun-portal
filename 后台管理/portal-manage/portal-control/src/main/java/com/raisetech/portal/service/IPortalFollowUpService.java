package com.raisetech.portal.service;

import java.util.List;
import com.raisetech.portal.domain.PortalFollowUp;

/**
 * 跟进记录Service接口
 *
 * @author 王有政
 */
public interface IPortalFollowUpService {
    /**
     * 查询跟进记录
     *
     * @param followId 跟进ID
     * @return 跟进记录对象
     */
    public PortalFollowUp selectPortalFollowUpByFollowId(Long followId);

    /**
     * 查询跟进记录列表
     *
     * @param portalFollowUp 跟进记录查询条件
     * @return 跟进记录集合
     */
    public List<PortalFollowUp> selectPortalFollowUpList(PortalFollowUp portalFollowUp);

    /**
     * 新增跟进记录
     *
     * @param portalFollowUp 跟进记录对象
     * @return 结果
     */
    public int insertPortalFollowUp(PortalFollowUp portalFollowUp);

    /**
     * 修改跟进记录
     *
     * @param portalFollowUp 跟进记录对象
     * @return 结果
     */
    public int updatePortalFollowUp(PortalFollowUp portalFollowUp);

    /**
     * 批量删除跟进记录（逻辑删除）
     *
     * @param followIds 需要删除的跟进ID数组
     * @return 结果
     */
    public int deletePortalFollowUpByFollowIds(Long[] followIds);

    /**
     * 根据客户ID查询跟进记录
     *
     * @param customerId 客户ID
     * @return 跟进记录集合
     */
    public List<PortalFollowUp> selectFollowUpsByCustomerId(Long customerId);
}
