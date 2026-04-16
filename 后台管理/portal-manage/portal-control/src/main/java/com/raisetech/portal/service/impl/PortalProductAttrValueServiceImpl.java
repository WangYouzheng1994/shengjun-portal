package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.portal.domain.PortalProductAttrValue;
import com.raisetech.portal.mapper.PortalProductAttrValueMapper;
import com.raisetech.portal.service.IPortalProductAttrValueService;

/**
 * 产品属性值Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductAttrValueServiceImpl implements IPortalProductAttrValueService {

    /**
     * 产品属性值Mapper接口
     */
    @Autowired
    private PortalProductAttrValueMapper portalProductAttrValueMapper;

    /**
     * 查询产品属性值
     *
     * @param valueId 属性值ID
     * @return 产品属性值
     */
    @Override
    public PortalProductAttrValue selectPortalProductAttrValueByValueId(Long valueId) {
        return portalProductAttrValueMapper.selectPortalProductAttrValueByValueId(valueId);
    }

    /**
     * 查询产品属性值列表
     *
     * @param portalProductAttrValue 产品属性值
     * @return 产品属性值集合
     */
    @Override
    public List<PortalProductAttrValue> selectPortalProductAttrValueList(PortalProductAttrValue portalProductAttrValue) {
        return portalProductAttrValueMapper.selectPortalProductAttrValueList(portalProductAttrValue);
    }

    /**
     * 根据产品ID查询属性值列表
     *
     * @param productId 产品ID
     * @return 产品属性值集合
     */
    @Override
    public List<PortalProductAttrValue> selectPortalProductAttrValueListByProductId(Long productId) {
        return portalProductAttrValueMapper.selectPortalProductAttrValueListByProductId(productId);
    }

    /**
     * 新增产品属性值
     *
     * @param portalProductAttrValue 产品属性值
     * @return 结果
     */
    @Override
    public int insertPortalProductAttrValue(PortalProductAttrValue portalProductAttrValue) {
        portalProductAttrValue.setCreateTime(DateUtils.getNowDate());
        return portalProductAttrValueMapper.insertPortalProductAttrValue(portalProductAttrValue);
    }

    /**
     * 批量新增产品属性值
     *
     * @param list 产品属性值列表
     * @return 结果
     */
    @Override
    public int batchInsertPortalProductAttrValue(List<PortalProductAttrValue> list) {
        if (list != null && !list.isEmpty()) {
            for (PortalProductAttrValue attrValue : list) {
                attrValue.setCreateTime(DateUtils.getNowDate());
            }
            return portalProductAttrValueMapper.batchInsertPortalProductAttrValue(list);
        }
        return 0;
    }

    /**
     * 修改产品属性值
     *
     * @param portalProductAttrValue 产品属性值
     * @return 结果
     */
    @Override
    public int updatePortalProductAttrValue(PortalProductAttrValue portalProductAttrValue) {
        portalProductAttrValue.setUpdateTime(DateUtils.getNowDate());
        return portalProductAttrValueMapper.updatePortalProductAttrValue(portalProductAttrValue);
    }

    /**
     * 批量删除产品属性值
     *
     * @param valueIds 需要删除的属性值ID
     * @return 结果
     */
    @Override
    public int deletePortalProductAttrValueByValueIds(Long[] valueIds) {
        return portalProductAttrValueMapper.deletePortalProductAttrValueByValueIds(valueIds);
    }

    /**
     * 删除产品属性值信息
     *
     * @param valueId 属性值ID
     * @return 结果
     */
    @Override
    public int deletePortalProductAttrValueByValueId(Long valueId) {
        return portalProductAttrValueMapper.deletePortalProductAttrValueByValueId(valueId);
    }
}
