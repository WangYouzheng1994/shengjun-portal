-- ----------------------------
-- 门户管理模块SQL脚本
-- 目录结构：门户管理 > 基础管理/信息发布 > 具体功能
-- 包含：轮播图表、企业信息表、文章分类表、文章表、菜单、角色、权限
--       企业办公点、企业荣誉墙、门户公告、产品管理、企业发展历程
-- 作者：王有政
-- 日期：2026-04-12
-- 最后更新：2026-04-15（新增企业发展历程里程碑模块）
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

-- 门户首页菜单（作为门户管理的第一个子菜单）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `is_frame`, `is_cache`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('门户首页', @portalMenuId, 0, 'dashboard', 'portal/dashboard/index', 'C', '0', '0', 'portal:dashboard:query', 'dashboard', 'PortalDashboard', 1, 0, 'admin', sysdate(), '', null, '门户数据概览首页');

SET @dashboardMenuId = LAST_INSERT_ID();

-- 门户首页按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('门户概览查询', @dashboardMenuId, 1, '#', '', 'F', '0', '0', 'portal:dashboard:query', '#', 'admin', sysdate(), '', null, '');

-- 二级目录：基础管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('基础管理', @portalMenuId, 1, 'base', NULL, 'M', '0', '0', '', 'system', 'admin', sysdate(), '', null, '基础管理目录');

SET @baseMenuId = LAST_INSERT_ID();

-- 三级菜单：轮播图管理（属于基础管理）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('轮播图管理', @baseMenuId, 1, 'banner', 'portal/base/banner/index', 'C', '0', '0', 'portal:base:banner:list', '#', 'Banner', 'admin', sysdate(), '', null, '轮播图菜单');

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
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('企业信息', @baseMenuId, 2, 'company', 'portal/base/company/index', 'C', '0', '0', 'portal:base:company:list', '#', 'CompanyInfo', 'admin', sysdate(), '', null, '企业信息菜单');

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
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章分类', @articleDirMenuId, 1, 'category', 'portal/article/category/index', 'C', '0', '0', 'portal:article:category:list', '#', 'ArticleCategory', 'admin', sysdate(), '', null, '文章分类菜单');

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
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('文章管理', @articleDirMenuId, 2, 'article', 'portal/article/article/index', 'C', '0', '0', 'portal:article:article:list', '#', 'Article', 'admin', sysdate(), '', null, '文章管理菜单');

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
  '门户管理', '门户首页', '门户概览查询',
  '基础管理', '信息发布',
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
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('客户列表', @customerDirMenuId, 1, 'list', 'portal/crm/customer/list', 'C', '0', '0', 'portal:crm:customer:list', '#', 'CustomerList', 'admin', sysdate(), '', null, '客户列表菜单');

-- 二级目录：留言管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('留言管理', @crmMenuId, 2, 'message', NULL, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, '留言管理目录');

SET @messageDirMenuId = LAST_INSERT_ID();

-- 三级菜单：留言列表
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('留言列表', @messageDirMenuId, 1, 'list', 'portal/crm/message/list', 'C', '0', '0', 'portal:crm:message:list', '#', 'CrmMessageList', 'admin', sysdate(), '', null, '留言列表菜单');

-- 二级菜单：跟进记录
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('跟进记录', @crmMenuId, 3, 'followup', 'portal/crm/followup/list', 'C', '0', '0', 'portal:crm:followup:list', '#', 'FollowUpList', 'admin', sysdate(), '', null, '跟进记录菜单');

-- 二级菜单：标签管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('标签管理', @crmMenuId, 4, 'tag', 'portal/crm/tag/index', 'C', '0', '0', 'portal:crm:tag:list', '#', 'TagIndex', 'admin', sysdate(), '', null, '标签管理菜单');

-- ----------------------------
-- 11、门户公告表（完全独立设计）
-- ----------------------------
DROP TABLE IF EXISTS `portal_notice`;
CREATE TABLE `portal_notice` (
    `notice_id`       BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `title`           VARCHAR(200) NOT NULL DEFAULT ''      COMMENT '公告标题',
    `content`         LONGTEXT                              COMMENT '公告内容（富文本）',
    `notice_type`     VARCHAR(30)  NOT NULL DEFAULT 'notice' COMMENT '公告类型（notice系统公告 announcement重要通知 holiday节日问候 activity活动通告）',
    `priority_level`  VARCHAR(20)  NOT NULL DEFAULT 'normal' COMMENT '优先级（low低 normal普通 high高 urgent紧急 critical特急）',
    `display_mode`    VARCHAR(20)  NOT NULL DEFAULT 'list'  COMMENT '展示方式（list列表 popup弹窗 banner横幅 scroll滚动 both列表+弹窗）',
    `is_top`          CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '是否置顶（0否 1是）',
    `is_sticky`       CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '是否常驻（0否 1是，常驻公告始终显示在公告栏顶部）',
    `status`          CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '状态（0草稿 1已发布 2已过期 3已停用）',
    `publish_time`    DATETIME              DEFAULT NULL    COMMENT '发布时间',
    `start_time`      DATETIME              DEFAULT NULL    COMMENT '有效开始时间（NULL表示立即生效）',
    `end_time`        DATETIME              DEFAULT NULL    COMMENT '有效结束时间（NULL表示永不失效）',
    `sort_order`      INT(11)      NOT NULL DEFAULT 0       COMMENT '排序权重（数值越大越靠前）',
    `view_count`      INT(11)      NOT NULL DEFAULT 0       COMMENT '查看次数',
    `click_count`     INT(11)      NOT NULL DEFAULT 0       COMMENT '点击次数（点击"查看详情"的次数）',
    `require_confirm` CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '是否需要确认（0否 1是，用户需点击"我已知晓"）',
    `confirm_text`    VARCHAR(100)           DEFAULT ''     COMMENT '确认按钮文字（如"我已知晓"、"确定"）',
    `popup_style`     VARCHAR(50)           DEFAULT 'default' COMMENT '弹窗样式（default默认 warning警告 info信息 success成功）',
    `target_audience` VARCHAR(50)  NOT NULL DEFAULT 'all'   COMMENT '目标受众（all全部 user注册游客 visitor未登录游客 member会员 enterprise企业用户）',
    `del_flag`        CHAR(1)      NOT NULL DEFAULT '0'     COMMENT '删除标志（0正常 1已删除）',
    `create_by`       VARCHAR(64)            DEFAULT ''     COMMENT '创建者',
    `create_time`     DATETIME                DEFAULT NULL   COMMENT '创建时间',
    `update_by`       VARCHAR(64)            DEFAULT ''     COMMENT '更新者',
    `update_time`     DATETIME                DEFAULT NULL   COMMENT '更新时间',
    `remark`          VARCHAR(500)            DEFAULT NULL   COMMENT '备注',
    PRIMARY KEY (`notice_id`) USING BTREE,
    KEY `idx_status_publish` (`status`, `publish_time`),
    KEY `idx_start_end_time` (`start_time`, `end_time`),
    KEY `idx_priority_level` (`priority_level`),
    KEY `idx_notice_type` (`notice_type`),
    KEY `idx_display_mode` (`display_mode`),
    KEY `idx_is_top` (`is_top`),
    KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='门户公告表';

-- 三级菜单：公告管理（属于信息发布）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告管理', @articleDirMenuId, 3, 'notice', 'portal/notice/index', 'C', '0', '0', 'portal:notice:list', 'message', 'PortalNotice', 'admin', sysdate(), '', null, '公告管理菜单');

SET @noticeMenuId = LAST_INSERT_ID();

-- 公告管理按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告查询', @noticeMenuId, 1, '#', '', 'F', '0', '0', 'portal:notice:query', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告新增', @noticeMenuId, 2, '#', '', 'F', '0', '0', 'portal:notice:add', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告修改', @noticeMenuId, 3, '#', '', 'F', '0', '0', 'portal:notice:edit', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告删除', @noticeMenuId, 4, '#', '', 'F', '0', '0', 'portal:notice:remove', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告导出', @noticeMenuId, 5, '#', '', 'F', '0', '0', 'portal:notice:export', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告发布', @noticeMenuId, 6, '#', '', 'F', '0', '0', 'portal:notice:publish', '#', 'admin', sysdate(), '', null, '');

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告停用', @noticeMenuId, 7, '#', '', 'F', '0', '0', 'portal:notice:offline', '#', 'admin', sysdate(), '', null, '');

-- 更新企业管理员角色权限（添加公告管理相关权限）
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND m.menu_name IN (
    '公告管理', '公告查询', '公告新增', '公告修改', '公告删除', '公告导出', '公告发布', '公告停用'
  );

-- ----------------------------
-- 12、字典数据：公告类型
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告类型', 'portal_notice_type', '0', 'admin', sysdate(), '', NULL, '公告类型');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '系统公告', 'notice', 'portal_notice_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '系统公告'),
      (2, '重要通知', 'announcement', 'portal_notice_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '重要通知'),
      (3, '节日问候', 'holiday', 'portal_notice_type', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '节日问候'),
      (4, '活动通告', 'activity', 'portal_notice_type', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '活动通告');

-- ----------------------------
-- 13、字典数据：优先级
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告优先级', 'portal_notice_priority', '0', 'admin', sysdate(), '', NULL, '公告优先级');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '低', 'low', 'portal_notice_priority', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '低'),
      (2, '普通', 'normal', 'portal_notice_priority', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '普通'),
      (3, '高', 'high', 'portal_notice_priority', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '高'),
      (4, '紧急', 'urgent', 'portal_notice_priority', '', 'danger', 'N', '0', 'admin', sysdate(), '', NULL, '紧急'),
      (5, '特急', 'critical', 'portal_notice_priority', '', 'danger', 'N', '0', 'admin', sysdate(), '', NULL, '特急');

-- ----------------------------
-- 14、字典数据：展示方式
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('展示方式', 'portal_notice_display', '0', 'admin', sysdate(), '', NULL, '展示方式');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '列表', 'list', 'portal_notice_display', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '列表'),
      (2, '弹窗', 'popup', 'portal_notice_display', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '弹窗'),
      (3, '横幅', 'banner', 'portal_notice_display', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '横幅'),
      (4, '滚动', 'scroll', 'portal_notice_display', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '滚动'),
      (5, '列表+弹窗', 'both', 'portal_notice_display', '', 'danger', 'N', '0', 'admin', sysdate(), '', NULL, '列表+弹窗');

