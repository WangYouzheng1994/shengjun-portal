#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
MySQL Query Tool - MySQL数据库查询工具
支持从项目配置文件自动检测数据库连接参数

使用方式:
  python mysql_query.py "SHOW TABLES;"
  python mysql_query.py "SELECT * FROM portal_article LIMIT 5"
  python mysql_query.py --file sql_file.sql
  python mysql_query.py --config application-druid.yml "DESCRIBE sys_menu"

作者: 王有政
日期: 2026-04-12
"""

import argparse
import json
import os
import re
import sys
import yaml
from datetime import datetime

try:
    import pymysql
    from pymysql.cursors import DictCursor
except ImportError:
    print("错误: 需要安装pymysql库")
    print("请执行: pip install pymysql")
    sys.exit(1)


class MySQLQueryTool:
    """MySQL查询工具类"""

    def __init__(self, host='localhost', port=3306, user='root',
                 password='', database='test', charset='utf8mb4'):
        self.host = host
        self.port = port
        self.user = user
        self.password = password
        self.database = database
        self.charset = charset
        self.connection = None

    def connect(self):
        """建立数据库连接"""
        try:
            self.connection = pymysql.connect(
                host=self.host,
                port=self.port,
                user=self.user,
                password=self.password,
                database=self.database,
                charset=self.charset,
                cursorclass=DictCursor,
                connect_timeout=10
            )
            return True
        except Exception as e:
            print(f"连接失败: {e}")
            return False

    def close(self):
        """关闭数据库连接"""
        if self.connection and self.connection.open:
            self.connection.close()

    def execute(self, sql, fetch=True, limit=None):
        """
        执行SQL语句

        Args:
            sql: SQL语句
            fetch: 是否获取结果
            limit: 自动添加LIMIT限制（用于保护）
        Returns:
            查询结果或受影响行数
        """
        if not self.connection or not self.connection.open:
            if not self.connect():
                return None

        cursor = self.connection.cursor()
        try:
            sql_upper = sql.strip().upper()

            # 对于SELECT语句，自动添加LIMIT保护
            if fetch and sql_upper.startswith('SELECT') and 'LIMIT' not in sql_upper:
                if limit:
                    sql = f"{sql.rstrip(';')} LIMIT {limit};"

            start_time = datetime.now()
            cursor.execute(sql)
            elapsed = (datetime.now() - start_time).total_seconds() * 1000

            if fetch and sql_upper.startswith(('SELECT', 'SHOW', 'DESC', 'EXPLAIN')):
                results = cursor.fetchall()
                print(f"\n查询成功! 耗时: {elapsed:.2f}ms | 返回 {len(results)} 行\n")
                return results
            else:
                affected = cursor.rowcount
                self.connection.commit()
                print(f"执行成功! 耗时: {elapsed:.2f}ms | 影响 {affected} 行\n")
                return affected

        except Exception as e:
            print(f"执行错误: {e}")
            if self.connection:
                self.connection.rollback()
            return None
        finally:
            cursor.close()


def load_config_from_yaml(config_path):
    """
    从YAML配置文件加载数据库连接参数

    支持的格式:
    - Spring Boot application.yml / application-druid.yml (Druid数据源)
    - 通用YAML格式
    """
    if not os.path.exists(config_path):
        return None

    try:
        with open(config_path, 'r', encoding='utf-8') as f:
            config = yaml.safe_load(f)

        # Spring Boot Druid格式
        if 'spring' in config and 'datasource' in config['spring']:
            ds = config['spring']['datasource']
            if 'druid' in ds and 'master' in ds['druid']:
                master = ds['druid']['master']
                url = master.get('url', '')
                match = re.search(r'mysql://([^:]+):(\d+)/(\w+)', url)
                if match:
                    return {
                        'host': match.group(1),
                        'port': int(match.group(2)),
                        'database': match.group(3),
                        'user': master.get('username', 'root'),
                        'password': master.get('password', '')
                    }

        # 直接格式
        if 'database' in config:
            db_config = config['database']
            return {
                'host': db_config.get('host', 'localhost'),
                'port': int(db_config.get('port', 3306)),
                'database': db_config.get('name', 'test'),
                'user': db_config.get('user', 'root'),
                'password': db_config.get('password', '')
            }

        return None
    except Exception as e:
        print(f"解析配置文件失败: {e}")
        return None


def load_config_from_env():
    """从环境变量加载配置"""
    env_mapping = [
        ('MYSQL_HOST', 'DB_HOST', 'host', 'localhost'),
        ('MYSQL_PORT', 'DB_PORT', 'port', 3306),
        ('MYSQL_USER', 'DB_USER', 'user', 'root'),
        ('MYSQL_PASSWORD', 'DB_PASSWORD', 'password', ''),
        ('MYSQL_DATABASE', 'DB_DATABASE', 'database', 'test')
    ]

    config = {}
    for env1, env2, key, default in env_mapping:
        value = os.environ.get(env1) or os.environ.get(env2) or default
        if key == 'port':
            value = int(value) if value else default
        config[key] = value

    return config


def auto_detect_config(project_dir=None):
    """
    自动检测数据库配置

    检测顺序:
    1. 项目目录下的Spring Boot配置文件
    2. 环境变量
    3. 默认值
    """
    config_files = [
        'application-druid.yml',
        'application.yml',
        'application-druid.yaml',
        'application.yaml',
        '.env'
    ]

    if project_dir:
        for cf in config_files:
            config_path = os.path.join(project_dir, cf)
            if os.path.exists(config_path):
                if cf.endswith('.yml') or cf.endswith('.yaml'):
                    result = load_config_from_yaml(config_path)
                    if result:
                        print(f"已从配置文件加载: {cf}")
                        return result
                elif cf == '.env':
                    result = load_config_from_env()
                    if any(os.environ.get(k) for k in ['MYSQL_HOST', 'DB_HOST']):
                        print(f"已从环境文件加载: {cf}")
                        return result

    # 尝试环境变量
    env_config = load_config_from_env()
    if env_config.get('host') != 'localhost' or env_config.get('password'):
        print("已从环境变量加载配置")
        return env_config

    return None


def format_table(results, max_col_width=50):
    """格式化表格输出"""
    if not results:
        print("(空结果集)")
        return

    columns = list(results[0].keys())

    # 计算每列宽度
    col_widths = {}
    for col in columns:
        header_len = len(str(col))
        max_val_len = max(len(str(row.get(col, ''))) for row in results)
        col_widths[col] = min(max(header_len, max_val_len) + 2, max_col_width)

    # 打印表头
    header = '|'.join(str(col).center(col_widths[col]) for col in columns)
    separator = '+'.join('-' * col_widths[col] for col in columns)

    print(separator)
    print(f"|{header}|")
    print(separator)

    # 打印数据行
    for row in results:
        line = '|'.join(str(row.get(col, '')).center(col_widths[col]) for col in columns)
        print(f"|{line}|")

    print(separator)


def format_json(results):
    """JSON格式输出"""
    print(json.dumps(results, ensure_ascii=False, indent=2, default=str))


def main():
    parser = argparse.ArgumentParser(
        description='MySQL数据库查询工具',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
示例:
  %(prog)s "SHOW TABLES LIKE 'portal_%';"
  %(prog)s "DESCRIBE portal_article;"
  %(prog)s "SELECT COUNT(*) FROM sys_menu WHERE parent_id = 0;"
  %(prog)s --json "SELECT * FROM portal_banner LIMIT 3;"
  %(prog)s --file query.sql
  %(prog)s --host 192.168.0.69 --db ry392 "SELECT 1;"
        """
    )

    parser.add_argument('sql', nargs='?', help='要执行的SQL语句')
    parser.add_argument('--file', '-f', help='从文件执行SQL')
    parser.add_argument('--host', '-H', help='数据库主机地址')
    parser.add_argument('--port', '-P', type=int, help='数据库端口')
    parser.add_argument('--user', '-u', help='用户名')
    parser.add_argument('--password', '-p', help='密码')
    parser.add_argument('--db', '-d', help='数据库名')
    parser.add_argument('--config', '-c', help='指定配置文件路径')
    parser.add_argument('--project-dir', help='项目目录路径（用于自动检测配置）')
    parser.add_argument('--json', '-j', action='store_true', help='JSON格式输出')
    parser.add_argument('--limit', '-l', type=int, default=100, help='SELECT语句自动LIMIT（默认100）')
    parser.add_argument('--no-limit', action='store_true', help='禁用自动LIMIT')

    args = parser.parse_args()

    # 获取SQL语句
    sql = args.sql
    if args.file:
        if not os.path.exists(args.file):
            print(f"文件不存在: {args.file}")
            sys.exit(1)
        with open(args.file, 'r', encoding='utf-8') as f:
            sql = f.read().strip()

    if not sql:
        parser.print_help()
        sys.exit(0)

    # 获取数据库配置
    config = None

    # 命令行参数优先
    if args.host:
        config = {
            'host': args.host,
            'port': args.port or 3306,
            'user': args.user or 'root',
            'password': args.password or '',
            'database': args.db or 'test'
        }
    # 指定配置文件
    elif args.config:
        config = load_config_from_yaml(args.config)
        if not config:
            print(f"无法从配置文件加载: {args.config}")
            sys.exit(1)
    # 自动检测
    else:
        project_dir = args.project_dir or os.getcwd()
        config = auto_detect_config(project_dir)
        if not config:
            print("无法自动检测到数据库配置，请使用以下方式之一:")
            print("  1. 使用命令行参数: --host --port --user --password --db")
            print("  2. 指定配置文件: --config application-druid.yml")
            print("  3. 设置环境变量: MYSQL_HOST, MYSQL_PORT等")
            sys.exit(1)

    # 创建工具实例
    tool = MySQLQueryTool(**config)

    # 显示连接信息
    print(f"连接: {config['user']}@{config['host']}:{config['port']}/{config['database']}")
    print(f"SQL: {sql[:100]}{'...' if len(sql) > 100 else ''}")
    print("-" * 60)

    # 连接并执行
    if not tool.connect():
        sys.exit(1)

    try:
        limit = None if args.no_limit else args.limit
        is_select = sql.strip().upper().startswith(('SELECT', 'SHOW', 'DESC', 'EXPLAIN'))
        results = tool.execute(sql, fetch=is_select, limit=limit)

        if results is not None and is_select:
            if args.json:
                format_json(results)
            else:
                format_table(results)
    finally:
        tool.close()


if __name__ == '__main__':
    main()
