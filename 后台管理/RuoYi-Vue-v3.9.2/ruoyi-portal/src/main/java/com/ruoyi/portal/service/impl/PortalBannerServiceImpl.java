package com.ruoyi.portal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.portal.mapper.PortalBannerMapper;
import com.ruoyi.portal.domain.PortalBanner;
import com.ruoyi.portal.service.IPortalBannerService;

/**
 * 轮播图配置Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalBannerServiceImpl implements IPortalBannerService {

    @Autowired
    private PortalBannerMapper portalBannerMapper;

    /**
     * 查询轮播图配置
     *
     * @param bannerId 轮播图ID主键
     * @return 轮播图配置
     */
    @Override
    public PortalBanner selectPortalBannerByBannerId(Long bannerId) {
        return portalBannerMapper.selectPortalBannerByBannerId(bannerId);
    }

    /**
     * 查询轮播图配置列表
     *
     * @param portalBanner 轮播图配置
     * @return 轮播图配置
     */
    @Override
    public List<PortalBanner> selectPortalBannerList(PortalBanner portalBanner) {
        return portalBannerMapper.selectPortalBannerList(portalBanner);
    }

    /**
     * 新增轮播图配置
     *
     * @param portalBanner 轮播图配置
     * @return 结果
     */
    @Override
    public int insertPortalBanner(PortalBanner portalBanner) {
        portalBanner.setCreateTime(DateUtils.getNowDate());
        return portalBannerMapper.insertPortalBanner(portalBanner);
    }

    /**
     * 修改轮播图配置
     *
     * @param portalBanner 轮播图配置
     * @return 结果
     */
    @Override
    public int updatePortalBanner(PortalBanner portalBanner) {
        portalBanner.setUpdateTime(DateUtils.getNowDate());
        return portalBannerMapper.updatePortalBanner(portalBanner);
    }

    /**
     * 批量删除轮播图配置
     *
     * @param bannerIds 需要删除的轮播图ID主键集合
     * @return 结果
     */
    @Override
    public int deletePortalBannerByBannerIds(Long[] bannerIds) {
        return portalBannerMapper.deletePortalBannerByBannerIds(bannerIds);
    }

    /**
     * 删除轮播图配置信息
     *
     * @param bannerId 轮播图ID主键
     * @return 结果
     */
    @Override
    public int deletePortalBannerByBannerId(Long bannerId) {
        return portalBannerMapper.deletePortalBannerByBannerId(bannerId);
    }
}
