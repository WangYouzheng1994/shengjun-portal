---
name: "mysql-query"
description: "全局MySQL查询与SQL生成技能。用于查询任意MySQL数据库的表结构和数据，生成符合规范的SQL脚本（建表、菜单、权限、升级等）。当需要了解数据库现状、查看表结构/数据、生成SQL脚本时自动调用。适用于所有MySQL项目。"
---

# MySQL Query & SQL Generator (全局技能)

## 概述

这是一个**全局通用**的MySQL数据库查询与SQL生成技能，具备以下核心能力：

- **表结构查询**: 查看表定义、字段、索引、建表语句
- **数据查询**: 查询表中的实际数据（支持条件、统计、关联查询）
- **SQL生成**: 生成标准化的DDL/DML脚本（建表、菜单、升级等）
- **诊断验证**: 执行SQL并验证结果

---

## 1. 数据库连接配置

### 1.1 配置优先级（从高到低）

```
1. 用户显式提供的参数（最高优先级）
2. 项目配置文件（自动检测）
3. 环境变量
4. 默认值（最低优先级）
```

### 1.2 自动检测项目配置

按以下顺序检查并提取数据库连接参数：

#### Spring Boot 项目
```yaml
# application.yml 或 application-druid.yml
spring:
  datasource:
    druid:
      master:
        url: jdbc:mysql://{host}:{port}/{database}?...
        username: {user}
        password: {password}
```

#### 通用 .env 文件
```env
DB_HOST=localhost
DB_PORT=3306
DB_USER=root
DB_PASSWORD=your_password
DB_DATABASE=test_db
# 或
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USER=root
MYSQL_PASSWORD=your_password
MYSQL_DATABASE=test_db
```

### 1.3 默认连接参数

当无法自动检测时使用：
- **host**: localhost
- **port**: 3306
- **user**: root
- **password**: (空)
- **database**: test

### 1.4 Python查询脚本（推荐）

本Skill内置 **mysql_query.py** 脚本，无需安装MySQL客户端即可执行查询。

**依赖**: 需安装pymysql库
```bash
pip install pymysql
```

**脚本位置**: `.trae/skills/mysql-query/mysql_query.py`

**基本用法**:
```bash
# 使用项目配置文件自动检测连接（推荐）
python .trae/skills/mysql-query/mysql_query.py --config {config_path} "SQL语句"

# 手动指定连接参数
python .trae/skills/mysql-query/mysql_query.py --host {ip} --port {port} --user {user} --password {pass} --db {database} "SQL语句"

# JSON格式输出
python .trae/skills/mysql-query/mysql_query.py --config {config_path} --json "SELECT * FROM table LIMIT 5"

# 从文件执行SQL
python .trae/skills/mysql-query/mysql_query.py --config {config_path} --file query.sql

# 禁用自动LIMIT（默认SELECT会加LIMIT 100保护）
python .trae/skills/mysql-query/mysql_query.py --config {config_path} --no-limit "SELECT * FROM table"
```

**输出格式选项**:
- 默认：表格格式（自动对齐）
- `--json` / `-j`: JSON格式
- `--limit N` / `-l N`: 设置自动LIMIT值（默认100）
- `--no-limit`: 禁用自动LIMIT

**当前项目快速命令** (RuoYi-Vue-v3.9.2):
```bash
# 配置文件路径（固定）
CONFIG="ruoyi-admin/src/main/resources/application-druid.yml"

# 示例：列出所有portal_表
python .trae/skills/mysql-query/mysql_query.py --config $CONFIG "SHOW TABLES LIKE 'portal_%';"

# 示例：查看表结构
python .trae/skills/mysql-query/mysql_query.py --config $CONFIG "DESCRIBE portal_article;"

# 示例：查询数据
python .trae/skills/mysql-query/mysql_query.py --config $CONFIG "SELECT COUNT(*) AS total FROM portal_article WHERE del_flag='0';"
```

---

## 2. 表结构查询

### 2.1 列出所有表

```bash
# 列出所有表
SHOW TABLES;

# 按模式筛选
SHOW TABLES LIKE 'prefix_%';
SHOW TABLES LIKE '%keyword%';
```

**使用场景**:
- 了解数据库中有哪些表
- 查找特定前缀的表（如 portal_*, sys_*）
- 搜索包含关键字的表

