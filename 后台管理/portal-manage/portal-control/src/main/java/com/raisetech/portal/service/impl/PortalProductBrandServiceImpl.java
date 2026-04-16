package com.raisetech.portal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raisetech.common.exception.ServiceException;
import com.raisetech.common.utils.DateUtils;
import com.raisetech.common.utils.StringUtils;
import com.raisetech.portal.mapper.PortalProductBrandMapper;
import com.raisetech.portal.domain.PortalProductBrand;
import com.raisetech.portal.service.IPortalProductBrandService;

/**
 * 产品品牌Service业务层处理
 *
 * @author 王有政
 */
@Service
public class PortalProductBrandServiceImpl implements IPortalProductBrandService {

    /**
     * 产品品牌Mapper接口
     */
    @Autowired
    private PortalProductBrandMapper portalProductBrandMapper;

    /**
     * 查询产品品牌
     *
     * @param brandId 品牌ID
     * @return 产品品牌
     */
    @Override
    public PortalProductBrand selectPortalProductBrandByBrandId(Long brandId) {
        return portalProductBrandMapper.selectPortalProductBrandByBrandId(brandId);
    }

    /**
     * 查询产品品牌列表
     *
     * @param portalProductBrand 产品品牌
     * @return 产品品牌集合
     */
    @Override
    public List<PortalProductBrand> selectPortalProductBrandList(PortalProductBrand portalProductBrand) {
        return portalProductBrandMapper.selectPortalProductBrandList(portalProductBrand);
    }

    /**
     * 新增产品品牌
     *
     * @param portalProductBrand 产品品牌
     * @return 结果
     */
    @Override
    public int insertPortalProductBrand(PortalProductBrand portalProductBrand) {
        if (StringUtils.isEmpty(portalProductBrand.getBrandName())) {
            throw new ServiceException("品牌名称不能为空");
        }
        if (StringUtils.isEmpty(portalProductBrand.getBrandCode())) {
            throw new ServiceException("品牌编码不能为空");
        }
        portalProductBrand.setCreateTime(DateUtils.getNowDate());
        return portalProductBrandMapper.insertPortalProductBrand(portalProductBrand);
    }

    /**
     * 修改产品品牌
     *
     * @param portalProductBrand 产品品牌
     * @return 结果
     */
    @Override
    public int updatePortalProductBrand(PortalProductBrand portalProductBrand) {
        if (StringUtils.isEmpty(portalProductBrand.getBrandName())) {
            throw new ServiceException("品牌名称不能为空");
        }
        if (StringUtils.isEmpty(portalProductBrand.getBrandCode())) {
            throw new ServiceException("品牌编码不能为空");
        }
        portalProductBrand.setUpdateTime(DateUtils.getNowDate());
        return portalProductBrandMapper.updatePortalProductBrand(portalProductBrand);
    }

    /**
     * 批量删除产品品牌
     *
     * @param brandIds 需要删除的品牌ID
     * @return 结果
     */
    @Override
    public int deletePortalProductBrandByBrandIds(Long[] brandIds) {
        return portalProductBrandMapper.deletePortalProductBrandByBrandIds(brandIds);
    }

    /**
     * 删除产品品牌信息
     *
     * @param brandId 品牌ID
     * @return 结果
     */
    @Override
    public int deletePortalProductBrandByBrandId(Long brandId) {
        return portalProductBrandMapper.deletePortalProductBrandByBrandId(brandId);
    }
}
