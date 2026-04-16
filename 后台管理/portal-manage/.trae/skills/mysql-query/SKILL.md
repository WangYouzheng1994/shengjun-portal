---
name: "mysql-query"
description: "全局MySQL查询与SQL生成技能。用于查询任意MySQL数据库的表结构和数据，生成符合规范的SQL脚本（建表、菜单、权限、升级等）。当需要了解数据库现状、查看表结构/数据、生成SQL脚本时自动调用。适用于所有MySQL项目。"
---

# MySQL Query & SQL Generator (全局技能)

## 概述

这是一个**全局通用**的MySQL数据库查询与SQL生成技能，具备以下核心能力：

- **表结构查询**: 查看表定义、字段、索引，建表语句
- **数据查询**: 查询表中的实际数据（支持条件、统计、关联查询）
- **SQL执行**: 执行SQL文件（支持多SQL）和Python脚本（复杂操作）
- **SQL生成**: 生成标准化的DDL/DML脚本（建表、菜单、权限、升级等）
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
```

### 1.3 Python查询脚本

本Skill内置 **mysql_query.py** 脚本，无需安装MySQL客户端即可执行查询。

**依赖**: 需安装pymysql库
```bash
pip install pymysql
```

**脚本位置**: `.trae/skills/mysql-query/mysql_query.py`

---

## 2. 使用方式

### 2.1 查询操作

```bash
# 使用项目配置文件自动检测连接（推荐）
python .trae/skills/mysql-query/mysql_query.py "SHOW TABLES LIKE 'portal_%';"

# 手动指定连接参数
python .trae/skills/mysql-query/mysql_query.py --host 192.168.0.69 --port 3306 --user root --password 000000 --db ry392 "SELECT 1;"

# JSON格式输出
python .trae/skills/mysql-query/mysql_query.py --json "SELECT * FROM table LIMIT 5"

# 禁用自动LIMIT
python .trae/skills/mysql-query/mysql_query.py --no-limit "SELECT * FROM table"
```

### 2.2 执行SQL文件（多SQL支持）

```bash
# 执行SQL文件（支持多SQL语句，用分号分隔）
python .trae/skills/mysql-query/mysql_query.py --file sql/upgrade_portal_message_v2.sql
```

**特点**：
- 自动分割多SQL语句（处理字符串内的分号）
- 逐条执行并显示进度
- 统计执行结果

### 2.3 执行Python脚本（复杂操作）

```bash
# 执行Python脚本（注入DB_*环境变量）
python .trae/skills/mysql-query/mysql_query.py --python exec_script.py
```

**注入的环境变量**：
- `DB_HOST` - 数据库主机
- `DB_PORT` - 数据库端口
- `DB_USER` - 用户名
- `DB_PASSWORD` - 密码
- `DB_DATABASE` - 数据库名
- `DB_CHARSET` - 字符集

**Python脚本示例**：
```python
import pymysql

conn = pymysql.connect(
    host=DB_HOST,
    port=DB_PORT,
    user=DB_USER,
    password=DB_PASSWORD,
    database=DB_DATABASE,
    charset=DB_CHARSET
)
cursor = conn.cursor()

cursor.execute('''CREATE TABLE IF NOT EXISTS portal_message_template (
  template_id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT "模板ID",
  template_code VARCHAR(50) NOT NULL DEFAULT '' COMMENT "模板编码",
  ...
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT="消息模板表"''')
conn.commit()
print('消息模板表创建成功')

cursor.close()
conn.close()
print('所有SQL执行完成！')
```

---

## 3. 表结构查询

### 3.1 列出所有表

```bash
# 列出所有表
python .trae/skills/mysql-query/mysql_query.py "SHOW TABLES LIKE 'portal_%';"
```

### 3.2 查看表结构详情

```bash
# 查看字段定义
python .trae/skills/mysql-query/mysql_query.py "DESCRIBE portal_article;"

# 查看完整建表语句
python .trae/skills/mysql-query/mysql_query.py "SHOW CREATE TABLE portal_article;"
```

---

## 4. SQL生成模板

### 4.1 建表模板（通用版）

```sql
CREATE TABLE `{table_name}` (
  `{pk_column}`       BIGINT(20)      NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `field_name`       VARCHAR(200)    NOT NULL DEFAULT ''     COMMENT '字段说明',
  `del_flag`         CHAR(1)         NOT NULL DEFAULT '0'   COMMENT '删除标志',
  `create_by`        VARCHAR(64)     DEFAULT ''             COMMENT '创建者',
  `create_time`      DATETIME        DEFAULT NULL           COMMENT '创建时间',
  `update_by`        VARCHAR(64)     DEFAULT ''             COMMENT '更新者',
  `update_time`      DATETIME        DEFAULT NULL           COMMENT '更新时间',
  `remark`           VARCHAR(500)    DEFAULT NULL           COMMENT '备注',
  PRIMARY KEY (`{pk_column}`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='{table_comment}';
```

### 4.2 菜单SQL生成模板

```sql
-- 一级目录
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time, remark)
SELECT "菜单名称", 0, 1, "path", NULL, "M", "0", "0", "", "icon", "admin", NOW(), "备注"
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = "菜单名称" AND parent_id = 0);

-- 二级菜单
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, route_name, create_by, create_time, remark)
SELECT "菜单名称", (SELECT menu_id FROM sys_menu WHERE menu_name = "父菜单" LIMIT 1), 1, "index", "module/page/index", "C", "0", "0", "module:page:list", "#", "PageName", "admin", NOW(), "备注"
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = "菜单名称");

-- 按钮权限
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT "操作名称", (SELECT menu_id FROM sys_menu WHERE menu_name = "菜单" LIMIT 1), 1, "#", "", "F", "0", "0", "module:page:action", "#", "admin", NOW()
FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE menu_name = "操作名称");
```

---

## 5. 最佳实践

### 5.1 简单查询 → 直接SQL

```bash
python .trae/skills/mysql-query/mysql_query.py "SELECT COUNT(*) FROM sys_menu WHERE del_flag='0';"
```

### 5.2 多SQL升级脚本 → --file

```bash
python .trae/skills/mysql-query/mysql_query.py --file sql/upgrade_portal_message_v2.sql
```

### 5.3 复杂幂等操作（如需要变量、条件判断）→ --python

```bash
python .trae/skills/mysql-query/mysql_query.py --python exec_complex_sql.py
```

---

## 6. 常见问题排查

| 问题 | 可能原因 | 解决方法 |
|------|----------|----------|
| 连接失败 | 密码/端口/防火墙 | 检查连接参数和网络 |
| 中文乱码 | 字符集不一致 | 确保连接和表都是utf8mb4 |
| SQL执行失败 | 语法错误 | 检查SQL语句 |
| 多SQL部分失败 | 依赖的表/数据不存在 | 使用Python脚本模式 |

---

*版本: 1.2.0*
*作者: 王有政*
*更新: 2026-04-16*

### 变更历史
- v1.2.0 (2026-04-16): 新增 `--python` 参数支持执行Python脚本，新增 `--file` 多SQL支持
- v1.1.0 (2026-04-12): 新增Python查询脚本(mysql_query.py)，无需MySQL客户端
- v1.0.0 (2026-04-12): 初始版本，纯文档模式