### 2.2 查看表结构详情

```bash
# 查看字段定义
DESCRIBE {table_name};
# 或
DESC {table_name};

# 查看完整建表语句（含引擎、字符集、索引、约束）
SHOW CREATE TABLE {table_name}\G

# 仅查看索引信息
SHOW INDEX FROM {table_name};

# 查看表状态信息
SHOW TABLE STATUS LIKE '{table_name}'\G
```

**返回信息解读**:

| DESCRIBE列 | 说明 |
|-----------|------|
| Field | 字段名 |
| Type | 数据类型 |
| Null | 是否允许NULL |
| Key | 键类型（PRI主键, UNI唯一索引, MUL普通索引） |
| Default | 默认值 |
| Extra | 额外信息（auto_increment等） |

### 2.3 常用表结构查询示例

```bash
# 查看portal_article表的完整结构
python .trae/skills/mysql-query/mysql_query.py --config ruoyi-admin/src/main/resources/application-druid.yml "DESCRIBE portal_article;"

# 查看建表语句（含索引）
python .trae/skills/mysql-query/mysql_query.py --config ruoyi-admin/src/main/resources/application-druid.yml "SHOW CREATE TABLE portal_article;"

# 列出所有portal_开头的表
python .trae/skills/mysql-query/mysql_query.py --config ruoyi-admin/src/main/resources/application-druid.yml "SHOW TABLES LIKE 'portal_%';"

# 查看索引信息
python .trae/skills/mysql-query/mysql_query.py --config ruoyi-admin/src/main/resources/application-druid.yml "SHOW INDEX FROM portal_article;"
```

---

## 3. 数据查询

### 3.1 基础查询

```sql
-- 查询前N条记录（避免大数据量）
SELECT * FROM {table} LIMIT 10;

-- 按条件查询
SELECT * FROM {table} WHERE {condition} LIMIT 100;

-- 统计记录数
SELECT COUNT(*) AS total FROM {table};
SELECT COUNT(*) AS total FROM {table} WHERE del_flag = '0';

-- 查询去重值
SELECT DISTINCT {column} FROM {table};
```

### 3.2 菜单/权限专用查询

```sql
-- 查询一级菜单（parent_id = 0）
SELECT menu_id, menu_name, order_num, path, icon
FROM sys_menu
WHERE parent_id = 0 AND status = '0'
ORDER BY order_num;

-- 查询完整的菜单树（递归查询，MySQL 8.0+ WITH RECURSIVE）
WITH RECURSIVE menu_tree AS (
    SELECT *, 1 AS level
    FROM sys_menu WHERE parent_id = 0
    UNION ALL
    SELECT m.*, mt.level + 1
    FROM sys_menu m
    JOIN menu_tree mt ON m.parent_id = mt.menu_id
)
SELECT * FROM menu_tree ORDER BY level, parent_id, order_num;

-- 查询某模块的所有菜单及子菜单
SELECT * FROM sys_menu WHERE path = 'portal'
   OR parent_id IN (SELECT menu_id FROM sys_menu WHERE path = 'portal');

-- 查询角色的权限分配
SELECT r.role_key, r.role_name, m.menu_name, m.perms, m.menu_type
FROM sys_role_menu rm
JOIN sys_role r ON rm.role_id = r.role_id
JOIN sys_menu m ON rm.menu_id = m.menu_id
WHERE r.role_key = '{role_key}'
ORDER BY m.parent_id, m.order_num;

-- 查询按钮权限列表
SELECT menu_id, menu_name, perms
FROM sys_menu
WHERE menu_type = 'F' AND parent_id = {parent_menu_id}
ORDER BY order_num;
```

### 3.3 业务数据常用查询

```sql
-- 查询未删除的记录（通用模式）
SELECT * FROM {table} WHERE del_flag = '0';

-- 按时间范围查询
SELECT * FROM {table}
WHERE create_time >= '2026-01-01 00:00:00'
  AND create_time <= '2026-12-31 23:59:59'
ORDER BY create_time DESC;

-- 分页查询
SELECT * FROM {table}
WHERE del_flag = '0'
ORDER BY create_time DESC
LIMIT {offset}, {pageSize};

-- 模糊搜索
SELECT * FROM {table}
WHERE {column} LIKE '%{keyword}%'
LIMIT 50;

-- 多表统计
SELECT
  (SELECT COUNT(*) FROM table1 WHERE del_flag='0') AS count1,
  (SELECT COUNT(*) FROM table2 WHERE del_flag='0') AS count2,
  (SELECT COUNT(*) FROM table3 WHERE del_flag='0') AS count3;

-- GROUP BY统计
SELECT status, COUNT(*) AS count
FROM {table}
WHERE del_flag = '0'
GROUP BY status;
```

