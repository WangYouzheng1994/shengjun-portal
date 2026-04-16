package com.raisetech.message.mapper;

import java.util.List;
import com.raisetech.message.domain.PortalMessageTemplate;

/**
 * 消息模板Mapper接口
 *
 * @author 王有政
 */
public interface MessageTemplateMapper {
    /**
     * 查询消息模板
     *
     * @param templateId 模板ID
     * @return 消息模板
     */
    public PortalMessageTemplate selectMessageTemplateByTemplateId(Long templateId);

    /**
     * 根据模板编码查询消息模板
     *
     * @param templateCode 模板编码
     * @return 消息模板
     */
    public PortalMessageTemplate selectMessageTemplateByTemplateCode(String templateCode);

    /**
     * 查询消息模板列表
     *
     * @param template 消息模板
     * @return 消息模板集合
     */
    public List<PortalMessageTemplate> selectMessageTemplateList(PortalMessageTemplate template);

    /**
     * 新增消息模板
     *
     * @param template 消息模板
     * @return 结果
     */
    public int insertMessageTemplate(PortalMessageTemplate template);

    /**
     * 修改消息模板
     *
     * @param template 消息模板
     * @return 结果
     */
    public int updateMessageTemplate(PortalMessageTemplate template);

    /**
     * 删除消息模板
     *
     * @param templateId 模板ID
     * @return 结果
     */
    public int deleteMessageTemplateByTemplateId(Long templateId);

    /**
     * 批量删除消息模板
     *
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageTemplateByTemplateIds(Long[] templateIds);
}