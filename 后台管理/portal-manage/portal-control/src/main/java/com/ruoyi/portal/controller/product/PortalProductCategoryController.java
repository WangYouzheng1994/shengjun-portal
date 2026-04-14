package com.ruoyi.portal.controller.product;

import java.util.List;
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
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.portal.domain.PortalProductCategory;
import com.ruoyi.portal.service.IPortalProductCategoryService;

/**
 * 产品分类管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/product/category")
public class PortalProductCategoryController extends BaseController {

    /**
     * 日志记录器
     */
    private static final Logger log = LoggerFactory.getLogger(PortalProductCategoryController.class);

    /**
     * 产品分类Service接口
     */
    @Autowired
    private IPortalProductCategoryService portalProductCategoryService;

    /**
     * 查询产品分类列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:list')")
    @GetMapping("/list")
    public AjaxResult list(PortalProductCategory portalProductCategory) {
        List<PortalProductCategory> list = portalProductCategoryService.selectPortalProductCategoryList(portalProductCategory);
        return success(list);
    }

    /**
     * 查询产品分类树形结构
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:list')")
    @GetMapping("/tree")
    public AjaxResult tree(PortalProductCategory portalProductCategory) {
        List<PortalProductCategory> list = portalProductCategoryService.selectPortalProductCategoryTree(portalProductCategory);
        return success(list);
    }

    /**
     * 获取产品分类下拉树列表
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:list')")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(PortalProductCategory portalProductCategory) {
        List<PortalProductCategory> categories = portalProductCategoryService.selectPortalProductCategoryList(portalProductCategory);
        return success(portalProductCategoryService.buildCategoryTreeSelect(categories));
    }

    /**
     * 获取产品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId) {
        return success(portalProductCategoryService.selectPortalProductCategoryByCategoryId(categoryId));
    }

    /**
     * 新增产品分类
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:add')")
    @Log(title = "产品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalProductCategory portalProductCategory) {
        portalProductCategory.setCreateBy(getUsername());
        return toAjax(portalProductCategoryService.insertPortalProductCategory(portalProductCategory));
    }

    /**
     * 修改产品分类
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:edit')")
    @Log(title = "产品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalProductCategory portalProductCategory) {
        portalProductCategory.setUpdateBy(getUsername());
        return toAjax(portalProductCategoryService.updatePortalProductCategory(portalProductCategory));
    }

    /**
     * 删除产品分类
     */
    @PreAuthorize("@ss.hasPermi('portal:product:category:remove')")
    @Log(title = "产品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        return toAjax(portalProductCategoryService.deletePortalProductCategoryByCategoryIds(categoryIds));
    }
}
