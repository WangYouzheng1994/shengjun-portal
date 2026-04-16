package com.raisetech.portal.service;

import java.util.List;
import com.raisetech.portal.domain.PortalArticle;

/**
 * 文章Service接口
 *
 * @author 王有政
 */
public interface IPortalArticleService {
    /**
     * 查询文章
     *
     * @param 文章ID
     * @return 文章
     */
    public PortalArticle selectPortalArticleByArticleId(Long articleId);

    /**
     * 查询文章列表
     *
     * @param portalArticle 文章
     * @return 文章集合
     */
    public List<PortalArticle> selectPortalArticleList(PortalArticle portalArticle);

    /**
     * 新增文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    public int insertPortalArticle(PortalArticle portalArticle);

    /**
     * 修改文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    public int updatePortalArticle(PortalArticle portalArticle);

    /**
     * 批量删除文章
     *
     * @param 需要删除的文章ID
     * @return 结果
     */
    public int deletePortalArticleByArticleIds(Long[] articleIds);

    /**
     * 删除文章信息
     *
     * @param 文章ID
     * @return 结果
     */
    public int deletePortalArticleByArticleId(Long articleId);

    /**
     * 发布文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    public int publishArticle(PortalArticle portalArticle);

    /**
     * 下架文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    public int offlineArticle(PortalArticle portalArticle);

    /**
     * 填充文章的多语言翻译内容
     * 根据指定语言代码批量查询翻译数据并填充到文章对象中
     * 如果某字段没有翻译则保留主表的原始值（回退机制）
     *
     * @param articles 文章列表
     * @param langCode 目标语言代码（如 en-US）
     */
    public void fillI18nContent(List<PortalArticle> articles, String langCode);
}