-- ----------------------------
-- 15、字典数据：公告状态
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('公告状态', 'portal_notice_status', '0', 'admin', sysdate(), '', NULL, '公告状态');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '草稿', '0', 'portal_notice_status', '', 'info', 'Y', '0', 'admin', sysdate(), '', NULL, '草稿'),
      (2, '已发布', '1', 'portal_notice_status', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '已发布'),
      (3, '已过期', '2', 'portal_notice_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '已过期'),
      (4, '已停用', '3', 'portal_notice_status', '', 'danger', 'N', '0', 'admin', sysdate(), '', NULL, '已停用');

-- ----------------------------
-- 16、字典数据：目标受众
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('目标受众', 'portal_notice_audience', '0', 'admin', sysdate(), '', NULL, '目标受众');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '全部', 'all', 'portal_notice_audience', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '全部'),
      (2, '注册游客', 'user', 'portal_notice_audience', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '注册游客'),
      (3, '未登录游客', 'visitor', 'portal_notice_audience', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '未登录游客'),
      (4, '会员', 'member', 'portal_notice_audience', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '会员'),
      (5, '企业用户', 'enterprise', 'portal_notice_audience', '', 'danger', 'N', '0', 'admin', sysdate(), '', NULL, '企业用户');

-- ----------------------------
-- 17、企业办公点表
-- ----------------------------
DROP TABLE IF EXISTS `portal_office_location`;
CREATE TABLE `portal_office_location` (
  `location_id`     BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT '办公点ID',
  `info_id`         BIGINT(20)   NOT NULL COMMENT '关联的企业信息ID',
  `location_name`   VARCHAR(100) NOT NULL DEFAULT '' COMMENT '办公点名称（如：北京总部、上海分公司）',
  `location_type`   CHAR(1)      NOT NULL DEFAULT '0' COMMENT '类型（0总部 1分公司 2仓库 3办事处 4其他）',
  `province`        VARCHAR(50)           DEFAULT '' COMMENT '省份',
  `city`            VARCHAR(50)           DEFAULT '' COMMENT '城市',
  `district`        VARCHAR(50)           DEFAULT '' COMMENT '区县',
  `address`         VARCHAR(500)          DEFAULT '' COMMENT '详细地址',
  `longitude`       DECIMAL(10,6)         DEFAULT NULL COMMENT '经度',
  `latitude`        DECIMAL(10,6)         DEFAULT NULL COMMENT '纬度',
  `contact_person`  VARCHAR(50)           DEFAULT '' COMMENT '联系人',
  `contact_phone`   VARCHAR(20)           DEFAULT '' COMMENT '联系电话',
  `contact_email`   VARCHAR(100)          DEFAULT '' COMMENT '邮箱',
  `wechat`          VARCHAR(100)          DEFAULT '' COMMENT '微信号',
  `feishu`          VARCHAR(100)          DEFAULT '' COMMENT '飞书号',
  `sort_order`      INT(11)      NOT NULL DEFAULT 0 COMMENT '排序',
  `status`          CHAR(1)      NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `del_flag`        CHAR(1)      NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  `create_by`       VARCHAR(64)           DEFAULT '' COMMENT '创建者',
  `create_time`     DATETIME               DEFAULT NULL COMMENT '创建时间',
  `update_by`       VARCHAR(64)           DEFAULT '' COMMENT '更新者',
  `update_time`     DATETIME               DEFAULT NULL COMMENT '更新时间',
  `remark`          VARCHAR(500)           DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`location_id`) USING BTREE,
  KEY `idx_info_id` (`info_id`),
  KEY `idx_status` (`status`),
  KEY `idx_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='企业办公点表';

-- ----------------------------
-- 18、字典数据：办公点类型
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('办公点类型', 'portal_office_type', '0', 'admin', sysdate(), '', NULL, '办公点类型');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '总部', '0', 'portal_office_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '总部'),
      (2, '分公司', '1', 'portal_office_type', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '分公司'),
      (3, '仓库', '2', 'portal_office_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '仓库'),
      (4, '办事处', '3', 'portal_office_type', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '办事处'),
      (5, '其他', '4', 'portal_office_type', '', 'default', 'N', '0', 'admin', sysdate(), '', NULL, '其他');

-- ----------------------------
-- 19、企业荣誉墙表
-- ----------------------------
DROP TABLE IF EXISTS `portal_company_honor`;
CREATE TABLE `portal_company_honor` (
    `honor_id`          BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '荣誉ID',
    `honor_title`       VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '荣誉标题',
    `honor_image`       VARCHAR(500)    NOT NULL DEFAULT ''     COMMENT '荣誉图片地址',
    `honor_description` VARCHAR(1000)   DEFAULT ''              COMMENT '荣誉说明',
    `award_org`         VARCHAR(200)    DEFAULT ''              COMMENT '授予机构',
    `award_date`        DATE            DEFAULT NULL            COMMENT '获得时间',
    `honor_type`        CHAR(1)         DEFAULT '0'             COMMENT '荣誉类型（0资质认证 1行业奖项 2荣誉称号 3其他）',
    `sort_order`        INT(11)         NOT NULL DEFAULT 0      COMMENT '排序（升序）',
    `status`            CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '状态（0正常 1停用）',
    `del_flag`          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`         VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    `create_time`       DATETIME        DEFAULT NULL            COMMENT '创建时间',
    `update_by`         VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    `update_time`       DATETIME        DEFAULT NULL            COMMENT '更新时间',
    `remark`            VARCHAR(500)    DEFAULT NULL            COMMENT '备注',

    PRIMARY KEY (`honor_id`) USING BTREE,
    INDEX `idx_status` (`status`),
    INDEX `idx_sort_order` (`sort_order`),
    INDEX `idx_honor_type` (`honor_type`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业荣誉墙表';

-- 三级菜单：企业荣誉墙（属于基础管理，order_num=3在企业信息之后）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '企业荣誉墙', @baseMenuId, 3, 'companyHonor', 'portal/base/companyHonor/index', 'C', '0', '0', 'portal:base:honor:list', 'trophy', 'CompanyHonor', 'admin', sysdate(), '', null, '企业荣誉墙菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '企业荣誉墙' AND parent_id = @baseMenuId);

SET @honorMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业荣誉墙' AND parent_id = @baseMenuId LIMIT 1);

-- 荣誉墙按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '荣誉查询', @honorMenuId, 1, '#', '', 'F', '0', '0', 'portal:base:honor:query', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @honorMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '荣誉查询' AND parent_id = @honorMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '荣誉新增', @honorMenuId, 2, '#', '', 'F', '0', '0', 'portal:base:honor:add', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @honorMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '荣誉新增' AND parent_id = @honorMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '荣誉修改', @honorMenuId, 3, '#', '', 'F', '0', '0', 'portal:base:honor:edit', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @honorMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '荣誉修改' AND parent_id = @honorMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '荣誉删除', @honorMenuId, 4, '#', '', 'F', '0', '0', 'portal:base:honor:remove', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @honorMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '荣誉删除' AND parent_id = @honorMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '荣誉导出', @honorMenuId, 5, '#', '', 'F', '0', '0', 'portal:base:honor:export', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @honorMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '荣誉导出' AND parent_id = @honorMenuId);

