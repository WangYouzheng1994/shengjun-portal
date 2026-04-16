package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalCustomer;

/**
 * 客户Mapper接口
 *
 * @author 王有政
 */
public interface PortalCustomerMapper {
    /**
     * 查询客户
     *
     * @param customerId 客户ID
     * @return 客户对象
     */
    public PortalCustomer selectPortalCustomerByCustomerId(Long customerId);

    /**
     * 查询客户列表
     *
     * @param portalCustomer 客户查询条件
     * @return 客户集合
     */
    public List<PortalCustomer> selectPortalCustomerList(PortalCustomer portalCustomer);

    /**
     * 新增客户
     *
     * @param portalCustomer 客户对象
     * @return 结果
     */
    public int insertPortalCustomer(PortalCustomer portalCustomer);

    /**
     * 修改客户
     *
     * @param portalCustomer 客户对象
     * @return 结果
     */
    public int updatePortalCustomer(PortalCustomer portalCustomer);

    /**
     * 删除客户（逻辑删除）
     *
     * @param customerId 客户ID
     * @return 结果
     */
    public int deletePortalCustomerByCustomerId(Long customerId);

    /**
     * 批量删除客户（逻辑删除）
     *
     * @param customerIds 需要删除的客户ID数组
     * @return 结果
     */
    public int deletePortalCustomerByCustomerIds(Long[] customerIds);

    /**
     * 根据手机号查询客户
     *
     * @param phone 手机号
     * @return 客户对象
     */
    public PortalCustomer selectCustomerByPhone(String phone);

    /**
     * 根据邮箱查询客户
     *
     * @param email 邮箱
     * @return 客户对象
     */
    public PortalCustomer selectCustomerByEmail(String email);
}
