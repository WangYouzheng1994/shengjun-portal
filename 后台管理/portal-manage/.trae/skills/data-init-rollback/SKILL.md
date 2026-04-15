---
name: "data-init-rollback"
description: "全局数据初始化与回滚技能。用于生成幂等的初始化SQL（可重复执行不重复数据）、自动生成回滚SQL、数据清洗与迁移。适用于所有MySQL项目。当需要为新模块初始化数据、迭代更新配置数据、或需要回滚变更时自动调用。"
---

# 数据初始化与回滚 (Data Init & Rollback) - 全局技能

## 概述

这是一个**全局通用**的业务模块数据管理技能，适用于任何MySQL数据库项目。

### 核心能力

- **幂等初始化**: SQL脚本可安全重复执行，不会产生重复数据
- **自动回滚**: 根据初始化SQL自动生成逆向回滚SQL
- **数据保留**: 迭代时保护已有业务数据不被破坏
- **可追溯**: 每次变更都有对应的回滚方案
- **智能适配**: 自动检测项目类型并应用对应规范

### 全局性保证

- ✅ 不局限于任何特定框架或项目
- ✅ 可适配任意MySQL数据库
- ✅ 支持通过规则文件定制行为
- ✅ 内置多框架规范模板

---

## 1. 核心原则（通用版）

### 1.1 幂等性原则 (Idempotency)

**定义**: 同一个SQL脚本无论执行多少次，结果都相同。

**通用实现方式**:

| 操作类型 | 幂等写法 | 说明 |
|----------|----------|------|
| 建表 | `DROP TABLE IF EXISTS` + `CREATE TABLE` | 先删后建 |
| 增量建字段 | `IF NOT EXISTS` 检查 + `ALTER TABLE ADD` | 安全增量 |
| 插入记录 | `INSERT IGNORE INTO ...` | 跳过已存在(唯一键/主键) |
| 更新配置 | `INSERT ... ON DUPLICATE KEY UPDATE` | 存在则更新 |
| 分配关联 | `INSERT IGNORE INTO ...` + 子查询去重 | 避免重复 |

### 1.2 数据保护原则

**必须保护的场景**:
- 用户已录入的业务数据
- 系统运行产生的日志/统计数据
- 用户自定义的配置扩展

**可以重建的场景**:
- 系统预设的字典/枚举值
- 默认模板/标签/分类
- 使用INSERT IGNORE的菜单/权限结构

### 1.3 可回滚原则

每个初始化SQL都应该有对应的**回滚SQL**:
- 版本回退时恢复原状
- 测试环境快速重置
- 出问题时紧急恢复

---

## 2. 项目规范适配层

### 2.1 规范配置来源

```
检测优先级:
1. .trae/rules/database.md      ← 数据库特定规则（最高）
2. .trae/rules/{project}.md    ← 项目通用规则
3. 内置默认规范                  ← 兜底
```

### 2.2 可配置规范项（全部可选覆盖）

| 规范项 | 默认值 | 说明 | 适用场景 |
|--------|--------|------|----------|
| `table_prefix` | (空) | 表名前缀 | 如 `portal_`, `app_`, `t_` |
| `pk_naming` | `id` | 主键命名规则 | 如 `{table}_id`, `id`, `uuid` |
| `pk_type` | `BIGINT AUTO_INCREMENT` | 主键类型 | UUID、雪花ID等 |
| `menu_table` | `sys_menu` | 菜单表名 | RuoYi用sys_menu, 其他可能不同 |
| `menu_pk` | `menu_id` | 菜单主键名 | - |
| `menu_name_col` | `menu_name` | 菜单名称列 | - |
| `menu_parent_col` | `parent_id` | 父级列 | - |
| `menu_type_col` | `menu_type` | 类型列(M/C/F) | - |
| `menu_perms_col` | `perms` | 权限标识列 | - |
| `dict_type_table` | `sys_dict_type` | 字典类型表 | - |
| `dict_data_table` | `sys_dict_data` | 字典数据表 | - |
| `role_table` | `sys_role` | 角色表 | - |
| `role_menu_table` | `sys_role_menu` | 角色菜单关联表 | - |
| `role_key_col` | `role_key` | 角色标识列 | - |
| `system_user` | `admin` | 系统创建者标记 | 用于区分系统预设数据 |
| `required_fields` | 见下方 | 必选字段列表 | create_time, update_time等 |

### 2.3 默认必选字段