-- 为企业管理员角色分配荣誉墙权限
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND m.menu_name IN ('企业荣誉墙', '荣誉查询', '荣誉新增', '荣誉修改', '荣誉删除', '荣誉导出')
  AND NOT EXISTS (
      SELECT 1 FROM sys_role_menu rm
      WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id
  );

-- ----------------------------
-- 20、字典数据：荣誉类型
-- ----------------------------
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '荣誉类型', 'portal_honor_type', '0', 'admin', sysdate(), '', NULL, '企业荣誉墙-荣誉类型'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'portal_honor_type');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '资质认证', '0', 'portal_honor_type', '', 'primary', 'Y', '0', 'admin', sysdate(), '', NULL, '资质认证'),
      (2, '行业奖项', '1', 'portal_honor_type', '', 'success', 'N', '0', 'admin', sysdate(), '', NULL, '行业奖项'),
      (3, '荣誉称号', '2', 'portal_honor_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '荣誉称号'),
      (4, '其他', '3', 'portal_honor_type', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '其他');

-- ----------------------------
-- 21、产品管理模块
-- ----------------------------

-- 21.1 产品品牌表
DROP TABLE IF EXISTS `portal_product_brand`;
CREATE TABLE `portal_product_brand` (
    `brand_id`        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '品牌ID',
    `brand_name`      VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '品牌名称',
    `brand_code`      VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '品牌编码',
    `brand_logo`      VARCHAR(500)    DEFAULT NULL           COMMENT '品牌LOGO',
    `brand_image`     VARCHAR(500)    DEFAULT NULL           COMMENT '品牌图片',
    `description`     VARCHAR(1000)   DEFAULT NULL           COMMENT '品牌描述',
    `website_url`     VARCHAR(500)    DEFAULT NULL           COMMENT '品牌官网地址',
    `sort_order`      INT(11)         NOT NULL DEFAULT 0      COMMENT '显示顺序',
    `status`          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '状态（0正常 1停用）',
    `del_flag`        CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`       VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
    `create_time`     DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`       VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
    `update_time`     DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`          VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`brand_id`),
    UNIQUE KEY `uk_brand_code` (`brand_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品品牌表';

-- 21.2 产品分类表（树形结构）
DROP TABLE IF EXISTS `portal_product_category`;
CREATE TABLE `portal_product_category` (
    `category_id`     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `category_name`   VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '分类名称',
    `category_code`   VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '分类编码',
    `parent_id`       BIGINT(20)      NOT NULL DEFAULT 0      COMMENT '父级ID（0为顶级）',
    `ancestors`       VARCHAR(500)    NOT NULL DEFAULT ''     COMMENT '祖级列表（如: 0,1,2）',
    `icon`            VARCHAR(500)    DEFAULT NULL           COMMENT '分类图标',
    `image`           VARCHAR(500)    DEFAULT NULL           COMMENT '分类图片',
    `keywords`        VARCHAR(300)    DEFAULT NULL           COMMENT '关键词（SEO）',
    `description`     VARCHAR(1000)   DEFAULT NULL           COMMENT '分类描述',
    `template_id`     BIGINT(20)      DEFAULT NULL           COMMENT '绑定的属性模板ID',
    `sort_order`      INT(11)         NOT NULL DEFAULT 0      COMMENT '显示顺序',
    `status`          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '状态（0正常 1停用）',
    `del_flag`        CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`       VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
    `create_time`     DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`       VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
    `update_time`     DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`          VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`category_id`),
    UNIQUE KEY `uk_category_code` (`category_code`),
    INDEX `idx_parent_id` (`parent_id`),
    INDEX `idx_template_id` (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品分类表';

-- 21.3 属性模板表
DROP TABLE IF EXISTS `portal_product_attr_template`;
CREATE TABLE `portal_product_attr_template` (
    `template_id`      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '模板ID',
    `template_name`    VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '模板名称',
    `template_code`    VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '模板编码',
    `description`      VARCHAR(500)    DEFAULT NULL           COMMENT '模板描述',
    `is_default`       CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否默认模板（0否 1是）',
    `status`           CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '状态（0正常 1停用）',
    `del_flag`         CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`        VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
    `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`        VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
    `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`           VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`template_id`),
    UNIQUE KEY `uk_template_code` (`template_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品属性模板表';

-- 21.4 属性定义表
DROP TABLE IF EXISTS `portal_product_attr_def`;
CREATE TABLE `portal_product_attr_def` (
    `attr_def_id`      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '属性定义ID',
    `template_id`      BIGINT(20)      NOT NULL                 COMMENT '所属模板ID',
    `attr_name`        VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '属性名称',
    `attr_code`        VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '属性编码',
    `attr_group`       VARCHAR(100)    DEFAULT NULL           COMMENT '属性分组',
    `attr_type`        TINYINT(4)      NOT NULL DEFAULT 1      COMMENT '属性类型（1文本 2数字 3单选 4多选 5日期 6布尔 7富文本 8图片 9附件）',
    `input_type`       VARCHAR(50)     DEFAULT NULL           COMMENT '输入控件类型',
    `options`          TEXT            DEFAULT NULL           COMMENT '选项值（JSON数组格式）',
    `default_value`    VARCHAR(500)    DEFAULT NULL           COMMENT '默认值',
    `validation_rule`  VARCHAR(500)    DEFAULT NULL           COMMENT '验证规则（正则表达式）',
    `unit`             VARCHAR(50)     DEFAULT NULL           COMMENT '单位（仅数字类型）',
    `is_required`      CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否必填（0否 1是）',
    `is_searchable`    CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否可搜索（0否 1是）',
    `is_filterable`    CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否可筛选（0否 1是）',
    `is_sku_attr`      CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否SKU属性',
    `show_in_list`     CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否在列表页显示',
    `show_in_detail`   CHAR(1)         NOT NULL DEFAULT '1'   COMMENT '是否在详情页显示',
    `sort_order`       INT(11)         NOT NULL DEFAULT 0      COMMENT '排序',
    `status`           CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '状态（0正常 1停用）',
    `del_flag`         CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`        VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
    `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`        VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
    `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`           VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`attr_def_id`),
    INDEX `idx_template_id` (`template_id`),
    INDEX `idx_attr_group` (`attr_group`),
    INDEX `idx_is_sku_attr` (`is_sku_attr`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品属性定义表';

-- 21.5 产品基本信息表
DROP TABLE IF EXISTS `portal_product`;
CREATE TABLE `portal_product` (
    `product_id`       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '产品ID',
    `product_code`     VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '产品编码',
    `product_name`     VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '产品名称',
    `category_id`      BIGINT(20)      DEFAULT NULL           COMMENT '所属分类ID',
    `brand_id`         BIGINT(20)      DEFAULT NULL           COMMENT '品牌ID',
    `main_image`       VARCHAR(500)    DEFAULT NULL           COMMENT '主图',
    `thumbnail_image`  VARCHAR(500)    DEFAULT NULL           COMMENT '缩略图',
    `image_list`       JSON            DEFAULT NULL           COMMENT '图片列表（JSON数组）',
    `video_url`        VARCHAR(500)    DEFAULT NULL           COMMENT '视频地址',
    `sub_title`        VARCHAR(300)    DEFAULT NULL           COMMENT '副标题',
    `summary`          VARCHAR(1000)   DEFAULT NULL           COMMENT '产品摘要',
    `description`      LONGTEXT        DEFAULT NULL           COMMENT '详细介绍（富文本）',
    `price`            DECIMAL(10,2)   DEFAULT NULL           COMMENT '价格',
    `original_price`   DECIMAL(10,2)   DEFAULT NULL           COMMENT '原价',
    `cost_price`       DECIMAL(10,2)   DEFAULT NULL           COMMENT '成本价',
    `unit`             VARCHAR(50)     DEFAULT NULL           COMMENT '计量单位',
    `weight`           DECIMAL(10,3)   DEFAULT NULL           COMMENT '重量（kg）',
    `volume`           VARCHAR(50)     DEFAULT NULL           COMMENT '体积（长x宽x高）',
    `sales_count`      INT(11)         DEFAULT 0              COMMENT '销量',
    `view_count`       INT(11)         DEFAULT 0              COMMENT '浏览次数',
    `collect_count`    INT(11)         DEFAULT 0              COMMENT '收藏次数',
    `comment_count`    INT(11)         DEFAULT 0              COMMENT '评论数',
    `sku_count`        INT(11)         DEFAULT 0              COMMENT 'SKU数量',
    `is_recommend`     CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否推荐（0否 1是）',
    `is_new`           CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否新品（0否 1是）',
    `is_hot`           CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否热销（0否 1是）',
    `status`           CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '状态（0下架 1上架 2草稿）',
    `sort_order`       INT(11)         NOT NULL DEFAULT 0      COMMENT '排序',
    `publish_time`     DATETIME        DEFAULT NULL           COMMENT '发布时间',
    `del_flag`         CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`        VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
    `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`        VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
    `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`           VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`product_id`),
    UNIQUE KEY `uk_product_code` (`product_code`),
    INDEX `idx_category_id` (`category_id`),
    INDEX `idx_brand_id` (`brand_id`),
    INDEX `idx_status` (`status`),
    FULLTEXT KEY `ft_product_name` (`product_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品基本信息表';

-- 23.3 消息模板表
CREATE TABLE IF NOT EXISTS portal_message_template (
  template_id      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '模板ID',
  template_code    VARCHAR(50)     NOT NULL DEFAULT '' COMMENT '模板编码',
  template_name    VARCHAR(100)    NOT NULL DEFAULT '' COMMENT '模板名称',
  channel_type     VARCHAR(20)     NOT NULL DEFAULT '' COMMENT '渠道类型（dingtalk/wecom/feishu/email）',
  template_type    VARCHAR(20)     NOT NULL DEFAULT '' COMMENT '模板类型（notification/reminder/alert）',
  title_template   VARCHAR(500)    DEFAULT '' COMMENT '标题模板',
  content_template TEXT            DEFAULT NULL COMMENT '内容模板（支持变量占位符${xxx}）',
  is_enabled       CHAR(1)         NOT NULL DEFAULT '1' COMMENT '是否启用（0否 1是）',
  del_flag         CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  create_by        VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time      DATETIME        DEFAULT NULL COMMENT '创建时间',
  update_by        VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time      DATETIME        DEFAULT NULL COMMENT '更新时间',
  remark           VARCHAR(500)    DEFAULT NULL COMMENT '备注',

  PRIMARY KEY (template_id),
  UNIQUE KEY uk_template_code (template_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息模板表';

-- 23.4 钉钉配置表
CREATE TABLE IF NOT EXISTS portal_message_channel_dingtalk (
  config_id         BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  channel_id        BIGINT(20)      DEFAULT NULL COMMENT '关联渠道主表',
  dingtalk_type     VARCHAR(20)     NOT NULL DEFAULT 'internal' COMMENT '对接方式（internal企业内部应用/robot群机器人/isv第三方）',
  corp_id           VARCHAR(100)    DEFAULT '' COMMENT '企业ID',
  app_key           VARCHAR(100)    DEFAULT '' COMMENT '应用AppKey',
  app_secret        VARCHAR(200)    DEFAULT '' COMMENT '应用AppSecret',
  agent_id          VARCHAR(50)     DEFAULT '' COMMENT '应用AgentID',
  webhook_url       VARCHAR(500)    DEFAULT '' COMMENT 'Webhook地址（机器人）',
  secret_key        VARCHAR(200)    DEFAULT '' COMMENT '加签密钥（机器人）',
  suite_id          VARCHAR(100)    DEFAULT '' COMMENT '套件ID（ISV）',
  suite_secret      VARCHAR(200)    DEFAULT '' COMMENT '套件Secret（ISV）',
  token             VARCHAR(200)    DEFAULT '' COMMENT '调用凭证',
  encoding_aes_key  VARCHAR(200)    DEFAULT '' COMMENT '加解密密钥',
  del_flag          CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  create_by         VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time       DATETIME        DEFAULT NULL COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time       DATETIME        DEFAULT NULL COMMENT '更新时间',

  PRIMARY KEY (config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='钉钉消息配置表';

-- 23.5 企业微信配置表
CREATE TABLE IF NOT EXISTS portal_message_channel_wecom (
  config_id         BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  channel_id        BIGINT(20)      DEFAULT NULL COMMENT '关联渠道主表',
  wecom_type        VARCHAR(20)     NOT NULL DEFAULT 'internal' COMMENT '对接方式（internal企业自建应用/robot群机器人）',
  corp_id           VARCHAR(100)    DEFAULT '' COMMENT '企业ID',
  agent_id          VARCHAR(50)     DEFAULT '' COMMENT '应用AgentID',
  agent_secret      VARCHAR(200)    DEFAULT '' COMMENT '应用Secret',
  webhook_url       VARCHAR(500)    DEFAULT '' COMMENT 'Webhook地址（机器人）',
  del_flag          CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  create_by         VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time       DATETIME        DEFAULT NULL COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time       DATETIME        DEFAULT NULL COMMENT '更新时间',

  PRIMARY KEY (config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='企业微信消息配置表';

-- 23.6 飞书配置表
CREATE TABLE IF NOT EXISTS portal_message_channel_feishu (
  config_id         BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  channel_id        BIGINT(20)      DEFAULT NULL COMMENT '关联渠道主表',
  feishu_type       VARCHAR(20)     NOT NULL DEFAULT 'internal' COMMENT '对接方式（internal企业自建应用/webhook WebHook机器人/thirdparty第三方）',
  app_id            VARCHAR(100)    DEFAULT '' COMMENT '应用AppID',
  app_secret        VARCHAR(200)    DEFAULT '' COMMENT '应用AppSecret',
  tenant_key        VARCHAR(100)    DEFAULT '' COMMENT '租户标识',
  webhook_url       VARCHAR(500)    DEFAULT '' COMMENT 'Webhook地址（机器人）',
  del_flag          CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  create_by         VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time       DATETIME        DEFAULT NULL COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time       DATETIME        DEFAULT NULL COMMENT '更新时间',

  PRIMARY KEY (config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='飞书消息配置表';

-- 23.7 邮件配置表
CREATE TABLE IF NOT EXISTS portal_message_channel_email (
  config_id         BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  channel_id        BIGINT(20)      DEFAULT NULL COMMENT '关联渠道主表',
  smtp_host         VARCHAR(200)    DEFAULT '' COMMENT 'SMTP服务器',
  smtp_port         INT             DEFAULT 465 COMMENT 'SMTP端口',
  username          VARCHAR(200)    DEFAULT '' COMMENT '用户名',
  password          VARCHAR(200)    DEFAULT '' COMMENT '密码/授权码',
  from_address      VARCHAR(200)    DEFAULT '' COMMENT '发件人地址',
  from_name         VARCHAR(100)    DEFAULT '' COMMENT '发件人名称',
  use_ssl           CHAR(1)         NOT NULL DEFAULT '1' COMMENT '是否使用SSL（0否 1是）',
  use_tls           CHAR(1)         NOT NULL DEFAULT '0' COMMENT '是否使用TLS（0否 1是）',
  del_flag          CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
  create_by         VARCHAR(64)     DEFAULT '' COMMENT '创建者',
  create_time       DATETIME        DEFAULT NULL COMMENT '创建时间',
  update_by         VARCHAR(64)     DEFAULT '' COMMENT '更新者',
  update_time       DATETIME        DEFAULT NULL COMMENT '更新时间',

  PRIMARY KEY (config_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='邮件消息配置表';

-- 23.8 消息中心菜单数据（幂等版本）
-- 注意：一级菜单parent_id必须为0

-- 一级菜单：消息中心
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '消息中心', 0, 4, 'message', NULL, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', NULL, '消息中心目录'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '消息中心' AND parent_id = 0);

SET @messageParentId = (SELECT menu_id FROM sys_menu WHERE menu_name = '消息中心' AND parent_id = 0 LIMIT 1);

-- 消息模板二级目录
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '消息模板', @messageParentId, 1, 'template', NULL, 'M', '0', '0', '', 'document', 'admin', sysdate(), '', NULL, '消息模板目录'
FROM DUAL WHERE @messageParentId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '消息模板' AND parent_id = @messageParentId);

SET @templateDirId = (SELECT menu_id FROM sys_menu WHERE menu_name = '消息模板' AND parent_id = @messageParentId LIMIT 1);

-- 消息模板列表菜单
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `route_name`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '模板列表', @templateDirId, 1, 'index', 'message/template/index', 'C', '0', '0', 'message:template:list', '#', 'MessageTemplate', 'admin', sysdate(), '', NULL, '消息模板列表菜单'
FROM DUAL WHERE @templateDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板列表' AND parent_id = @templateDirId);

SET @templateMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '模板列表' AND parent_id = @templateDirId LIMIT 1);

-- 消息模板按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板查询', @templateMenuId, 1, '#', '', 'F', '0', '0', 'message:template:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @templateMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板查询' AND parent_id = @templateMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板新增', @templateMenuId, 2, '#', '', 'F', '0', '0', 'message:template:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @templateMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板新增' AND parent_id = @templateMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板修改', @templateMenuId, 3, '#', '', 'F', '0', '0', 'message:template:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @templateMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板修改' AND parent_id = @templateMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板删除', @templateMenuId, 4, '#', '', 'F', '0', '0', 'message:template:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @templateMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板删除' AND parent_id = @templateMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板导出', @templateMenuId, 5, '#', '', 'F', '0', '0', 'message:template:export', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @templateMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板导出' AND parent_id = @templateMenuId);

-- 渠道配置二级目录
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '渠道配置', @messageParentId, 2, 'channel', NULL, 'M', '0', '0', '', 'setting', 'admin', sysdate(), '', NULL, '渠道配置目录'
FROM DUAL WHERE @messageParentId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '渠道配置' AND parent_id = @messageParentId);

SET @channelDirId = (SELECT menu_id FROM sys_menu WHERE menu_name = '渠道配置' AND parent_id = @messageParentId LIMIT 1);

-- 钉钉配置菜单
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `route_name`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '钉钉配置', @channelDirId, 1, 'dingtalk', 'message/channel/dingtalk', 'C', '0', '0', 'message:channel:dingtalk:list', '#', 'DingtalkChannel', 'admin', sysdate(), '', NULL, '钉钉配置菜单'
FROM DUAL WHERE @channelDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '钉钉配置' AND parent_id = @channelDirId);

SET @dingtalkMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '钉钉配置' AND parent_id = @channelDirId LIMIT 1);

-- 钉钉配置按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', @dingtalkMenuId, 1, '#', '', 'F', '0', '0', 'message:channel:dingtalk:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @dingtalkMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = @dingtalkMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', @dingtalkMenuId, 2, '#', '', 'F', '0', '0', 'message:channel:dingtalk:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @dingtalkMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = @dingtalkMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', @dingtalkMenuId, 3, '#', '', 'F', '0', '0', 'message:channel:dingtalk:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @dingtalkMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = @dingtalkMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', @dingtalkMenuId, 4, '#', '', 'F', '0', '0', 'message:channel:dingtalk:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @dingtalkMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = @dingtalkMenuId);

-- 企业微信配置菜单
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `route_name`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '企业微信配置', @channelDirId, 2, 'wecom', 'message/channel/wecom', 'C', '0', '0', 'message:channel:wecom:list', '#', 'WecomChannel', 'admin', sysdate(), '', NULL, '企业微信配置菜单'
FROM DUAL WHERE @channelDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '企业微信配置' AND parent_id = @channelDirId);

SET @wecomMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业微信配置' AND parent_id = @channelDirId LIMIT 1);

-- 企业微信配置按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', @wecomMenuId, 1, '#', '', 'F', '0', '0', 'message:channel:wecom:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @wecomMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = @wecomMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', @wecomMenuId, 2, '#', '', 'F', '0', '0', 'message:channel:wecom:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @wecomMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = @wecomMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', @wecomMenuId, 3, '#', '', 'F', '0', '0', 'message:channel:wecom:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @wecomMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = @wecomMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', @wecomMenuId, 4, '#', '', 'F', '0', '0', 'message:channel:wecom:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @wecomMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = @wecomMenuId);

-- 飞书配置菜单
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `route_name`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '飞书配置', @channelDirId, 3, 'feishu', 'message/channel/feishu', 'C', '0', '0', 'message:channel:feishu:list', '#', 'FeishuChannel', 'admin', sysdate(), '', NULL, '飞书配置菜单'
FROM DUAL WHERE @channelDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '飞书配置' AND parent_id = @channelDirId);

SET @feishuMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '飞书配置' AND parent_id = @channelDirId LIMIT 1);

-- 飞书配置按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', @feishuMenuId, 1, '#', '', 'F', '0', '0', 'message:channel:feishu:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @feishuMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = @feishuMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', @feishuMenuId, 2, '#', '', 'F', '0', '0', 'message:channel:feishu:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @feishuMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = @feishuMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', @feishuMenuId, 3, '#', '', 'F', '0', '0', 'message:channel:feishu:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @feishuMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = @feishuMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', @feishuMenuId, 4, '#', '', 'F', '0', '0', 'message:channel:feishu:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @feishuMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = @feishuMenuId);

-- 邮件配置菜单
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `route_name`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '邮件配置', @channelDirId, 4, 'email', 'message/channel/email', 'C', '0', '0', 'message:channel:email:list', '#', 'EmailChannel', 'admin', sysdate(), '', NULL, '邮件配置菜单'
FROM DUAL WHERE @channelDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '邮件配置' AND parent_id = @channelDirId);

SET @emailMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '邮件配置' AND parent_id = @channelDirId LIMIT 1);

-- 邮件配置按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置查询', @emailMenuId, 1, '#', '', 'F', '0', '0', 'message:channel:email:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @emailMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置查询' AND parent_id = @emailMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置新增', @emailMenuId, 2, '#', '', 'F', '0', '0', 'message:channel:email:add', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @emailMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置新增' AND parent_id = @emailMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置修改', @emailMenuId, 3, '#', '', 'F', '0', '0', 'message:channel:email:edit', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @emailMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置修改' AND parent_id = @emailMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '配置删除', @emailMenuId, 4, '#', '', 'F', '0', '0', 'message:channel:email:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @emailMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '配置删除' AND parent_id = @emailMenuId);

-- 发送记录二级目录
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '发送记录', @messageParentId, 3, 'sendLog', NULL, 'M', '0', '0', '', 'log', 'admin', sysdate(), '', NULL, '发送记录目录'
FROM DUAL WHERE @messageParentId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '发送记录' AND parent_id = @messageParentId);

SET @sendLogDirId = (SELECT menu_id FROM sys_menu WHERE menu_name = '发送记录' AND parent_id = @messageParentId LIMIT 1);

-- 发送日志菜单
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `route_name`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
)
SELECT '发送日志', @sendLogDirId, 1, 'list', 'message/sendLog/index', 'C', '0', '0', 'message:sendLog:list', '#', 'MessageSendLog', 'admin', sysdate(), '', NULL, '发送日志菜单'
FROM DUAL WHERE @sendLogDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '发送日志' AND parent_id = @sendLogDirId);

SET @sendLogMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '发送日志' AND parent_id = @sendLogDirId LIMIT 1);

