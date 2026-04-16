package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.mapper.PortalCustomerMapper;
import com.raisetech.portal.domain.PortalCustomer;
import com.raisetech.portal.service.IPortalCustomerService;

/**
 * 客户Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalCustomerServiceImpl implements IPortalCustomerService {

    @Autowired
    private PortalCustomerMapper portalCustomerMapper;

    /**
     * 查询客户
     *
     * @param customerId 客户ID
     * @return 客户对象
     */
    @Override
    public PortalCustomer selectPortalCustomerByCustomerId(Long customerId) {
        return portalCustomerMapper.selectPortalCustomerByCustomerId(customerId);
    }

    /**
     * 查询客户列表
     *
     * @param portalCustomer 客户查询条件
     * @return 客户集合
     */
    @Override
    public List<PortalCustomer> selectPortalCustomerList(PortalCustomer portalCustomer) {
        return portalCustomerMapper.selectPortalCustomerList(portalCustomer);
    }

    /**
     * 新增客户
     *
     * @param portalCustomer 客户对象
     * @return 结果
     */
    @Override
    public int insertPortalCustomer(PortalCustomer portalCustomer) {
        if (StringUtils.isEmpty(portalCustomer.getCustomerName())) {
            throw new ServiceException("客户姓名不能为空");
        }
        if (StringUtils.isNotEmpty(portalCustomer.getPhone())) {
            PortalCustomer existCustomer = portalCustomerMapper.selectCustomerByPhone(portalCustomer.getPhone());
            if (existCustomer != null) {
                throw new ServiceException("该手机号已存在客户记录");
            }
        }
        portalCustomer.setCreateTime(DateUtils.getNowDate());
        if (StringUtils.isEmpty(portalCustomer.getStatus())) {
            portalCustomer.setStatus("0");
        }
        if (StringUtils.isEmpty(portalCustomer.getLevel())) {
            portalCustomer.setLevel("0");
        }
        if (StringUtils.isEmpty(portalCustomer.getSource())) {
            portalCustomer.setSource("0");
        }
        return portalCustomerMapper.insertPortalCustomer(portalCustomer);
    }

    /**
     * 修改客户
     *
     * @param portalCustomer 客户对象
     * @return 结果
     */
    @Override
    public int updatePortalCustomer(PortalCustomer portalCustomer) {
        if (StringUtils.isEmpty(portalCustomer.getCustomerName())) {
            throw new ServiceException("客户姓名不能为空");
        }
        portalCustomer.setUpdateTime(DateUtils.getNowDate());
        return portalCustomerMapper.updatePortalCustomer(portalCustomer);
    }

    /**
     * 批量删除客户（逻辑删除）
     *
     * @param customerIds 需要删除的客户ID数组
     * @return 结果
     */
    @Override
    public int deletePortalCustomerByCustomerIds(Long[] customerIds) {
        return portalCustomerMapper.deletePortalCustomerByCustomerIds(customerIds);
    }

    /**
     * 删除客户（逻辑删除）
     *
     * @param customerId 客户ID
     * @return 结果
     */
    @Override
    public int deletePortalCustomerByCustomerId(Long customerId) {
        return portalCustomerMapper.deletePortalCustomerByCustomerId(customerId);
    }
}