```sql
-- 以下字段在业务表中默认必须包含：
`del_flag`        CHAR(1)     NOT NULL DEFAULT '0'   -- 删除标志
`create_by`       VARCHAR(64)  DEFAULT ''            -- 创建者
`create_time`     DATETIME     DEFAULT NULL          -- 创建时间
`update_by`       VARCHAR(64)  DEFAULT ''            -- 更新者
`update_time`     DATETIME     DEFAULT NULL          -- 更新时间
`remark`          VARCHAR(500) DEFAULT NULL          -- 备注
```

> **注意**: 以上为默认值，可通过规则文件完全自定义或禁用。

### 2.4 内置框架规范示例

#### RuoYi / RuoYi-Vue 项目
```markdown
## RuoYi 项目规范
table_prefix: portal_ (业务表), sys_ (系统表)
pk_naming: {table}_id
menu_table: sys_menu
dict_type_table: sys_dict_type
role_table: sys_role
system_user: admin
菜单层级: 三级 (目录M > 菜单C > 按钮F)
权限格式: {module}:{function}:{action}
```

#### Django + django-cms 项目
```markdown
## Django CMS 规范
table_prefix: cms_, auth_
pk_naming: id
menu_table: (无统一菜单表，按应用自行设计)
dict_type_table: (使用django choices或独立表)
role_table: auth_group / 自定义
system_user: (无此概念，用is_superuser判断)
```

#### Laravel 项目
```markdown
## Laravel 规范
table_prefix: (空，Laravel不用前缀)
pk_naming: id
menu_table: menus (如使用spatie/laravel-permission)
dict_type_table: (无内置，需自建)
role_table: roles (spatie/laravel-permission)
system_user: (用seeders管理初始数据)
```

#### 通用/自定义项目
```markdown
## 自定义项目规范
# 完全根据实际表结构定义上述各项
# 无菜单系统? → menu_table留空则跳过菜单相关功能
# 无字典系统? → dict_*_table留空则跳过字典功能
# 无角色系统? → role_*_table留空则跳过角色功能
```

---

## 3. 通用幂等SQL模板库

### 3.1 表结构DDL模板

#### 3.1.1 先删后建模式（全新部署）

```sql
-- ================================
-- {表名中文}表（幂等：先删后建）
-- [警告] 此方式会清空表中所有数据！仅适用于无业务数据的表
-- ================================
DROP TABLE IF EXISTS `{prefix}{table_name}`;
CREATE TABLE `{prefix}{table_name}` (
  `{pk_column}`       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',

  -- ===== 业务字段（根据需求添加）=====

  -- ===== 系统字段（可按规范调整）=====
  `del_flag`          CHAR(1)         NOT NULL DEFAULT '0'    COMMENT '删除标志',
  `create_by`         VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
  `create_time`       DATETIME        DEFAULT NULL           COMMENT '创建时间',
  `update_by`         VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
  `update_time`       DATETIME        DEFAULT NULL           COMMENT '更新时间',
  `remark`            VARCHAR(500)    DEFAULT NULL           COMMENT '备注',

  PRIMARY KEY (`{pk_column}`)
) ENGINE=InnoDB AUTO_INCREMENT=1 COMMENT='{表注释}';
```

#### 3.1.2 增量DDL模式（保护已有数据）

```sql
-- 安全的增量DDL（检查字段是否存在后再添加）
SET @dbname = DATABASE();
SET @tablename = '{prefix}{table_name}';
SET @colname = '{new_column}';

SET @sql = (SELECT IF(
  (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
   WHERE TABLE_SCHEMA = @dbname AND TABLE_NAME = @tablename AND COLUMN_NAME = @colname) = 0,
  CONCAT('ALTER TABLE `', @tablename, '` ADD COLUMN `', @colname, '` {type} {default} {comment};'),
  CONCAT('SELECT ''Column ', @colname, ' already exists'' AS info;')
));
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
```

### 3.2 菜单INSERT模板（通用版）

> **适用条件**: 项目有菜单系统（`menu_table` 已配置）

