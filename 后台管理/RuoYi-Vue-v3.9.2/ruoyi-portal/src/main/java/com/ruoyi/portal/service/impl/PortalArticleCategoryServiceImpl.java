package com.ruoyi.portal.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.portal.mapper.PortalArticleCategoryMapper;
import com.ruoyi.portal.domain.PortalArticleCategory;
import com.ruoyi.portal.service.IPortalArticleCategoryService;

/**
 * 文章分类Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalArticleCategoryServiceImpl implements IPortalArticleCategoryService {

    @Autowired
    private PortalArticleCategoryMapper portalArticleCategoryMapper;

    /**
     * 查询文章分类
     *
     * @param 分类ID
     * @return 文章分类
     */
    @Override
    public PortalArticleCategory selectPortalArticleCategoryByCategoryId(Long categoryId) {
        return portalArticleCategoryMapper.selectPortalArticleCategoryByCategoryId(categoryId);
    }

    /**
     * 查询文章分类列表
     *
     * @param portalArticleCategory 文章分类
     * @return 文章分类
     */
    @Override
    public List<PortalArticleCategory> selectPortalArticleCategoryList(PortalArticleCategory portalArticleCategory) {
        return portalArticleCategoryMapper.selectPortalArticleCategoryList(portalArticleCategory);
    }

    /**
     * 新增文章分类
     *
     * @param portalArticleCategory 文章分类
     * @return 结果
     */
    @Override
    public int insertPortalArticleCategory(PortalArticleCategory portalArticleCategory) {
        return portalArticleCategoryMapper.insertPortalArticleCategory(portalArticleCategory);
    }

    /**
     * 修改文章分类
     *
     * @param portalArticleCategory 文章分类
     * @return 结果
     */
    @Override
    public int updatePortalArticleCategory(PortalArticleCategory portalArticleCategory) {
        return portalArticleCategoryMapper.updatePortalArticleCategory(portalArticleCategory);
    }

    /**
     * 批量删除文章分类
     *
     * @param 需要删除的分类ID
     * @return 结果
     */
    @Override
    public int deletePortalArticleCategoryByCategoryIds(Long[] categoryIds) {
        return portalArticleCategoryMapper.deletePortalArticleCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除文章分类信息
     *
     * @param 分类ID
     * @return 结果
     */
    @Override
    public int deletePortalArticleCategoryByCategoryId(Long categoryId) {
        return portalArticleCategoryMapper.deletePortalArticleCategoryByCategoryId(categoryId);
    }

    /**
     * 构建前端所需要树结构
     *
     * @param categories 分类列表
     * @return 树结构列表
     */
    @Override
    public List<PortalArticleCategory> buildCategoryTree(List<PortalArticleCategory> categories) {
        List<PortalArticleCategory> returnList = new ArrayList<PortalArticleCategory>();
        List<Long> tempList = categories.stream().map(PortalArticleCategory::getCategoryId).collect(Collectors.toList());
        for (Iterator<PortalArticleCategory> iterator = categories.iterator(); iterator.hasNext();) {
            PortalArticleCategory category = (PortalArticleCategory) iterator.next();
            if (!tempList.contains(category.getParentId())) {
                recursionFn(categories, category);
                returnList.add(category);
            }
        }
        if (returnList.isEmpty()) {
            returnList = categories;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param categories 分类列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildCategoryTreeSelect(List<PortalArticleCategory> categories) {
        List<PortalArticleCategory> categoryTrees = buildCategoryTree(categories);
        return convertToTreeSelect(categoryTrees);
    }

    /**
     * 将分类列表转换为TreeSelect树形结构
     *
     * @param categories 分类列表（已构建为树形）
     * @return TreeSelect树形列表
     */
    private List<TreeSelect> convertToTreeSelect(List<PortalArticleCategory> categories) {
        List<TreeSelect> treeSelects = new ArrayList<TreeSelect>();
        if (categories == null || categories.isEmpty()) {
            return treeSelects;
        }
        for (PortalArticleCategory category : categories) {
            TreeSelect treeSelect = new TreeSelect();
            treeSelect.setId(category.getCategoryId());
            treeSelect.setLabel(category.getCategoryName());
            if ("1".equals(category.getStatus())) {
                treeSelect.setDisabled(true);
            }
            if (category.getChildren() != null && !category.getChildren().isEmpty()) {
                treeSelect.setChildren(convertToTreeSelect(category.getChildren()));
            }
            treeSelects.add(treeSelect);
        }
        return treeSelects;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t 子节点
     */
    private void recursionFn(List<PortalArticleCategory> list, PortalArticleCategory t) {
        List<PortalArticleCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (PortalArticleCategory tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<PortalArticleCategory> getChildList(List<PortalArticleCategory> list, PortalArticleCategory t) {
        List<PortalArticleCategory> tlist = new ArrayList<PortalArticleCategory>();
        Iterator<PortalArticleCategory> it = list.iterator();
        while (it.hasNext()) {
            PortalArticleCategory n = (PortalArticleCategory) it.next();
            if (n.getParentId().longValue() == t.getCategoryId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<PortalArticleCategory> list, PortalArticleCategory t) {
        return getChildList(list, t).size() > 0;
    }
}
