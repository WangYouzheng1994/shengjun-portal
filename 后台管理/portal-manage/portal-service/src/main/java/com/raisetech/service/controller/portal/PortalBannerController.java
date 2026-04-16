package com.raisetech.service.controller.portal;

import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.portal.domain.PortalBanner;
import com.raisetech.portal.service.IPortalBannerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门户前端站-轮播图公开接口
 * 返回已启用的轮播图数据，用于首页展示
 * 
 * @author 王有政
 */
@Tag(name = "轮播图公开接口")
@RestController
@RequestMapping("/portal/banner")
public class PortalBannerController extends BaseController
{
    /**
     * 轮播图服务接口
     */
    @Autowired
    private IPortalBannerService bannerService;

    /**
     * 获取轮播图列表
     * 返回所有已启用的轮播图，按排序号升序排列
     */
    @Operation(summary = "获取轮播图列表")
    @GetMapping("/list")
    public AjaxResult list(PortalBanner banner)
    {
        // 设置查询条件：仅查询启用状态的轮播图
        // 注意：需要根据实际实体类字段调整
        startPage();
        List<PortalBanner> list = bannerService.selectPortalBannerList(banner);
        return success(getDataTable(list));
    }

    /**
     * 获取首页展示的轮播图（限制数量）
     * 通常首页只展示3-5张轮播图
     */
    @Operation(summary = "获取首页轮播图")
    @GetMapping("/home")
    public AjaxResult homeList()
    {
        PortalBanner banner = new PortalBanner();
        // 设置状态为启用（根据实际字段调整）
        // banner.setStatus("1");

        // 使用startPage()开始分页
        startPage();
        List<PortalBanner> list = bannerService.selectPortalBannerList(banner);
        // 手动限制返回数量为5条（取前5条）
        if (list.size() > 5)
        {
            list = list.subList(0, 5);
        }
        return success(list);
    }
}
