-- ----------------------------
-- 门户管理模块SQL脚本
-- 目录结构：门户管理 > 基础管理/信息发布 > 具体功能
-- 包含：轮播图表、企业信息表、文章分类表、文章表、菜单、角色、权限
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
  `del_flag`        CHAR(1)       NOT NULL DEFAULT '0'               COMMENT '删除标志（0正常 1已删除）',
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
  `del_flag`         CHAR(1)       NOT NULL DEFAULT '0'               COMMENT '删除标志（0正常 1已删除）',
  `create_by`        VARCHAR(64)            DEFAULT ''                  COMMENT '创建者',
  `create_time`      DATETIME                                          DEFAULT NULL COMMENT '创建时间',
  `update_by`        VARCHAR(64)            DEFAULT ''                  COMMENT '更新者',
  `update_time`      DATETIME                                          DEFAULT NULL COMMENT '更新时间',
  `remark`           VARCHAR(500)           DEFAULT NULL                COMMENT '备注',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='企业基础信息表';

-- ----------------------------
-- 3、文章分类表
-- ----------------------------
DROP TABLE IF EXISTS `portal_article_category`;
CREATE TABLE `portal_article_category` (
  `category_id`     BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name`   VARCHAR(100) NOT NULL DEFAULT ''                  COMMENT '分类名称',
  `category_code`   VARCHAR(50)  NOT NULL DEFAULT ''                  COMMENT '分类编码',
  `parent_id`       BIGINT(20)   NOT NULL DEFAULT 0                   COMMENT '父级ID（0为顶级分类）',
  `sort_order`      INT(11)      NOT NULL DEFAULT 0                   COMMENT '显示顺序',
  `status`          CHAR(1)      NOT NULL DEFAULT '0'                 COMMENT '状态（0正常 1停用）',
  `del_flag`        CHAR(1)      NOT NULL DEFAULT '0'                 COMMENT '删除标志（0正常 1已删除）',
  `create_by`       VARCHAR(64)            DEFAULT ''                  COMMENT '创建者',
  `create_time`     DATETIME               DEFAULT NULL               COMMENT '创建时间',
  `update_by`       VARCHAR(64)            DEFAULT ''                  COMMENT '更新者',
  `update_time`     DATETIME               DEFAULT NULL               COMMENT '更新时间',
  `remark`          VARCHAR(500)           DEFAULT NULL                COMMENT '备注',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文章分类表';

-- ----------------------------
-- 4、文章表
-- ----------------------------
DROP TABLE IF EXISTS `portal_article`;
CREATE TABLE `portal_article` (
  `article_id`       BIGINT(20)    NOT NULL AUTO_INCREMENT COMMENT '文章ID',
  `category_id`      BIGINT(20)    NOT NULL DEFAULT 0                    COMMENT '分类ID',
  `title`            VARCHAR(200)  NOT NULL DEFAULT ''                   COMMENT '文章标题',
  `subtitle`         VARCHAR(200)           DEFAULT ''                   COMMENT '副标题',
  `summary`          VARCHAR(500)           DEFAULT ''                   COMMENT '文章摘要',
  `content`          LONGTEXT                                              COMMENT '正文内容（富文本）',
  `cover_image`      VARCHAR(500)           DEFAULT ''                   COMMENT '封面图片地址',
  `author`           VARCHAR(50)            DEFAULT ''                   COMMENT '作者',
  `source`           VARCHAR(100)           DEFAULT ''                   COMMENT '来源',
  `view_count`       INT(11)       NOT NULL DEFAULT 0                    COMMENT '浏览次数',
  `is_top`           CHAR(1)       NOT NULL DEFAULT '0'                  COMMENT '是否置顶（0否 1是）',
  `is_recommend`     CHAR(1)       NOT NULL DEFAULT '0'                  COMMENT '是否推荐（0否 1是）',
  `status`           CHAR(1)       NOT NULL DEFAULT '0'                  COMMENT '状态（0草稿 1已发布 2已下架）',
  `publish_time`     DATETIME               DEFAULT NULL                  COMMENT '发布时间',
  `sort_order`       INT(11)       NOT NULL DEFAULT 0                    COMMENT '排序（升序）',
  `del_flag`         CHAR(1)       NOT NULL DEFAULT '0'                  COMMENT '删除标志（0正常 1已删除）',
  `create_by`        VARCHAR(64)            DEFAULT ''                    COMMENT '创建者',
  `create_time`      DATETIME                DEFAULT NULL                 COMMENT '创建时间',
  `update_by`        VARCHAR(64)            DEFAULT ''                    COMMENT '更新者',
  `update_time`      DATETIME                DEFAULT NULL                 COMMENT '更新时间',
  `remark`           VARCHAR(500)           DEFAULT NULL                  COMMENT '备注',
  PRIMARY KEY (`article_id`) USING BTREE,
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`),
  KEY `idx_publish_time` (`publish_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='文章表';

-- ----------------------------
-- 5、菜单配置（三级结构）
-- ----------------------------

-- 一级菜单：门户管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('门户管理', 0, 10, 'portal', NULL, 'M', '0', '0', '', 'documentation', 'admin', sysdate(), '', null, '门户管理目录');

SET @portalMenuId = LAST_INSERT_ID();

-- 二级目录：基础管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('基础管理', @portalMenuId, 1, 'base', NULL, 'M', '0', '0', '', 'system', 'admin', sysdate(), '', null, '基础管理目录');

SET @baseMenuId = LAST_INSERT_ID();

-- 三级菜单：轮播图管理（属于基础管理）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图管理', @baseMenuId, 1, 'banner', 'portal/base/banner/index', 'C', '0', '0', 'portal:base:banner:list', '#', 'admin', sysdate(), '', null, '轮播图菜单');

SET @bannerMenuId = LAST_INSERT_ID();

-- 轮播图按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图查询', @bannerMenuId, 1, '#', '', 'F', '0', '0', 'portal:base:banner:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图新增', @bannerMenuId, 2, '#', '', 'F', '0', '0', 'portal:base:banner:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图修改', @bannerMenuId, 3, '#', '', 'F', '0', '0', 'portal:base:banner:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图删除', @bannerMenuId, 4, '#', '', 'F', '0', '0', 'portal:base:banner:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图导出', @bannerMenuId, 5, '#', '', 'F', '0', '0', 'portal:base:banner:export', '#', 'admin', sysdate(), '', null, '');

-- 三级菜单：企业信息（属于基础管理）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息', @baseMenuId, 2, 'company', 'portal/base/company/index', 'C', '0', '0', 'portal:base:company:list', '#', 'admin', sysdate(), '', null, '企业信息菜单');

SET @companyMenuId = LAST_INSERT_ID();

-- 企业信息按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息查询', @companyMenuId, 1, '#', '', 'F', '0', '0', 'portal:base:company:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息新增', @companyMenuId, 2, '#', '', 'F', '0', '0', 'portal:base:company:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息修改', @companyMenuId, 3, '#', '', 'F', '0', '0', 'portal:base:company:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息删除', @companyMenuId, 4, '#', '', 'F', '0', '0', 'portal:base:company:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息导出', @companyMenuId, 5, '#', '', 'F', '0', '0', 'portal:base:company:export', '#', 'admin', sysdate(), '', null, '');

-- 二级目录：信息发布
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('信息发布', @portalMenuId, 2, 'article', NULL, 'M', '0', '0', '', 'documentation', 'admin', sysdate(), '', null, '信息发布目录');

SET @articleDirMenuId = LAST_INSERT_ID();

-- 三级菜单：文章分类（属于信息发布）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类', @articleDirMenuId, 1, 'category', 'portal/article/category/index', 'C', '0', '0', 'portal:article:category:list', '#', 'admin', sysdate(), '', null, '文章分类菜单');

SET @articleCategoryMenuId = LAST_INSERT_ID();

-- 文章分类按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类查询', @articleCategoryMenuId, 1, '#', '', 'F', '0', '0', 'portal:article:category:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类新增', @articleCategoryMenuId, 2, '#', '', 'F', '0', '0', 'portal:article:category:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类修改', @articleCategoryMenuId, 3, '#', '', 'F', '0', '0', 'portal:article:category:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类删除', @articleCategoryMenuId, 4, '#', '', 'F', '0', '0', 'portal:article:category:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类导出', @articleCategoryMenuId, 5, '#', '', 'F', '0', '0', 'portal:article:category:export', '#', 'admin', sysdate(), '', null, '');

-- 三级菜单：文章管理（属于信息发布）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章管理', @articleDirMenuId, 2, 'article', 'portal/article/article/index', 'C', '0', '0', 'portal:article:article:list', '#', 'admin', sysdate(), '', null, '文章管理菜单');

SET @articleMenuId = LAST_INSERT_ID();

-- 文章按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章查询', @articleMenuId, 1, '#', '', 'F', '0', '0', 'portal:article:article:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章新增', @articleMenuId, 2, '#', '', 'F', '0', '0', 'portal:article:article:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章修改', @articleMenuId, 3, '#', '', 'F', '0', '0', 'portal:article:article:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章删除', @articleMenuId, 4, '#', '', 'F', '0', '0', 'portal:article:article:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章导出', @articleMenuId, 5, '#', '', 'F', '0', '0', 'portal:article:article:export', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章发布', @articleMenuId, 6, '#', '', 'F', '0', '0', 'portal:article:article:publish', '#', 'admin', sysdate(), '', null, '');

-- ----------------------------
-- 6、企业管理员角色
-- ----------------------------
INSERT INTO `sys_role`(`role_name`, `role_key`, `role_sort`, `data_scope`, `menu_check_strictly`, `dept_check_strictly`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业管理员', 'company_admin', 3, '2', 1, 1, '0', '0', 'admin', sysdate(), '', null, '企业管理员角色');

SET @companyAdminRoleId = LAST_INSERT_ID();

-- 为企业管理员角色分配所有门户管理相关菜单权限
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT @companyAdminRoleId, menu_id FROM sys_menu WHERE menu_name IN (
  '门户管理', '基础管理', '信息发布',
  '轮播图管理', '轮播图查询', '轮播图新增', '轮播图修改', '轮播图删除', '轮播图导出',
  '企业信息', '企业信息查询', '企业信息新增', '企业信息修改', '企业信息删除', '企业信息导出',
  '文章分类', '文章分类查询', '文章分类新增', '文章分类修改', '文章分类删除', '文章分类导出',
  '文章管理', '文章查询', '文章新增', '文章修改', '文章删除', '文章导出', '文章发布'
);

-- ----------------------------
-- 7、字典数据：链接类型
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('链接类型', 'portal_link_type', '0', 'admin', sysdate(), '', NULL, '轮播图链接类型');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '无跳转', '0', 'portal_link_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '无跳转'),
      (2, '内部链接', '1', 'portal_link_type', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '内部链接'),
      (3, '外部链接', '2', 'portal_link_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '外部链接');

-- ----------------------------
-- 8、字典数据：文章状态
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章状态', 'portal_article_status', '0', 'admin', sysdate(), '', NULL, '文章状态');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '草稿', '0', 'portal_article_status', '', 'info', 'Y', '0', 'admin', sysdate(), '', NULL, '草稿'),
      (2, '已发布', '1', 'portal_article_status', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '已发布'),
      (3, '已下架', '2', 'portal_article_status', '', 'danger', 'N', '0', 'admin', sysdate(), '', NULL, '已下架');

-- ----------------------------
-- 9、初始化默认数据
-- ----------------------------

-- 初始化企业基础数据
INSERT INTO `portal_company_info`(`company_name`, `status`, `create_by`, `create_time`) VALUES('请填写公司名称', '0', 'admin', sysdate());

-- 插入默认文章分类数据
INSERT INTO `portal_article_category`(`category_name`, `category_code`, `parent_id`, `sort_order`, `status`, `create_by`, `create_time`)
VALUES
('新闻中心', 'news', 0, 1, '0', 'admin', sysdate()),
('行业资讯', 'info', 0, 2, '0', 'admin', sysdate()),
('关于我们', 'about', 0, 3, '0', 'admin', sysdate()),
('帮助中心', 'help', 0, 4, '0', 'admin', sysdate());

-- ----------------------------
-- 10、客户管理模块（CRM）
-- ----------------------------

-- 一级菜单：客户管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('客户管理', 0, 20, 'crm', NULL, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', null, '客户管理目录');

SET @crmMenuId = LAST_INSERT_ID();

-- 二级目录：客户信息
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('客户信息', @crmMenuId, 1, 'customer', NULL, 'M', '0', '0', '', 'user', 'admin', sysdate(), '', null, '客户信息目录');

SET @customerDirMenuId = LAST_INSERT_ID();

-- 三级菜单：客户列表（包含按钮权限，完整配置见upgrade_portal_crm.sql）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('客户列表', @customerDirMenuId, 1, 'list', 'portal/crm/customer/list', 'C', '0', '0', 'portal:crm:customer:list', '#', 'admin', sysdate(), '', null, '客户列表菜单');

-- 二级目录：留言管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('留言管理', @crmMenuId, 2, 'message', NULL, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, '留言管理目录');

SET @messageDirMenuId = LAST_INSERT_ID();

-- 三级菜单：留言列表
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('留言列表', @messageDirMenuId, 1, 'list', 'portal/crm/message/list', 'C', '0', '0', 'portal:crm:message:list', '#', 'admin', sysdate(), '', null, '留言列表菜单');

-- 二级菜单：跟进记录
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('跟进记录', @crmMenuId, 3, 'followup', 'portal/crm/followup/list', 'C', '0', '0', 'portal:crm:followup:list', '#', 'admin', sysdate(), '', null, '跟进记录菜单');

-- 二级菜单：标签管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('标签管理', @crmMenuId, 4, 'tag', 'portal/crm/tag/index', 'C', '0', '0', 'portal:crm:tag:list', '#', 'admin', sysdate(), '', null, '标签管理菜单');
