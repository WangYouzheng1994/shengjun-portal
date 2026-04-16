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
import com.ruoyi.portal.domain.PortalProductBrand;
import com.ruoyi.portal.service.IPortalProductBrandService;

/**
 * 产品品牌管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/product/brand")
public class PortalProductBrandController extends BaseController {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(PortalProductBrandController.class);

    /**
     * 产品品牌Service接口
     */
    @Autowired
    private IPortalProductBrandService portalProductBrandService;

    /**
     * 查询产品品牌列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:brand:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalProductBrand portalProductBrand) {
        startPage();
        List<PortalProductBrand> list = portalProductBrandService.selectPortalProductBrandList(portalProductBrand);
        return getDataTable(list);
    }

    /**
     * 导出产品品牌列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:brand:export')")
    @Log(title = "产品品牌", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalProductBrand portalProductBrand) {
        List<PortalProductBrand> list = portalProductBrandService.selectPortalProductBrandList(portalProductBrand);
        ExcelUtil<PortalProductBrand> util = new ExcelUtil<PortalProductBrand>(PortalProductBrand.class);
        util.exportExcel(response, list, "产品品牌");
    }

    /**
     * 获取产品品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:product:brand:query')")
    @GetMapping(value = "/{brandId}")
    public AjaxResult getInfo(@PathVariable("brandId") Long brandId) {
        return success(portalProductBrandService.selectPortalProductBrandByBrandId(brandId));
    }

    /**
     * 新增产品品牌
     */
    @PreAuthorize("@ss.hasPermi('portal:product:brand:add')")
    @Log(title = "产品品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalProductBrand portalProductBrand) {
        portalProductBrand.setCreateBy(getUsername());
        return toAjax(portalProductBrandService.insertPortalProductBrand(portalProductBrand));
    }

    /**
     * 修改产品品牌
     */
    @PreAuthorize("@ss.hasPermi('portal:product:brand:edit')")
    @Log(title = "产品品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalProductBrand portalProductBrand) {
        portalProductBrand.setUpdateBy(getUsername());
        return toAjax(portalProductBrandService.updatePortalProductBrand(portalProductBrand));
    }

    /**
     * 删除产品品牌
     */
    @PreAuthorize("@ss.hasPermi('portal:product:brand:remove')")
    @Log(title = "产品品牌", businessType = BusinessType.DELETE)
    @DeleteMapping("/{brandIds}")
    public AjaxResult remove(@PathVariable Long[] brandIds) {
        return toAjax(portalProductBrandService.deletePortalProductBrandByBrandIds(brandIds));
    }
}
