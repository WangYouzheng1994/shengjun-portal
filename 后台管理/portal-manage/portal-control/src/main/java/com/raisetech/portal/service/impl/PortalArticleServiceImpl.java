package com.raisetech.portal.service.impl;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.I18nHelper;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.mapper.PortalArticleMapper;
import com.raisetech.portal.mapper.PortalArticleI18nMapper;
import com.raisetech.portal.domain.PortalArticle;
import com.raisetech.portal.domain.PortalArticleI18n;
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

    @Autowired
    private PortalArticleI18nMapper articleI18nMapper;

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

    /**
     * 填充文章的多语言翻译内容
     * 批量查询指定语言的翻译数据，并覆盖到文章对象中
     * 未翻译的字段保留主表原始值（回退机制）
     *
     * @param articles 文章列表
     * @param langCode 目标语言代码（如 en-US）
     */
    @Override
    public void fillI18nContent(List<PortalArticle> articles, String langCode) {
        if (articles == null || articles.isEmpty()) {
            return;
        }
        // 如果是默认语言则无需填充翻译
        if (I18nHelper.isDefaultLanguage(langCode)) {
            return;
        }
        // 提取所有文章ID
        List<Long> articleIds = articles.stream()
            .map(PortalArticle::getArticleId)
            .collect(Collectors.toList());
        // 批量查询该语言的所有翻译
        List<PortalArticleI18n> i18nList = articleI18nMapper.selectByArticleIdsAndLang(articleIds, langCode);
        if (i18nList == null || i18nList.isEmpty()) {
            return;
        }
        // 构建Map：articleId -> I18n对象
        Map<Long, PortalArticleI18n> i18nMap = i18nList.stream()
            .collect(Collectors.toMap(PortalArticleI18n::getArticleId, Function.identity(), (a, b) -> a));
        // 填充翻译内容（使用翻译值覆盖主表字段，未翻译字段保留原值）
        articles.forEach(article -> {
            PortalArticleI18n i18n = i18nMap.get(article.getArticleId());
            if (i18n != null) {
                if (StringUtils.isNotBlank(i18n.getTitle())) {
                    article.setTitle(i18n.getTitle());
                }
                if (StringUtils.isNotBlank(i18n.getSubtitle())) {
                    article.setSubtitle(i18n.getSubtitle());
                }
                if (StringUtils.isNotBlank(i18n.getSummary())) {
                    article.setSummary(i18n.getSummary());
                }
                if (StringUtils.isNotBlank(i18n.getContent())) {
                    article.setContent(i18n.getContent());
                }
            }
        });
    }

    /**
     * 新增文章并保存多语言翻译数据
     *
     * @param article 文章对象
     * @param i18nList 多语言翻译列表
     * @return 结果
     */
    @Transactional
    public int insertArticleWithI18n(PortalArticle article, List<PortalArticleI18n> i18nList) {
        int rows = insertPortalArticle(article);
        if (i18nList != null && !i18nList.isEmpty()) {
            for (PortalArticleI18n i18n : i18nList) {
                i18n.setArticleId(article.getArticleId());
                articleI18nMapper.insertPortalArticleI18n(i18n);
            }
        }
        return rows;
    }

    /**
     * 修改文章并更新多语言翻译数据
     *
     * @param article 文章对象
     * @param i18nList 多语言翻译列表
     * @return 结果
     */
    @Transactional
    public int updateArticleWithI18n(PortalArticle article, List<PortalArticleI18n> i18nList) {
        int rows = updatePortalArticle(article);
        // 先删除旧的多语言数据
        articleI18nMapper.deleteByArticleId(article.getArticleId());
        // 再插入新的多语言数据
        if (i18nList != null && !i18nList.isEmpty()) {
            for (PortalArticleI18n i18n : i18nList) {
                i18n.setArticleId(article.getArticleId());
                articleI18nMapper.insertPortalArticleI18n(i18n);
            }
        }
        return rows;
    }
}
