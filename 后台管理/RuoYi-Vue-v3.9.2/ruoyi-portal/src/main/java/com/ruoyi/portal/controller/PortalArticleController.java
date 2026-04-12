package com.ruoyi.portal.controller;

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
import com.ruoyi.portal.domain.PortalArticle;
import com.ruoyi.portal.service.IPortalArticleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文章Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/article")
public class PortalArticleController extends BaseController {

    @Autowired
    private IPortalArticleService portalArticleService;

    /**
     * 查询文章列表
     */
    @PreAuthorize("@ss.hasPermi('portal:article:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalArticle portalArticle) {
        startPage();
        List<PortalArticle> list = portalArticleService.selectPortalArticleList(portalArticle);
        return getDataTable(list);
    }

    /**
     * 导出文章列表
     */
    @PreAuthorize("@ss.hasPermi('portal:article:export')")
    @Log(title = "文章", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalArticle portalArticle) {
        List<PortalArticle> list = portalArticleService.selectPortalArticleList(portalArticle);
        ExcelUtil<PortalArticle> util = new ExcelUtil<PortalArticle>(PortalArticle.class);
        util.exportExcel(response, list, "文章数据");
    }

    /**
     * 获取文章详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:article:query')")
    @GetMapping(value = "/{articleId}")
    public AjaxResult getInfo(@PathVariable("articleId") Long articleId) {
        return success(portalArticleService.selectPortalArticleByArticleId(articleId));
    }

    /**
     * 新增文章
     */
    @PreAuthorize("@ss.hasPermi('portal:article:add')")
    @Log(title = "文章", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalArticle portalArticle) {
        return toAjax(portalArticleService.insertPortalArticle(portalArticle));
    }

    /**
     * 修改文章
     */
    @PreAuthorize("@ss.hasPermi('portal:article:edit')")
    @Log(title = "文章", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalArticle portalArticle) {
        return toAjax(portalArticleService.updatePortalArticle(portalArticle));
    }

    /**
     * 删除文章
     */
    @PreAuthorize("@ss.hasPermi('portal:article:remove')")
    @Log(title = "文章", businessType = BusinessType.DELETE)
    @DeleteMapping("/{articleIds}")
    public AjaxResult remove(@PathVariable Long[] articleIds) {
        return toAjax(portalArticleService.deletePortalArticleByArticleIds(articleIds));
    }

    /**
     * 发布文章
     */
    @PreAuthorize("@ss.hasPermi('portal:article:publish')")
    @Log(title = "文章发布", businessType = BusinessType.UPDATE)
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody PortalArticle portalArticle) {
        return toAjax(portalArticleService.publishArticle(portalArticle));
    }

    /**
     * 下架文章
     */
    @PreAuthorize("@ss.hasPermi('portal:article:edit')")
    @Log(title = "文章下架", businessType = BusinessType.UPDATE)
    @PostMapping("/offline")
    public AjaxResult offline(@RequestBody PortalArticle portalArticle) {
        return toAjax(portalArticleService.offlineArticle(portalArticle));
    }
}