### 3.4 关联查询示例

```sql
-- 文章及其分类名称
SELECT a.article_id, a.title, c.category_name, a.status, a.create_time
FROM portal_article a
LEFT JOIN portal_article_category c ON a.category_id = c.category_id
WHERE a.del_flag = '0'
ORDER BY a.create_time DESC
LIMIT 20;
```

---

## 4. SQL生成器

### 4.1 建表模板（通用版）

```sql
CREATE TABLE `{table_name}` (
  `{pk_column}`       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',

  -- ===== 业务字段开始（根据实际需求添加）=====
  -- `field_name`      VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '字段说明',
  -- `another_field`   INT(11)         DEFAULT NULL           COMMENT '另一字段',
  -- ===== 业务字段结束 ======

  `del_flag`          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志（0正常 1已删除）',
  `create_by`         VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
  `create_time`       DATETIME        DEFAULT NULL           COMMENT '创建时间',
  `update_by`         VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
  `update_time`       DATETIME        DEFAULT NULL           COMMENT '更新时间',
  `remark`            VARCHAR(500)    DEFAULT NULL           COMMENT '备注',

  PRIMARY KEY (`{pk_column}`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='{table_comment}';
```

**必选字段说明**:
| 字段 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| pk_column | BIGINT(20) | AUTO_INCREMENT | 主键ID |
| del_flag | CHAR(1) | '0' | 删除标志 |
| create_by | VARCHAR(64) | '' | 创建者 |
| create_time | DATETIME | NULL | 创建时间 |
| update_by | VARCHAR(64) | '' | 更新者 |
| update_time | DATETIME | NULL | 更新时间 |
| remark | VARCHAR(500) | NULL | 备注 |

**常用数据类型参考**:
| 类型 | 适用场景 | 示例 |
|------|----------|------|
| BIGINT(20) | 主键、大数值ID | user_id |
| INT(11) | 数量、排序、状态码 | sort_order, status |
| VARCHAR(n) | 短文本（n≤500） | name, title |
| TEXT | 长文本 | content, description |
| LONGTEXT | 超长文本（富文本） | article_content |
| CHAR(1) | 标志位（0/1, Y/N） | del_flag, sex |
| DECIMAL(m,d) | 金额、精确小数 | price, amount |
| DATETIME | 时间戳 | create_time |
| DATE | 日期（无时分秒） | birthday |

### 4.2 菜单SQL生成模板

#### 4.2.1 一级目录菜单

```sql
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
) VALUES (
  '{目录名称}',
  0,
  {排序号},
  '{路径标识}',
  NULL,
  'M',
  '0',
  '0',
  '',
  '{图标}',
  'admin',
  sysdate(),
  '',
  NULL,
  '{备注}'
);

SET @parentId = LAST_INSERT_ID();
```

**参数说明**:
- `menu_type`: M=目录, C=菜单, F=按钮
- `visible`: 0=显示, 1=隐藏
- `status`: 0=正常, 1=停用
- `parent_id`: 一级必须为0

#### 4.2.2 二级/三级功能菜单

```sql
INSERT INTO `sys_menu`(
  `menu_name`, `parent_id`, `order_num`, `path`,
  `component`, `menu_type`, `visible`, `status`,
  `perms`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
) VALUES (
  '{菜单名称}',
  @parentId,
  {排序号},
  '{路径}',
  '{组件路径}',
  'C',
  '0',
  '0',
  '{模块}:{功能}:list',
  '#',
  'admin',
  sysdate(),
  '',
  NULL,
  '{备注}'
);

SET @menuId = LAST_INSERT_ID();
```

**路径规范**:
- 目录：如 `portal`, `system`
- 功能菜单：如 `banner`, `article`, `user`
- 组件路径：如 `portal/article/index`, `system/user/index`

#### 4.2.3 按钮权限批量生成

