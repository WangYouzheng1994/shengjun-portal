package com.raisetech.portal.service.impl;

import java.util.List;
import com.raisetech.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.portal.mapper.PortalOfficeLocationMapper;
import com.raisetech.portal.domain.PortalOfficeLocation;
import com.raisetech.portal.service.IPortalOfficeLocationService;

/**
 * 企业办公点Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalOfficeLocationServiceImpl implements IPortalOfficeLocationService {

    @Autowired
    private PortalOfficeLocationMapper portalOfficeLocationMapper;

    /**
     * 查询企业办公点
     *
     * @param locationId 办公点ID主键
     * @return 企业办公点
     */
    @Override
    public PortalOfficeLocation selectPortalOfficeLocationByLocationId(Long locationId) {
        return portalOfficeLocationMapper.selectPortalOfficeLocationByLocationId(locationId);
    }

    /**
     * 根据企业信息ID查询办公点列表
     *
     * @param infoId 企业信息ID
     * @return 办公点集合
     */
    @Override
    public List<PortalOfficeLocation> selectPortalOfficeLocationByInfoId(Long infoId) {
        return portalOfficeLocationMapper.selectPortalOfficeLocationByInfoId(infoId);
    }

    /**
     * 查询企业办公点列表
     *
     * @param portalOfficeLocation 企业办公点
     * @return 企业办公点集合
     */
    @Override
    public List<PortalOfficeLocation> selectPortalOfficeLocationList(PortalOfficeLocation portalOfficeLocation) {
        return portalOfficeLocationMapper.selectPortalOfficeLocationList(portalOfficeLocation);
    }

    /**
     * 新增企业办公点
     *
     * @param portalOfficeLocation 企业办公点
     * @return 结果
     */
    @Override
    public int insertPortalOfficeLocation(PortalOfficeLocation portalOfficeLocation) {
        portalOfficeLocation.setCreateTime(DateUtils.getNowDate());
        return portalOfficeLocationMapper.insertPortalOfficeLocation(portalOfficeLocation);
    }

    /**
     * 修改企业办公点
     *
     * @param portalOfficeLocation 企业办公点
     * @return 结果
     */
    @Override
    public int updatePortalOfficeLocation(PortalOfficeLocation portalOfficeLocation) {
        portalOfficeLocation.setUpdateTime(DateUtils.getNowDate());
        return portalOfficeLocationMapper.updatePortalOfficeLocation(portalOfficeLocation);
    }

    /**
     * 批量删除企业办公点
     *
     * @param locationIds 需要删除的办公点ID主键集合
     * @return 结果
     */
    @Override
    public int deletePortalOfficeLocationByLocationIds(Long[] locationIds) {
        return portalOfficeLocationMapper.deletePortalOfficeLocationByLocationIds(locationIds);
    }

    /**
     * 删除企业办公点信息
     *
     * @param locationId 办公点ID主键
     * @return 结果
     */
    @Override
    public int deletePortalOfficeLocationByLocationId(Long locationId) {
        return portalOfficeLocationMapper.deletePortalOfficeLocationByLocationId(locationId);
    }
}
