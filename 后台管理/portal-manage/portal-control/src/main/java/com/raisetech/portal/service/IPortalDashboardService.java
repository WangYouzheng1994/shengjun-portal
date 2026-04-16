package com.raisetech.portal.service;

import com.raisetech.portal.domain.vo.PortalDashboardVO;

/**
 * 门户Dashboard统计Service接口
 *
 * @author 王有政
 */
public interface IPortalDashboardService {

    /**
     * 获取Dashboard概览数据
     *
     * @return Dashboard数据VO
     */
    public PortalDashboardVO getDashboardOverview();
}
