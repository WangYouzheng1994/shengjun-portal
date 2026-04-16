package com.raisetech.message.service.impl;

import java.util.List;
import com.raisetech.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.message.mapper.MessageTemplateMapper;
import com.raisetech.message.domain.PortalMessageTemplate;
import com.raisetech.message.service.IMessageTemplateService;

/**
 * 消息模板Service业务层处理
 *
 * @author 王有政
 */
@Service
public class MessageTemplateServiceImpl implements IMessageTemplateService {

    @Autowired
    private MessageTemplateMapper messageTemplateMapper;

    /**
     * 查询消息模板
     *
     * @param templateId 模板ID
     * @return 消息模板
     */
    @Override
    public PortalMessageTemplate selectMessageTemplateByTemplateId(Long templateId) {
        return messageTemplateMapper.selectMessageTemplateByTemplateId(templateId);
    }

    /**
     * 根据模板编码查询消息模板
     *
     * @param templateCode 模板编码
     * @return 消息模板
     */
    @Override
    public PortalMessageTemplate selectMessageTemplateByTemplateCode(String templateCode) {
        return messageTemplateMapper.selectMessageTemplateByTemplateCode(templateCode);
    }

    /**
     * 查询消息模板列表
     *
     * @param template 消息模板
     * @return 消息模板
     */
    @Override
    public List<PortalMessageTemplate> selectMessageTemplateList(PortalMessageTemplate template) {
        return messageTemplateMapper.selectMessageTemplateList(template);
    }

    /**
     * 新增消息模板
     *
     * @param template 消息模板
     * @return 结果
     */
    @Override
    public int insertMessageTemplate(PortalMessageTemplate template) {
        template.setCreateTime(DateUtils.getNowDate());
        template.setDelFlag("0");
        return messageTemplateMapper.insertMessageTemplate(template);
    }

    /**
     * 修改消息模板
     *
     * @param template 消息模板
     * @return 结果
     */
    @Override
    public int updateMessageTemplate(PortalMessageTemplate template) {
        template.setUpdateTime(DateUtils.getNowDate());
        return messageTemplateMapper.updateMessageTemplate(template);
    }

    /**
     * 删除消息模板
     *
     * @param templateId 模板ID
     * @return 结果
     */
    @Override
    public int deleteMessageTemplateByTemplateId(Long templateId) {
        return messageTemplateMapper.deleteMessageTemplateByTemplateId(templateId);
    }

    /**
     * 批量删除消息模板
     *
     * @param templateIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteMessageTemplateByTemplateIds(Long[] templateIds) {
        return messageTemplateMapper.deleteMessageTemplateByTemplateIds(templateIds);
    }
}