```sql
-- 一级目录菜单（按{menu_name_col}去重）
INSERT IGNORE INTO `{menu_table}`(
  `{menu_name_col}`, `{menu_parent_col}`, `order_num`, `path`,
  `component`, `{menu_type_col}`, `visible`, `status`,
  `{menu_perms_col}`, `icon`, `create_by`, `create_time`,
  `update_by`, `update_time`, `remark`
) VALUES (
  '{一级目录名}',
  0,
  {排序号},
  '{路径标识}',
  NULL,
  'M',
  '0',
  '0',
  '',
  '{图标}',
  '{system_user}',
  sysdate(),
  '',
  NULL,
  '{备注}'
);

-- 获取ID（兼容不同数据库语法）
SET @{varName}Id = (SELECT `{menu_pk}` FROM `{menu_table}`
  WHERE `{menu_name_col}` = '{一级目录名}' AND `{menu_parent_col}` = 0 LIMIT 1);

-- 二级/三级菜单和按钮权限（同样使用INSERT IGNORE）
INSERT IGNORE INTO `{menu_table}`(...) VALUES (...);

-- 按钮权限批量插入
INSERT IGNORE INTO `{menu_table}`(`{menu_name_col}`, `{menu_parent_col}`, `order_num`, ..., `{menu_perms_col}`)
VALUES
('{功能}查询', @{parentId}, 1, ..., '{perms_prefix}:query'),
('{功能}新增', @{parentId}, 2, ..., '{perms_prefix}:add'),
('{功能}修改', @{parentId}, 3, ..., '{perms_prefix}:edit'),
('{功能}删除', @{parentId}, 4, ..., '{perms_prefix}:remove'),
('{功能}导出', @{parentId}, 5, ..., '{perms_prefix}:export');
```

### 3.3 字典/枚举数据模板（通用版）

> **适用条件**: 项目有字典系统（`dict_type_table` 已配置）

```sql
-- 字典类型（按dict_type去重）
INSERT IGNORE INTO `{dict_type_table}`(
  `dict_name`, `dict_type`, `status`,
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`
) VALUES ('{字典名称}', '{type_code}', '0', '{system_user}', sysdate(), '', NULL, '');

-- 字典数据（按type+value联合去重）
INSERT IGNORE INTO `{dict_data_table}`(
  `dict_sort`, `dict_label`, `dict_value`, `dict_type`,
  `css_class`, `list_class`, `is_default`, `status`,
  `create_by`, `create_time`, `update_by`, `update_time`, `remark`
) VALUES
(1, '{选项1}', '0', '{type_code}', '', 'primary', 'Y', '0', '{system_user}', sysdate(), '', NULL, ''),
(2, '{选项2}', '1', '{type_code}', '', 'success', 'N', '0', '{system_user}', sysdate(), '', NULL, ''),
(3, '{选项3}', '2', '{type_code}', '', 'info', 'N', '0', '{system_user}', sysdate(), '', NULL, '');
```

### 3.4 角色权限分配模板（通用版）

> **适用条件**: 项目有角色系统（`role_table` 已配置）

```sql
-- 为角色分配菜单/权限（子查询去重避免重复分配）
INSERT IGNORE INTO `{role_menu_table}`(`role_id`, `menu_id`)
SELECT
  (SELECT `{role_pk}` FROM `{role_table}` WHERE `{role_key_col}` = '{role_key}'),
  `{menu_pk}`
FROM `{menu_table}`
WHERE `{menu_name_col}` IN (
  '{菜单1}', '{菜单2}', ...
)
AND `{menu_pk}` NOT IN (
  SELECT `{menu_pk}` FROM `{role_menu_table}`
  WHERE `role_id` = (SELECT `{role_pk}` FROM `{role_table}` WHERE `{role_key_col}` = '{role_key}')
);
```

### 3.5 业务初始数据模板

```sql
-- 方式一：INSERT IGNORE（适合有唯一约束的表）
INSERT IGNORE INTO `{prefix}{table}`(`unique_col`, `col1`, `col2`, ..., `create_by`, `create_time`)
VALUES
('{唯一值1}', '{值1}', '{值2}', ..., '{system_user}', sysdate()),
('{唯一值2}', '{值3}', '{值4}', ..., '{system_user}', sysdate());

-- 方式二：ON DUPLICATE KEY UPDATE（存在则更新）
INSERT INTO `{prefix}{table}`(`unique_col`, `col1`, `col2`, ..., `update_time`)
VALUES ('{唯一值}', '{新值1}', '{新值2}', ..., sysdate())
ON DUPLICATE KEY UPDATE
  `col1` = VALUES(`col1`),
  `col2` = VALUES(`col2`),
  `update_time` = sysdate();
