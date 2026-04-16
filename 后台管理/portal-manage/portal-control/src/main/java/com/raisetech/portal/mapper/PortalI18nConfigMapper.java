package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalI18nConfig;

/**
 * 多语言配置Mapper接口
 *
 * @author 王有政
 */
public interface PortalI18nConfigMapper {
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
     * 删除多语言配置
     *
     * @param configId 配置ID
     * @return 结果
     */
    public int deletePortalI18nConfigByConfigId(Long configId);

    /**
     * 批量删除多语言配置
     *
     * @param 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalI18nConfigByConfigIds(Long[] configIds);
}
