package com.ruoyi.portal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.portal.mapper.PortalCompanyInfoMapper;
import com.ruoyi.portal.domain.PortalCompanyInfo;
import com.ruoyi.portal.service.IPortalCompanyInfoService;

/**
 * 企业基础信息Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalCompanyInfoServiceImpl implements IPortalCompanyInfoService {

    @Autowired
    private PortalCompanyInfoMapper portalCompanyInfoMapper;

    /**
     * 查询企业基础信息
     *
     * @param infoId 信息ID主键
     * @return 企业基础信息
     */
    @Override
    public PortalCompanyInfo selectPortalCompanyInfoByInfoId(Long infoId) {
        return portalCompanyInfoMapper.selectPortalCompanyInfoByInfoId(infoId);
    }

    /**
     * 查询企业基础信息列表
     *
     * @param portalCompanyInfo 企业基础信息
     * @return 企业基础信息
     */
    @Override
    public List<PortalCompanyInfo> selectPortalCompanyInfoList(PortalCompanyInfo portalCompanyInfo) {
        return portalCompanyInfoMapper.selectPortalCompanyInfoList(portalCompanyInfo);
    }

    /**
     * 新增企业基础信息
     *
     * @param portalCompanyInfo 企业基础信息
     * @return 结果
     */
    @Override
    public int insertPortalCompanyInfo(PortalCompanyInfo portalCompanyInfo) {
        portalCompanyInfo.setCreateTime(DateUtils.getNowDate());
        return portalCompanyInfoMapper.insertPortalCompanyInfo(portalCompanyInfo);
    }

    /**
     * 修改企业基础信息
     *
     * @param portalCompanyInfo 企业基础信息
     * @return 结果
     */
    @Override
    public int updatePortalCompanyInfo(PortalCompanyInfo portalCompanyInfo) {
        portalCompanyInfo.setUpdateTime(DateUtils.getNowDate());
        return portalCompanyInfoMapper.updatePortalCompanyInfo(portalCompanyInfo);
    }

    /**
     * 批量删除企业基础信息
     *
     * @param infoIds 需要删除的信息ID主键集合
     * @return 结果
     */
    @Override
    public int deletePortalCompanyInfoByInfoIds(Long[] infoIds) {
        return portalCompanyInfoMapper.deletePortalCompanyInfoByInfoIds(infoIds);
    }

    /**
     * 删除企业基础信息信息
     *
     * @param infoId 信息ID主键
     * @return 结果
     */
    @Override
    public int deletePortalCompanyInfoByInfoId(Long infoId) {
        return portalCompanyInfoMapper.deletePortalCompanyInfoByInfoId(infoId);
    }
}
