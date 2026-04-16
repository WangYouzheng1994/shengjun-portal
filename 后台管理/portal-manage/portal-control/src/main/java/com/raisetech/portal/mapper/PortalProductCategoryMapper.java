package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalProductCategory;

/**
 * 产品分类Mapper接口
 *
 * @author 王有政
 */
public interface PortalProductCategoryMapper {
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
     * 查询所有产品分类（构建树形结构）
     *
     * @param portalProductCategory 产品分类
     * @return 产品分类集合
     */
    public List<PortalProductCategory> selectPortalProductCategoryTree(PortalProductCategory portalProductCategory);

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
     * 删除产品分类
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    public int deletePortalProductCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除产品分类
     *
     * @param categoryIds 需要删除的数据ID
     * @return 结果
     */
    public int deletePortalProductCategoryByCategoryIds(Long[] categoryIds);
}
