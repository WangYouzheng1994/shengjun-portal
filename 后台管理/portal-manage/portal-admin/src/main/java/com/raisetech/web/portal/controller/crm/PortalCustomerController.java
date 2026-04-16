package com.raisetech.web.portal.controller.crm;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.portal.domain.PortalCustomer;
import com.raisetech.portal.service.IPortalCustomerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/crm/customer")
public class PortalCustomerController extends BaseController {

    @Autowired
    private IPortalCustomerService portalCustomerService;

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalCustomer portalCustomer) {
        startPage();
        List<PortalCustomer> list = portalCustomerService.selectPortalCustomerList(portalCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客户列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:customer:export')")
    @Log(title = "客户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalCustomer portalCustomer) {
        List<PortalCustomer> list = portalCustomerService.selectPortalCustomerList(portalCustomer);
        ExcelUtil<PortalCustomer> util = new ExcelUtil<PortalCustomer>(PortalCustomer.class);
        util.exportExcel(response, list, "客户数据");
    }

    /**
     * 获取客户详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:customer:query')")
    @GetMapping(value = "/{customerId}")
    public AjaxResult getInfo(@PathVariable("customerId") Long customerId) {
        return success(portalCustomerService.selectPortalCustomerByCustomerId(customerId));
    }

    /**
     * 新增客户
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:customer:add')")
    @Log(title = "客户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalCustomer portalCustomer) {
        return toAjax(portalCustomerService.insertPortalCustomer(portalCustomer));
    }

    /**
     * 修改客户
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:customer:edit')")
    @Log(title = "客户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalCustomer portalCustomer) {
        return toAjax(portalCustomerService.updatePortalCustomer(portalCustomer));
    }

    /**
     * 删除客户
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:customer:remove')")
    @Log(title = "客户", businessType = BusinessType.DELETE)
    @DeleteMapping("/{customerIds}")
    public AjaxResult remove(@PathVariable Long[] customerIds) {
        return toAjax(portalCustomerService.deletePortalCustomerByCustomerIds(customerIds));
    }
}
