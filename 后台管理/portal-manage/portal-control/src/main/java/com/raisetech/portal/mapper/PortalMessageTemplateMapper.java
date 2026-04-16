package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalMessageTemplate;

/**
 * 留言回复模板Mapper接口
 *
 * @author 王有政
 */
public interface PortalMessageTemplateMapper {
    /**
     * 查询模板
     *
     * @param templateId 模板ID
     * @return 模板对象
     */
    public PortalMessageTemplate selectPortalMessageTemplateByTemplateId(Long templateId);

    /**
     * 查询模板列表
     *
     * @param portalMessageTemplate 模板查询条件
     * @return 模板集合
     */
    public List<PortalMessageTemplate> selectPortalMessageTemplateList(PortalMessageTemplate portalMessageTemplate);

    /**
     * 新增模板
     *
     * @param portalMessageTemplate 模板对象
     * @return 结果
     */
    public int insertPortalMessageTemplate(PortalMessageTemplate portalMessageTemplate);

    /**
     * 修改模板
     *
     * @param portalMessageTemplate 模板对象
     * @return 结果
     */
    public int updatePortalMessageTemplate(PortalMessageTemplate portalMessageTemplate);

    /**
     * 删除模板（逻辑删除）
     *
     * @param templateId 模板ID
     * @return 结果
     */
    public int deletePortalMessageTemplateByTemplateId(Long templateId);

    /**
     * 批量删除模板（逻辑删除）
     *
     * @param templateIds 需要删除的模板ID数组
     * @return 结果
     */
    public int deletePortalMessageTemplateByTemplateIds(Long[] templateIds);
}