-- 发送日志按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '日志查询', @sendLogMenuId, 1, '#', '', 'F', '0', '0', 'message:sendLog:query', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @sendLogMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '日志查询' AND parent_id = @sendLogMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '日志删除', @sendLogMenuId, 2, '#', '', 'F', '0', '0', 'message:sendLog:remove', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @sendLogMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '日志删除' AND parent_id = @sendLogMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '日志导出', @sendLogMenuId, 3, '#', '', 'F', '0', '0', 'message:sendLog:export', '#', 'admin', sysdate(), '', NULL, ''
FROM DUAL WHERE @sendLogMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '日志导出' AND parent_id = @sendLogMenuId);

-- 为超级管理员角色分配消息中心权限
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

-- 21.6 产品属性值表
DROP TABLE IF EXISTS `portal_product_attr_value`;
CREATE TABLE `portal_product_attr_value` (
    `value_id`         BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '属性值ID',
    `product_id`       BIGINT(20)      NOT NULL                 COMMENT '产品ID',
    `attr_def_id`      BIGINT(20)      NOT NULL                 COMMENT '属性定义ID',
    `attr_value`       TEXT            DEFAULT NULL           COMMENT '属性值（原始值）',
    `attr_value_text`  VARCHAR(2000)   DEFAULT NULL           COMMENT '属性值文本（用于搜索）',
    `attr_value_num`   DECIMAL(18,4)   DEFAULT NULL           COMMENT '属性值数值（仅数字类型）',
    `sort_order`       INT(11)         NOT NULL DEFAULT 0      COMMENT '排序',
    `del_flag`         CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`        VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`        VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`           VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`value_id`),
    UNIQUE KEY `uk_product_attr` (`product_id`, `attr_def_id`),
    INDEX `idx_product_id` (`product_id`),
    INDEX `idx_attr_def_id` (`attr_def_id`),
    INDEX `idx_attr_value_text` (`attr_value_text`(191))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品属性值表';

-- 21.7 产品SKU表
DROP TABLE IF EXISTS `portal_product_sku`;
CREATE TABLE `portal_product_sku` (
    `sku_id`           BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
    `product_id`       BIGINT(20)      NOT NULL                 COMMENT '产品ID',
    `sku_code`         VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT 'SKU编码',
    `sku_name`         VARCHAR(200)    DEFAULT NULL           COMMENT 'SKU名称',
    `sku_image`        VARCHAR(500)    DEFAULT NULL           COMMENT 'SKU图片',
    `price`            DECIMAL(10,2)   DEFAULT NULL           COMMENT 'SKU价格',
    `original_price`   DECIMAL(10,2)   DEFAULT NULL           COMMENT 'SKU原价',
    `cost_price`       DECIMAL(10,2)   DEFAULT NULL           COMMENT 'SKU成本价',
    `stock_quantity`   INT(11)         DEFAULT 0              COMMENT 'SKU库存数量',
    `weight`           DECIMAL(10,3)   DEFAULT NULL           COMMENT 'SKU重量（kg）',
    `sku_attrs`        JSON            DEFAULT NULL           COMMENT 'SKU属性组合',
    `bar_code`         VARCHAR(100)    DEFAULT NULL           COMMENT '条形码',
    `status`           CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '状态（0下架 1上架）',
    `is_default`       CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否默认SKU（0否 1是）',
    `del_flag`         CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
    `create_by`        VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
    `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_by`        VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
    `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
    `remark`           VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
    PRIMARY KEY (`sku_id`),
    UNIQUE KEY `uk_sku_code` (`sku_code`),
    INDEX `idx_product_id` (`product_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品SKU表';

-- 21.8 插入初始化数据
INSERT INTO `portal_product_brand`(`brand_name`, `brand_code`, `brand_logo`, `description`, `sort_order`, `status`, `create_by`, `create_time`) VALUES
('华为', 'HUAWEI', '', '华为技术有限公司', 1, '0', 'admin', sysdate()),
('小米', 'XIAOMI', '', '小米科技有限公司', 2, '0', 'admin', sysdate()),
('苹果', 'APPLE', '', '苹果公司', 3, '0', 'admin', sysdate());

INSERT INTO `portal_product_attr_template`(`template_name`, `template_code`, `description`, `is_default`, `status`, `create_by`, `create_time`) VALUES
('通用产品模板', 'GENERAL_PRODUCT', '适用于大多数产品的通用属性模板', '1', '0', 'admin', sysdate()),
('电子产品模板', 'ELECTRONIC_PRODUCT', '适用于电子数码类产品的属性模板', '0', '0', 'admin', sysdate()),
('服装鞋帽模板', 'CLOTHING_PRODUCT', '适用于服装鞋帽类产品的属性模板', '0', '0', 'admin', sysdate());

INSERT INTO `portal_product_attr_def`(`template_id`, `attr_name`, `attr_code`, `attr_group`, `attr_type`, `input_type`, `options`, `unit`, `is_required`, `is_searchable`, `is_filterable`, `is_sku_attr`, `show_in_list`, `show_in_detail`, `sort_order`, `status`, `del_flag`, `create_by`, `create_time`) VALUES
(1, '材质', 'material', '基本属性', 3, 'select', '[{"label":"纯棉","value":"纯棉"},{"label":"涤纶","value":"涤纶"},{"label":"混纺","value":"混纺"},{"label":"皮革","value":"皮革"},{"label":"其他","value":"其他"}]', '', '0', '1', '1', '0', '0', '1', 1, '0', '0', 'admin', sysdate()),
(1, '产地', 'origin', '基本属性', 1, 'input', '', '', '0', '1', '1', '0', '0', '1', 2, '0', '0', 'admin', sysdate()),
(1, '保质期', 'shelf_life', '基本属性', 1, 'input', '', '天', '0', '0', '0', '0', '0', '1', 3, '0', '0', 'admin', sysdate()),
(1, '颜色', 'color', '规格参数', 3, 'select', '[{"label":"红色","value":"红色"},{"label":"蓝色","value":"蓝色"},{"label":"黑色","value":"黑色"},{"label":"白色","value":"白色"},{"label":"绿色","value":"绿色"}]', '', '0', '1', '1', '1', '1', '1', 10, '0', '0', 'admin', sysdate()),
(1, '尺寸', 'size', '规格参数', 3, 'select', '[{"label":"S","value":"S"},{"label":"M","value":"M"},{"label":"L","value":"L"},{"label":"XL","value":"XL"},{"label":"XXL","value":"XXL"}]', '', '0', '1', '1', '1', '1', '1', 11, '0', '0', 'admin', sysdate()),
(1, '重量', 'weight', '规格参数', 2, 'input', '', 'g', '0', '0', '1', '0', '0', '1', 12, '0', '0', 'admin', sysdate()),
(1, '包装方式', 'package_method', '详细信息', 1, 'input', '', '', '0', '0', '0', '0', '0', '1', 20, '0', '0', 'admin', sysdate()),
(1, '售后服务', 'after_sale_service', '详细信息', 7, 'textarea', '', '', '0', '0', '0', '0', '0', '1', 21, '0', '0', 'admin', sysdate());

-- 21.9 配置菜单权限

-- 一级目录：产品管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品管理', @portalMenuId, 6, 'product', NULL, 'M', '0', '0', '', 'shopping', 'admin', sysdate(), '', NULL, '产品管理目录'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品管理' AND parent_id = @portalMenuId);

SET @productDirId = (SELECT menu_id FROM sys_menu WHERE menu_name = '产品管理' AND parent_id = @portalMenuId LIMIT 1);

-- 二级菜单：产品列表
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品列表', @productDirId, 1, 'list', 'portal/product/list/index', 'C', '0', '0', 'portal:product:list', '#', 'ProductList', 'admin', sysdate(), '', NULL, '产品列表菜单'
FROM DUAL WHERE @productDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品列表' AND parent_id = @productDirId);

SET @productListId = (SELECT menu_id FROM sys_menu WHERE menu_name = '产品列表' AND parent_id = @productDirId LIMIT 1);

-- 二级菜单：产品分类
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品分类', @productDirId, 2, 'category', 'portal/product/category/index', 'C', '0', '0', 'portal:productCategory:list', '#', 'ProductCategory', 'admin', sysdate(), '', NULL, '产品分类菜单'
FROM DUAL WHERE @productDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品分类' AND parent_id = @productDirId);

SET @productCategoryListId = (SELECT menu_id FROM sys_menu WHERE menu_name = '产品分类' AND parent_id = @productDirId LIMIT 1);

-- 二级菜单：品牌管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '品牌管理', @productDirId, 3, 'brand', 'portal/product/brand/index', 'C', '0', '0', 'portal:productBrand:list', '#', 'ProductBrand', 'admin', sysdate(), '', NULL, '品牌管理菜单'
FROM DUAL WHERE @productDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '品牌管理' AND parent_id = @productDirId);

SET @productBrandListId = (SELECT menu_id FROM sys_menu WHERE menu_name = '品牌管理' AND parent_id = @productDirId LIMIT 1);

-- 二级菜单：属性模板
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '属性模板', @productDirId, 4, 'template', 'portal/product/template/index', 'C', '0', '0', 'portal:productAttrTemplate:list', '#', 'ProductAttrTemplate', 'admin', sysdate(), '', NULL, '属性模板菜单'
FROM DUAL WHERE @productDirId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '属性模板' AND parent_id = @productDirId);

SET @productTemplateListId = (SELECT menu_id FROM sys_menu WHERE menu_name = '属性模板' AND parent_id = @productDirId LIMIT 1);

-- 产品列表按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品查询', @productListId, 1, '#', '', 'F', '0', '0', 'portal:product:query', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品查询' AND parent_id = @productListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品新增', @productListId, 2, '#', '', 'F', '0', '0', 'portal:product:add', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品新增' AND parent_id = @productListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品修改', @productListId, 3, '#', '', 'F', '0', '0', 'portal:product:edit', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品修改' AND parent_id = @productListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品删除', @productListId, 4, '#', '', 'F', '0', '0', 'portal:product:remove', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品删除' AND parent_id = @productListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '产品导出', @productListId, 5, '#', '', 'F', '0', '0', 'portal:product:export', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '产品导出' AND parent_id = @productListId);

-- 产品分类按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '分类查询', @productCategoryListId, 1, '#', '', 'F', '0', '0', 'portal:productCategory:query', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productCategoryListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '分类查询' AND parent_id = @productCategoryListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '分类新增', @productCategoryListId, 2, '#', '', 'F', '0', '0', 'portal:productCategory:add', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productCategoryListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '分类新增' AND parent_id = @productCategoryListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '分类修改', @productCategoryListId, 3, '#', '', 'F', '0', '0', 'portal:productCategory:edit', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productCategoryListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '分类修改' AND parent_id = @productCategoryListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '分类删除', @productCategoryListId, 4, '#', '', 'F', '0', '0', 'portal:productCategory:remove', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productCategoryListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '分类删除' AND parent_id = @productCategoryListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '分类导出', @productCategoryListId, 5, '#', '', 'F', '0', '0', 'portal:productCategory:export', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productCategoryListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '分类导出' AND parent_id = @productCategoryListId);

-- 品牌管理按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '品牌查询', @productBrandListId, 1, '#', '', 'F', '0', '0', 'portal:productBrand:query', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productBrandListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '品牌查询' AND parent_id = @productBrandListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '品牌新增', @productBrandListId, 2, '#', '', 'F', '0', '0', 'portal:productBrand:add', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productBrandListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '品牌新增' AND parent_id = @productBrandListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '品牌修改', @productBrandListId, 3, '#', '', 'F', '0', '0', 'portal:productBrand:edit', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productBrandListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '品牌修改' AND parent_id = @productBrandListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '品牌删除', @productBrandListId, 4, '#', '', 'F', '0', '0', 'portal:productBrand:remove', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productBrandListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '品牌删除' AND parent_id = @productBrandListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '品牌导出', @productBrandListId, 5, '#', '', 'F', '0', '0', 'portal:productBrand:export', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productBrandListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '品牌导出' AND parent_id = @productBrandListId);

-- 属性模板按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板查询', @productTemplateListId, 1, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:query', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productTemplateListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板查询' AND parent_id = @productTemplateListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板新增', @productTemplateListId, 2, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:add', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productTemplateListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板新增' AND parent_id = @productTemplateListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板修改', @productTemplateListId, 3, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:edit', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productTemplateListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板修改' AND parent_id = @productTemplateListId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '模板删除', @productTemplateListId, 4, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:remove', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @productTemplateListId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '模板删除' AND parent_id = @productTemplateListId);

-- 为企业管理员角色分配产品管理菜单权限
INSERT IGNORE INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND m.parent_id = @productDirId;

INSERT IGNORE INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND (m.parent_id IN (@productListId, @productCategoryListId, @productBrandListId, @productTemplateListId));

-- ----------------------------
-- 22、企业发展历程里程碑模块
-- ----------------------------

-- 22.1 企业发展历程里程碑表
DROP TABLE IF EXISTS `portal_company_milestone`;
CREATE TABLE `portal_company_milestone` (
    `milestone_id`     BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '里程碑ID',
    `company_id`       BIGINT(20)      NOT NULL COMMENT '企业ID（关联portal_company_info.info_id）',
    `milestone_title`  VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '里程碑标题',
    `milestone_year`   INT(4)          NOT NULL COMMENT '年份',
    `milestone_month`  INT(2)          DEFAULT NULL COMMENT '月份（1-12）',
    `milestone_day`    INT(2)          DEFAULT NULL COMMENT '日期（1-31）',
    `milestone_level`  CHAR(1)         NOT NULL DEFAULT '3' COMMENT '节点级别（1重大里程碑 2重要事件 3一般事件）',
    `description`      LONGTEXT        DEFAULT NULL COMMENT '事件描述（富文本）',
    `thumbnail_image`  VARCHAR(500)    DEFAULT '' COMMENT '缩略图地址',
    `hd_image`         VARCHAR(500)    DEFAULT '' COMMENT '高清大图地址',
    `link_type`        CHAR(1)         DEFAULT '0' COMMENT '链接类型（0无链接 1详情页 2内部路由 3外部链接）',
    `link_url`         VARCHAR(500)    DEFAULT '' COMMENT '链接地址（路由路径或外链URL）',
    `link_content`     LONGTEXT        DEFAULT NULL COMMENT '详情页内容（富文本，link_type=1时使用）',
    `sort_order`       INT(11)         NOT NULL DEFAULT 0 COMMENT '排序（升序）',
    `status`           CHAR(1)         NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `del_flag`         CHAR(1)         NOT NULL DEFAULT '0' COMMENT '删除标志（0正常 1已删除）',
    `create_by`        VARCHAR(64)     DEFAULT ''              COMMENT '创建者',
    `create_time`      DATETIME        DEFAULT NULL            COMMENT '创建时间',
    `update_by`        VARCHAR(64)     DEFAULT ''              COMMENT '更新者',
    `update_time`      DATETIME        DEFAULT NULL            COMMENT '更新时间',
    `remark`           VARCHAR(500)    DEFAULT NULL            COMMENT '备注',

    PRIMARY KEY (`milestone_id`) USING BTREE,
    INDEX `idx_company_id` (`company_id`),
    INDEX `idx_company_year` (`company_id`, `milestone_year`),
    INDEX `idx_level` (`milestone_level`),
    INDEX `idx_status` (`status`),
    INDEX `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='企业发展历程里程碑表';

-- 22.2 三级菜单：企业发展历程（属于基础管理，order_num=4在企业荣誉墙之后）
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `route_name`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '企业发展历程', @baseMenuId, 4, 'companyMilestone', 'portal/base/companyMilestone/index', 'C', '0', '0', 'portal:base:milestone:list', 'time-line', 'CompanyMilestone', 'admin', sysdate(), '', null, '企业发展历程菜单'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '企业发展历程' AND parent_id = @baseMenuId);

SET @milestoneMenuId = (SELECT menu_id FROM sys_menu WHERE menu_name = '企业发展历程' AND parent_id = @baseMenuId LIMIT 1);

-- 发展历程按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '历程查询', @milestoneMenuId, 1, '#', '', 'F', '0', '0', 'portal:base:milestone:query', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @milestoneMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '历程查询' AND parent_id = @milestoneMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '历程新增', @milestoneMenuId, 2, '#', '', 'F', '0', '0', 'portal:base:milestone:add', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @milestoneMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '历程新增' AND parent_id = @milestoneMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '历程修改', @milestoneMenuId, 3, '#', '', 'F', '0', '0', 'portal:base:milestone:edit', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @milestoneMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '历程修改' AND parent_id = @milestoneMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '历程删除', @milestoneMenuId, 4, '#', '', 'F', '0', '0', 'portal:base:milestone:remove', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @milestoneMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '历程删除' AND parent_id = @milestoneMenuId);

INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '历程导出', @milestoneMenuId, 5, '#', '', 'F', '0', '0', 'portal:base:milestone:export', '#', 'admin', sysdate(), '', null, ''
FROM DUAL WHERE @milestoneMenuId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = '历程导出' AND parent_id = @milestoneMenuId);

-- 22.3 为企业管理员角色分配发展历程权限
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND m.menu_name IN ('企业发展历程', '历程查询', '历程新增', '历程修改', '历程删除', '历程导出')
  AND NOT EXISTS (
      SELECT 1 FROM sys_role_menu rm
      WHERE rm.role_id = r.role_id AND rm.menu_id = m.menu_id
  );

-- 22.4 字典数据：节点级别
INSERT INTO `sys_dict_type`(`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT '节点级别', 'portal_milestone_level', '0', 'admin', sysdate(), '', NULL, '企业发展历程-节点级别'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'portal_milestone_level');

INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES(1, '重大里程碑', '1', 'portal_milestone_level', '', 'danger', 'Y', '0', 'admin', sysdate(), '', NULL, '重大里程碑事件'),
      (2, '重要事件', '2', 'portal_milestone_level', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '重要事件'),
      (3, '一般事件', '3', 'portal_milestone_level', '', 'info', 'N', '0', 'admin', sysdate(), '', NULL, '一般事件');

-- 22.5 字典数据：链接类型（发展历程专用，与轮播图共用一个字典类型）
-- 注意：portal_link_type 已存在（第7节创建），此处只补充缺失的值
INSERT INTO `sys_dict_data`(`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
SELECT 4, '外部链接', '3', 'portal_link_type', '', 'warning', 'N', '0', 'admin', sysdate(), '', NULL, '外部链接打开'
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_value = '3' AND dict_type = 'portal_link_type');

