package com.ruoyi.portal.mapper;

import java.util.List;
import com.ruoyi.portal.domain.PortalCompanyMilestone;

/**
 * 企业发展历程里程碑Mapper接口
 *
 * @author 王有政
 */
public interface PortalCompanyMilestoneMapper {

    /**
     * 查询里程碑列表（支持按企业、年份、级别筛选）
     *
     * @param milestone 查询条件（必须包含companyId）
     * @return 里程碑集合
     */
    List<PortalCompanyMilestone> selectPortalCompanyMilestoneList(PortalCompanyMilestone milestone);

    /**
     * 根据ID查询里程碑详情
     *
     * @param milestoneId 里程碑ID
     * @return 里程碑对象
     */
    PortalCompanyMilestone selectPortalCompanyMilestoneByMilestoneId(Long milestoneId);

    /**
     * 新增里程碑
     *
     * @param milestone 里程碑对象
     * @return 影响行数
     */
    int insertPortalCompanyMilestone(PortalCompanyMilestone milestone);

    /**
     * 修改里程碑
     *
     * @param milestone 里程碑对象
     * @return 影响行数
     */
    int updatePortalCompanyMilestone(PortalCompanyMilestone milestone);

    /**
     * 批量删除里程碑（逻辑删除）
     *
     * @param milestoneIds 需要删除的里程碑ID数组
     * @return 影响行数
     */
    int deletePortalCompanyMilestoneByMilestoneIds(Long[] milestoneIds);

    /**
     * 删除单个里程碑信息（逻辑删除）
     *
     * @param milestoneId 里程碑ID
     * @return 影响行数
     */
    int deletePortalCompanyMilestoneByMilestoneId(Long milestoneId);

    /**
     * 统计某企业的里程碑总数
     *
     * @param companyId 企业ID
     * @return 数量
     */
    int countByCompanyId(Long companyId);

    /**
     * 按年份分组统计某企业的里程碑数量（用于前端时间轴展示优化）
     *
     * @param companyId 企业ID
     * @return Map<年份, 数量> 的结果集
     */
    List<java.util.Map<String, Object>> countGroupByYear(Long companyId);
}
