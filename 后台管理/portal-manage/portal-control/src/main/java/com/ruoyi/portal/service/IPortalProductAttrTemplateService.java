package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalProductAttrTemplate;

/**
 * 产品属性模板Service接口
 *
 * @author 王有政
 */
public interface IPortalProductAttrTemplateService {
    /**
     * 查询产品属性模板
     *
     * @param templateId 模板ID
     * @return 产品属性模板
     */
    public PortalProductAttrTemplate selectPortalProductAttrTemplateByTemplateId(Long templateId);

    /**
     * 查询产品属性模板列表
     *
     * @param portalProductAttrTemplate 产品属性模板
     * @return 产品属性模板集合
     */
    public List<PortalProductAttrTemplate> selectPortalProductAttrTemplateList(PortalProductAttrTemplate portalProductAttrTemplate);

    /**
     * 新增产品属性模板
     *
     * @param portalProductAttrTemplate 产品属性模板
     * @return 结果
     */
    public int insertPortalProductAttrTemplate(PortalProductAttrTemplate portalProductAttrTemplate);

    /**
     * 修改产品属性模板
     *
     * @param portalProductAttrTemplate 产品属性模板
     * @return 结果
     */
    public int updatePortalProductAttrTemplate(PortalProductAttrTemplate portalProductAttrTemplate);

    /**
     * 批量删除产品属性模板
     *
     * @param templateIds 需要删除的模板ID
     * @return 结果
     */
    public int deletePortalProductAttrTemplateByTemplateIds(Long[] templateIds);

    /**
     * 删除产品属性模板信息
     *
     * @param templateId 模板ID
     * @return 结果
     */
    public int deletePortalProductAttrTemplateByTemplateId(Long templateId);
}
