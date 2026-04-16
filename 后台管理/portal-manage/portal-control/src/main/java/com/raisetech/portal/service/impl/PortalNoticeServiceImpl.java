package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.mapper.PortalNoticeMapper;
import com.raisetech.portal.domain.PortalNotice;
import com.raisetech.portal.service.IPortalNoticeService;

/**
 * 门户公告Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalNoticeServiceImpl implements IPortalNoticeService {

    @Autowired
    private PortalNoticeMapper portalNoticeMapper;

    /**
     * 查询公告
     *
     * @param noticeId 公告ID
     * @return 公告
     */
    @Override
    public PortalNotice selectPortalNoticeByNoticeId(Long noticeId) {
        return portalNoticeMapper.selectPortalNoticeByNoticeId(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param portalNotice 公告
     * @return 公告集合
     */
    @Override
    public List<PortalNotice> selectPortalNoticeList(PortalNotice portalNotice) {
        return portalNoticeMapper.selectPortalNoticeList(portalNotice);
    }

    /**
     * 新增公告
     *
     * @param portalNotice 公告
     * @return 结果
     */
    @Override
    public int insertPortalNotice(PortalNotice portalNotice) {
        if (StringUtils.isEmpty(portalNotice.getTitle())) {
            throw new ServiceException("公告标题不能为空");
        }
        if (StringUtils.isEmpty(portalNotice.getContent())) {
            throw new ServiceException("公告内容不能为空");
        }
        portalNotice.setCreateTime(DateUtils.getNowDate());
        if (portalNotice.getViewCount() == null) {
            portalNotice.setViewCount(0);
        }
        if (portalNotice.getClickCount() == null) {
            portalNotice.setClickCount(0);
        }
        return portalNoticeMapper.insertPortalNotice(portalNotice);
    }

    /**
     * 修改公告
     *
     * @param portalNotice 公告
     * @return 结果
     */
    @Override
    public int updatePortalNotice(PortalNotice portalNotice) {
        if (StringUtils.isEmpty(portalNotice.getTitle())) {
            throw new ServiceException("公告标题不能为空");
        }
        if (StringUtils.isEmpty(portalNotice.getContent())) {
            throw new ServiceException("公告内容不能为空");
        }
        portalNotice.setUpdateTime(DateUtils.getNowDate());
        return portalNoticeMapper.updatePortalNotice(portalNotice);
    }

    /**
     * 批量删除公告
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deletePortalNoticeByNoticeIds(Long[] noticeIds) {
        return portalNoticeMapper.deletePortalNoticeByNoticeIds(noticeIds);
    }

    /**
     * 删除公告信息
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deletePortalNoticeByNoticeId(Long noticeId) {
        return portalNoticeMapper.deletePortalNoticeByNoticeId(noticeId);
    }

    /**
     * 发布公告
     * 调用Mapper层的发布方法，更新状态和时间
     *
     * @param portalNotice 公告
     * @return 结果
     */
    @Override
    public int publishNotice(PortalNotice portalNotice) {
        return portalNoticeMapper.publishNotice(portalNotice.getNoticeId());
    }

    /**
     * 停用公告
     * 调用Mapper层的停用方法，更新状态
     *
     * @param portalNotice 公告
     * @return 结果
     */
    @Override
    public int offlineNotice(PortalNotice portalNotice) {
        return portalNoticeMapper.offlineNotice(portalNotice.getNoticeId());
    }
}
