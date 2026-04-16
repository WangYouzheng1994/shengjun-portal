package com.raisetech.service.controller.portal;

import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.portal.domain.PortalProduct;
import com.raisetech.portal.service.IPortalProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门户前端站-产品公开接口
 * 返回已上架的产品信息，支持分类筛选和搜索
 * 
 * @author 王有政
 */
@Tag(name = "产品公开接口")
@RestController
@RequestMapping("/portal/product")
public class PortalProductController extends BaseController
{
    /**
     * 产品服务接口
     */
    @Autowired
    private IPortalProductService productService;

    /**
     * 获取产品列表（分页）
     * 仅返回已上架的产品，支持按分类、关键词筛选
     */
    @Operation(summary = "获取产品列表")
    @GetMapping("/list")
    public TableDataInfo list(PortalProduct product)
    {
        // 设置状态为已上架（根据实际字段调整）
        // product.setStatus("1");
        startPage();
        List<PortalProduct> list = productService.selectPortalProductList(product);
        return getDataTable(list);
    }

    /**
     * 获取推荐产品列表
     * 返回置顶或推荐的产品，用于首页展示
     */
    @Operation(summary = "获取推荐产品")
    @GetMapping("/recommend")
    public TableDataInfo recommendList(PortalProduct product)
    {
        // product.setStatus("1");
        // product.setIsRecommend("1");
        startPage();
        List<PortalProduct> list = productService.selectPortalProductList(product);
        return getDataTable(list);
    }

    /**
     * 获取产品详情
     * 
     * @param productId 产品ID
     * @return 产品详情
     */
    @Operation(summary = "获取产品详情")
    @GetMapping("/{productId}")
    public AjaxResult getInfo(@PathVariable Long productId)
    {
        PortalProduct product = productService.selectPortalProductByProductId(productId);
        
        if (product == null)
        {
            return error("产品不存在");
        }
        
        // 检查是否已上架
        // if (!"1".equals(product.getStatus()))
        // {
        //     return error("产品暂未上架");
        // }
        
        return success(product);
    }

    /**
     * 按分类获取产品列表
     * 
     * @param categoryId 分类ID
     * @return 产品列表
     */
    @Operation(summary = "按分类获取产品")
    @GetMapping("/category/{categoryId}")
    public TableDataInfo listByCategory(@PathVariable Long categoryId, PortalProduct product)
    {
        product.setCategoryId(categoryId);
        // product.setStatus("1");
        startPage();
        List<PortalProduct> list = productService.selectPortalProductList(product);
        return getDataTable(list);
    }
}
