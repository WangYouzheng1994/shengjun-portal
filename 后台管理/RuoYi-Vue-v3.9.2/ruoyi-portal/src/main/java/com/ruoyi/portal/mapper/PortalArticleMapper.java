package com.ruoyi.portal.mapper;

import java.util.List;
import com.ruoyi.portal.domain.PortalArticle;

/**
 * 文章Mapper接口
 *
 * @author 王有政
 */
public interface PortalArticleMapper {
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
     * 删除文章
     *
     * @param 文章ID
     * @return 结果
     */
    public int deletePortalArticleByArticleId(Long articleId);

    /**
     * 批量删除文章
     *
     * @param 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalArticleByArticleIds(Long[] articleIds);
}
