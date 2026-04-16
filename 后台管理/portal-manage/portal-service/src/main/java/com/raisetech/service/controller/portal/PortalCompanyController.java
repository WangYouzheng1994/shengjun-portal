package com.raisetech.service.controller.portal;

import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.portal.domain.PortalCompanyInfo;
import com.raisetech.portal.service.IPortalCompanyInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门户前端站-企业信息公开接口
 * 返回企业的基本信息、联系方式、简介等
 * 
 * @author 王有政
 */
@Tag(name = "企业信息公开接口")
@RestController
@RequestMapping("/portal/company")
public class PortalCompanyController extends BaseController
{
    /**
     * 企业信息服务接口
     */
    @Autowired
    private IPortalCompanyInfoService companyInfoService;

    /**
     * 获取企业基本信息
     * 包括公司名称、地址、联系方式等
     * 默认返回第一条企业信息（通常系统只配置一条）
     */
    @Operation(summary = "获取企业基本信息")
    @GetMapping("/info")
    public AjaxResult getCompanyInfo()
    {
        // 查询企业信息列表（通常只配置一条）
        List<PortalCompanyInfo> list = companyInfoService.selectPortalCompanyInfoList(new PortalCompanyInfo());
        
        if (list == null || list.isEmpty())
        {
            return error("企业信息暂未配置");
        }
        
        // 返回第一条企业信息
        return success(list.get(0));
    }

    /**
     * 获取企业简介
     * 用于"关于我们"页面展示
     */
    @Operation(summary = "获取企业简介")
    @GetMapping("/about")
    public AjaxResult getAboutUs()
    {
        List<PortalCompanyInfo> list = companyInfoService.selectPortalCompanyInfoList(new PortalCompanyInfo());
        
        if (list == null || list.isEmpty())
        {
            return error("企业信息暂未配置");
        }
        
        // 仅返回简介相关字段（脱敏处理）
        PortalCompanyInfo company = list.get(0);
        // 可根据需要构建返回对象，仅返回必要字段
        return success(company);
    }

    /**
     * 获取联系方式
     * 包括电话、邮箱、地址、二维码等
     */
    @Operation(summary = "获取联系方式")
    @GetMapping("/contact")
    public AjaxResult getContact()
    {
        List<PortalCompanyInfo> list = companyInfoService.selectPortalCompanyInfoList(new PortalCompanyInfo());
        
        if (list == null || list.isEmpty())
        {
            return error("联系信息暂未配置");
        }
        
        return success(list.get(0));
    }
}
