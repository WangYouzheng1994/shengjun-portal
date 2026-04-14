package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalProductAttrValue;

/**
 * 产品属性值Service接口
 *
 * @author 王有政
 */
public interface IPortalProductAttrValueService {
    /**
     * 查询产品属性值
     *
     * @param valueId 属性值ID
     * @return 产品属性值
     */
    public PortalProductAttrValue selectPortalProductAttrValueByValueId(Long valueId);

    /**
     * 查询产品属性值列表
     *
     * @param portalProductAttrValue 产品属性值
     * @return 产品属性值集合
     */
    public List<PortalProductAttrValue> selectPortalProductAttrValueList(PortalProductAttrValue portalProductAttrValue);

    /**
     * 根据产品ID查询属性值列表
     *
     * @param productId 产品ID
     * @return 产品属性值集合
     */
    public List<PortalProductAttrValue> selectPortalProductAttrValueListByProductId(Long productId);

    /**
     * 新增产品属性值
     *
     * @param portalProductAttrValue 产品属性值
     * @return 结果
     */
    public int insertPortalProductAttrValue(PortalProductAttrValue portalProductAttrValue);

    /**
     * 批量新增产品属性值
     *
     * @param list 产品属性值列表
     * @return 结果
     */
    public int batchInsertPortalProductAttrValue(List<PortalProductAttrValue> list);

    /**
     * 修改产品属性值
     *
     * @param portalProductAttrValue 产品属性值
     * @return 结果
     */
    public int updatePortalProductAttrValue(PortalProductAttrValue portalProductAttrValue);

    /**
     * 批量删除产品属性值
     *
     * @param valueIds 需要删除的属性值ID
     * @return 结果
     */
    public int deletePortalProductAttrValueByValueIds(Long[] valueIds);

    /**
     * 删除产品属性值信息
     *
     * @param valueId 属性值ID
     * @return 结果
     */
    public int deletePortalProductAttrValueByValueId(Long valueId);
}
