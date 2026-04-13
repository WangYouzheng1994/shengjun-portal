package com.ruoyi.portal.service;

import java.util.List;
import com.ruoyi.portal.domain.PortalMessage;
import com.ruoyi.portal.domain.PortalCustomer;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 官网消息通知Service接口
 *
 * @author 王有政
 */
public interface IPortalMessageService {
    /**
     * 查询消息通知
     *
     * @param messageId 消息通知ID
     * @return 消息通知对象
     */
    public PortalMessage selectPortalMessageByMessageId(Long messageId);

    /**
     * 查询消息通知列表
     *
     * @param portalMessage 消息通知查询条件
     * @return 消息通知集合
     */
    public List<PortalMessage> selectPortalMessageList(PortalMessage portalMessage);

    /**
     * 新增消息通知
     *
     * @param portalMessage 消息通知对象
     * @return 结果
     */
    public int insertPortalMessage(PortalMessage portalMessage);

    /**
     * 修改消息通知
     *
     * @param portalMessage 消息通知对象
     * @return 结果
     */
    public int updatePortalMessage(PortalMessage portalMessage);

    /**
     * 批量删除消息通知（逻辑删除）
     *
     * @param messageIds 需要删除的消息通知ID数组
     * @return 结果
     */
    public int deletePortalMessageByMessageIds(Long[] messageIds);

    /**
     * 将消息通知转换为客户
     *
     * @param messageId 消息通知ID
     * @param customerInfo 客户补充信息（公司名称、职位等）
     * @return 转换结果（包含客户信息）
     */
    public AjaxResult convertToCustomer(Long messageId, PortalCustomer customerInfo);
}
