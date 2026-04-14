package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalProductAttrDef;

/**
 * 产品属性定义Service接口
 *
 * @author 王有政
 */
public interface IPortalProductAttrDefService {
    /**
     * 查询产品属性定义
     *
     * @param attrDefId 属性定义ID
     * @return 产品属性定义
     */
    public PortalProductAttrDef selectPortalProductAttrDefByAttrDefId(Long attrDefId);

    /**
     * 查询产品属性定义列表
     *
     * @param portalProductAttrDef 产品属性定义
     * @return 产品属性定义集合
     */
    public List<PortalProductAttrDef> selectPortalProductAttrDefList(PortalProductAttrDef portalProductAttrDef);

    /**
     * 根据模板ID查询属性定义列表
     *
     * @param templateId 模板ID
     * @return 产品属性定义集合
     */
    public List<PortalProductAttrDef> selectPortalProductAttrDefListByTemplateId(Long templateId);

    /**
     * 新增产品属性定义
     *
     * @param portalProductAttrDef 产品属性定义
     * @return 结果
     */
    public int insertPortalProductAttrDef(PortalProductAttrDef portalProductAttrDef);

    /**
     * 修改产品属性定义
     *
     * @param portalProductAttrDef 产品属性定义
     * @return 结果
     */
    public int updatePortalProductAttrDef(PortalProductAttrDef portalProductAttrDef);

    /**
     * 批量删除产品属性定义
     *
     * @param attrDefIds 需要删除的属性定义ID
     * @return 结果
     */
    public int deletePortalProductAttrDefByAttrDefIds(Long[] attrDefIds);

    /**
     * 删除产品属性定义信息
     *
     * @param attrDefId 属性定义ID
     * @return 结果
     */
    public int deletePortalProductAttrDefByAttrDefId(Long attrDefId);
}
