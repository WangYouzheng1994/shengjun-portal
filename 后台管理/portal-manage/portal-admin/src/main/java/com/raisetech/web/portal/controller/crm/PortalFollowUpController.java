package com.raisetech.web.portal.controller.crm;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.portal.domain.PortalFollowUp;
import com.raisetech.portal.service.IPortalFollowUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
