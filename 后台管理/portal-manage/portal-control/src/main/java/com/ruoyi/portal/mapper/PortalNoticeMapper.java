package com.ruoyi.portal.mapper;

import java.util.List;
import com.ruoyi.portal.domain.PortalNotice;

/**
 * 门户公告Mapper接口
 *
 * @author 王有政
 */
public interface PortalNoticeMapper {
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
     * 删除公告
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    public int deletePortalNoticeByNoticeId(Long noticeId);

    /**
     * 批量删除公告
     *
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalNoticeByNoticeIds(Long[] noticeIds);

    /**
     * 发布公告
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    public int publishNotice(Long noticeId);

    /**
     * 停用公告
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    public int offlineNotice(Long noticeId);
}
