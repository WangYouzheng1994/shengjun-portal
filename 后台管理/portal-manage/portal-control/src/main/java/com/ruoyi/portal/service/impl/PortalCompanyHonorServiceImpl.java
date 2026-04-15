package com.ruoyi.portal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.portal.mapper.PortalCompanyHonorMapper;
import com.ruoyi.portal.domain.PortalCompanyHonor;
import com.ruoyi.portal.service.IPortalCompanyHonorService;

/**
 * 企业荣誉墙Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalCompanyHonorServiceImpl implements IPortalCompanyHonorService {

    @Autowired
    private PortalCompanyHonorMapper portalCompanyHonorMapper;

    /**
     * 查询企业荣誉墙
     *
     * @param honorId 荣誉ID主键
     * @return 企业荣誉墙
     */
    @Override
    public PortalCompanyHonor selectPortalCompanyHonorByHonorId(Long honorId) {
        return portalCompanyHonorMapper.selectPortalCompanyHonorByHonorId(honorId);
    }

    /**
     * 查询企业荣誉墙列表
     *
     * @param portalCompanyHonor 企业荣誉墙
     * @return 企业荣誉墙
     */
    @Override
    public List<PortalCompanyHonor> selectPortalCompanyHonorList(PortalCompanyHonor portalCompanyHonor) {
        return portalCompanyHonorMapper.selectPortalCompanyHonorList(portalCompanyHonor);
    }

    /**
     * 新增企业荣誉墙
     *
     * @param portalCompanyHonor 企业荣誉墙
     * @return 结果
     */
    @Override
    public int insertPortalCompanyHonor(PortalCompanyHonor portalCompanyHonor) {
        portalCompanyHonor.setCreateTime(DateUtils.getNowDate());
        return portalCompanyHonorMapper.insertPortalCompanyHonor(portalCompanyHonor);
    }

    /**
     * 修改企业荣誉墙
     *
     * @param portalCompanyHonor 企业荣誉墙
     * @return 结果
     */
    @Override
    public int updatePortalCompanyHonor(PortalCompanyHonor portalCompanyHonor) {
        portalCompanyHonor.setUpdateTime(DateUtils.getNowDate());
        return portalCompanyHonorMapper.updatePortalCompanyHonor(portalCompanyHonor);
    }

    /**
     * 批量删除企业荣誉墙
     *
     * @param honorIds 需要删除的荣誉ID主键集合
     * @return 结果
     */
    @Override
    public int deletePortalCompanyHonorByHonorIds(Long[] honorIds) {
        return portalCompanyHonorMapper.deletePortalCompanyHonorByHonorIds(honorIds);
    }

    /**
     * 删除企业荣誉墙信息
     *
     * @param honorId 荣誉ID主键
     * @return 结果
     */
    @Override
    public int deletePortalCompanyHonorByHonorId(Long honorId) {
        return portalCompanyHonorMapper.deletePortalCompanyHonorByHonorId(honorId);
    }
}