```sql
-- 查询按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('{菜单名}查询', @menuId, 1, '#', '', 'F', '0', '0', '{perms_prefix}:query', '#', 'admin', sysdate(), '', null, '');

-- 新增按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('{菜单名}新增', @menuId, 2, '#', '', 'F', '0', '0', '{perms_prefix}:add', '#', 'admin', sysdate(), '', null, '');

-- 修改按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('{菜单名}修改', @menuId, 3, '#', '', 'F', '0', '0', '{perms_prefix}:edit', '#', 'admin', sysdate(), '', null, '');

-- 删除按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('{菜单名}删除', @menuId, 4, '#', '', 'F', '0', '0', '{perms_prefix}:remove', '#', 'admin', sysdate(), '', null, '');

-- 导出按钮
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('{菜单名}导出', @menuId, 5, #', '', 'F', '0', '0', '{perms_prefix}:export', '#', 'admin', sysdate(), '', null, '');

-- （可选）其他自定义按钮
-- INSERT INTO `sys_menu`(...) VALUES('{自定义操作}', @menuId, 6, #', '', 'F', '0', '0', '{perms_prefix}:custom', ...);
```

**标准按钮权限列表**:
| 操作 | 排序 | 权限后缀 | 说明 |
|------|------|----------|------|
| 查询 | 1 | :query | 查看列表/详情 |
| 新增 | 2 | :add | 新增记录 |
| 修改 | 3 | :edit | 编辑记录 |
| 删除 | 4 | :remove | 删除记录 |
| 导出 | 5 | :export | 导出Excel |
| 发布 | 6 | :publish | 发布/上线（可选） |
| 审批 | 7 | :approve | 审批操作（可选） |

#### 4.2.4 完整菜单生成示例（三级结构）

```sql
-- ================================
-- 示例：为"产品管理"模块生成完整菜单
-- 层级：门户管理 > 产品管理 > 产品列表
-- 权限前缀：portal:product
-- ================================

-- Step 1: 假设已有@portalMenuId（一级目录"门户管理"）

-- Step 2: 创建二级目录"产品管理"
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('产品管理', @portalMenuId, 3, 'product', NULL, 'M', '0', '0', '', 'shopping', 'admin', sysdate(), '', null, '产品管理目录');

SET @productDirId = LAST_INSERT_ID();

-- Step 3: 创建三级菜单"产品列表"
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES('产品列表', @productDirId, 1, 'list', 'portal/product/list/index', 'C', '0', '0', 'portal:product:list', '#', 'admin', sysdate(), '', null, '产品列表菜单');

SET @productListId = LAST_INSERT_ID();

-- Step 4: 创建按钮权限
INSERT INTO `sys_menu`(`menu_name`, `parent_id`, `order_num`, `path`, `component`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
VALUES
('产品查询', @productListId, 1, '#', '', 'F', '0', '0', 'portal:product:query', '#', 'admin', sysdate(), '', null, ''),
('产品新增', @productListId, 2, '#', '', 'F', '0', '0', 'portal:product:add', '#', 'admin', sysdate(), '', null, ''),
('产品修改', @productListId, 3, '#', '', 'F', '0', '0', 'portal:product:edit', '#', 'admin', sysdate(), '', null, ''),
('产品删除', @productListId, 4, '#', '', 'F', '0', '0', 'portal:product:remove', '#', 'admin', sysdate(), '', null, ''),
('产品导出', @productListId, 5, '#', '', 'F', '0', '0', 'portal:product:export', '#', 'admin', sysdate(), '', null, '');

-- Step 5: 为角色分配权限（可选）
INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT role_id, @productDirId FROM sys_role WHERE role_key = 'company_admin';

INSERT INTO `sys_role_menu`(`role_id`, `menu_id`)
SELECT role_id, @productListId FROM sys_role WHERE role_key = 'company_admin';
```

### 4.3 升级/迁移SQL模板

#### 4.3.1 表结构变更

