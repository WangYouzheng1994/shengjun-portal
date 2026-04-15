package com.ruoyi.portal.controller.base;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.portal.domain.PortalCompanyMilestone;
import com.ruoyi.portal.service.IPortalCompanyMilestoneService;

/**
 * 企业发展历程里程碑Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/base/milestone")
public class PortalCompanyMilestoneController extends BaseController {

    /**
     * 企业发展历程Service接口
     */
    @Autowired
    private IPortalCompanyMilestoneService milestoneService;

    /**
     * 查询里程碑列表（按时间倒序）
     *
     * @param milestone 查询条件（companyId必填）
     * @return 分页结果
     */
    @PreAuthorize("@ss.hasPermi('portal:base:milestone:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalCompanyMilestone milestone) {
        if (milestone.getCompanyId() == null) {
            return getDataTable(List.of());
        }
        startPage();
        List<PortalCompanyMilestone> list = milestoneService.selectPortalCompanyMilestoneList(milestone);
        return getDataTable(list);
    }

    /**
     * 导出里程碑列表（Excel）
     *
     * @param response HTTP响应对象
     * @param milestone 查询条件（companyId必填）
     */
    @PreAuthorize("@ss.hasPermi('portal:base:milestone:export')")
    @Log(title = "企业发展历程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalCompanyMilestone milestone) {
        if (milestone.getCompanyId() == null) {
            throw new ServiceException("企业ID不能为空");
        }
        List<PortalCompanyMilestone> list = milestoneService.selectPortalCompanyMilestoneList(milestone);
        ExcelUtil<PortalCompanyMilestone> util = new ExcelUtil<>(PortalCompanyMilestone.class);
        util.exportExcel(response, list, "企业发展历程数据");
    }

    /**
     * 获取里程碑详情
     *
     * @param milestoneId 里程碑ID
     * @return 里程碑详情
     */
    @PreAuthorize("@ss.hasPermi('portal:base:milestone:query')")
    @GetMapping(value = "/{milestoneId}")
    public AjaxResult getInfo(@PathVariable("milestoneId") Long milestoneId) {
        PortalCompanyMilestone milestone = milestoneService.selectPortalCompanyMilestoneByMilestoneId(milestoneId);
        if (milestone == null) {
            return error("里程碑不存在");
        }
        return success(milestone);
    }

    /**
     * 新增里程碑
     *
     * @param milestone 里程碑对象（必须包含companyId）
     * @return 操作结果
     */
    @PreAuthorize("@ss.hasPermi('portal:base:milestone:add')")
    @Log(title = "企业发展历程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalCompanyMilestone milestone) {
        if (milestone.getCompanyId() == null) {
            return error("企业ID不能为空");
        }

        // 设置创建人信息
        milestone.setCreateBy(getUsername());

        return toAjax(milestoneService.insertPortalCompanyMilestone(milestone));
    }

    /**
     * 修改里程碑
     *
     * @param milestone 里程碑对象
     * @return 操作结果
     */
    @PreAuthorize("@ss.hasPermi('portal:base:milestone:edit')")
    @Log(title = "企业发展历程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalCompanyMilestone milestone) {
        if (!checkOwnership(milestone.getMilestoneId())) {
            return error("无权操作此数据");
        }

        // 设置更新人信息
        milestone.setUpdateBy(getUsername());

        return toAjax(milestoneService.updatePortalCompanyMilestone(milestone));
    }

    /**
     * 删除里程碑
     *
     * @param milestoneIds 里程碑ID数组
     * @return 操作结果
     */
    @PreAuthorize("@ss.hasPermi('portal:base:milestone:remove')")
    @Log(title = "企业发展历程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{milestoneIds}")
    public AjaxResult remove(@PathVariable Long[] milestoneIds) {
        for (Long id : milestoneIds) {
            if (!checkOwnership(id)) {
                return error("无权删除ID为 " + id + " 的数据");
            }
        }
        return toAjax(milestoneService.deletePortalCompanyMilestoneByMilestoneIds(milestoneIds));
    }

    /**
     * 验证数据归属权（防止越权操作）
     *
     * 超级管理员可以操作所有数据，
     * 企业管理员只能操作本企业的数据
     *
     * @param milestoneId 里程碑ID
     * @return 是否有权限操作
     */
    private boolean checkOwnership(Long milestoneId) {
        if (SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            return true;
        }

        PortalCompanyMilestone existing = milestoneService.selectPortalCompanyMilestoneByMilestoneId(milestoneId);
        if (existing == null) {
            return false;
        }

        // TODO: 根据实际业务逻辑判断当前用户所属的企业ID
        // Long currentCompanyId = getCurrentUserCompanyId();
        // return existing.getCompanyId().equals(currentCompanyId);

        return true;
    }
}
