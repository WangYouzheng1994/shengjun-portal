package com.ruoyi.portal.mapper;

import java.util.List;
import com.ruoyi.portal.domain.PortalMessage;

/**
 * 门户留言Mapper接口
 *
 * @author 王有政
 */
public interface PortalMessageMapper {
    /**
     * 查询留言
     *
     * @param messageId 留言ID
     * @return 留言对象
     */
    public PortalMessage selectPortalMessageByMessageId(Long messageId);

    /**
     * 查询留言列表
     *
     * @param portalMessage 留言查询条件
     * @return 留言集合
     */
    public List<PortalMessage> selectPortalMessageList(PortalMessage portalMessage);

    /**
     * 新增留言
     *
     * @param portalMessage 留言对象
     * @return 结果
     */
    public int insertPortalMessage(PortalMessage portalMessage);

    /**
     * 修改留言
     *
     * @param portalMessage 留言对象
     * @return 结果
     */
    public int updatePortalMessage(PortalMessage portalMessage);

    /**
     * 删除留言（逻辑删除）
     *
     * @param messageId 留言ID
     * @return 结果
     */
    public int deletePortalMessageByMessageId(Long messageId);

    /**
     * 批量删除留言（逻辑删除）
     *
     * @param messageIds 需要删除的留言ID数组
     * @return 结果
     */
    public int deletePortalMessageByMessageIds(Long[] messageIds);
}
