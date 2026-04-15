package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalNotice;

/**
 * 门户公告Service接口
 *
 * @author 王有政
 */
public interface IPortalNoticeService {
    /**
     * 查询公告
     *
     * @param noticeId 公告ID
     * @return 公告
     */
    public PortalNotice selectPortalNoticeByNoticeId(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param portalNotice 公告
     * @return 公告集合
     */
    public List<PortalNotice> selectPortalNoticeList(PortalNotice portalNotice);

    /**
     * 新增公告
     *
     * @param portalNotice 公告
     * @return 结果
     */
    public int insertPortalNotice(PortalNotice portalNotice);

    /**
     * 修改公告
     *
     * @param portalNotice 公告
     * @return 结果
     */
    public int updatePortalNotice(PortalNotice portalNotice);

    /**
     * 批量删除公告
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    public int deletePortalNoticeByNoticeIds(Long[] noticeIds);

    /**
     * 删除公告信息
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    public int deletePortalNoticeByNoticeId(Long noticeId);

    /**
     * 发布公告
     *
     * @param portalNotice 公告
     * @return 结果
     */
    public int publishNotice(PortalNotice portalNotice);

    /**
     * 停用公告
     *
     * @param portalNotice 公告
     * @return 结果
     */
    public int offlineNotice(PortalNotice portalNotice);
}
