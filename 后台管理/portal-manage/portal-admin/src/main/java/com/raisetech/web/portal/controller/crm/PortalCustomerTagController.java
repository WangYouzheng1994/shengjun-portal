package com.raisetech.web.portal.controller.crm;

import com.raisetech.common.annotation.Log;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.enums.BusinessType;
import com.raisetech.portal.domain.PortalCustomerTag;
import com.raisetech.portal.mapper.PortalCustomerTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理Controller
 *
 * @author 王有政
 */
@RestController
@RequestMapping("/portal/crm/tag")
public class PortalCustomerTagController extends BaseController {

    @Autowired
    private PortalCustomerTagMapper portalCustomerTagMapper;

    /**
     * 查询标签列表
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:tag:list')")
    @GetMapping("/list")
    public TableDataInfo list(PortalCustomerTag portalCustomerTag) {
        startPage();
        List<PortalCustomerTag> list = portalCustomerTagMapper.selectPortalCustomerTagList(portalCustomerTag);
        return getDataTable(list);
    }

    /**
     * 获取标签详细信息
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:tag:query')")
    @GetMapping(value = "/{tagId}")
    public AjaxResult getInfo(@PathVariable("tagId") Long tagId) {
        return success(portalCustomerTagMapper.selectPortalCustomerTagByTagId(tagId));
    }

    /**
     * 新增标签
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:tag:add')")
    @Log(title = "客户标签", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PortalCustomerTag portalCustomerTag) {
        return toAjax(portalCustomerTagMapper.insertPortalCustomerTag(portalCustomerTag));
    }

    /**
     * 修改标签
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:tag:edit')")
    @Log(title = "客户标签", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PortalCustomerTag portalCustomerTag) {
        return toAjax(portalCustomerTagMapper.updatePortalCustomerTag(portalCustomerTag));
    }

    /**
     * 删除标签
     */
    @PreAuthorize("@ss.hasPermi('portal:crm:tag:remove')")
    @Log(title = "客户标签", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tagIds}")
    public AjaxResult remove(@PathVariable Long[] tagIds) {
        return toAjax(portalCustomerTagMapper.deletePortalCustomerTagByTagIds(tagIds));
    }
}
