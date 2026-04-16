package com.raisetech.web.portal.controller.article;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.common.utils.poi.ExcelUtil;
import com.raisetech.portal.domain.PortalArticleCategory;
import com.raisetech.portal.service.IPortalArticleCategoryService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章分类Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/article/category")
public class PortalArticleCategoryController extends BaseController {

    @Autowired
    private IPortalArticleCategoryService portalArticleCategoryService;

    /**
     * 查询文章分类列表
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalArticleCategory portalArticleCategory) {
        startPage();
        List<PortalArticleCategory> list = portalArticleCategoryService.selectPortalArticleCategoryList(portalArticleCategory);
        return getDataTable(list);
    }

    /**
     * 获取文章分类下拉树列表
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:list')")
    @GetMapping("/treeselect")
    public AjaxResult treeselect(PortalArticleCategory portalArticleCategory) {
        List<PortalArticleCategory> categories = portalArticleCategoryService.selectPortalArticleCategoryList(portalArticleCategory);
        return success(portalArticleCategoryService.buildCategoryTreeSelect(categories));
    }

    /**
     * 导出文章分类列表
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:export')")
    @Log(title = "文章分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalArticleCategory portalArticleCategory) {
        List<PortalArticleCategory> list = portalArticleCategoryService.selectPortalArticleCategoryList(portalArticleCategory);
        ExcelUtil<PortalArticleCategory> util = new ExcelUtil<PortalArticleCategory>(PortalArticleCategory.class);
        util.exportExcel(response, list, "文章分类数据");
    }

    /**
     * 获取文章分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId) {
        return success(portalArticleCategoryService.selectPortalArticleCategoryByCategoryId(categoryId));
    }

    /**
     * 新增文章分类
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:add')")
    @Log(title = "文章分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalArticleCategory portalArticleCategory) {
        return toAjax(portalArticleCategoryService.insertPortalArticleCategory(portalArticleCategory));
    }

    /**
     * 修改文章分类
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:edit')")
    @Log(title = "文章分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalArticleCategory portalArticleCategory) {
        return toAjax(portalArticleCategoryService.updatePortalArticleCategory(portalArticleCategory));
    }

    /**
     * 删除文章分类
     */
    @PreAuthorize("@ss.hasPermi('portal:article:category:remove')")
    @Log(title = "文章分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds) {
        return toAjax(portalArticleCategoryService.deletePortalArticleCategoryByCategoryIds(categoryIds));
    }
}
