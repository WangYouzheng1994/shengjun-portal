package com.ruoyi.web.portal.controller.product;

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
import com.ruoyi.portal.domain.PortalProductSku;
import com.ruoyi.portal.service.IPortalProductSkuService;

/**
 * 产品SKU管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/product/sku")
public class PortalProductSkuController extends BaseController {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(PortalProductSkuController.class);

    /**
     * 产品SKUService接口
     */
    @Autowired
    private IPortalProductSkuService portalProductSkuService;

    /**
     * 查询产品SKU列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalProductSku portalProductSku) {
        startPage();
        List<PortalProductSku> list = portalProductSkuService.selectPortalProductSkuList(portalProductSku);
        return getDataTable(list);
    }

    /**
     * 根据产品ID查询SKU列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:list')")
    @GetMapping("/listByProductId/{productId}")
    public AjaxResult listByProductId(@PathVariable("productId") Long productId) {
        List<PortalProductSku> list = portalProductSkuService.selectPortalProductSkuListByProductId(productId);
        return success(list);
    }

    /**
     * 导出产品SKU列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:export')")
    @Log(title = "产品SKU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalProductSku portalProductSku) {
        List<PortalProductSku> list = portalProductSkuService.selectPortalProductSkuList(portalProductSku);
        ExcelUtil<PortalProductSku> util = new ExcelUtil<PortalProductSku>(PortalProductSku.class);
        util.exportExcel(response, list, "产品SKU");
    }

    /**
     * 获取产品SKU详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:query')")
    @GetMapping(value = "/{skuId}")
    public AjaxResult getInfo(@PathVariable("skuId") Long skuId) {
        return success(portalProductSkuService.selectPortalProductSkuBySkuId(skuId));
    }

    /**
     * 新增产品SKU
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:add')")
    @Log(title = "产品SKU", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalProductSku portalProductSku) {
        portalProductSku.setCreateBy(getUsername());
        return toAjax(portalProductSkuService.insertPortalProductSku(portalProductSku));
    }

    /**
     * 修改产品SKU
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:edit')")
    @Log(title = "产品SKU", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalProductSku portalProductSku) {
        portalProductSku.setUpdateBy(getUsername());
        return toAjax(portalProductSkuService.updatePortalProductSku(portalProductSku));
    }

    /**
     * 删除产品SKU
     */
    @PreAuthorize("@ss.hasPermi('portal:product:sku:remove')")
    @Log(title = "产品SKU", businessType = BusinessType.DELETE)
    @DeleteMapping("/{skuIds}")
    public AjaxResult remove(@PathVariable Long[] skuIds) {
        return toAjax(portalProductSkuService.deletePortalProductSkuBySkuIds(skuIds));
    }
}
