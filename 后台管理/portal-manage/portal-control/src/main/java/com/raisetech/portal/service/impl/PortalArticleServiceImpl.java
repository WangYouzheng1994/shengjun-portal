package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.mapper.PortalArticleMapper;
import com.raisetech.portal.domain.PortalArticle;
import com.raisetech.portal.service.IPortalArticleService;

/**
 * 文章Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalArticleServiceImpl implements IPortalArticleService {

    @Autowired
    private PortalArticleMapper portalArticleMapper;

    /**
     * 查询文章
     *
     * @param 文章ID
     * @return 文章
     */
    @Override
    public PortalArticle selectPortalArticleByArticleId(Long articleId) {
        return portalArticleMapper.selectPortalArticleByArticleId(articleId);
    }

    /**
     * 查询文章列表
     *
     * @param portalArticle 文章
     * @return 文章
     */
    @Override
    public List<PortalArticle> selectPortalArticleList(PortalArticle portalArticle) {
        return portalArticleMapper.selectPortalArticleList(portalArticle);
    }

    /**
     * 新增文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    @Override
    public int insertPortalArticle(PortalArticle portalArticle) {
        if (StringUtils.isEmpty(portalArticle.getTitle())) {
            throw new ServiceException("文章标题不能为空");
        }
        if (portalArticle.getCategoryId() == null) {
            throw new ServiceException("请选择文章分类");
        }
        portalArticle.setCreateTime(DateUtils.getNowDate());
        if (portalArticle.getViewCount() == null) {
            portalArticle.setViewCount(0);
        }
        return portalArticleMapper.insertPortalArticle(portalArticle);
    }

    /**
     * 修改文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    @Override
    public int updatePortalArticle(PortalArticle portalArticle) {
        if (StringUtils.isEmpty(portalArticle.getTitle())) {
            throw new ServiceException("文章标题不能为空");
        }
        if (portalArticle.getCategoryId() == null) {
            throw new ServiceException("请选择文章分类");
        }
        portalArticle.setUpdateTime(DateUtils.getNowDate());
        return portalArticleMapper.updatePortalArticle(portalArticle);
    }

    /**
     * 批量删除文章
     *
     * @param 需要删除的文章ID
     * @return 结果
     */
    @Override
    public int deletePortalArticleByArticleIds(Long[] articleIds) {
        return portalArticleMapper.deletePortalArticleByArticleIds(articleIds);
    }

    /**
     * 删除文章信息
     *
     * @param 文章ID
     * @return 结果
     */
    @Override
    public int deletePortalArticleByArticleId(Long articleId) {
        return portalArticleMapper.deletePortalArticleByArticleId(articleId);
    }

    /**
     * 发布文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    @Override
    public int publishArticle(PortalArticle portalArticle) {
        portalArticle.setStatus("1");
        portalArticle.setPublishTime(DateUtils.getNowDate());
        portalArticle.setUpdateTime(DateUtils.getNowDate());
        return portalArticleMapper.updatePortalArticle(portalArticle);
    }

    /**
     * 下架文章
     *
     * @param portalArticle 文章
     * @return 结果
     */
    @Override
    public int offlineArticle(PortalArticle portalArticle) {
        portalArticle.setStatus("2");
        portalArticle.setUpdateTime(DateUtils.getNowDate());
        return portalArticleMapper.updatePortalArticle(portalArticle);
    }
}
