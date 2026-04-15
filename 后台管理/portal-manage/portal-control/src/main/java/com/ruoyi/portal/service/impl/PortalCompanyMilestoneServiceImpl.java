package com.ruoyi.portal.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.portal.domain.PortalCompanyInfo;
import com.ruoyi.portal.domain.PortalCompanyMilestone;
import com.ruoyi.portal.mapper.PortalCompanyInfoMapper;
import com.ruoyi.portal.mapper.PortalCompanyMilestoneMapper;
import com.ruoyi.portal.service.IPortalCompanyMilestoneService;

/**
 * 企业发展历程里程碑Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalCompanyMilestoneServiceImpl implements IPortalCompanyMilestoneService {

    /**
     * 里程碑Mapper接口
     */
    @Autowired
    private PortalCompanyMilestoneMapper milestoneMapper;

    /**
     * 企业信息Mapper接口（用于校验企业存在性）
     */
    @Autowired
    private PortalCompanyInfoMapper companyMapper;

    /**
     * 查询里程碑列表（支持按企业、年份、级别筛选）
     *
     * @param milestone 查询条件（必须包含companyId）
     * @return 里程碑集合
     */
    @Override
    public List<PortalCompanyMilestone> selectPortalCompanyMilestoneList(PortalCompanyMilestone milestone) {
        if (milestone.getCompanyId() == null) {
            throw new ServiceException("企业ID不能为空");
        }
        return milestoneMapper.selectPortalCompanyMilestoneList(milestone);
    }

    /**
     * 根据ID查询里程碑详情
     *
     * @param milestoneId 里程碑ID
     * @return 里程碑对象
     */
    @Override
    public PortalCompanyMilestone selectPortalCompanyMilestoneByMilestoneId(Long milestoneId) {
        return milestoneMapper.selectPortalCompanyMilestoneByMilestoneId(milestoneId);
    }

    /**
     * 新增里程碑（自动校验企业存在性、时间合法性）
     *
     * @param milestone 里程碑对象
     * @return 影响行数
     */
    @Override
    @Transactional
    public int insertPortalCompanyMilestone(PortalCompanyMilestone milestone) {
        if (StringUtils.isEmpty(milestone.getMilestoneTitle())) {
            throw new ServiceException("里程碑标题不能为空");
        }
        if (milestone.getCompanyId() == null) {
            throw new ServiceException("企业ID不能为空");
        }

        // 校验企业是否存在
        PortalCompanyInfo company = companyMapper.selectPortalCompanyInfoByInfoId(milestone.getCompanyId());
        if (company == null) {
            throw new ServiceException("指定的企业不存在，ID=" + milestone.getCompanyId());
        }

        // 校验时间合法性
        if (milestone.getMilestoneYear() == null || milestone.getMilestoneYear() < 1900 || milestone.getMilestoneYear() > 2100) {
            throw new ServiceException("年份必须在1900-2100之间");
        }
        if (milestone.getMilestoneMonth() != null && (milestone.getMilestoneMonth() < 1 || milestone.getMilestoneMonth() > 12)) {
            throw new ServiceException("月份必须在1-12之间");
        }
        if (milestone.getMilestoneDay() != null && (milestone.getMilestoneDay() < 1 || milestone.getMilestoneDay() > 31)) {
            throw new ServiceException("日期必须在1-31之间");
        }

        // 设置默认值
        if (StringUtils.isEmpty(milestone.getMilestoneLevel())) {
            milestone.setMilestoneLevel("3");
        }
        if (StringUtils.isEmpty(milestone.getStatus())) {
            milestone.setStatus("0");
        }
        if (milestone.getSortOrder() == null) {
            milestone.setSortOrder(0);
        }
        milestone.setDelFlag("0");
        milestone.setCreateTime(DateUtils.getNowDate());

        return milestoneMapper.insertPortalCompanyMilestone(milestone);
    }

    /**
     * 修改里程碑
     *
     * @param milestone 里程碑对象
     * @return 影响行数
     */
    @Override
    @Transactional
    public int updatePortalCompanyMilestone(PortalCompanyMilestone milestone) {
        if (StringUtils.isEmpty(milestone.getMilestoneTitle())) {
            throw new ServiceException("里程碑标题不能为空");
        }

        // 校验时间合法性（如果提供了时间信息）
        if (milestone.getMilestoneYear() != null && (milestone.getMilestoneYear() < 1900 || milestone.getMilestoneYear() > 2100)) {
            throw new ServiceException("年份必须在1900-2100之间");
        }
        if (milestone.getMilestoneMonth() != null && (milestone.getMilestoneMonth() < 1 || milestone.getMilestoneMonth() > 12)) {
            throw new ServiceException("月份必须在1-12之间");
        }
        if (milestone.getMilestoneDay() != null && (milestone.getMilestoneDay() < 1 || milestone.getMilestoneDay() > 31)) {
            throw new ServiceException("日期必须在1-31之间");
        }

        milestone.setUpdateTime(DateUtils.getNowDate());
        return milestoneMapper.updatePortalCompanyMilestone(milestone);
    }

    /**
     * 批量删除里程碑（逻辑删除）
     *
     * @param milestoneIds 需要删除的里程碑ID数组
     * @return 影响行数
     */
    @Override
    public int deletePortalCompanyMilestoneByMilestoneIds(Long[] milestoneIds) {
        return milestoneMapper.deletePortalCompanyMilestoneByMilestoneIds(milestoneIds);
    }

    /**
     * 删除单个里程碑信息（逻辑删除）
     *
     * @param milestoneId 里程碑ID
     * @return 影响行数
     */
    @Override
    public int deletePortalCompanyMilestoneByMilestoneId(Long milestoneId) {
        return milestoneMapper.deletePortalCompanyMilestoneByMilestoneId(milestoneId);
    }

    /**
     * 统计某企业的里程碑总数
     *
     * @param companyId 企业ID
     * @return 数量
     */
    @Override
    public int countByCompanyId(Long companyId) {
        return milestoneMapper.countByCompanyId(companyId);
    }

    /**
     * 按年份分组统计某企业的里程碑数量（用于前端时间轴展示优化）
     *
     * @param companyId 企业ID
     * @return 年份-数量映射列表
     */
    @Override
    public List<Map<String, Object>> countGroupByYear(Long companyId) {
        return milestoneMapper.countGroupByYear(companyId);
    }
}
