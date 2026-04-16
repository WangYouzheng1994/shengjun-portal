package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalCustomerTag;

/**
 * 客户标签Mapper接口
 *
 * @author 王有政
 */
public interface PortalCustomerTagMapper {
    /**
     * 查询标签
     *
     * @param tagId 标签ID
     * @return 标签对象
     */
    public PortalCustomerTag selectPortalCustomerTagByTagId(Long tagId);

    /**
     * 查询标签列表
     *
     * @param portalCustomerTag 标签查询条件
     * @return 标签集合
     */
    public List<PortalCustomerTag> selectPortalCustomerTagList(PortalCustomerTag portalCustomerTag);

    /**
     * 新增标签
     *
     * @param portalCustomerTag 标签对象
     * @return 结果
     */
    public int insertPortalCustomerTag(PortalCustomerTag portalCustomerTag);

    /**
     * 修改标签
     *
     * @param portalCustomerTag 标签对象
     * @return 结果
     */
    public int updatePortalCustomerTag(PortalCustomerTag portalCustomerTag);

    /**
     * 删除标签（逻辑删除）
     *
     * @param tagId 标签ID
     * @return 结果
     */
    public int deletePortalCustomerTagByTagId(Long tagId);

    /**
     * 批量删除标签（逻辑删除）
     *
     * @param tagIds 需要删除的标签ID数组
     * @return 结果
     */
    public int deletePortalCustomerTagByTagIds(Long[] tagIds);

    /**
     * 根据客户ID查询标签列表
     *
     * @param customerId 客户ID
     * @return 标签集合
     */
    public List<PortalCustomerTag> selectTagsByCustomerId(Long customerId);
}
