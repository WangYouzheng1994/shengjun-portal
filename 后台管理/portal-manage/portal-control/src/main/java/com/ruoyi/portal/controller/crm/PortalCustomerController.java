package com.ruoyi.portal.controller.crm;

import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
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
import com.ruoyi.portal.domain.PortalCustomer;
import com.ruoyi.portal.service.IPortalCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

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
