package com.ruoyi.message.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.message.mapper.MessageSendLogMapper;
import com.ruoyi.message.domain.PortalMessageSendLog;
import com.ruoyi.message.service.IMessageSendLogService;

/**
 * 消息发送记录Service业务层处理
 *
 * @author 王有政
 */
@Service
public class MessageSendLogServiceImpl implements IMessageSendLogService {

    @Autowired
    private MessageSendLogMapper messageSendLogMapper;

    /**
     * 查询消息发送记录
     *
     * @param logId 日志ID
     * @return 消息发送记录
     */
    @Override
    public PortalMessageSendLog selectMessageSendLogByLogId(Long logId) {
        return messageSendLogMapper.selectMessageSendLogByLogId(logId);
    }

    /**
     * 查询消息发送记录列表
     *
     * @param log 消息发送记录
     * @return 消息发送记录
     */
    @Override
    public List<PortalMessageSendLog> selectMessageSendLogList(PortalMessageSendLog log) {
        return messageSendLogMapper.selectMessageSendLogList(log);
    }

    /**
     * 新增消息发送记录
     *
     * @param log 消息发送记录
     * @return 结果
     */
    @Override
    public int insertMessageSendLog(PortalMessageSendLog log) {
        log.setCreateTime(DateUtils.getNowDate());
        return messageSendLogMapper.insertMessageSendLog(log);
    }

    /**
     * 修改消息发送记录
     *
     * @param log 消息发送记录
     * @return 结果
     */
    @Override
    public int updateMessageSendLog(PortalMessageSendLog log) {
        log.setUpdateTime(DateUtils.getNowDate());
        return messageSendLogMapper.updateMessageSendLog(log);
    }

    /**
     * 删除消息发送记录
     *
     * @param logId 日志ID
     * @return 结果
     */
    @Override
    public int deleteMessageSendLogByLogId(Long logId) {
        return messageSendLogMapper.deleteMessageSendLogByLogId(logId);
    }

    /**
     * 批量删除消息发送记录
     *
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    @Override
    public int deleteMessageSendLogByLogIds(Long[] logIds) {
        return messageSendLogMapper.deleteMessageSendLogByLogIds(logIds);
    }
}
