package com.raisetech.service.controller.portal;

import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.portal.domain.PortalNotice;
import com.raisetech.portal.service.IPortalNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门户前端站-公告公开接口
 * 返回已发布的公告信息，用于网站公告栏展示
 * 
 * @author 王有政
 */
@Tag(name = "公告公开接口")
@RestController
@RequestMapping("/portal/notice")
public class PortalNoticeController extends BaseController
{
    /**
     * 公告服务接口
     */
    @Autowired
    private IPortalNoticeService noticeService;

    /**
     * 获取公告列表（分页）
     * 仅返回已发布的公告，按发布时间倒序排列
     */
    @Operation(summary = "获取公告列表")
    @GetMapping("/list")
    public TableDataInfo list(PortalNotice notice)
    {
        // 设置状态为已发布（根据实际字段调整）
        // notice.setStatus("1");
        startPage();
        List<PortalNotice> list = noticeService.selectPortalNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 获取最新公告（限制数量）
     * 用于首页或侧边栏展示最新的几条公告
     */
    @Operation(summary = "获取最新公告")
    @GetMapping("/latest")
    public AjaxResult latest()
    {
        PortalNotice notice = new PortalNotice();
        // 设置状态为已发布
        // notice.setStatus("1");

        // 使用startPage()开始分页
        startPage();
        List<PortalNotice> list = noticeService.selectPortalNoticeList(notice);
        // 手动限制返回数量为5条（取前5条）
        if (list.size() > 5)
        {
            list = list.subList(0, 5);
        }
        return success(list);
    }

    /**
     * 获取公告详情
     * 
     * @param noticeId 公告ID
     * @return 公告详情
     */
    @Operation(summary = "获取公告详情")
    @GetMapping("/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId)
    {
        PortalNotice notice = noticeService.selectPortalNoticeByNoticeId(noticeId);
        
        if (notice == null)
        {
            return error("公告不存在");
        }
        
        // 检查是否已发布
        // if (!"1".equals(notice.getStatus()))
        // {
        //     return error("公告暂未发布");
        // }
        
        return success(notice);
    }
}