-- 23����Ϣ����ģ���
-- ----------------------------
-- 23.1 ��Ϣ�������ñ�
CREATE TABLE IF NOT EXISTS portal_message_channel (
  channel_id        BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '����ID',
  channel_type      VARCHAR(20)     NOT NULL DEFAULT ''     COMMENT '�������ͣ�dingtalk/wecom/email/feishu��',
  channel_name      VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '��������',
  is_enabled        CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '�Ƿ����ã�0�� 1�ǣ�',
  config_content    TEXT            DEFAULT NULL           COMMENT '�������ݣ�JSON��ʽ�洢���������ã�',
  sort_order        INT(11)         NOT NULL DEFAULT 0     COMMENT '����',
  del_flag          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT 'ɾ����־��0���� 1��ɾ����',
  create_by         VARCHAR(64)     DEFAULT ''             COMMENT '������',
  create_time       DATETIME        DEFAULT NULL           COMMENT '����ʱ��',
  update_by         VARCHAR(64)     DEFAULT ''             COMMENT '������',
  update_time       DATETIME        DEFAULT NULL           COMMENT '����ʱ��',
  
emark            VARCHAR(500)    DEFAULT NULL           COMMENT '��ע',

  PRIMARY KEY (channel_id),
  UNIQUE KEY uk_channel_type (channel_type)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='��Ϣ�������ñ�';

-- 23.2 ��Ϣ���ͼ�¼��
CREATE TABLE IF NOT EXISTS portal_message_send_log (
  log_id            BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '��־ID',
  channel_type      VARCHAR(20)     NOT NULL DEFAULT ''     COMMENT '��������',
  	emplate_id      BIGINT(20)      DEFAULT NULL           COMMENT '����ģ��ID',
  	emplate_name    VARCHAR(100)    DEFAULT ''             COMMENT 'ģ������',
  send_type        VARCHAR(20)     NOT NULL DEFAULT ''     COMMENT '�������ͣ�broadcast/group/personal��',
  
eceiver         VARCHAR(500)    DEFAULT ''             COMMENT '������',
  	itle            VARCHAR(200)   DEFAULT ''             COMMENT '��Ϣ����',
  content          TEXT            DEFAULT NULL           COMMENT '��Ϣ����',
  send_status      CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '����״̬��0������ 1�ɹ� 2ʧ�ܣ�',
  error_code       VARCHAR(50)     DEFAULT ''             COMMENT '������',
  error_message    VARCHAR(500)    DEFAULT ''             COMMENT '������Ϣ',
  send_time        DATETIME        DEFAULT NULL           COMMENT '����ʱ��',
  del_flag         CHAR(1)         NOT NULL DEFAULT '0'    COMMENT 'ɾ����־��0���� 1��ɾ����',
  create_by        VARCHAR(64)     DEFAULT ''             COMMENT '������',
  create_time       DATETIME        DEFAULT NULL           COMMENT '����ʱ��',
  update_by        VARCHAR(64)     DEFAULT ''             COMMENT '������',
  update_time       DATETIME        DEFAULT NULL           COMMENT '����ʱ��',
  
emark           VARCHAR(500)    DEFAULT NULL           COMMENT '��ע',

  PRIMARY KEY (log_id),
  KEY idx_channel_type (channel_type),
  KEY idx_send_status (send_status),
  KEY idx_send_time (send_time)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='��Ϣ���ͼ�¼��';
