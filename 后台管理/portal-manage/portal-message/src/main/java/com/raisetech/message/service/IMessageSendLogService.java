package com.raisetech.message.service;

import java.util.List;
import com.raisetech.message.domain.PortalMessageSendLog;

/**
 * 消息发送记录Service接口
 *
 * @author 王有政
 */
public interface IMessageSendLogService {
    /**
     * 查询消息发送记录
     *
     * @param logId 日志ID
     * @return 消息发送记录
     */
    public PortalMessageSendLog selectMessageSendLogByLogId(Long logId);

    /**
     * 查询消息发送记录列表
     *
     * @param log 消息发送记录
     * @return 消息发送记录集合
     */
    public List<PortalMessageSendLog> selectMessageSendLogList(PortalMessageSendLog log);

    /**
     * 新增消息发送记录
     *
     * @param log 消息发送记录
     * @return 结果
     */
    public int insertMessageSendLog(PortalMessageSendLog log);

    /**
     * 修改消息发送记录
     *
     * @param log 消息发送记录
     * @return 结果
     */
    public int updateMessageSendLog(PortalMessageSendLog log);

    /**
     * 删除消息发送记录
     *
     * @param logId 日志ID
     * @return 结果
     */
    public int deleteMessageSendLogByLogId(Long logId);

    /**
     * 批量删除消息发送记录
     *
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMessageSendLogByLogIds(Long[] logIds);
}