```

---

## 4. 回滚SQL生成规则（通用版）

### 4.1 正向→逆向操作映射

| 正向操作 | 回滚操作 | 条件/说明 |
|----------|----------|-----------|
| `CREATE TABLE t` | `DROP TABLE IF EXISTS t` | 删除整张表 |
| `DROP IF EXISTS + CREATE` | **无法回滚数据** | 数据已丢失 ⚠️ |
| `ALTER TABLE ADD col` | `ALTER TABLE DROP COLUMN col` | 删除添加的字段 |
| `ALTER TABLE MODIFY col` | `ALTER TABLE MODIFY col` | 恢复原始类型 |
| `ALTER TABLE ADD INDEX` | `ALTER TABLE DROP INDEX idx` | 删除索引 |
| `INSERT IGNORE` (新记录) | `DELETE ... WHERE key IN (...)` | 按唯一键删除 |
| `INSERT IGNORE` (可能已存在) | **无需回滚** | 记录可能原本就存在 |
| `UPDATE` | `UPDATE` (恢复原值) | 需保存原值快照 |
| `DELETE` | `INSERT` (从备份恢复) | 需先备份 |

### 4.2 通用回滚脚本模板

```sql
-- ----------------------------
-- 回滚脚本：{模块名称}
-- 对应初始化文件：{input_file}
-- 作者：{author}
-- 日期：{date}
-- 说明：撤销上述初始化SQL的所有变更
--       [警告] 执行前确认没有重要业务数据依赖这些变更
-- ----------------------------

START TRANSACTION;

-- 【回滚1】删除系统预设的业务初始数据
-- 仅删除 system_user 标记的数据，保留用户数据
DELETE FROM `{table1}` WHERE `create_by` = '{system_user}';
DELETE FROM `{table2}` WHERE `create_by` = '{system_user}';

-- 【回滚2】删除新增的菜单及子菜单（级联删除按钮权限）
DELETE FROM `{menu_table}` WHERE `{menu_name_col}` IN ('{菜单1}', '{菜单2}') AND `{menu_parent_col}` = 0;

-- 【回滚3】删除新增的字典数据和类型
DELETE FROM `{dict_data_table}` WHERE `dict_type` IN ('{type1}', '{type2}');
DELETE FROM `{dict_type_table}` WHERE `dict_type` IN ('{type1}', '{type2}');

-- 【回滚4】清理失效的角色关联
DELETE FROM `{role_menu_table}`
WHERE `role_id` = (SELECT `{role_pk}` FROM `{role_table}` WHERE `{role_key_col}` = '{role_key}')
  AND `menu_id` NOT IN (SELECT `{menu_pk}` FROM `{menu_table}`);

-- 【回滚5】删除新建的表（可选，默认注释）
-- [警告] 会永久删除所有数据！如有业务数据请注释掉
-- DROP TABLE IF EXISTS `{table1}`;
-- DROP TABLE IF EXISTS `{table2}`;

COMMIT;

-- 回滚完成。如需撤销本次回滚，重新执行对应的init SQL即可。
```

---

## 5. 工具脚本

### 5.1 rollback_generator.py

本Skill附带**回滚SQL自动生成工具**，位于同目录下。

**位置**: `.trae/skills/data-init-rollback/rollback_generator.py`

**用法**:

```bash
# 从SQL文件生成回滚脚本
python .trae/skills/data-init-rollback/rollback_generator.py \
  --input upgrade_xxx.sql \
  --output rollback_xxx.sql

# 预览生成的回滚SQL（不写文件）
python .trae/skills/data-init-rollback/rollback_generator.py \
  --input upgrade_xxx.sql \
  --dry-run

# 指定自定义配置（覆盖默认值）
python .trae/skills/data-init-rollback/rollback_generator.py \
  --input upgrade_xxx.sql \
  --menu-table custom_menus \
  --system-user system \
  --output rollback_xxx.sql
```

**支持的参数**:

| 参数 | 说明 | 默认值 |
|------|------|--------|
| `--input` / `-i` | 输入的初始化SQL文件 | (必填) |
| `--output` / `-o` | 输出的回滚SQL文件路径 | 自动生成 |
| `--dry-run` | 只显示结果不写文件 | False |
| `--config` / `-c` | 数据库配置文件路径 | (可选) |
| `--menu-table` | 自定义菜单表名 | sys_menu |
| `--dict-type-table` | 自定义字典类型表名 | sys_dict_type |
| `--system-user` | 系统用户标记 | admin |

### 5.2 配合 mysql-query Skill

两个Skill配合使用效果最佳：

```bash
CONFIG="application-druid.yml"  # 或其他配置文件

# 1. 执行前快照
python .trae/skills/mysql-query/mysql_query.py --config $CONFIG \
  "SELECT COUNT(*) as before_count FROM {menu_table} WHERE {perms_col} LIKE 'new_module:%';"

# 2. 执行初始化SQL ...

# 3. 执行后验证
python .trae/skills/mysql-query/mysql_query.py --config $CONFIG \
  "SELECT COUNT(*) as after_count FROM {menu_table} WHERE {perms_col} LIKE 'new_module:%';"

