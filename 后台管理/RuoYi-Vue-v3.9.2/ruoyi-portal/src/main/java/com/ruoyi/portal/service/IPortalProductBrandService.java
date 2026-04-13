package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalProductBrand;

/**
 * 产品品牌Service接口
 *
 * @author 王有政
 */
public interface IPortalProductBrandService {
    /**
     * 查询产品品牌
     *
     * @param brandId 品牌ID
     * @return 产品品牌
     */
    public PortalProductBrand selectPortalProductBrandByBrandId(Long brandId);

    /**
     * 查询产品品牌列表
     *
     * @param portalProductBrand 产品品牌
     * @return 产品品牌集合
     */
    public List<PortalProductBrand> selectPortalProductBrandList(PortalProductBrand portalProductBrand);

    /**
     * 新增产品品牌
     *
     * @param portalProductBrand 产品品牌
     * @return 结果
     */
    public int insertPortalProductBrand(PortalProductBrand portalProductBrand);

    /**
     * 修改产品品牌
     *
     * @param portalProductBrand 产品品牌
     * @return 结果
     */
    public int updatePortalProductBrand(PortalProductBrand portalProductBrand);

    /**
     * 批量删除产品品牌
     *
     * @param brandIds 需要删除的品牌ID
     * @return 结果
     */
    public int deletePortalProductBrandByBrandIds(Long[] brandIds);

    /**
     * 删除产品品牌信息
     *
     * @param brandId 品牌ID
     * @return 结果
     */
    public int deletePortalProductBrandByBrandId(Long brandId);
}
