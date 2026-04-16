package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalArticleCategory;

/**
 * 文章分类Mapper接口
 *
 * @author 王有政
 */
public interface PortalArticleCategoryMapper {
    /**
     * 查询文章分类
     *
     * @param 分类ID
     * @return 文章分类
     */
    public PortalArticleCategory selectPortalArticleCategoryByCategoryId(Long categoryId);

    /**
     * 查询文章分类列表
     *
     * @param portalArticleCategory 文章分类
     * @return 文章分类集合
     */
    public List<PortalArticleCategory> selectPortalArticleCategoryList(PortalArticleCategory portalArticleCategory);

    /**
     * 新增文章分类
     *
     * @param portalArticleCategory 文章分类
     * @return 结果
     */
    public int insertPortalArticleCategory(PortalArticleCategory portalArticleCategory);

    /**
     * 修改文章分类
     *
     * @param portalArticleCategory 文章分类
     * @return 结果
     */
    public int updatePortalArticleCategory(PortalArticleCategory portalArticleCategory);

    /**
     * 删除文章分类
     *
     * @param 分类ID
     * @return 结果
     */
    public int deletePortalArticleCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除文章分类
     *
     * @param 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalArticleCategoryByCategoryIds(Long[] categoryIds);
}
