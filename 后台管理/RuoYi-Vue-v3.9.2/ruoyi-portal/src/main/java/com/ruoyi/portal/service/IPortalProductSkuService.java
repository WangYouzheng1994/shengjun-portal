package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalProductSku;

/**
 * 产品SKUService接口
 *
 * @author 王有政
 */
public interface IPortalProductSkuService {
    /**
     * 查询产品SKU
     *
     * @param skuId SKU ID
     * @return 产品SKU
     */
    public PortalProductSku selectPortalProductSkuBySkuId(Long skuId);

    /**
     * 查询产品SKU列表
     *
     * @param portalProductSku 产品SKU
     * @return 产品SKU集合
     */
    public List<PortalProductSku> selectPortalProductSkuList(PortalProductSku portalProductSku);

    /**
     * 根据产品ID查询SKU列表
     *
     * @param productId 产品ID
     * @return 产品SKU集合
     */
    public List<PortalProductSku> selectPortalProductSkuListByProductId(Long productId);

    /**
     * 新增产品SKU
     *
     * @param portalProductSku 产品SKU
     * @return 结果
     */
    public int insertPortalProductSku(PortalProductSku portalProductSku);

    /**
     * 批量新增产品SKU
     *
     * @param list 产品SKU列表
     * @return 结果
     */
    public int batchInsertPortalProductSku(List<PortalProductSku> list);

    /**
     * 修改产品SKU
     *
     * @param portalProductSku 产品SKU
     * @return 结果
     */
    public int updatePortalProductSku(PortalProductSku portalProductSku);

    /**
     * 批量删除产品SKU
     *
     * @param skuIds 需要删除的SKU ID
     * @return 结果
     */
    public int deletePortalProductSkuBySkuIds(Long[] skuIds);

    /**
     * 删除产品SKU信息
     *
     * @param skuId SKU ID
     * @return 结果
     */
    public int deletePortalProductSkuBySkuId(Long skuId);
}
