package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.domain.PortalI18nConfig;
import com.raisetech.portal.mapper.PortalI18nConfigMapper;
import com.raisetech.portal.service.IPortalI18nConfigService;

/**
 * 多语言配置Service实现
 *
 * @author 王有政
 */
@Service
public class PortalI18nConfigServiceImpl implements IPortalI18nConfigService {

    @Autowired
    private PortalI18nConfigMapper i18nConfigMapper;

    /**
     * 查询多语言配置
     */
    @Override
    public PortalI18nConfig selectPortalI18nConfigByConfigId(Long configId) {
        return i18nConfigMapper.selectPortalI18nConfigByConfigId(configId);
    }

    /**
     * 查询多语言配置列表
     */
    @Override
    public List<PortalI18nConfig> selectPortalI18nConfigList(PortalI18nConfig portalI18nConfig) {
        return i18nConfigMapper.selectPortalI18nConfigList(portalI18nConfig);
    }

    /**
     * 查询所有启用的语言配置
     */
    @Override
    public List<PortalI18nConfig> selectEnabledLanguageList() {
        return i18nConfigMapper.selectEnabledLanguageList();
    }

    /**
     * 新增多语言配置
     */
    @Override
    public int insertPortalI18nConfig(PortalI18nConfig portalI18nConfig) {
        return i18nConfigMapper.insertPortalI18nConfig(portalI18nConfig);
    }

    /**
     * 修改多语言配置
     */
    @Override
    public int updatePortalI18nConfig(PortalI18nConfig portalI18nConfig) {
        return i18nConfigMapper.updatePortalI18nConfig(portalI18nConfig);
    }

    /**
     * 批量删除多语言配置
     */
    @Override
    public int deletePortalI18nConfigByConfigIds(Long[] configIds) {
        return i18nConfigMapper.deletePortalI18nConfigByConfigIds(configIds);
    }

    /**
     * 获取默认语言代码
     * 从数据库查询标记为默认的语言配置，如果未找到则返回中文作为默认值
     */
    @Override
    public String getDefaultLangCode() {
        PortalI18nConfig query = new PortalI18nConfig();
        query.setIsDefault("1");
        List<PortalI18nConfig> list = i18nConfigMapper.selectPortalI18nConfigList(query);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getLangCode();
        }
        return "zh-CN";
    }
}
