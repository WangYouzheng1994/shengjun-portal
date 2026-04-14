package com.ruoyi.portal.mapper;

import java.util.List;
import com.ruoyi.portal.domain.PortalProduct;

/**
 * 产品Mapper接口
 *
 * @author 王有政
 */
public interface PortalProductMapper {
    /**
     * 查询产品
     *
     * @param productId 产品ID
     * @return 产品
     */
    public PortalProduct selectPortalProductByProductId(Long productId);

    /**
     * 查询产品列表
     *
     * @param portalProduct 产品
     * @return 产品集合
     */
    public List<PortalProduct> selectPortalProductList(PortalProduct portalProduct);

    /**
     * 新增产品
     *
     * @param portalProduct 产品
     * @return 结果
     */
    public int insertPortalProduct(PortalProduct portalProduct);

    /**
     * 修改产品
     *
     * @param portalProduct 产品
     * @return 结果
     */
    public int updatePortalProduct(PortalProduct portalProduct);

    /**
     * 删除产品
     *
     * @param productId 产品ID
     * @return 结果
     */
    public int deletePortalProductByProductId(Long productId);

    /**
     * 批量删除产品
     *
     * @param productIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalProductByProductIds(Long[] productIds);
}
