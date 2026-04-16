package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalArticleI18n;
import org.apache.ibatis.annotations.Param;

/**
 * 文章多语言Mapper接口
 *
 * @author 王有政
 */
public interface PortalArticleI18nMapper {
    /**
     * 查询文章多语言
     *
     * @param id 主键ID
     * @return 文章多语言
     */
    public PortalArticleI18n selectPortalArticleI18nById(Long id);

    /**
     * 根据文章ID和语言代码查询文章多语言
     *
     * @param articleId 文章ID
     * @param langCode 语言代码
     * @return 文章多语言
     */
    public PortalArticleI18n selectByArticleIdAndLang(@Param("articleId") Long articleId, @Param("langCode") String langCode);

    /**
     * 批量查询文章的多语言翻译（根据多个文章ID和指定语言）
     *
     * @param articleIds 文章ID列表
     * @param langCode 语言代码
     * @return 文章多语言集合
     */
    public List<PortalArticleI18n> selectByArticleIdsAndLang(@Param("articleIds") List<Long> articleIds, @Param("langCode") String langCode);

    /**
     * 根据文章ID查询所有语言版本的翻译
     *
     * @param articleId 文章ID
     * @return 文章多语言集合
     */
    public List<PortalArticleI18n> selectByArticleId(Long articleId);

    /**
     * 新增文章多语言
     *
     * @param portalArticleI18n 文章多语言
     * @return 结果
     */
    public int insertPortalArticleI18n(PortalArticleI18n portalArticleI18n);

    /**
     * 修改文章多语言
     *
     * @param portalArticleI18n 文章多语言
     * @return 结果
     */
    public int updatePortalArticleI18n(PortalArticleI18n portalArticleI18n);

    /**
     * 删除文章多语言
     *
     * @param id 主键ID
     * @return 结果
     */
    public int deletePortalArticleI18nById(Long id);

    /**
     * 根据文章ID删除所有语言版本的多语言数据
     *
     * @param articleId 文章ID
     * @return 结果
     */
    public int deleteByArticleId(Long articleId);

    /**
     * 批量根据文章ID删除多语言数据
     *
     * @param articleIds 文章ID数组
     * @return 结果
     */
    public int deleteByArticleIds(@Param("articleIds") Long[] articleIds);
}
