package com.raisetech.portal.service;

import java.util.List;
import com.raisetech.portal.domain.PortalOfficeLocation;

/**
 * 企业办公点Service接口
 *
 * @author 王有政
 */
public interface IPortalOfficeLocationService {
    /**
     * 查询企业办公点
     *
     * @param locationId 办公点ID主键
     * @return 企业办公点
     */
    public PortalOfficeLocation selectPortalOfficeLocationByLocationId(Long locationId);

    /**
     * 根据企业信息ID查询办公点列表
     *
     * @param infoId 企业信息ID
     * @return 办公点集合
     */
    public List<PortalOfficeLocation> selectPortalOfficeLocationByInfoId(Long infoId);

    /**
     * 查询企业办公点列表
     *
     * @param portalOfficeLocation 企业办公点
     * @return 企业办公点集合
     */
    public List<PortalOfficeLocation> selectPortalOfficeLocationList(PortalOfficeLocation portalOfficeLocation);

    /**
     * 新增企业办公点
     *
     * @param portalOfficeLocation 企业办公点
     * @return 结果
     */
    public int insertPortalOfficeLocation(PortalOfficeLocation portalOfficeLocation);

    /**
     * 修改企业办公点
     *
     * @param portalOfficeLocation 企业办公点
     * @return 结果
     */
    public int updatePortalOfficeLocation(PortalOfficeLocation portalOfficeLocation);

    /**
     * 批量删除企业办公点
     *
     * @param locationIds 需要删除的办公点ID主键集合
     * @return 结果
     */
    public int deletePortalOfficeLocationByLocationIds(Long[] locationIds);

    /**
     * 删除企业办公点信息
     *
     * @param locationId 办公点ID主键
     * @return 结果
     */
    public int deletePortalOfficeLocationByLocationId(Long locationId);
}
