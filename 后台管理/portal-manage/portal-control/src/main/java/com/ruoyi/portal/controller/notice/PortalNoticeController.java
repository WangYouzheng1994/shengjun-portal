package com.ruoyi.portal.controller.notice;

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
import com.ruoyi.portal.domain.PortalNotice;
import com.ruoyi.portal.service.IPortalNoticeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 门户公告Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/notice/notice")
public class PortalNoticeController extends BaseController {

    @Autowired
    private IPortalNoticeService portalNoticeService;

    /**
     * 查询公告列表
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalNotice portalNotice) {
        startPage();
        List<PortalNotice> list = portalNoticeService.selectPortalNoticeList(portalNotice);
        return getDataTable(list);
    }

    /**
     * 导出公告列表
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:export')")
    @Log(title = "公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PortalNotice portalNotice) {
        List<PortalNotice> list = portalNoticeService.selectPortalNoticeList(portalNotice);
        ExcelUtil<PortalNotice> util = new ExcelUtil<PortalNotice>(PortalNotice.class);
        util.exportExcel(response, list, "公告数据");
    }

    /**
     * 获取公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:query')")
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable("noticeId") Long noticeId) {
        return success(portalNoticeService.selectPortalNoticeByNoticeId(noticeId));
    }

    /**
     * 新增公告
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:add')")
    @Log(title = "公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalNotice portalNotice) {
        return toAjax(portalNoticeService.insertPortalNotice(portalNotice));
    }

    /**
     * 修改公告
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:edit')")
    @Log(title = "公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalNotice portalNotice) {
        return toAjax(portalNoticeService.updatePortalNotice(portalNotice));
    }

    /**
     * 删除公告
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:remove')")
    @Log(title = "公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(portalNoticeService.deletePortalNoticeByNoticeIds(noticeIds));
    }

    /**
     * 发布公告
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:publish')")
    @Log(title = "公告发布", businessType = BusinessType.UPDATE)
    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody PortalNotice portalNotice) {
        return toAjax(portalNoticeService.publishNotice(portalNotice));
    }

    /**
     * 停用公告
     */
    @PreAuthorize("@ss.hasPermi('portal:notice:offline')")
    @Log(title = "公告停用", businessType = BusinessType.UPDATE)
    @PostMapping("/offline")
    public AjaxResult offline(@RequestBody PortalNotice portalNotice) {
        return toAjax(portalNoticeService.offlineNotice(portalNotice));
    }
}
