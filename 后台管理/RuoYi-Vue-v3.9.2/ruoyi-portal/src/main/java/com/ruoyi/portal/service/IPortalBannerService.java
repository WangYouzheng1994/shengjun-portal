package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalBanner;

/**
 * 轮播图配置Service接口
 *
 * @author 王有政
 */
public interface IPortalBannerService {
    /**
     * 查询轮播图配置
     *
     * @param bannerId 轮播图ID主键
     * @return 轮播图配置
     */
    public PortalBanner selectPortalBannerByBannerId(Long bannerId);

    /**
     * 查询轮播图配置列表
     *
     * @param portalBanner 轮播图配置
     * @return 轮播图配置集合
     */
    public List<PortalBanner> selectPortalBannerList(PortalBanner portalBanner);

    /**
     * 新增轮播图配置
     *
     * @param portalBanner 轮播图配置
     * @return 结果
     */
    public int insertPortalBanner(PortalBanner portalBanner);

    /**
     * 修改轮播图配置
     *
     * @param portalBanner 轮播图配置
     * @return 结果
     */
    public int updatePortalBanner(PortalBanner portalBanner);

    /**
     * 批量删除轮播图配置
     *
     * @param bannerIds 需要删除的轮播图ID主键集合
     * @return 结果
     */
    public int deletePortalBannerByBannerIds(Long[] bannerIds);

    /**
     * 删除轮播图配置信息
     *
     * @param bannerId 轮播图ID主键
     * @return 结果
     */
    public int deletePortalBannerByBannerId(Long bannerId);
}
