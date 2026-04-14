package com.ruoyi.portal.controller.crm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.portal.domain.PortalFollowUp;
import com.ruoyi.portal.service.IPortalFollowUpService;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 跟进记录Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/crm/followup")
public class PortalFollowUpController extends BaseController {

    @Autowired
    private IPortalFollowUpService portalFollowUpService;

    /**
     * 查询跟进记录列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:followup:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalFollowUp portalFollowUp) {
        startPage();
        List<PortalFollowUp> list = portalFollowUpService.selectPortalFollowUpList(portalFollowUp);
        return getDataTable(list);
    }

    /**
     * 获取跟进记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:followup:query')")
    @GetMapping(value = "/{followId}")
    public AjaxResult getInfo(@PathVariable("followId") Long followId) {
        return success(portalFollowUpService.selectPortalFollowUpByFollowId(followId));
    }

    /**
     * 根据客户ID查询跟进记录
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:followup:list')")
    @GetMapping("/customer/{customerId}")
    public TableDataInfo listByCustomer(@PathVariable("customerId") Long customerId) {
        startPage();
        List<PortalFollowUp> list = portalFollowUpService.selectFollowUpsByCustomerId(customerId);
        return getDataTable(list);
    }

    /**
     * 新增跟进记录
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:followup:add')")
    @Log(title = "跟进记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalFollowUp portalFollowUp) {
        return toAjax(portalFollowUpService.insertPortalFollowUp(portalFollowUp));
    }

    /**
     * 修改跟进记录
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:followup:edit')")
    @Log(title = "跟进记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalFollowUp portalFollowUp) {
        return toAjax(portalFollowUpService.updatePortalFollowUp(portalFollowUp));
    }

    /**
     * 删除跟进记录
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:followup:remove')")
    @Log(title = "跟进记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{followIds}")
    public AjaxResult remove(@PathVariable Long[] followIds) {
        return toAjax(portalFollowUpService.deletePortalFollowUpByFollowIds(followIds));
    }
}
