package com.raisetech.portal.mapper;

import java.util.List;
import com.raisetech.portal.domain.PortalProductI18n;
import org.apache.ibatis.annotations.Param;

/**
 * 产品多语言Mapper接口
 *
 * @author 王有政
 */
public interface PortalProductI18nMapper {
    /**
     * 查询产品多语言
     *
     * @param id 主键ID
     * @return 产品多语言
     */
    public PortalProductI18n selectPortalProductI18nById(Long id);

    /**
     * 根据产品ID和语言代码查询产品多语言
     *
     * @param productId 产品ID
     * @param langCode 语言代码
     * @return 产品多语言
     */
    public PortalProductI18n selectByProductIdAndLang(@Param("productId") Long productId, @Param("langCode") String langCode);

    /**
     * 批量查询产品的多种语言翻译
     *
     * @param productIds 产品ID列表
     * @param langCode 语言代码
     * @return 产品多语言集合
     */
    public List<PortalProductI18n> selectByProductIdsAndLang(@Param("productIds") List<Long> productIds, @Param("langCode") String langCode);

    /**
     * 根据产品ID查询所有语言版本
     *
     * @param productId 产品ID
     * @return 产品多语言集合
     */
    public List<PortalProductI18n> selectByProductId(Long productId);

    /**
     * 新增产品多语言
     *
     * @param portalProductI18n 产品多语言
     * @return 结果
     */
    public int insertPortalProductI18n(PortalProductI18n portalProductI18n);

    /**
     * 修改产品多语言
     *
     * @param portalProductI18n 产品多语言
     * @return 结果
     */
    public int updatePortalProductI18n(PortalProductI18n portalProductI18n);

    /**
     * 删除产品多语言
     *
     * @param id 主键ID
     * @return 结果
     */
    public int deletePortalProductI18nById(Long id);

    /**
     * 根据产品ID删除所有语言版本
     *
     * @param productId 产品ID
     * @return 结果
     */
    public int deleteByProductId(Long productId);

    /**
     * 批量根据产品ID删除多语言数据
     *
     * @param productIds 产品ID数组
     * @return 结果
     */
    public int deleteByProductIds(@Param("productIds") Long[] productIds);
}
