package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.domain.PortalProductAttrDef;
import com.raisetech.portal.mapper.PortalProductAttrDefMapper;
import com.raisetech.portal.service.IPortalProductAttrDefService;

/**
 * 产品属性定义Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductAttrDefServiceImpl implements IPortalProductAttrDefService {

    /**
     * 产品属性定义Mapper接口
     */
    @Autowired
    private PortalProductAttrDefMapper portalProductAttrDefMapper;

    /**
     * 查询产品属性定义
     *
     * @param attrDefId 属性定义ID
     * @return 产品属性定义
     */
    @Override
    public PortalProductAttrDef selectPortalProductAttrDefByAttrDefId(Long attrDefId) {
        return portalProductAttrDefMapper.selectPortalProductAttrDefByAttrDefId(attrDefId);
    }

    /**
     * 查询产品属性定义列表
     *
     * @param portalProductAttrDef 产品属性定义
     * @return 产品属性定义集合
     */
    @Override
    public List<PortalProductAttrDef> selectPortalProductAttrDefList(PortalProductAttrDef portalProductAttrDef) {
        return portalProductAttrDefMapper.selectPortalProductAttrDefList(portalProductAttrDef);
    }

    /**
     * 根据模板ID查询属性定义列表
     *
     * @param templateId 模板ID
     * @return 产品属性定义集合
     */
    @Override
    public List<PortalProductAttrDef> selectPortalProductAttrDefListByTemplateId(Long templateId) {
        return portalProductAttrDefMapper.selectPortalProductAttrDefListByTemplateId(templateId);
    }

    /**
     * 新增产品属性定义
     *
     * @param portalProductAttrDef 产品属性定义
     * @return 结果
     */
    @Override
    public int insertPortalProductAttrDef(PortalProductAttrDef portalProductAttrDef) {
        if (StringUtils.isEmpty(portalProductAttrDef.getAttrName())) {
            throw new ServiceException("属性名称不能为空");
        }
        if (StringUtils.isEmpty(portalProductAttrDef.getAttrCode())) {
            throw new ServiceException("属性编码不能为空");
        }
        if (portalProductAttrDef.getTemplateId() == null) {
            throw new ServiceException("请选择所属模板");
        }
        portalProductAttrDef.setCreateTime(DateUtils.getNowDate());
        return portalProductAttrDefMapper.insertPortalProductAttrDef(portalProductAttrDef);
    }

    /**
     * 修改产品属性定义
     *
     * @param portalProductAttrDef 产品属性定义
     * @return 结果
     */
    @Override
    public int updatePortalProductAttrDef(PortalProductAttrDef portalProductAttrDef) {
        if (StringUtils.isEmpty(portalProductAttrDef.getAttrName())) {
            throw new ServiceException("属性名称不能为空");
        }
        if (StringUtils.isEmpty(portalProductAttrDef.getAttrCode())) {
            throw new ServiceException("属性编码不能为空");
        }
        portalProductAttrDef.setUpdateTime(DateUtils.getNowDate());
        return portalProductAttrDefMapper.updatePortalProductAttrDef(portalProductAttrDef);
    }

    /**
     * 批量删除产品属性定义
     *
     * @param attrDefIds 需要删除的属性定义ID
     * @return 结果
     */
    @Override
    public int deletePortalProductAttrDefByAttrDefIds(Long[] attrDefIds) {
        return portalProductAttrDefMapper.deletePortalProductAttrDefByAttrDefIds(attrDefIds);
    }

    /**
     * 删除产品属性定义信息
     *
     * @param attrDefId 属性定义ID
     * @return 结果
     */
    @Override
    public int deletePortalProductAttrDefByAttrDefId(Long attrDefId) {
        return portalProductAttrDefMapper.deletePortalProductAttrDefByAttrDefId(attrDefId);
    }
}
