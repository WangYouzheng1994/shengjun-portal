package com.raisetech.portal.service;

import java.util.List;
import com.raisetech.common.core.domain.TreeSelect;
import com.raisetech.portal.domain.PortalArticleCategory;

/**
 * 文章分类Service接口
 *
 * @author 王有政
 */
public interface IPortalArticleCategoryService {
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
     * 批量删除文章分类
     *
     * @param 需要删除的分类ID
     * @return 结果
     */
    public int deletePortalArticleCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除文章分类信息
     *
     * @param 分类ID
     * @return 结果
     */
    public int deletePortalArticleCategoryByCategoryId(Long categoryId);

    /**
     * 构建前端所需要树结构
     *
     * @param categories 分类列表
     * @return 树结构列表
     */
    public List<PortalArticleCategory> buildCategoryTree(List<PortalArticleCategory> categories);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param categories 分类列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildCategoryTreeSelect(List<PortalArticleCategory> categories);
}
