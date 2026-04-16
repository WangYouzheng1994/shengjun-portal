package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.domain.PortalProduct;
import com.raisetech.portal.domain.PortalProductAttrValue;
import com.raisetech.portal.domain.PortalProductSku;
import com.raisetech.portal.mapper.PortalProductMapper;
import com.raisetech.portal.mapper.PortalProductAttrValueMapper;
import com.raisetech.portal.mapper.PortalProductSkuMapper;
import com.raisetech.portal.service.IPortalProductService;

/**
 * 产品Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductServiceImpl implements IPortalProductService {

    /**
     * 产品Mapper接口
     */
    @Autowired
    private PortalProductMapper portalProductMapper;

    /**
     * 产品属性值Mapper接口
     */
    @Autowired
    private PortalProductAttrValueMapper portalProductAttrValueMapper;

    /**
     * 产品SKUMapper接口
     */
    @Autowired
    private PortalProductSkuMapper portalProductSkuMapper;

    /**
     * 查询产品
     *
     * @param productId 产品ID
     * @return 产品
     */
    @Override
    public PortalProduct selectPortalProductByProductId(Long productId) {
        PortalProduct product = portalProductMapper.selectPortalProductByProductId(productId);
        if (product != null) {
            List<PortalProductAttrValue> attrValueList = portalProductAttrValueMapper.selectPortalProductAttrValueListByProductId(productId);
            product.setAttrValueList(attrValueList);
            List<PortalProductSku> skuList = portalProductSkuMapper.selectPortalProductSkuListByProductId(productId);
            product.setSkuList(skuList);
        }
        return product;
    }

    /**
     * 查询产品列表
     *
     * @param portalProduct 产品
     * @return 产品集合
     */
    @Override
    public List<PortalProduct> selectPortalProductList(PortalProduct portalProduct) {
        return portalProductMapper.selectPortalProductList(portalProduct);
    }

    /**
     * 新增产品（包含属性值和SKU）
     *
     * @param portalProduct 产品
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPortalProduct(PortalProduct portalProduct) {
        if (StringUtils.isEmpty(portalProduct.getProductName())) {
            throw new ServiceException("产品名称不能为空");
        }
        if (portalProduct.getCategoryId() == null) {
            throw new ServiceException("请选择产品分类");
        }
        portalProduct.setCreateTime(DateUtils.getNowDate());
        if (portalProduct.getViewCount() == null) {
            portalProduct.setViewCount(0);
        }
        if (portalProduct.getSalesCount() == null) {
            portalProduct.setSalesCount(0);
        }
        if (portalProduct.getCollectCount() == null) {
            portalProduct.setCollectCount(0);
        }
        if (portalProduct.getCommentCount() == null) {
            portalProduct.setCommentCount(0);
        }
        int rows = portalProductMapper.insertPortalProduct(portalProduct);
        Long productId = portalProduct.getProductId();
        insertAttrValues(productId, portalProduct.getAttrValueList());
        insertSkus(productId, portalProduct.getSkuList());
        updateSkuCount(productId);
        return rows;
    }

    /**
     * 修改产品（包含属性值和SKU）
     *
     * @param portalProduct 产品
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePortalProduct(PortalProduct portalProduct) {
        if (StringUtils.isEmpty(portalProduct.getProductName())) {
            throw new ServiceException("产品名称不能为空");
        }
        if (portalProduct.getCategoryId() == null) {
            throw new ServiceException("请选择产品分类");
        }
        portalProduct.setUpdateTime(DateUtils.getNowDate());
        int rows = portalProductMapper.updatePortalProduct(portalProduct);
        Long productId = portalProduct.getProductId();
        portalProductAttrValueMapper.deletePortalProductAttrValueByProductId(productId);
        portalProductSkuMapper.deletePortalProductSkuByProductId(productId);
        insertAttrValues(productId, portalProduct.getAttrValueList());
        insertSkus(productId, portalProduct.getSkuList());
        updateSkuCount(productId);
        return rows;
    }

    /**
     * 批量删除产品
     *
     * @param productIds 需要删除的产品ID
     * @return 结果
     */
    @Override
    public int deletePortalProductByProductIds(Long[] productIds) {
        for (Long productId : productIds) {
            portalProductAttrValueMapper.deletePortalProductAttrValueByProductId(productId);
            portalProductSkuMapper.deletePortalProductSkuByProductId(productId);
        }
        return portalProductMapper.deletePortalProductByProductIds(productIds);
    }

    /**
     * 删除产品信息
     *
     * @param productId 产品ID
     * @return 结果
     */
    @Override
    public int deletePortalProductByProductId(Long productId) {
        portalProductAttrValueMapper.deletePortalProductAttrValueByProductId(productId);
        portalProductSkuMapper.deletePortalProductSkuByProductId(productId);
        return portalProductMapper.deletePortalProductByProductId(productId);
    }

    /**
     * 上架产品
     *
     * @param portalProduct 产品
     * @return 结果
     */
    @Override
    public int publishProduct(PortalProduct portalProduct) {
        portalProduct.setStatus("1");
        portalProduct.setPublishTime(DateUtils.getNowDate());
        portalProduct.setUpdateTime(DateUtils.getNowDate());
        return portalProductMapper.updatePortalProduct(portalProduct);
    }

    /**
     * 下架产品
     *
     * @param portalProduct 产品
     * @return 结果
     */
    @Override
    public int offlineProduct(PortalProduct portalProduct) {
        portalProduct.setStatus("0");
        portalProduct.setUpdateTime(DateUtils.getNowDate());
        return portalProductMapper.updatePortalProduct(portalProduct);
    }

    /**
     * 插入产品属性值列表
     *
     * @param productId 产品ID
     * @param attrValueList 属性值列表
     */
    private void insertAttrValues(Long productId, List<PortalProductAttrValue> attrValueList) {
        if (attrValueList != null && !attrValueList.isEmpty()) {
            for (PortalProductAttrValue attrValue : attrValueList) {
                attrValue.setProductId(productId);
                attrValue.setCreateTime(DateUtils.getNowDate());
                portalProductAttrValueMapper.insertPortalProductAttrValue(attrValue);
            }
        }
    }

    /**
     * 插入产品SKU列表
     *
     * @param productId 产品ID
     * @param skuList SKU列表
     */
    private void insertSkus(Long productId, List<PortalProductSku> skuList) {
        if (skuList != null && !skuList.isEmpty()) {
            for (PortalProductSku sku : skuList) {
                sku.setProductId(productId);
                sku.setCreateTime(DateUtils.getNowDate());
                portalProductSkuMapper.insertPortalProductSku(sku);
            }
        }
    }

    /**
     * 更新产品的SKU数量
     *
     * @param productId 产品ID
     */
    private void updateSkuCount(Long productId) {
        PortalProduct product = new PortalProduct();
        product.setProductId(productId);
        List<PortalProductSku> skuList = portalProductSkuMapper.selectPortalProductSkuListByProductId(productId);
        if (skuList != null && !skuList.isEmpty()) {
            product.setSkuCount(skuList.size());
        } else {
            product.setSkuCount(0);
        }
        portalProductMapper.updatePortalProduct(product);
    }
}
