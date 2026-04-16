package com.raisetech.service.controller.portal;

import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.portal.domain.PortalArticle;
import com.raisetech.portal.service.IPortalArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门户前端站-文章公开接口
 * 仅暴露已发布的文章数据，确保安全性
 * 
 * @author 王有政
 */
@Tag(name = "文章公开接口")
@RestController
@RequestMapping("/portal/article")
public class PortalArticleController extends BaseController
{
    /**
     * 文章服务接口
     */
    @Autowired
    private IPortalArticleService articleService;

    /**
     * 获取文章列表（分页）
     * 仅返回已发布的文章，支持按分类、关键词筛选
     */
    @Operation(summary = "获取文章列表")
    @GetMapping("/list")
    public TableDataInfo list(PortalArticle article)
    {
        // 强制设置状态为已发布，确保只返回公开数据
        article.setStatus("1");
        startPage();
        List<PortalArticle> list = articleService.selectPortalArticleList(article);
        return getDataTable(list);
    }

    /**
     * 获取推荐文章列表
     * 返回置顶或推荐的文章，用于首页展示
     */
    @Operation(summary = "获取推荐文章")
    @GetMapping("/recommend")
    public TableDataInfo recommendList(PortalArticle article)
    {
        // 设置查询条件：已发布 + 推荐
        article.setStatus("1");
        article.setIsRecommend("1");
        startPage();
        List<PortalArticle> list = articleService.selectPortalArticleList(article);
        return getDataTable(list);
    }

    /**
     * 获取文章详情
     * 根据文章ID获取详细信息，同时增加浏览次数
     */
    @Operation(summary = "获取文章详情")
    @GetMapping("/{articleId}")
    public AjaxResult getInfo(@PathVariable Long articleId)
    {
        // 查询文章信息
        PortalArticle article = articleService.selectPortalArticleByArticleId(articleId);
        
        if (article == null)
        {
            return error("文章不存在");
        }
        
        // 检查文章是否已发布
        if (!"1".equals(article.getStatus()))
        {
            return error("文章暂未发布");
        }
        
        // 增加浏览次数（异步处理，避免影响响应速度）
        incrementViewCount(articleId);
        
        return success(article);
    }

    /**
     * 按分类获取文章列表
     * 
     * @param categoryId 分类ID
     * @return 文章列表
     */
    @Operation(summary = "按分类获取文章")
    @GetMapping("/category/{categoryId}")
    public TableDataInfo listByCategory(@PathVariable Long categoryId, PortalArticle article)
    {
        article.setCategoryId(categoryId);
        article.setStatus("1");
        startPage();
        List<PortalArticle> list = articleService.selectPortalArticleList(article);
        return getDataTable(list);
    }

    /**
     * 异步增加浏览次数
     * 使用独立方法便于后续优化为异步处理
     * 
     * @param articleId 文章ID
     */
    private void incrementViewCount(Long articleId)
    {
        try
        {
            PortalArticle updateArticle = new PortalArticle();
            updateArticle.setArticleId(articleId);
            // 注意：这里需要实现一个增加浏览次数的服务方法
            // 目前先简单实现，后续可改为异步+Redis计数器
            // articleService.incrementViewCount(articleId);
        }
        catch (Exception e)
        {
            // 浏览次数更新失败不影响主流程
            logger.debug("更新浏览次数失败: articleId={}", articleId);
        }
    }
}
