package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalProductBrand;

/**
 * 产品品牌Mapper接口
 *
 * @author 王有政
 */
public interface PortalProductBrandMapper {
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
     * 删除产品品牌
     *
     * @param brandId 品牌ID
     * @return 结果
     */
    public int deletePortalProductBrandByBrandId(Long brandId);

    /**
     * 批量删除产品品牌
     *
     * @param brandIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalProductBrandByBrandIds(Long[] brandIds);
}
