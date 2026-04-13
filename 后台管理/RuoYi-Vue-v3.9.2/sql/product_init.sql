-- ============================================
-- 产品模块初始化SQL脚本
-- 作者: 王有政
-- 日期: 2026-04-13
-- 说明: 创建产品模块所需的7张核心表 + 菜单权限
-- ============================================

-- ============================================
-- 第一部分：创建7张数据表
-- ============================================

-- 1. 产品品牌表
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

-- 2. 产品分类表（树形结构，无限层级）
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

-- 3. 属性模板表（定义一组属性的集合）
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

-- 4. 属性定义表（属于某个模板）
DROP TABLE IF EXISTS `portal_product_attr_def`;
CREATE TABLE `portal_product_attr_def` (
    `attr_def_id`      BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '属性定义ID',
    `template_id`      BIGINT(20)      NOT NULL                 COMMENT '所属模板ID',
    `attr_name`        VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '属性名称',
    `attr_code`        VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '属性编码',
    `attr_group`       VARCHAR(100)    DEFAULT NULL           COMMENT '属性分组（规格参数/基本属性/详细信息等）',
    `attr_type`        TINYINT(4)      NOT NULL DEFAULT 1      COMMENT '属性类型（1文本 2数字 3单选 4多选 5日期 6布尔 7富文本 8图片 9附件）',
    `input_type`       VARCHAR(50)     DEFAULT NULL           COMMENT '输入控件类型（input/select/checkbox/radio/textarea/date/switch等）',
    `options`          TEXT            DEFAULT NULL           COMMENT '选项值（JSON数组格式）',
    `default_value`    VARCHAR(500)    DEFAULT NULL           COMMENT '默认值',
    `validation_rule`  VARCHAR(500)    DEFAULT NULL           COMMENT '验证规则（正则表达式）',
    `unit`             VARCHAR(50)     DEFAULT NULL           COMMENT '单位（仅数字类型）',
    `is_required`      CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否必填（0否 1是）',
    `is_searchable`    CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否可搜索（0否 1是）',
    `is_filterable`    CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否可筛选（0否 1是）',
    `is_sku_attr`      CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否SKU属性（用于生成SKU组合）',
    `show_in_list`     CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '是否在列表页显示（0否 1是）',
    `show_in_detail`   CHAR(1)         NOT NULL DEFAULT '1'   COMMENT '是否在详情页显示（0否 1是）',
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

-- 5. 产品基本信息表
DROP TABLE IF EXISTS `portal_product`;
CREATE TABLE `portal_product` (
    `product_id`       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '产品ID',
    `product_code`     VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT '产品编码',
    `product_name`     VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '产品名称',
    `category_id`      BIGINT(20)      DEFAULT NULL           COMMENT '所属分类ID',
    `brand_id`         BIGINT(20)      DEFAULT NULL           COMMENT '品牌ID',
    `main_image`       VARCHAR(500)    DEFAULT NULL           COMMENT '主图（大图）',
    `thumbnail_image`  VARCHAR(500)    DEFAULT NULL           COMMENT '缩略图（小图）',
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

-- 6. 产品属性值表
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
    `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
    `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
    PRIMARY KEY (`value_id`),
    UNIQUE KEY `uk_product_attr` (`product_id`, `attr_def_id`),
    INDEX `idx_product_id` (`product_id`),
    INDEX `idx_attr_def_id` (`attr_def_id`),
    INDEX `idx_attr_value_text` (`attr_value_text`(191))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='产品属性值表';

-- 7. 产品SKU表
DROP TABLE IF EXISTS `portal_product_sku`;
CREATE TABLE `portal_product_sku` (
    `sku_id`           BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
    `product_id`       BIGINT(20)      NOT NULL                 COMMENT '产品ID',
    `sku_code`         VARCHAR(100)    NOT NULL DEFAULT ''     COMMENT 'SKU编码',
    `sku_name`         VARCHAR(200)    DEFAULT NULL           COMMENT 'SKU名称（如：红色-XL）',
    `sku_image`        VARCHAR(500)    DEFAULT NULL           COMMENT 'SKU图片',
    `price`            DECIMAL(10,2)   DEFAULT NULL           COMMENT 'SKU价格',
    `original_price`   DECIMAL(10,2)   DEFAULT NULL           COMMENT 'SKU原价',
    `cost_price`       DECIMAL(10,2)   DEFAULT NULL           COMMENT 'SKU成本价',
    `stock_quantity`   INT(11)         DEFAULT 0              COMMENT 'SKU库存数量',
    `weight`           DECIMAL(10,3)   DEFAULT NULL           COMMENT 'SKU重量（kg）',
    `sku_attrs`        JSON            DEFAULT NULL           COMMENT 'SKU属性组合（{"颜色":"红色","尺寸":"XL"}）',
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


-- ============================================
-- 第二部分：插入示例数据和初始化数据
-- ============================================

-- 插入默认品牌数据
INSERT INTO `portal_product_brand`(`brand_name`, `brand_code`, `brand_logo`, `description`, `sort_order`, `status`, `create_by`, `create_time`) VALUES
('华为', 'HUAWEI', '', '华为技术有限公司', 1, '0', 'admin', sysdate()),
('小米', 'XIAOMI', '', '小米科技有限公司', 2, '0', 'admin', sysdate()),
('苹果', 'APPLE', '', '苹果公司', 3, '0', 'admin', sysdate());

-- 插入默认属性模板
INSERT INTO `portal_product_attr_template`(`template_name`, `template_code`, `description`, `is_default`, `status`, `create_by`, `create_time`) VALUES
('通用产品模板', 'GENERAL_PRODUCT', '适用于大多数产品的通用属性模板', '1', '0', 'admin', sysdate()),
('电子产品模板', 'ELECTRONIC_PRODUCT', '适用于电子数码类产品的属性模板', '0', '0', 'admin', sysdate()),
('服装鞋帽模板', 'CLOTHING_PRODUCT', '适用于服装鞋帽类产品的属性模板', '0', '0', 'admin', sysdate());

-- 插入通用产品模板的属性定义
INSERT INTO `portal_product_attr_def`(`template_id`, `attr_name`, `attr_code`, `attr_group`, `attr_type`, `input_type`, `options`, `unit`, `is_required`, `is_searchable`, `is_filterable`, `is_sku_attr`, `show_in_list`, `show_in_detail`, `sort_order`, `status`, `create_by`, `create_time`) VALUES
(1, '材质', 'material', '基本属性', 3, 'select', '[{"label":"纯棉","value":"纯棉"},{"label":"涤纶","value":"涤纶"},{"label":"混纺","value":"混纺"},{"label":"皮革","value":"皮革"},{"label":"其他","value":"其他"}]', '', '0', '1', '1', '0', '0', '1', 1, '0', 'admin', sysdate()),
(1, '产地', 'origin', '基本属性', 1, 'input', '', '', '0', '1', '1', '0', '0', '1', 2, '0', 'admin', sysdate()),
(1, '保质期', 'shelf_life', '基本属性', 1, 'input', '', '天', '0', '0', '0', '0', '0', '1', 3, '0', 'admin', sysdate()),
(1, '颜色', 'color', '规格参数', 3, 'select', '[{"label":"红色","value":"红色"},{"label":"蓝色","value":"蓝色"},{"label":"黑色","value":"黑色"},{"label":"白色","value":"白色"},{"label":"绿色","value":"绿色"}]', '', '0', '1', '1', '1', '1', '1', 10, '0', 'admin', sysdate()),
(1, '尺寸', 'size', '规格参数', 3, 'select', '[{"label":"S","value":"S"},{"label":"M","value":"M"},{"label":"L","value":"L"},{"label":"XL","value":"XL"},{"label":"XXL","value":"XXL"}]', '', '0', '1', '1', '1', '1', '1', 11, '0', 'admin', sysdate()),
(1, '重量', 'weight', '规格参数', 2, 'input', '', 'g', '0', '0', '1', '0', '0', '1', 12, '0', 'admin', sysdate()),
(1, '包装方式', 'package_method', '详细信息', 1, 'input', '', '', '0', '0', '0', '0', '0', '1', 20, '0', 'admin', sysdate()),
(1, '售后服务', 'after_sale_service', '详细信息', 7, 'textarea', '', '', '0', '0', '0', '0', '0', '1', 21, '0', 'admin', sysdate());


-- ============================================
-- 第三部分：配置菜单权限
-- ============================================

-- 查询门户管理一级菜单的ID（假设已存在，menu_name='门户管理'，parent_id=0）
SET @portalMenuId = (SELECT menu_id FROM sys_menu WHERE parent_id = 0 AND menu_name = '门户管理' LIMIT 1);

-- 如果不存在则创建（通常应该已经存在）
INSERT IGNORE INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('门户管理', 0, 5, 'portal', NULL, 'M', '0', '0', '', 'example', 'admin', sysdate(), '', NULL, '门户管理目录');

SET @portalMenuId = (SELECT menu_id FROM sys_menu WHERE parent_id = 0 AND menu_name = '门户管理' LIMIT 1);

-- 一级目录：产品管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('产品管理', @portalMenuId, 6, 'product', NULL, 'M', '0', '0', '', 'shopping', 'admin', sysdate(), '', NULL, '产品管理目录');

SET @productDirId = LAST_INSERT_ID();

-- 二级菜单：产品列表
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('产品列表', @productDirId, 1, 'list', 'portal/product/list/index', 'C', '0', '0', 'portal:product:list', '#', 'admin', sysdate(), '', NULL, '产品列表菜单');

SET @productListId = LAST_INSERT_ID();

-- 二级菜单：产品分类
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('产品分类', @productDirId, 2, 'category', 'portal/product/category/index', 'C', '0', '0', 'portal:productCategory:list', '#', 'admin', sysdate(), '', NULL, '产品分类菜单');

SET @productCategoryListId = LAST_INSERT_ID();

-- 二级菜单：品牌管理
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('品牌管理', @productDirId, 3, 'brand', 'portal/product/brand/index', 'C', '0', '0', 'portal:productBrand:list', '#', 'admin', sysdate(), '', NULL, '品牌管理菜单');

SET @productBrandListId = LAST_INSERT_ID();

-- 二级菜单：属性模板
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('属性模板', @productDirId, 4, 'template', 'portal/product/template/index', 'C', '0', '0', 'portal:productAttrTemplate:list', '#', 'admin', sysdate(), '', NULL, '属性模板菜单');

SET @productTemplateListId = LAST_INSERT_ID();

-- 产品列表按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('产品查询', @productListId, 1, '#', '', 'F', '0', '0', 'portal:product:query', '#', 'admin', sysdate(), '', null, ''),
('产品新增', @productListId, 2, '#', '', 'F', '0', '0', 'portal:product:add', '#', 'admin', sysdate(), '', null, ''),
('产品修改', @productListId, 3, '#', '', 'F', '0', '0', 'portal:product:edit', '#', 'admin', sysdate(), '', null, ''),
('产品删除', @productListId, 4, '#', '', 'F', '0', '0', 'portal:product:remove', '#', 'admin', sysdate(), '', null, ''),
('产品导出', @productListId, 5, '#', '', 'F', '0', '0', 'portal:product:export', '#', 'admin', sysdate(), '', null, '');

-- 产品分类按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('分类查询', @productCategoryListId, 1, '#', '', 'F', '0', '0', 'portal:productCategory:query', '#', 'admin', sysdate(), '', null, ''),
('分类新增', @productCategoryListId, 2, '#', '', 'F', '0', '0', 'portal:productCategory:add', '#', 'admin', sysdate(), '', null, ''),
('分类修改', @productCategoryListId, 3, '#', '', 'F', '0', '0', 'portal:productCategory:edit', '#', 'admin', sysdate(), '', null, ''),
('分类删除', @productCategoryListId, 4, '#', '', 'F', '0', '0', 'portal:productCategory:remove', '#', 'admin', sysdate(), '', null, ''),
('分类导出', @productCategoryListId, 5, '#', '', 'F', '0', '0', 'portal:productCategory:export', '#', 'admin', sysdate(), '', null, '');

-- 品牌管理按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('品牌查询', @productBrandListId, 1, '#', '', 'F', '0', '0', 'portal:productBrand:query', '#', 'admin', sysdate(), '', null, ''),
('品牌新增', @productBrandListId, 2, '#', '', 'F', '0', '0', 'portal:productBrand:add', '#', 'admin', sysdate(), '', null, ''),
('品牌修改', @productBrandListId, 3, '#', '', 'F', '0', '0', 'portal:productBrand:edit', '#', 'admin', sysdate(), '', null, ''),
('品牌删除', @productBrandListId, 4, '#', '', 'F', '0', '0', 'portal:productBrand:remove', '#', 'admin', sysdate(), '', null, ''),
('品牌导出', @productBrandListId, 5, '#', '', 'F', '0', '0', 'portal:productBrand:export', '#', 'admin', sysdate(), '', null, '');

-- 属性模板按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('模板查询', @productTemplateListId, 1, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:query', '#', 'admin', sysdate(), '', null, ''),
('模板新增', @productTemplateListId, 2, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:add', '#', 'admin', sysdate(), '', null, ''),
('模板修改', @productTemplateListId, 3, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:edit', '#', 'admin', sysdate(), '', null, ''),
('模板删除', @productTemplateListId, 4, '#', '', 'F', '0', '0', 'portal:productAttrTemplate:remove', '#', 'admin', sysdate(), '', null, '');

-- 为企业管理员角色分配产品管理菜单权限（如果存在company_admin角色）
INSERT IGNORE INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND m.parent_id = @productDirId;

-- 为企业管理员角色分配所有子菜单权限
INSERT IGNORE INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT r.role_id, m.menu_id
FROM sys_role r, sys_menu m
WHERE r.role_key = 'company_admin'
  AND (m.parent_id IN (@productListId, @productCategoryListId, @productBrandListId, @productTemplateListId));


-- ============================================
-- 完成！
-- ============================================
-- 已创建：
-- 1. portal_product_brand - 品牌表
-- 2. portal_product_category - 产品分类表
-- 3. portal_product_attr_template - 属性模板表
-- 4. portal_product_attr_def - 属性定义表
-- 5. portal_product - 产品表
-- 6. portal_product_attr_value - 产品属性值表
-- 7. portal_product_sku - 产品SKU表
--
-- 菜单结构：
-- 门户管理 > 产品管理 > 产品列表 / 产品分类 / 品牌管理 / 属性模板
-- ============================================
