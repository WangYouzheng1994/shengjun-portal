package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalCompanyInfoI18n;
import org.apache.ibatis.annotations.Param;

/**
 * 企业信息多语言Mapper接口
 *
 * @author 王有政
 */
public interface PortalCompanyInfoI18nMapper {
    /**
     * 查询企业信息多语言
     *
     * @param id 主键ID
     * @return 企业信息多语言
     */
    public PortalCompanyInfoI18n selectPortalCompanyInfoI18nById(Long id);

    /**
     * 根据信息ID和语言代码查询企业信息多语言
     *
     * @param infoId 信息ID
     * @param langCode 语言代码
     * @return 企业信息多语言
     */
    public PortalCompanyInfoI18n selectByInfoIdAndLang(@Param("infoId") Long infoId, @Param("langCode") String langCode);

    /**
     * 批量查询企业信息的多种语言翻译
     *
     * @param infoIds 信息ID列表
     * @param langCode 语言代码
     * @return 企业信息多语言集合
     */
    public List<PortalCompanyInfoI18n selectByInfoIdsAndLang(@Param("infoIds") List<Long> infoIds, @Param("langCode") String langCode);

    /**
     * 根据信息ID查询所有语言版本
     *
     * @param infoId 信息ID
     * @return 企业信息多语言集合
     */
    public List<PortalCompanyInfoI18n selectByInfoId(Long infoId);

    /**
     * 新增企业信息多语言
     *
     * @param portalCompanyInfoI18n 企业信息多语言
     * @return 结果
     */
    public int insertPortalCompanyInfoI18n(PortalCompanyInfoI18n portalCompanyInfoI18n);

    /**
     * 修改企业信息多语言
     *
     * @param portalCompanyInfoI18n 企业信息多语言
     * @return 结果
     */
    public int updatePortalCompanyInfoI18n(PortalCompanyInfoI18n portalCompanyInfoI18n);

    /**
     * 删除企业信息多语言
     *
     * @param id 主键ID
     * @return 结果
     */
    public int deletePortalCompanyInfoI18nById(Long id);

    /**
     * 根据信息ID删除所有语言版本
     *
     * @param infoId 信息ID
     * @return 结果
     */
    public int deleteByInfoId(Long infoId);

    /**
     * 批量根据信息ID删除多语言数据
     *
     * @param infoIds 信息ID数组
     * @return 结果
     */
    public int deleteByInfoIds(@Param("infoIds") Long[] infoIds);
}
