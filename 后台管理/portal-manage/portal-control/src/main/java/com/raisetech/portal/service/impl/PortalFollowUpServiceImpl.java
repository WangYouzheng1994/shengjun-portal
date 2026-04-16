package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.mapper.PortalFollowUpMapper;
import com.raisetech.portal.mapper.PortalCustomerMapper;
import com.raisetech.portal.domain.PortalFollowUp;
import com.raisetech.portal.domain.PortalCustomer;
import com.raisetech.portal.service.IPortalFollowUpService;

/**
 * 跟进记录Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalFollowUpServiceImpl implements IPortalFollowUpService {

    @Autowired
    private PortalFollowUpMapper portalFollowUpMapper;

    @Autowired
    private PortalCustomerMapper portalCustomerMapper;

    /**
     * 查询跟进记录
     *
     * @param followId 跟进ID
     * @return 跟进记录对象
     */
    @Override
    public PortalFollowUp selectPortalFollowUpByFollowId(Long followId) {
        return portalFollowUpMapper.selectPortalFollowUpByFollowId(followId);
    }

    /**
     * 查询跟进记录列表
     *
     * @param portalFollowUp 跟进记录查询条件
     * @return 跟进记录集合
     */
    @Override
    public List<PortalFollowUp> selectPortalFollowUpList(PortalFollowUp portalFollowUp) {
        return portalFollowUpMapper.selectPortalFollowUpList(portalFollowUp);
    }

    /**
     * 新增跟进记录
     *
     * @param portalFollowUp 跟进记录对象
     * @return 结果
     */
    @Override
    public int insertPortalFollowUp(PortalFollowUp portalFollowUp) {
        if (portalFollowUp.getCustomerId() == null) {
            throw new ServiceException("客户ID不能为空");
        }
        if (StringUtils.isEmpty(portalFollowUp.getFollowContent())) {
            throw new ServiceException("跟进内容不能为空");
        }
        portalFollowUp.setCreateTime(DateUtils.getNowDate());
        int result = portalFollowUpMapper.insertPortalFollowUp(portalFollowUp);

        if (result > 0) {
            PortalCustomer customer = new PortalCustomer();
            customer.setCustomerId(portalFollowUp.getCustomerId());
            customer.setLastContactTime(DateUtils.getNowDate());
            if (portalFollowUp.getNextContactTime() != null) {
                customer.setNextContactTime(portalFollowUp.getNextContactTime());
            }
            customer.setUpdateTime(DateUtils.getNowDate());
            portalCustomerMapper.updatePortalCustomer(customer);
        }

        return result;
    }

    /**
     * 修改跟进记录
     *
     * @param portalFollowUp 跟进记录对象
     * @return 结果
     */
    @Override
    public int updatePortalFollowUp(PortalFollowUp portalFollowUp) {
        portalFollowUp.setUpdateTime(DateUtils.getNowDate());
        return portalFollowUpMapper.updatePortalFollowUp(portalFollowUp);
    }

    /**
     * 批量删除跟进记录（逻辑删除）
     *
     * @param followIds 需要删除的跟进ID数组
     * @return 结果
     */
    @Override
    public int deletePortalFollowUpByFollowIds(Long[] followIds) {
        return portalFollowUpMapper.deletePortalFollowUpByFollowIds(followIds);
    }

    /**
     * 根据客户ID查询跟进记录
     *
     * @param customerId 客户ID
     * @return 跟进记录集合
     */
    @Override
    public List<PortalFollowUp> selectFollowUpsByCustomerId(Long customerId) {
        return portalFollowUpMapper.selectFollowUpsByCustomerId(customerId);
    }
}
