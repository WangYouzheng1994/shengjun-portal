-- ----------------------------
-- 门户管理模块SQL脚本
-- 包含：轮播图表、企业信息表、菜单、角色、权限
-- 作者：王有政
-- 日期：2026-04-12
-- ----------------------------

-- ----------------------------
-- 1、轮播图配置表
-- ----------------------------
DROP TABLE IF EXISTS `portal_banner`;
CREATE TABLE `portal_banner` (
  `banner_id`       BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `title`           VARCHAR(200) NOT NULL DEFAULT ''                  COMMENT '标题',
  `pc_image`        VARCHAR(500) NOT NULL DEFAULT ''                  COMMENT 'PC端图片地址',
  `mobile_image`    VARCHAR(500)          DEFAULT ''                  COMMENT '移动端图片地址',
  `pc_width`        INT(11)                DEFAULT 1920               COMMENT 'PC端图片宽度',
  `pc_height`       INT(11)                DEFAULT 600                COMMENT 'PC端图片高度',
  `mobile_width`    INT(11)                DEFAULT 750                COMMENT '移动端图片宽度',
  `mobile_height`   INT(11)                DEFAULT 400                COMMENT '移动端图片高度',
  `link_type`       CHAR(1)       NOT NULL DEFAULT '0'               COMMENT '跳转链接类型（0无跳转 1内部链接 2外部链接）',
  `link_url`        VARCHAR(500)          DEFAULT ''                  COMMENT '跳转链接地址',
  `sort_order`      INT(11)       NOT NULL DEFAULT 0                 COMMENT '排序（升序）',
  `status`          CHAR(1)       NOT NULL DEFAULT '0'               COMMENT '状态（0正常 1停用）',
  `create_by`       VARCHAR(64)            DEFAULT ''                 COMMENT '创建者',
  `create_time`     DATETIME                                         DEFAULT NULL COMMENT '创建时间',
  `update_by`       VARCHAR(64)            DEFAULT ''                 COMMENT '更新者',
  `update_time`     DATETIME                                         DEFAULT NULL COMMENT '更新时间',
  `remark`          VARCHAR(500)           DEFAULT NULL               COMMENT '备注',
  PRIMARY KEY (`banner_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='轮播图配置表';

-- ----------------------------
-- 2、企业基础信息表
-- ----------------------------
DROP TABLE IF EXISTS `portal_company_info`;
CREATE TABLE `portal_company_info` (
  `info_id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '信息ID',
  `company_name`     VARCHAR(200) NOT NULL DEFAULT ''                  COMMENT '公司名称',
  `company_logo`     VARCHAR(500)          DEFAULT ''                  COMMENT '公司Logo地址',
  `address`          VARCHAR(500)          DEFAULT ''                  COMMENT '公司地址',
  `phone`            VARCHAR(50)            DEFAULT ''                  COMMENT '联系电话',
  `email`            VARCHAR(100)           DEFAULT ''                  COMMENT '电子邮箱',
  `fax`              VARCHAR(50)            DEFAULT ''                  COMMENT '传真号码',
  `wechat`           VARCHAR(200)           DEFAULT ''                  COMMENT '微信公众号',
  `weibo`            VARCHAR(200)           DEFAULT ''                  COMMENT '新浪微博',
  `douyin`           VARCHAR(200)           DEFAULT ''                  COMMENT '抖音账号',
  `qq`               VARCHAR(50)            DEFAULT ''                  COMMENT 'QQ号',
  `introduction`     TEXT                                               COMMENT '公司简介（富文本）',
  `business_license` VARCHAR(500)           DEFAULT ''                  COMMENT '营业执照',
  `icp_number`       VARCHAR(100)           DEFAULT ''                  COMMENT 'ICP备案号',
  `status`           CHAR(1)       NOT NULL DEFAULT '0'               COMMENT '状态（0正常 1停用）',
  `create_by`        VARCHAR(64)            DEFAULT ''                  COMMENT '创建者',
  `create_time`      DATETIME                                          DEFAULT NULL COMMENT '创建时间',
  `update_by`        VARCHAR(64)            DEFAULT ''                  COMMENT '更新者',
  `update_time`      DATETIME                                          DEFAULT NULL COMMENT '更新时间',
  `remark`           VARCHAR(500)           DEFAULT NULL                COMMENT '备注',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='企业基础信息表';

-- ----------------------------
-- 3、菜单配置
-- ----------------------------

-- 一级菜单：信息管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('信息管理', 0, 10, 'portal', NULL, 'M', '0', '0', '', 'documentation', 'admin', sysdate(), '', null, '信息管理目录');

-- 获取刚插入的一级菜单ID（假设为2000，实际使用时需要根据实际情况调整）
SET @portalMenuId = LAST_INSERT_ID();

-- 二级菜单：轮播图管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图管理', @portalMenuId, 1, 'banner', 'portal/banner/index', 'C', '0', '0', 'portal:banner:list', '#', 'admin', sysdate(), '', null, '轮播图菜单');

SET @bannerMenuId = LAST_INSERT_ID();

-- 轮播图按钮：查询
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图查询', @bannerMenuId, 1, '#', '', 'F', '0', '0', 'portal:banner:query', '#', 'admin', sysdate(), '', null, '');

-- 轮播图按钮：新增
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图新增', @bannerMenuId, 2, '#', '', 'F', '0', '0', 'portal:banner:add', '#', 'admin', sysdate(), '', null, '');

-- 轮播图按钮：修改
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图修改', @bannerMenuId, 3, '#', '', 'F', '0', '0', 'portal:banner:edit', '#', 'admin', sysdate(), '', null, '');

-- 轮播图按钮：删除
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图删除', @bannerMenuId, 4, '#', '', 'F', '0', '0', 'portal:banner:remove', '#', 'admin', sysdate(), '', null, '');

-- 轮播图按钮：导出
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图导出', @bannerMenuId, 5, '#', '', 'F', '0', '0', 'portal:banner:export', '#', 'admin', sysdate(), '', null, '');

-- 二级菜单：企业信息管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息', @portalMenuId, 2, 'company', 'portal/company/index', 'C', '0', '0', 'portal:company:list', '#', 'admin', sysdate(), '', null, '企业信息菜单');

SET @companyMenuId = LAST_INSERT_ID();

-- 企业信息按钮：查询
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息查询', @companyMenuId, 1, '#', '', 'F', '0', '0', 'portal:company:query', '#', 'admin', sysdate(), '', null, '');

-- 企业信息按钮：新增
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息新增', @companyMenuId, 2, '#', '', 'F', '0', '0', 'portal:company:add', '#', 'admin', sysdate(), '', null, '');

-- 企业信息按钮：修改
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息修改', @companyMenuId, 3, '#', '', 'F', '0', '0', 'portal:company:edit', '#', 'admin', sysdate(), '', null, '');

-- 企业信息按钮：删除
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息删除', @companyMenuId, 4, '#', '', 'F', '0', '0', 'portal:company:remove', '#', 'admin', sysdate(), '', null, '');

-- 企业信息按钮：导出
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息导出', @companyMenuId, 5, '#', '', 'F', '0', '0', 'portal:company:export', '#', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 4、企业管理员角色
-- ----------------------------
INSERT INTO `sys_role`(`role_name`, `role_key`, `role_sort`, `data_scope`, `menu_check_strictly`, `dept_check_strictly`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业管理员', 'company_admin', 3, '2', 1, 1, '0', '0', 'admin', sysdate(), '', null, '企业管理员角色');

SET @companyAdminRoleId = LAST_INSERT_ID();

-- 为企业管理员角色分配菜单权限（信息管理的所有菜单）
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT @companyAdminRoleId, menu_id FROM sys_menu WHERE menu_name IN ('信息管理', '轮播图管理', '轮播图查询', '轮播图新增', '轮播图修改', '轮播图删除', '轮播图导出', '企业信息', '企业信息查询', '企业信息新增', '企业信息修改', '企业信息删除', '企业信息导出');

-- ----------------------------
-- 5、字典数据（可选）
-- ----------------------------

-- 链接类型字典
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('链接类型', 'portal_link_type', '0', 'admin', sysdate(), '', NULL, '轮播图链接类型');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '无跳转', '0', 'portal_link_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '无跳转'),
      (2, '内部链接', '1', 'portal_link_type', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '内部链接'),
      (3, '外部链接', '2', 'portal_link_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '外部链接');

-- ----------------------------
-- 6、初始化企业基础数据（可选）
-- ----------------------------
INSERT INTO `portal_company_info`(`company_name`, `status`, `create_by`, `create_time`) VALUES('请填写公司名称', '0', 'admin', sysdate());