# 4. 如需回滚，执行生成的rollback SQL
```

---

## 6. 使用场景

### 场景A：全新模块上线（任意框架）

```
需求: 新增一个业务模块
↓
1. 设计表结构 → 用3.1节模板生成DDL
2. 有菜单系统? → 用3.2节生成菜单SQL
3. 有字典系统? → 用3.3节生成字典SQL
4. 准备初始数据 → 用3.5节模板
5. 生成回滚脚本 → 用rollback_generator.py
6. 验证 → 用mysql-query skill检查
```

### 场景B：模块迭代（增量更新）

```
需求: 给已有模块增加功能
↓
1. 查看现状 → mysql-query查看当前表结构和数据
2. 增量DDL → 用3.1.2节模板添加字段
3. 新增配置 → INSERT IGNORE新增菜单/字典
4. 数据迁移 → UPDATE设置合理默认值
5. 生成回滚 → rollback_generator.py
6. 测试验证 → 确保原有数据不受影响
```

### 场景C：数据清洗/修复

```
需求: 清理重复数据或修正错误
↓
1. 分析问题 → mysql-query查询定位问题数据
2. 备份受影响数据 → INSERT INTO backup SELECT ...
3. 编写清洗SQL → DELETE/UPDATE带精确WHERE
4. 事务执行 → START TRANSACTION ... COMMIT
5. 验证结果 → mysql-query确认正确
6. 保留恢复方案 → 从backup恢复的SQL
```

---

## 7. 最佳实践清单

### ✅ 必须遵守

- [ ] 所有INSERT使用`INSERT IGNORE`或`ON DUPLICATE KEY UPDATE`
- [ ] DDL操作标注是否清除数据的警告
- [ ] SQL文件头部包含作者、日期、说明
- [ ] 每个init SQL对应一个rollback SQL
- [ ] init和rollback成对放在同一目录

### ❌ 必须避免

- [ ] 直接使用非幂等`INSERT INTO`
- [ ] 不带条件的`DELETE`/`UPDATE`
- [ ] 在DDL中使用硬编码的自增ID
- [ ] 忘记考虑无菜单/无字典的项目场景

### 💡 推荐做法

- [ ] 文件命名: `upgrade_{module}.sql` + `rollback_{module}.sql`
- [ ] 大型模块拆分为多个小SQL文件
- [ ] 关键操作放在事务中
- [ ] 通过规则文件定制项目特定规范

---

## 8. 快速参考卡

### 幂等INSERT速查

| 场景 | 写法 | 去重依据 |
|------|------|----------|
| 菜单/权限 | `INSERT IGNORE INTO {menu_table}` | 主键/唯一索引 |
| 字典数据 | `INSERT IGNORE INTO {dict_data_table}` | type+value联合 |
| 角色关联 | `INSERT IGNORE INTO {role_menu_table}` | role+menu联合 |
| 业务配置 | `INSERT ... ON DUPLICATE KEY UPDATE` | 自定义唯一键 |

### 回滚速查

| 正向SQL | 回滚SQL |
|----------|----------|
| `INSERT IGNORE INTO t(name)VALUES('X')` | `DELETE FROM t WHERE name='X' AND create_by='{system}'` |
| `INSERT IGNORE INTO dt(type,val)VALUES('T','0')` | `DELETE FROM dt WHERE type='T' AND create_by='{system}'` |
| `ALTER TABLE t ADD col INT` | `ALTER TABLE t DROP COLUMN col` |
| `CREATE TABLE t(...)` | `DROP TABLE IF EXISTS t` |

---

## 附录：当前项目连接信息（自动检测）

> 当检测到特定项目时，以下信息会自动应用。

**RuoYi-Vue-v3.9.2 项目当前状态**:
- 配置来源: `ruoyi-admin/src/main/resources/application-druid.yml`
- 数据库: ry392 @ 192.168.0.69:3306
- 表前缀: `portal_` (业务表), `sys_` (系统表)
- 菜单表: `sys_menu` (三级结构 M>C>F)
- 字典表: `sys_dict_type` / `sys_dict_data`
- 角色表: `sys_role` / `sys_role_menu`
- 系统用户: `admin`

**已知模块**:
- portal (门户): 文章、分类、轮播图、企业信息
- crm (客户管理): 客户、标签、留言、跟进、模板

---

*版本: 2.0.0*
*作者: AI Assistant / 王有政*
*更新: 2026-04-13*

### 变更历史
- v2.0.0 (2026-04-13): 升级为全局通用版本，支持项目规范适配层
- v1.0.0 (2026-04-12): 初始版本（RuoYi专用）
