package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalCompanyHonor;

/**
 * 企业荣誉墙Mapper接口
 *
 * @author 王有政
 */
public interface PortalCompanyHonorMapper {
    /**
     * 查询企业荣誉墙
     *
     * @param honorId 荣誉ID主键
     * @return 企业荣誉墙
     */
    public PortalCompanyHonor selectPortalCompanyHonorByHonorId(Long honorId);

    /**
     * 查询企业荣誉墙列表
     *
     * @param portalCompanyHonor 企业荣誉墙
     * @return 企业荣誉墙集合
     */
    public List<PortalCompanyHonor> selectPortalCompanyHonorList(PortalCompanyHonor portalCompanyHonor);

    /**
     * 新增企业荣誉墙
     *
     * @param portalCompanyHonor 企业荣誉墙
     * @return 结果
     */
    public int insertPortalCompanyHonor(PortalCompanyHonor portalCompanyHonor);

    /**
     * 修改企业荣誉墙
     *
     * @param portalCompanyHonor 企业荣誉墙
     * @return 结果
     */
    public int updatePortalCompanyHonor(PortalCompanyHonor portalCompanyHonor);

    /**
     * 删除企业荣誉墙
     *
     * @param honorId 荣誉ID主键
     * @return 结果
     */
    public int deletePortalCompanyHonorByHonorId(Long honorId);

    /**
     * 批量删除企业荣誉墙
     *
     * @param honorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePortalCompanyHonorByHonorIds(Long[] honorIds);
}
