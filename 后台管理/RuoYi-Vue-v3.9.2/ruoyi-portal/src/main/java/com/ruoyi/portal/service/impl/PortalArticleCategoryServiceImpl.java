package com.ruoyi.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
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
}
