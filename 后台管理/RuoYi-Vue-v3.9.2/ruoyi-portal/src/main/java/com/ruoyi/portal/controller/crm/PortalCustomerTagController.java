package com.ruoyi.portal.controller.crm;

import java.util.List;

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
import com.ruoyi.portal.domain.PortalCustomerTag;
import com.ruoyi.portal.mapper.PortalCustomerTagMapper;
import com.ruoyi.common.core.page.TableDataInfo;

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
