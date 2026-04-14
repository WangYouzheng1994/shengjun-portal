package com.ruoyi.portal.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.portal.domain.PortalProductCategory;
import com.ruoyi.portal.mapper.PortalProductCategoryMapper;
import com.ruoyi.portal.service.IPortalProductCategoryService;

/**
 * 产品分类Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductCategoryServiceImpl implements IPortalProductCategoryService {

    /**
     * 产品分类Mapper接口
     */
    @Autowired
    private PortalProductCategoryMapper portalProductCategoryMapper;

    /**
     * 查询产品分类
     *
     * @param categoryId 分类ID
     * @return 产品分类
     */
    @Override
    public PortalProductCategory selectPortalProductCategoryByCategoryId(Long categoryId) {
        return portalProductCategoryMapper.selectPortalProductCategoryByCategoryId(categoryId);
    }

    /**
     * 查询产品分类列表
     *
     * @param portalProductCategory 产品分类
     * @return 产品分类集合
     */
    @Override
    public List<PortalProductCategory> selectPortalProductCategoryList(PortalProductCategory portalProductCategory) {
        return portalProductCategoryMapper.selectPortalProductCategoryList(portalProductCategory);
    }

    /**
     * 查询产品分类树形结构
     *
     * @param portalProductCategory 产品分类
     * @return 产品分类集合（树形结构）
     */
    @Override
    public List<PortalProductCategory> selectPortalProductCategoryTree(PortalProductCategory portalProductCategory) {
        List<PortalProductCategory> categoryList = portalProductCategoryMapper.selectPortalProductCategoryTree(portalProductCategory);
        return buildCategoryTree(categoryList);
    }

    /**
     * 构建分类树形结构
     *
     * @param categories 分类列表
     * @return 树形结构分类列表
     */
    @Override
    public List<PortalProductCategory> buildCategoryTree(List<PortalProductCategory> categories) {
        List<PortalProductCategory> returnList = new ArrayList<PortalProductCategory>();
        List<Long> tempList = categories.stream().map(PortalProductCategory::getCategoryId).collect(Collectors.toList());
        for (Iterator<PortalProductCategory> iterator = categories.iterator(); iterator.hasNext(); ) {
            PortalProductCategory category = iterator.next();
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
     * 递归构建子节点
     *
     * @param list 分类列表
     * @param category 当前分类
     */
    private void recursionFn(List<PortalProductCategory> list, PortalProductCategory category) {
        List<PortalProductCategory> childList = getChildList(list, category);
        category.setChildren(childList);
        for (PortalProductCategory t : childList) {
            if (hasChild(list, t)) {
                recursionFn(list, t);
            }
        }
    }

    /**
     * 获取子节点列表
     *
     * @param list 分类列表
     * @param category 当前分类
     * @return 子分类列表
     */
    private List<PortalProductCategory> getChildList(List<PortalProductCategory> list, PortalProductCategory category) {
        List<PortalProductCategory> childList = new ArrayList<PortalProductCategory>();
        Iterator<PortalProductCategory> it = list.iterator();
        while (it.hasNext()) {
            PortalProductCategory n = it.next();
            if (n.getParentId().longValue() == category.getCategoryId().longValue()) {
                childList.add(n);
            }
        }
        return childList;
    }

    /**
     * 判断是否有子节点
     *
     * @param list 分类列表
     * @param category 当前分类
     * @return 是否有子节点
     */
    private boolean hasChild(List<PortalProductCategory> list, PortalProductCategory category) {
        return getChildList(list, category).size() > 0;
    }

    /**
     * 新增产品分类
     *
     * @param portalProductCategory 产品分类
     * @return 结果
     */
    @Override
    public int insertPortalProductCategory(PortalProductCategory portalProductCategory) {
        if (StringUtils.isEmpty(portalProductCategory.getCategoryName())) {
            throw new ServiceException("分类名称不能为空");
        }
        if (portalProductCategory.getParentId() == null) {
            portalProductCategory.setParentId(0L);
        }
        if (portalProductCategory.getParentId() == 0L) {
            portalProductCategory.setAncestors("0");
        } else {
            PortalProductCategory parent = portalProductCategoryMapper.selectPortalProductCategoryByCategoryId(portalProductCategory.getParentId());
            if (parent != null) {
                portalProductCategory.setAncestors(parent.getAncestors() + "," + parent.getCategoryId());
            } else {
                throw new ServiceException("父级分类不存在");
            }
        }
        portalProductCategory.setCreateTime(DateUtils.getNowDate());
        return portalProductCategoryMapper.insertPortalProductCategory(portalProductCategory);
    }

    /**
     * 修改产品分类
     *
     * @param portalProductCategory 产品分类
     * @return 结果
     */
    @Override
    public int updatePortalProductCategory(PortalProductCategory portalProductCategory) {
        if (StringUtils.isEmpty(portalProductCategory.getCategoryName())) {
            throw new ServiceException("分类名称不能为空");
        }
        portalProductCategory.setUpdateTime(DateUtils.getNowDate());
        return portalProductCategoryMapper.updatePortalProductCategory(portalProductCategory);
    }

    /**
     * 批量删除产品分类
     *
     * @param categoryIds 需要删除的分类ID
     * @return 结果
     */
    @Override
    public int deletePortalProductCategoryByCategoryIds(Long[] categoryIds) {
        return portalProductCategoryMapper.deletePortalProductCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除产品分类信息
     *
     * @param categoryId 分类ID
     * @return 结果
     */
    @Override
    public int deletePortalProductCategoryByCategoryId(Long categoryId) {
        return portalProductCategoryMapper.deletePortalProductCategoryByCategoryId(categoryId);
    }

    /**
     * 构建下拉树选择器所需的树形结构
     *
     * @param categories 分类列表
     * @return TreeSelect树形列表
     */
    @Override
    public List<TreeSelect> buildCategoryTreeSelect(List<PortalProductCategory> categories) {
        List<PortalProductCategory> categoryTrees = buildCategoryTree(categories);
        return convertToTreeSelect(categoryTrees);
    }

    /**
     * 将分类列表转换为TreeSelect树形结构
     *
     * @param categories 分类树形列表
     * @return TreeSelect树形列表
     */
    private List<TreeSelect> convertToTreeSelect(List<PortalProductCategory> categories) {
        List<TreeSelect> treeSelects = new ArrayList<TreeSelect>();
        if (categories == null) {
            return treeSelects;
        }
        for (PortalProductCategory category : categories) {
            TreeSelect treeSelect = new TreeSelect();
            treeSelect.setId(category.getCategoryId());
            treeSelect.setLabel(category.getCategoryName());
            treeSelect.setChildren(convertToTreeSelect(category.getChildren()));
            treeSelects.add(treeSelect);
        }
        return treeSelects;
    }
}
