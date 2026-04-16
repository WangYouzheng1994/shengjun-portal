package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalArticleCategoryI18n;
import org.apache.ibatis.annotations.Param;

/**
 * 文章分类多语言Mapper接口
 *
 * @author 王有政
 */
public interface PortalArticleCategoryI18nMapper {
    /**
     * 查询文章分类多语言
     *
     * @param id 主键ID
     * @return 文章分类多语言
     */
    public PortalArticleCategoryI18n selectPortalArticleCategoryI18nById(Long id);

    /**
     * 根据分类ID和语言代码查询分类多语言
     *
     * @param categoryId 分类ID
     * @param langCode 语言代码
     * @return 文章分类多语言
     */
    public PortalArticleCategoryI18n selectByCategoryIdAndLang(@Param("categoryId") Long categoryId, @Param("langCode") String langCode);

    /**
     * 批量查询分类的多语言翻译
     *
     * @param categoryIds 分类ID列表
     * @param langCode 语言代码
     * @return 分类多语言集合
     */
    public List<PortalArticleCategoryI18n> selectByCategoryIdsAndLang(@Param("categoryIds") List<Long> categoryIds, @Param("langCode") String langCode);

    /**
     * 根据分类ID查询所有语言版本
     *
     * @param categoryId 分类ID
     * @return 分类多语言集合
     */
    public List<PortalArticleCategoryI18n> selectByCategoryId(Long categoryId);

    /**
     * 新增文章分类多语言
     *
     * @param portalArticleCategoryI18n 文章分类多语言
     * @return 结果
     */
    public int insertPortalArticleCategoryI18n(PortalArticleCategoryI18n portalArticleCategoryI18n);

    /**
     * 修改文章分类多语言
     *
     * @param portalArticleCategoryI18n 文章分类多语言
     * @return 结果
     */
    public int updatePortalArticleCategoryI18n(PortalArticleCategoryI18n portalArticleCategoryI18n);

    /**
     * 删除文章分类多语言
     *
     * @param id 主键ID
     * @return 结果
     */
    public int deletePortalArticleCategoryI18nById(Long id);

    /**
     * 根据分类ID删除所有语言版本
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    public int deleteByCategoryId(Long categoryId);

    /**
     * 批量根据分类ID删除多语言数据
     *
     * @param categoryIds 分类ID数组
     * @return 结果
     */
    public int deleteByCategoryIds(@Param("categoryIds") Long[] categoryIds);
}
