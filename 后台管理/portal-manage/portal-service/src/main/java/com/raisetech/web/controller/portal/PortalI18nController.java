package com.raisetech.web.controller.portal;

import java.util.List;
import com.raisetech.common.core.controller.BaseController;
import com.raisetech.common.core.domain.AjaxResult;
import com.raisetech.common.core.page.TableDataInfo;
import com.raisetech.common.utils.SecurityUtils;
import com.raisetech.portal.domain.PortalI18nConfig;
import com.raisetech.portal.service.IPortalI18nConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 多语言管理Controller
 * 提供语言配置管理和翻译内容的CRUD接口
 *
 * @author 王有政
 */
@Tag(name = "多语言管理")
@RestController
@RequestMapping("/portal/i18n")
public class PortalI18nController extends BaseController {

    @Autowired
    private IPortalI18nConfigService i18nConfigService;

    /**
     * 获取所有启用的语言配置列表
     * 用于前端语言选择器展示
     */
    @Operation(summary = "获取启用的语言列表")
    @GetMapping("/config/enabled")
    public AjaxResult getEnabledLanguages() {
        List<PortalI18nConfig> list = i18nConfigService.selectEnabledLanguageList();
        return success(list);
    }

    /**
     * 获取默认语言代码
     */
    @Operation(summary = "获取默认语言代码")
    @GetMapping("/config/default")
    public AjaxResult getDefaultLangCode() {
        String defaultLang = i18nConfigService.getDefaultLangCode();
        return success(defaultLang);
    }

    /**
     * 查询语言配置列表（分页）
     * 需要管理员权限
     */
    @PreAuthorize("@ss.hasPermi('portal:i18n:config:list')")
    @Operation(summary = "查询语言配置列表")
    @GetMapping("/config/list")
    public TableDataInfo list(PortalI18nConfig portalI18nConfig) {
        startPage();
        List<PortalI18nConfig> list = i18nConfigService.selectPortalI18nConfigList(portalI18nConfig);
        return getDataTable(list);
    }

    /**
     * 获取语言配置详情
     */
    @PreAuthorize("@ss.hasPermi('portal:i18n:config:query')")
    @Operation(summary = "获取语言配置详情")
    @GetMapping("/config/{configId}")
    public AjaxResult getInfo(@PathVariable Long configId) {
        return success(i18nConfigService.selectPortalI18nConfigByConfigId(configId));
    }

    /**
     * 新增语言配置
     */
    @PreAuthorize("@ss.hasPermi('portal:i18n:config:add')")
    @Operation(summary = "新增语言配置")
    @PostMapping("/config")
    public AjaxResult add(@RequestBody PortalI18nConfig portalI18nConfig) {
        // 校验语言代码是否已存在
        PortalI18nConfig query = new PortalI18nConfig();
        query.setLangCode(portalI18nConfig.getLangCode());
        List<PortalI18nConfig> existList = i18nConfigService.selectPortalI18nConfigList(query);
        if (existList != null && !existList.isEmpty()) {
            return error("该语言代码已存在");
        }
        // 如果设置为默认语言，需要先取消其他默认设置
        if ("1".equals(portalI18nConfig.getIsDefault())) {
            resetDefaultLanguage();
        }
        portalI18nConfig.setCreateBy(SecurityUtils.getUsername());
        return toAjax(i18nConfigService.insertPortalI18nConfig(portalI18nConfig));
    }

    /**
     * 修改语言配置
     */
    @PreAuthorize("@ss.hasPermi('portal:i18n:config:edit')")
    @Operation(summary = "修改语言配置")
    @PutMapping("/config")
    public AjaxResult edit(@RequestBody PortalI18nConfig portalI18nConfig) {
        // 如果设置为默认语言，需要先取消其他默认设置
        if ("1".equals(portalI18nConfig.getIsDefault())) {
            resetDefaultLanguage();
        }
        portalI18nConfig.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(i18nConfigService.updatePortalI18nConfig(portalI18nConfig));
    }

    /**
     * 删除语言配置
     */
    @PreAuthorize("@ss.hasPermi('portal:i18n:config:remove')")
    @Operation(summary = "删除语言配置")
    @Delete("/config/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds) {
        return toAjax(i18nConfigService.deletePortalI18nConfigByConfigIds(configIds));
    }

    /**
     * 重置所有语言的默认标记
     * 确保只有一个默认语言
     */
    private void resetDefaultLanguage() {
        PortalI18nConfig updateConfig = new PortalI18nConfig();
        updateConfig.setIsDefault("0");
        updateConfig.setUpdateBy(SecurityUtils.getUsername());
        // 查询所有标记为默认的配置并重置
        PortalI18nConfig query = new PortalI18nConfig();
        query.setIsDefault("1");
        List<PortalI18nConfig> defaultList = i18nConfigService.selectPortalI18nConfigList(query);
        if (defaultList != null) {
            for (PortalI18nConfig config : defaultList) {
                config.setIsDefault("0");
                i18nConfigService.updatePortalI18nConfig(config);
            }
        }
    }
}
