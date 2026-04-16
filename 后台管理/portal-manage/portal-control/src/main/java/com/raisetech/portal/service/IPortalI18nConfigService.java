package com.raisetech.portal.service;

import java.util.List;
import com.raisetech.portal.domain.PortalI18nConfig;

/**
 * 多语言配置Service接口
 *
 * @author 王有政
 */
public interface IPortalI18nConfigService {
    /**
     * 查询多语言配置
     *
     * @param configId 配置ID
     * @return 多语言配置
     */
    public PortalI18nConfig selectPortalI18nConfigByConfigId(Long configId);

    /**
     * 查询多语言配置列表
     *
     * @param portalI18nConfig 多语言配置
     * @return 多语言配置集合
     */
    public List<PortalI18nConfig> selectPortalI18nConfigList(PortalI18nConfig portalI18nConfig);

    /**
     * 查询所有启用的语言配置（按排序字段排序）
     *
     * @return 启用的语言配置集合
     */
    public List<PortalI18nConfig> selectEnabledLanguageList();

    /**
     * 新增多语言配置
     *
     * @param portalI18nConfig 多语言配置
     * @return 结果
     */
    public int insertPortalI18nConfig(PortalI18nConfig portalI18nConfig);

    /**
     * 修改多语言配置
     *
     * @param portalI18nConfig 多语言配置
     * @return 结果
     */
    public int updatePortalI18nConfig(PortalI18nConfig portalI18nConfig);

    /**
     * 批量删除多语言配置
     *
     * @param configIds 需要删除的配置ID数组
     * @return 结果
     */
    public int deletePortalI18nConfigByConfigIds(Long[] configIds);

    /**
     * 获取默认语言代码
     *
     * @return 默认语言代码（如 zh-CN）
     */
    public String getDefaultLangCode();
}
