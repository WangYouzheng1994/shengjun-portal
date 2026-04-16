package com.raisetech.portal.service;

import java.util.List;
import com.raisetech.common.core.domain.TreeSelect;
import com.raisetech.portal.domain.PortalProductCategory;

/**
 * 产品分类Service接口
 *
 * @author 王有政
 */
public interface IPortalProductCategoryService {
    /**
     * 查询产品分类
     *
     * @param categoryId 分类ID
     * @return 产品分类
     */
    public PortalProductCategory selectPortalProductCategoryByCategoryId(Long categoryId);

    /**
     * 查询产品分类列表
     *
     * @param portalProductCategory 产品分类
     * @return 产品分类集合
     */
    public List<PortalProductCategory> selectPortalProductCategoryList(PortalProductCategory portalProductCategory);

    /**
     * 查询产品分类树形结构
     *
     * @param portalProductCategory 产品分类
     * @return 产品分类集合（树形结构）
     */
    public List<PortalProductCategory> selectPortalProductCategoryTree(PortalProductCategory portalProductCategory);

    /**
     * 构建分类树形结构
     *
     * @param categories 分类列表
     * @return 树形结构分类列表
     */
    public List<PortalProductCategory> buildCategoryTree(List<PortalProductCategory> categories);

    /**
     * 构建下拉树选择器所需的树形结构
     *
     * @param categories 分类列表
     * @return TreeSelect树形列表
     */
    public List<TreeSelect> buildCategoryTreeSelect(List<PortalProductCategory> categories);

    /**
     * 新增产品分类
     *
     * @param portalProductCategory 产品分类
     * @return 结果
     */
    public int insertPortalProductCategory(PortalProductCategory portalProductCategory);

    /**
     * 修改产品分类
     *
     * @param portalProductCategory 产品分类
     * @return 结果
     */
    public int updatePortalProductCategory(PortalProductCategory portalProductCategory);

    /**
     * 批量删除产品分类
     *
     * @param categoryIds 需要删除的分类ID
     * @return 结果
     */
    public int deletePortalProductCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除产品分类信息
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    public int deletePortalProductCategoryByCategoryId(Long categoryId);
}
