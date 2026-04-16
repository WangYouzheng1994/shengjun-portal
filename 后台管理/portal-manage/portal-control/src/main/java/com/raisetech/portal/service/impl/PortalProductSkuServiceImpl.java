package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.portal.domain.PortalProductSku;
import com.raisetech.portal.mapper.PortalProductSkuMapper;
import com.raisetech.portal.service.IPortalProductSkuService;

/**
 * 产品SKUService业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductSkuServiceImpl implements IPortalProductSkuService {

    /**
     * 产品SKUMapper接口
     */
    @Autowired
    private PortalProductSkuMapper portalProductSkuMapper;

    /**
     * 查询产品SKU
     *
     * @param skuId SKU ID
     * @return 产品SKU
     */
    @Override
    public PortalProductSku selectPortalProductSkuBySkuId(Long skuId) {
        return portalProductSkuMapper.selectPortalProductSkuBySkuId(skuId);
    }

    /**
     * 查询产品SKU列表
     *
     * @param portalProductSku 产品SKU
     * @return 产品SKU集合
     */
    @Override
    public List<PortalProductSku> selectPortalProductSkuList(PortalProductSku portalProductSku) {
        return portalProductSkuMapper.selectPortalProductSkuList(portalProductSku);
    }

    /**
     * 根据产品ID查询SKU列表
     *
     * @param productId 产品ID
     * @return 产品SKU集合
     */
    @Override
    public List<PortalProductSku> selectPortalProductSkuListByProductId(Long productId) {
        return portalProductSkuMapper.selectPortalProductSkuListByProductId(productId);
    }

    /**
     * 新增产品SKU
     *
     * @param portalProductSku 产品SKU
     * @return 结果
     */
    @Override
    public int insertPortalProductSku(PortalProductSku portalProductSku) {
        portalProductSku.setCreateTime(DateUtils.getNowDate());
        return portalProductSkuMapper.insertPortalProductSku(portalProductSku);
    }

    /**
     * 批量新增产品SKU
     *
     * @param list 产品SKU列表
     * @return 结果
     */
    @Override
    public int batchInsertPortalProductSku(List<PortalProductSku> list) {
        if (list != null && !list.isEmpty()) {
            for (PortalProductSku sku : list) {
                sku.setCreateTime(DateUtils.getNowDate());
            }
            return portalProductSkuMapper.batchInsertPortalProductSku(list);
        }
        return 0;
    }

    /**
     * 修改产品SKU
     *
     * @param portalProductSku 产品SKU
     * @return 结果
     */
    @Override
    public int updatePortalProductSku(PortalProductSku portalProductSku) {
        portalProductSku.setUpdateTime(DateUtils.getNowDate());
        return portalProductSkuMapper.updatePortalProductSku(portalProductSku);
    }

    /**
     * 批量删除产品SKU
     *
     * @param skuIds 需要删除的SKU ID
     * @return 结果
     */
    @Override
    public int deletePortalProductSkuBySkuIds(Long[] skuIds) {
        return portalProductSkuMapper.deletePortalProductSkuBySkuIds(skuIds);
    }

    /**
     * 删除产品SKU信息
     *
     * @param skuId SKU ID
     * @return 结果
     */
    @Override
    public int deletePortalProductSkuBySkuId(Long skuId) {
        return portalProductSkuMapper.deletePortalProductSkuBySkuId(skuId);
    }
}