```sql
-- 添加新字段
ALTER TABLE `{table_name}`
ADD COLUMN `{column_name}` {data_type} {default_value} {comment}
AFTER `{existing_column}`;

-- 示例：在title后添加subtitle字段
ALTER TABLE `portal_article`
ADD COLUMN `subtitle` VARCHAR(200) DEFAULT '' COMMENT '副标题'
AFTER `title`;

-- 修改字段属性
ALTER TABLE `{table_name}`
MODIFY COLUMN `{column_name}` {new_data_type} {new_comment};

-- 示例：将VARCHAR长度从200改为500
ALTER TABLE `portal_article`
MODIFY COLUMN `title` VARCHAR(500) NOT NULL DEFAULT '' COMMENT '文章标题（扩展）';

-- 删除字段
ALTER TABLE `{table_name}` DROP COLUMN `{column_name}`;

-- 添加索引
ALTER TABLE `{table_name}`
ADD INDEX `idx_{column_name}` (`{column_name}`);

-- 添加联合索引
ALTER TABLE `{table_name}`
ADD INDEX `idx_{col1}_{col2}` (`{col1}`, `{col2}`);

-- 删除索引
ALTER TABLE `{table_name}` DROP INDEX `idx_{index_name}`;
```

#### 4.3.2 菜单结构调整

```sql
-- 更改菜单父节点（移动菜单位置）
UPDATE `sys_menu`
SET `parent_id` = {new_parent_id},
    `order_num` = {new_order},
    `path` = '{new_path}',
    `component` = '{new_component}'
WHERE `menu_id` = {menu_id};

-- 批量修改权限标识前缀
UPDATE `sys_menu`
SET `perms` = CONCAT('{new_prefix}', SUBSTRING_INDEX(`perms`, ':', -1))
WHERE `parent_id` = {parent_menu_id}
  AND `perms` LIKE '{old_prefix}:%';

-- 启用/禁用菜单
UPDATE `sys_menu`
SET `status` = '{0或1}'
WHERE `menu_id` = {menu_id};

-- 显示/隐藏菜单
UPDATE `sys_menu`
SET `visible` = '{0或1}'
WHERE `menu_id` = {menu_id};
```

#### 4.3.3 数据迁移模板

```sql
-- 复制表结构（不复制数据）
CREATE TABLE `{new_table}` LIKE `{old_table}`;

-- 复制表结构和数据
CREATE TABLE `{new_table}` AS SELECT * FROM `{old_table}`;

-- 部分字段迁移
INSERT INTO `{new_table}` (col1, col2, col3)
SELECT old_col1, old_col2, old_col3
FROM `{old_table}`
WHERE del_flag = '0';

-- 数据归档（移到历史表）
INSERT INTO `{archive_table}`
SELECT * FROM `{source_table}`
WHERE create_time < '{cutoff_date}';
```

---

## 5. 项目规范适配

### 5.1 规范覆盖机制

本Skill支持通过项目规则文件定制行为。检测顺序：

1. 检查 `.trae/rules/database.md` - 数据库特定规则
2. 检查 `.trae/rules/{project}.md` - 项目通用规则
3. 使用内置默认规范

### 5.2 可配置规范项

| 规范项 | 默认值 | 说明 |
|--------|--------|------|
| table_prefix | (空) | 表名前缀 |
| pk_naming | id | 主键命名规则 |
| pk_type | BIGINT(20) AUTO_INCREMENT | 主键类型 |
| required_fields | create_time, update_time, remark, del_flag | 必选字段列表 |
| charset | utf8mb4 | 字符集 |
| engine | InnoDB | 存储引擎 |
| menu_table | sys_menu | 菜单表名 |
| perms_format | {module}:{function}:{action} | 权限标识格式 |
| soft_delete_field | del_flag | 软删除字段 |
| soft_delete_normal | '0' | 正常值 |
| soft_delete_deleted | '1' | 已删除值 |

### 5.3 RuoYi框架项目规范示例

当检测到RuoYi-Vue项目时，自动应用以下规范：

```markdown
## RuoYi Portal 项目规范

### 表结构
- **前缀**: portal_ (业务表), sys_ (系统表)
- **主键**: {table}_id 格式，如 article_id, category_id
- **必选字段**: 全部启用（见上方默认值）
- **字符集**: utf8mb4

### 菜单系统
- **层级**: 三级（一级目录 > 二级模块 > 三级功能）
- **权限格式**: portal:{module}:{function}:{action}
- **一级菜单parent_id**: 必须为0
- **变量命名**: @xxxMenuId, @xxxDirId, @xxxListId

### SQL脚本
- **初始化文件**: sql/{module}_init.sql
- **升级文件**: sql/upgrade_{description}.sql
- **头部注释**: 包含作者、日期、说明
```

