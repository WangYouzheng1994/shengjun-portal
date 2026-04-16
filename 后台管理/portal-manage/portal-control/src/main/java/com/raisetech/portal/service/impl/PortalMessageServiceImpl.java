package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.portal.mapper.PortalMessageMapper;
import com.raisetech.portal.mapper.PortalCustomerMapper;
import com.raisetech.portal.domain.PortalMessage;
import com.raisetech.portal.domain.PortalCustomer;
import com.raisetech.portal.service.IPortalMessageService;
import com.raisetech.portal.service.IPortalCustomerService;

/**
 * 官网消息通知Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalMessageServiceImpl implements IPortalMessageService {

    @Autowired
    private PortalMessageMapper portalMessageMapper;

    @Autowired
    private PortalCustomerMapper portalCustomerMapper;

    @Autowired
    private IPortalCustomerService portalCustomerService;

    /**
     * 查询消息通知
     *
     * @param messageId 消息通知ID
     * @return 消息通知对象
     */
    @Override
    public PortalMessage selectPortalMessageByMessageId(Long messageId) {
        return portalMessageMapper.selectPortalMessageByMessageId(messageId);
    }

    /**
     * 查询消息通知列表
     *
     * @param portalMessage 消息通知查询条件
     * @return 消息通知集合
     */
    @Override
    public List<PortalMessage> selectPortalMessageList(PortalMessage portalMessage) {
        return portalMessageMapper.selectPortalMessageList(portalMessage);
    }

    /**
     * 新增消息通知
     *
     * @param portalMessage 消息通知对象
     * @return 结果
     */
    @Override
    public int insertPortalMessage(PortalMessage portalMessage) {
        portalMessage.setCreateTime(DateUtils.getNowDate());
        if (portalMessage.getHandleStatus() == null) {
            portalMessage.setHandleStatus("0");
        }
        if (portalMessage.getIsRead() == null) {
            portalMessage.setIsRead("0");
        }
        return portalMessageMapper.insertPortalMessage(portalMessage);
    }

    /**
     * 修改消息通知
     *
     * @param portalMessage 消息通知对象
     * @return 结果
     */
    @Override
    public int updatePortalMessage(PortalMessage portalMessage) {
        portalMessage.setUpdateTime(DateUtils.getNowDate());
        return portalMessageMapper.updatePortalMessage(portalMessage);
    }

    /**
     * 批量删除消息通知（逻辑删除）
     *
     * @param messageIds 需要删除的消息通知ID数组
     * @return 结果
     */
    @Override
    public int deletePortalMessageByMessageIds(Long[] messageIds) {
        return portalMessageMapper.deletePortalMessageByMessageIds(messageIds);
    }

    /**
     * 将消息通知转换为客户
     * 核心流程：
     * 1. 校验消息通知是否存在且未删除
     * 2. 如果已关联客户则返回提示
     * 3. 根据手机号/邮箱智能匹配已有客户
     * 4. 未匹配到则创建新客户（来源=官网留言）
     * 5. 更新消息通知的customer_id字段
     *
     * @param messageId 消息通知ID
     * @param customerInfo 客户补充信息
     * @return 转换结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult convertToCustomer(Long messageId, PortalCustomer customerInfo) {
        PortalMessage message = portalMessageMapper.selectPortalMessageByMessageId(messageId);

        if (message == null) {
            return AjaxResult.error("消息通知不存在");
        }

        if ("1".equals(message.getDelFlag())) {
            return AjaxResult.error("消息通知已被删除");
        }

        if (message.getCustomerId() != null) {
            return AjaxResult.error("该消息通知已关联客户，无需重复转换");
        }

        PortalCustomer customer = null;

        if (message.getPhone() != null && !message.getPhone().isEmpty()) {
            customer = portalCustomerMapper.selectCustomerByPhone(message.getPhone());
        }

        if (customer == null && message.getEmail() != null && !message.getEmail().isEmpty()) {
            customer = portalCustomerMapper.selectCustomerByEmail(message.getEmail());
        }

        if (customer == null) {
            customer = new PortalCustomer();
            customer.setCustomerName(message.getName());
            customer.setPhone(message.getPhone());
            customer.setEmail(message.getEmail());
            customer.setSource("0");
            customer.setStatus("0");
            customer.setLevel("0");
            customer.setGender("0");

            if (customerInfo != null) {
                if (customerInfo.getCompanyName() != null) {
                    customer.setCompanyName(customerInfo.getCompanyName());
                }
                if (customerInfo.getPosition() != null) {
                    customer.setPosition(customerInfo.getPosition());
                }
                if (customerInfo.getLevel() != null) {
                    customer.setLevel(customerInfo.getLevel());
                }
                if (customerInfo.getAddress() != null) {
                    customer.setAddress(customerInfo.getAddress());
                }
                if (customerInfo.getRemark() != null) {
                    customer.setRemark(customerInfo.getRemark());
                }
            } else {
                customer.setRemark("来源于官网消息通知：" + message.getSubject());
            }

            portalCustomerService.insertPortalCustomer(customer);
        }

        message.setCustomerId(customer.getCustomerId());
        message.setHandleStatus("1");
        message.setUpdateTime(DateUtils.getNowDate());
        portalMessageMapper.updatePortalMessage(message);

        AjaxResult result = AjaxResult.success("转换成功");
        result.put("customerId", customer.getCustomerId());
        result.put("customerName", customer.getCustomerName());
        result.put("isNewCustomer", customer.getCreateTime() != null &&
            Math.abs(DateUtils.getNowDate().getTime() - customer.getCreateTime().getTime()) < 5000);

        return result;
    }
}
