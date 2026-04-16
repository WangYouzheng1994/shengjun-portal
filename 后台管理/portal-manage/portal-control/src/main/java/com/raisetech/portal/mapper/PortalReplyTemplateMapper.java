package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalReplyTemplate;

/**
 * 留言回复模板Mapper接口
 *
 * @author 王有政
 */
public interface PortalReplyTemplateMapper {
    /**
     * 查询模板
     *
     * @param templateId 模板ID
     * @return 模板对象
     */
    public PortalReplyTemplate selectPortalReplyTemplateByTemplateId(Long templateId);

    /**
     * 查询模板列表
     *
     * @param portalReplyTemplate 模板查询条件
     * @return 模板集合
     */
    public List<PortalReplyTemplate> selectPortalReplyTemplateList(PortalReplyTemplate portalReplyTemplate);

    /**
     * 新增模板
     *
     * @param portalReplyTemplate 模板对象
     * @return 结果
     */
    public int insertPortalReplyTemplate(PortalReplyTemplate portalReplyTemplate);

    /**
     * 修改模板
     *
     * @param portalReplyTemplate 模板对象
     * @return 结果
     */
    public int updatePortalReplyTemplate(PortalReplyTemplate portalReplyTemplate);

    /**
     * 删除模板（逻辑删除）
     *
     * @param templateId 模板ID
     * @return 结果
     */
    public int deletePortalReplyTemplateByTemplateId(Long templateId);

    /**
     * 批量删除模板（逻辑删除）
     *
     * @param templateIds 需要删除的模板ID数组
     * @return 结果
     */
    public int deletePortalReplyTemplateByTemplateIds(Long[] templateIds);
}