---

## 6. 最佳实践与注意事项

### 6.1 查询安全

✅ **推荐做法**:
- 使用LIMIT限制返回行数（特别是SELECT *）
- WHERE条件加上del_flag过滤（如果表有此字段）
- 大表查询先COUNT估算数据量
- 使用EXPLAIN分析慢查询

❌ **避免做法**:
- 不加LIMIT的全表SELECT *
- 在生产环境执行DELETE without WHERE
- 忽略del_flag导致查到已删除数据

### 6.2 SQL编写规范

✅ **命名规范**:
- 表名：小写+下划线，如 portal_article
- 字段名：小写+下划线，如 create_time
- 索引名：idx_ + 字段名，如 idx_status
- 变量名：@ + 描述，如 @portalMenuId

✅ **格式规范**:
- 关键字大写：SELECT, FROM, WHERE, INSERT
- 缩进对齐：多行时保持缩进一致
- 注释清晰：每个逻辑块添加说明

### 6.3 性能优化建议

```sql
-- 为常用查询条件添加索引
ALTER TABLE `{table}` ADD INDEX `idx_{field}` (`{field}`);

-- 避免SELECT *，只查需要的字段
SELECT id, name, status FROM {table} WHERE ...

-- 分页查询优化（大数据量表）
SELECT * FROM {table}
WHERE id > {last_id}  -- 使用上次最后ID作为游标
ORDER BY id ASC
LIMIT {page_size};
```

### 6.4 常见问题排查

| 问题 | 可能原因 | 解决方法 |
|------|----------|----------|
| 菜单不显示 | parent_id错误/status=1 | 检查parent_id是否正确，确认status='0' |
| 权限不足 | 未分配角色菜单 | 检查sys_role_menu表 |
| 查询慢 | 缺少索引 | EXPLAIN分析后添加索引 |
| 中文乱码 | 字符集不一致 | 确保连接和表都是utf8mb4 |
| 连接失败 | 密码/端口/防火墙 | 检查连接参数和网络 |

---

## 7. 快速参考卡

### 常用命令速查

| 需求 | SQL/命令 |
|------|----------|
| 查看所有表 | SHOW TABLES; |
| 查看表结构 | DESC {table}; |
| 查看建表语句 | SHOW CREATE TABLE {table}\G |
| 查看索引 | SHOW INDEX FROM {table}; |
| 统计行数 | SELECT COUNT(*) FROM {table}; |
| 查询前10条 | SELECT * FROM {table} LIMIT 10; |
| 查询菜单树 | 见3.2节 |
| 创建表 | 见4.1节模板 |
| 添加菜单 | 见4.2节模板 |
| 修改表结构 | 见4.3节模板 |

### 权限标识速查

| 后缀 | 含义 | 常用位置 |
|------|------|----------|
| :list | 列表权限 | 菜单C类型 |
| :query | 查询 | 按钮F类型 |
| :add | 新增 | 按钮F类型 |
| :edit | 修改 | 按钮F类型 |
| :remove | 删除 | 按钮F类型 |
| :export | 导出 | 按钮F类型 |
| :publish | 发布 | 按钮F类型（可选）|

---

## 附录：当前项目连接信息（自动检测）

> 当在本项目（RuoYi-Vue-v3.9.2）中使用时，以下信息会自动从配置文件中读取：

**配置来源**: ruoyi-admin/src/main/resources/application-druid.yml

**连接参数**:
- Host: 192.168.0.69
- Port: 3306
- Database: ry392
- User: root
- Password: (已配置)

**已知表（portal_前缀）**:
- portal_banner (轮播图)
- portal_company_info (企业信息)
- portal_article_category (文章分类)
- portal_article (文章)

**菜单结构**:
- 一级：门户管理 (portal)
- 二级：基础管理 (base), 信息发布 (article)
- 三级：轮播图管理, 企业信息, 文章分类, 文章管理

---

*版本: 1.1.0*
*作者: AI Assistant / 王有政*
*更新: 2026-04-12*

### 变更历史
- v1.1.0 (2026-04-12): 新增Python查询脚本(mysql_query.py)，无需MySQL客户端
- v1.0.0 (2026-04-12): 初始版本，纯文档模式
