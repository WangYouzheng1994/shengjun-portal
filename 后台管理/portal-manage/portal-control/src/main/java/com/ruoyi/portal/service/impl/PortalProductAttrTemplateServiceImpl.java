package com.ruoyi.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.portal.domain.PortalProductAttrDef;
import com.ruoyi.portal.domain.PortalProductAttrTemplate;
import com.ruoyi.portal.mapper.PortalProductAttrDefMapper;
import com.ruoyi.portal.mapper.PortalProductAttrTemplateMapper;
import com.ruoyi.portal.service.IPortalProductAttrTemplateService;

/**
 * 产品属性模板Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductAttrTemplateServiceImpl implements IPortalProductAttrTemplateService {

    /**
     * 产品属性模板Mapper接口
     */
    @Autowired
    private PortalProductAttrTemplateMapper portalProductAttrTemplateMapper;

    /**
     * 产品属性定义Mapper接口
     */
    @Autowired
    private PortalProductAttrDefMapper portalProductAttrDefMapper;

    /**
     * 查询产品属性模板
     *
     * @param templateId 模板ID
     * @return 产品属性模板
     */
    @Override
    public PortalProductAttrTemplate selectPortalProductAttrTemplateByTemplateId(Long templateId) {
        PortalProductAttrTemplate template = portalProductAttrTemplateMapper.selectPortalProductAttrTemplateByTemplateId(templateId);
        if (template != null) {
            List<PortalProductAttrDef> attrDefs = portalProductAttrDefMapper.selectPortalProductAttrDefListByTemplateId(templateId);
            template.setAttrDefs(attrDefs);
        }
        return template;
    }

    /**
     * 查询产品属性模板列表
     *
     * @param portalProductAttrTemplate 产品属性模板
     * @return 产品属性模板集合
     */
    @Override
    public List<PortalProductAttrTemplate> selectPortalProductAttrTemplateList(PortalProductAttrTemplate portalProductAttrTemplate) {
        return portalProductAttrTemplateMapper.selectPortalProductAttrTemplateList(portalProductAttrTemplate);
    }

    /**
     * 新增产品属性模板（包含属性定义）
     *
     * @param portalProductAttrTemplate 产品属性模板
     * @return 结果
     */
    @Override
    @Transactional
    public int insertPortalProductAttrTemplate(PortalProductAttrTemplate portalProductAttrTemplate) {
        if (StringUtils.isEmpty(portalProductAttrTemplate.getTemplateName())) {
            throw new ServiceException("模板名称不能为空");
        }
        if (StringUtils.isEmpty(portalProductAttrTemplate.getTemplateCode())) {
            throw new ServiceException("模板编码不能为空");
        }
        portalProductAttrTemplate.setCreateTime(DateUtils.getNowDate());
        int rows = portalProductAttrTemplateMapper.insertPortalProductAttrTemplate(portalProductAttrTemplate);
        insertAttrDefs(portalProductAttrTemplate);
        return rows;
    }

    /**
     * 修改产品属性模板（包含属性定义）
     *
     * @param portalProductAttrTemplate 产品属性模板
     * @return 结果
     */
    @Override
    @Transactional
    public int updatePortalProductAttrTemplate(PortalProductAttrTemplate portalProductAttrTemplate) {
        if (StringUtils.isEmpty(portalProductAttrTemplate.getTemplateName())) {
            throw new ServiceException("模板名称不能为空");
        }
        if (StringUtils.isEmpty(portalProductAttrTemplate.getTemplateCode())) {
            throw new ServiceException("模板编码不能为空");
        }
        portalProductAttrTemplate.setUpdateTime(DateUtils.getNowDate());
        int rows = portalProductAttrTemplateMapper.updatePortalProductAttrTemplate(portalProductAttrTemplate);
        portalProductAttrDefMapper.deletePortalProductAttrDefByTemplateId(portalProductAttrTemplate.getTemplateId());
        insertAttrDefs(portalProductAttrTemplate);
        return rows;
    }

    /**
     * 批量删除产品属性模板
     *
     * @param templateIds 需要删除的模板ID
     * @return 结果
     */
    @Override
    public int deletePortalProductAttrTemplateByTemplateIds(Long[] templateIds) {
        for (Long templateId : templateIds) {
            portalProductAttrDefMapper.deletePortalProductAttrDefByTemplateId(templateId);
        }
        return portalProductAttrTemplateMapper.deletePortalProductAttrTemplateByTemplateIds(templateIds);
    }

    /**
     * 删除产品属性模板信息
     *
     * @param templateId 模板ID
     * @return 结果
     */
    @Override
    public int deletePortalProductAttrTemplateByTemplateId(Long templateId) {
        portalProductAttrDefMapper.deletePortalProductAttrDefByTemplateId(templateId);
        return portalProductAttrTemplateMapper.deletePortalProductAttrTemplateByTemplateId(templateId);
    }

    /**
     * 插入属性定义列表
     *
     * @param template 属性模板
     */
    private void insertAttrDefs(PortalProductAttrTemplate template) {
        List<PortalProductAttrDef> attrDefs = template.getAttrDefs();
        if (attrDefs != null && !attrDefs.isEmpty()) {
            for (PortalProductAttrDef attrDef : attrDefs) {
                attrDef.setTemplateId(template.getTemplateId());
                attrDef.setDelFlag("0");
                attrDef.setCreateTime(DateUtils.getNowDate());
                portalProductAttrDefMapper.insertPortalProductAttrDef(attrDef);
            }
        }
    }
}
