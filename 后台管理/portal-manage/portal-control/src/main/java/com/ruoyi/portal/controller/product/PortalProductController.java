package com.ruoyi.portal.controller.product;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.portal.domain.PortalProduct;
import com.ruoyi.portal.service.IPortalProductService;

/**
 * 产品管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/product/info")
public class PortalProductController extends BaseController {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(PortalProductController.class);

    /**
     * 产品Service接口
     */
    @Autowired
    private IPortalProductService portalProductService;

    /**
     * 查询产品列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalProduct portalProduct) {
        startPage();
        List<PortalProduct> list = portalProductService.selectPortalProductList(portalProduct);
        return getDataTable(list);
    }

    /**
     * 导出产品列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:export')")
    @Log(title = "产品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalProduct portalProduct) {
        List<PortalProduct> list = portalProductService.selectPortalProductList(portalProduct);
        ExcelUtil<PortalProduct> util = new ExcelUtil<PortalProduct>(PortalProduct.class);
        util.exportExcel(response, list, "产品数据");
    }

    /**
     * 获取产品详细信息（包含属性值和SKU）
     */
    @PreAuthorize("@ss.hasPermi('portal:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId) {
        return success(portalProductService.selectPortalProductByProductId(productId));
    }

    /**
     * 新增产品（包含属性值和SKU）
     */
    @PreAuthorize("@ss.hasPermi('portal:product:add')")
    @Log(title = "产品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalProduct portalProduct) {
        portalProduct.setCreateBy(getUsername());
        return toAjax(portalProductService.insertPortalProduct(portalProduct));
    }

    /**
     * 修改产品（包含属性值和SKU）
     */
    @PreAuthorize("@ss.hasPermi('portal:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalProduct portalProduct) {
        portalProduct.setUpdateBy(getUsername());
        return toAjax(portalProductService.updatePortalProduct(portalProduct));
    }

    /**
     * 上架产品
     */
    @PreAuthorize("@ss.hasPermi('portal:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping("/publish")
    public AjaxResult publish(@RequestBody PortalProduct portalProduct) {
        return toAjax(portalProductService.publishProduct(portalProduct));
    }

    /**
     * 下架产品
     */
    @PreAuthorize("@ss.hasPermi('portal:product:edit')")
    @Log(title = "产品", businessType = BusinessType.UPDATE)
    @PutMapping("/offline")
    public AjaxResult offline(@RequestBody PortalProduct portalProduct) {
        return toAjax(portalProductService.offlineProduct(portalProduct));
    }

    /**
     * 删除产品
     */
    @PreAuthorize("@ss.hasPermi('portal:product:remove')")
    @Log(title = "产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds) {
        return toAjax(portalProductService.deletePortalProductByProductIds(productIds));
    }
}
