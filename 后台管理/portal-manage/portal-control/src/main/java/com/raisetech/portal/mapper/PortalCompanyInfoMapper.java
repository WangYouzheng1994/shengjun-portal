package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalCompanyInfo;

/**
 * 企业基础信息Mapper接口
 *
 * @author 王有政
 */
public interface PortalCompanyInfoMapper {
    /**
     * 查询企业基础信息
     *
     * @param infoId 信息ID主键
     * @return 企业基础信息
     */
    public PortalCompanyInfo selectPortalCompanyInfoByInfoId(Long infoId);

    /**
     * 查询企业基础信息列表
     *
     * @param portalCompanyInfo 企业基础信息
     * @return 企业基础信息集合
     */
    public List<PortalCompanyInfo> selectPortalCompanyInfoList(PortalCompanyInfo portalCompanyInfo);

    /**
     * 新增企业基础信息
     *
     * @param portalCompanyInfo 企业基础信息
     * @return 结果
     */
    public int insertPortalCompanyInfo(PortalCompanyInfo portalCompanyInfo);

    /**
     * 修改企业基础信息
     *
     * @param portalCompanyInfo 企业基础信息
     * @return 结果
     */
    public int updatePortalCompanyInfo(PortalCompanyInfo portalCompanyInfo);

    /**
     * 删除企业基础信息
     *
     * @param infoId 信息ID主键
     * @return 结果
     */
    public int deletePortalCompanyInfoByInfoId(Long infoId);

    /**
     * 批量删除企业基础信息
     *
     * @param infoIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePortalCompanyInfoByInfoIds(Long[] infoIds);
}
