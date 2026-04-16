-- ----------------------------
-- 消息中心模块升级SQL v2（幂等版本）
-- 包含：消息模板表、修正菜单数据
-- 作者：王有政
-- 日期：2026-04-16
-- ----------------------------

-- ----------------------------
-- 1、新建消息模板表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `portal_message_template` (
  `template_id`      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  `template_code`    VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '模板编码',
  `template_name`    VARCHAR(100)    NOT NULL DEFAULT '' COMMENT '模板名称',
  `channel_type`     VARCHAR(20)     NOT NULL DEFAULT '' COMMENT '渠道类型（dingtalk/wecom/feishu/email）',
  `template_type`    VARCHAR(20)     NOT NULL DEFAULT '' COMMENT '模板类型（notification/reminder/alert）',
  `title_template`   VARCHAR(500)    DEFAULT '' COMMENT '标题模板',
  `content_template` TEXT            DEFAULT NULL COMMENT '内容模板（支持变量占位符${xxx}）',
  `is_enabled`       CHAR(1)         NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  `del_flag`         CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  `create_by`        VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  `create_time`      DATETIME        DEFAULT NULL COMMENT '创建时间',
  `update_by`        VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  `update_time`      DATETIME        DEFAULT NULL COMMENT '更新时间',
  `remark`           VARCHAR(500)    DEFAULT NULL COMMENT '备注',

  PRIMARY KEY (`template_id`),
  UNIQUE KEY `uk_template_code` (`template_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息模板表';

-- ----------------------------
-- 2、清理旧的消息中心菜单
-- ----------------------------
DELETE FROM sys_menu WHERE menu_name IN (
  '消息中心', '消息管理', '消息列表', '消息查询', '消息新增', '消息修改', '消息删除', '消息导出',
  '消息模板', '模板查询', '模板新增', '模板修改', '模板删除', '模板导出',
  '渠道配置', '钉钉配置', '配置查询', '配置新增', '配置修改', '配置删除', '配置导出',
  '企业微信配置', '邮件配置', '飞书配置',
  '发送记录', '发送日志', '日志查询', '日志删除', '日志导出'
);

-- 清理旧的角色菜单关联
DELETE FROM sys_role_menu WHERE menu_id NOT IN (SELECT menu_id FROM sys_menu);

-- ----------------------------
-- 3、插入正确的消息中心菜单数据
-- ----------------------------
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '消息中心', 0, 4, 'message', NULL, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', NULL, '消息中心目录'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '消息中心' AND parent_id = 0);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '消息模板', (SELECT menu_id FROM sys_menu WHERE menu_name = '消息中心' AND parent_id = 0 LIMIT 1), 1, 'template', NULL, 'M', '0', '0', '', 'document', 'admin', sysdate(), '', NULL, '消息模板目录'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '消息模板');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板列表', (SELECT menu_id FROM sys_menu WHERE menu_name = '消息模板' LIMIT 1), 1, 'index', 'message/template/index', 'C', '0', '0', 'message:template:list', '#', 'MessageTemplate', 'admin', sysdate(), '', NULL, '消息模板列表菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板列表');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '模板列表' LIMIT 1), 1, '#', '', 'F', '0', '0', 'message:template:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板查询');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '模板列表' LIMIT 1), 2, '#', '', 'F', '0', '0', 'message:template:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板新增');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '模板列表' LIMIT 1), 3, '#', '', 'F', '0', '0', 'message:template:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板修改');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '模板列表' LIMIT 1), 4, '#', '', 'F', '0', '0', 'message:template:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板删除');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板导出', (SELECT menu_id FROM sys_menu WHERE menu_name = '模板列表' LIMIT 1), 5, '#', '', 'F', '0', '0', 'message:template:export', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板导出');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '渠道配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '消息中心' AND parent_id = 0 LIMIT 1), 2, 'channel', NULL, 'M', '0', '0', '', 'setting', 'admin', sysdate(), '', NULL, '渠道配置目录'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '渠道配置');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '钉钉配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '渠道配置' LIMIT 1), 1, 'dingtalk', 'message/channel/dingtalk', 'C', '0', '0', 'message:channel:dingtalk:list', '#', 'DingtalkChannel', 'admin', sysdate(), '', NULL, '钉钉配置菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '钉钉配置');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1), 1, '#', '', 'F', '0', '0', 'message:channel:dingtalk:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1), 2, '#', '', 'F', '0', '0', 'message:channel:dingtalk:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1), 3, '#', '', 'F', '0', '0', 'message:channel:dingtalk:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1), 4, '#', '', 'F', '0', '0', 'message:channel:dingtalk:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '企业微信配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '渠道配置' LIMIT 1), 2, 'wecom', 'message/channel/wecom', 'C', '0', '0', 'message:channel:wecom:list', '#', 'WecomChannel', 'admin', sysdate(), '', NULL, '企业微信配置菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '企业微信配置');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1), 1, '#', '', 'F', '0', '0', 'message:channel:wecom:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1), 2, '#', '', 'F', '0', '0', 'message:channel:wecom:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1), 3, '#', '', 'F', '0', '0', 'message:channel:wecom:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1), 4, '#', '', 'F', '0', '0', 'message:channel:wecom:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '飞书配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '渠道配置' LIMIT 1), 3, 'feishu', 'message/channel/feishu', 'C', '0', '0', 'message:channel:feishu:list', '#', 'FeishuChannel', 'admin', sysdate(), '', NULL, '飞书配置菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '飞书配置');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1), 1, '#', '', 'F', '0', '0', 'message:channel:feishu:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1), 2, '#', '', 'F', '0', '0', 'message:channel:feishu:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1), 3, '#', '', 'F', '0', '0', 'message:channel:feishu:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1), 4, '#', '', 'F', '0', '0', 'message:channel:feishu:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '邮件配置', (SELECT menu_id FROM sys_menu WHERE menu_name = '渠道配置' LIMIT 1), 4, 'email', 'message/channel/email', 'C', '0', '0', 'message:channel:email:list', '#', 'EmailChannel', 'admin', sysdate(), '', NULL, '邮件配置菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '邮件配置');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1), 1, '#', '', 'F', '0', '0', 'message:channel:email:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1), 2, '#', '', 'F', '0', '0', 'message:channel:email:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1), 3, '#', '', 'F', '0', '0', 'message:channel:email:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1), 4, '#', '', 'F', '0', '0', 'message:channel:email:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '发送记录', (SELECT menu_id FROM sys_menu WHERE menu_name = '消息中心' AND parent_id = 0 LIMIT 1), 3, 'sendLog', NULL, 'M', '0', '0', '', 'log', 'admin', sysdate(), '', NULL, '发送记录目录'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '发送记录');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '发送日志', (SELECT menu_id FROM sys_menu WHERE menu_name = '发送记录' LIMIT 1), 1, 'list', 'message/sendLog/index', 'C', '0', '0', 'message:sendLog:list', '#', 'MessageSendLog', 'admin', sysdate(), '', NULL, '发送日志菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '发送日志');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '日志查询', (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' LIMIT 1), 1, '#', '', 'F', '0', '0', 'message:sendLog:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '日志查询' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '日志删除', (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' LIMIT 1), 2, '#', '', 'F', '0', '0', 'message:sendLog:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '日志删除' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' LIMIT 1));

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '日志导出', (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' LIMIT 1), 3, '#', '', 'F', '0', '0', 'message:sendLog:export', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '日志导出' AND parent_id = (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' LIMIT 1));

INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'admin'
  AND m.menu_name IN ('消息中心', '消息模板', '模板列表', '模板查询', '模板新增', '模板修改', '模板删除', '模板导出',
                      '渠道配置', '钉钉配置', '配置查询', '配置新增', '配置修改', '配置删除',
                      '企业微信配置', '邮件配置', '飞书配置',
                      '发送记录', '发送日志', '日志查询', '日志删除', '日志导出')
  AND NOT EXISTS (
      SELECT 1 FROM sys_role_menu rm
      WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id
  );
