#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
MySQL Query Tool - MySQL数据库查询工具
支持从项目配置文件自动检测数据库连接参数
支持执行SQL文件和Python脚本

使用方式:
  # 查询
  python mysql_query.py "SHOW TABLES;"
  python mysql_query.py "SELECT * FROM portal_article LIMIT 5"
  python mysql_query.py --config application-druid.yml "DESCRIBE sys_menu"

  # 执行SQL文件（支持多SQL）
  python mysql_query.py --file sql_file.sql

  # 执行Python脚本（支持复杂SQL操作）
  python mysql_query.py --python exec_script.py

作者: 王有政
日期: 2026-04-16
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
        执行单条SQL语句

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

    def execute_file(self, file_path, limit=100):
        """
        执行SQL文件（支持多SQL语句，用分号分隔）

        Args:
            file_path: SQL文件路径
            limit: SELECT语句的LIMIT值
        Returns:
            执行结果列表
        """
        if not os.path.exists(file_path):
            print(f"文件不存在: {file_path}")
            return None

        with open(file_path, 'r', encoding='utf-8') as f:
            content = f.read()

        if not self.connection or not self.connection.open:
            if not self.connect():
                return None

        results = []
        statements = self._split_sql_statements(content)

        print(f"开始执行文件: {file_path}")
        print(f"共 {len(statements)} 条SQL语句\n")

        for i, sql in enumerate(statements, 1):
            sql = sql.strip()
            if not sql or sql.startswith('--'):
                continue

            sql_upper = sql.upper()

            if sql_upper.startswith(('SELECT', 'SHOW', 'DESC', 'EXPLAIN')):
                if 'LIMIT' not in sql_upper:
                    sql = f"{sql.rstrip(';')} LIMIT {limit};"
                cursor = self.connection.cursor()
                try:
                    cursor.execute(sql)
                    result = cursor.fetchall()
                    print(f"[{i}/{len(statements)}] 查询成功: {len(result)} 行")
                    results.append(result)
                except Exception as e:
                    print(f"[{i}/{len(statements)}] 执行错误: {e}")
                    results.append(None)
                finally:
                    cursor.close()
            else:
                cursor = self.connection.cursor()
                try:
                    cursor.execute(sql)
                    affected = cursor.rowcount
                    self.connection.commit()
                    print(f"[{i}/{len(statements)}] 执行成功: 影响 {affected} 行")
                    results.append(affected)
                except Exception as e:
                    print(f"[{i}/{len(statements)}] 执行错误: {e}")
                    self.connection.rollback()
                    results.append(None)
                finally:
                    cursor.close()

        print(f"\n文件执行完成! 成功执行 {sum(1 for r in results if r is not None)}/{len(statements)} 条SQL")
        return results

    def _split_sql_statements(self, content):
        """分割SQL语句（处理字符串内的分号）"""
        statements = []
        current = []
        in_string = False
        string_char = None

        for char in content:
            if char in ("'", '"') and not in_string:
                in_string = True
                string_char = char
            elif char == string_char and in_string:
                in_string = False
                string_char = None
            elif char == ';' and not in_string:
                stmt = ''.join(current).strip()
                if stmt:
                    statements.append(stmt)
                current = []
                continue
            current.append(char)

        last_stmt = ''.join(current).strip()
        if last_stmt:
            statements.append(last_stmt)

        return statements

    def get_connection_params(self):
        """获取连接参数（供Python脚本使用）"""
        return {
            'host': self.host,
            'port': self.port,
            'user': self.user,
            'password': self.password,
            'database': self.database,
            'charset': self.charset
        }


def load_config_from_yaml(config_path):
    """从YAML配置文件加载数据库连接参数"""
    if not os.path.exists(config_path):
        return None

    try:
        with open(config_path, 'r', encoding='utf-8') as f:
            config = yaml.safe_load(f)

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
    """自动检测数据库配置"""
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

    col_widths = {}
    for col in columns:
        header_len = len(str(col))
        max_val_len = max(len(str(row.get(col, ''))) for row in results)
        col_widths[col] = min(max(header_len, max_val_len) + 2, max_col_width)

    header = '|'.join(str(col).center(col_widths[col]) for col in columns)
    separator = '+'.join('-' * col_widths[col] for col in columns)

    print(separator)
    print(f"|{header}|")
    print(separator)

    for row in results:
        line = '|'.join(str(row.get(col, '')).center(col_widths[col]) for col in columns)
        print(f"|{line}|")

    print(separator)


def format_json(results):
    """JSON格式输出"""
    print(json.dumps(results, ensure_ascii=False, indent=2, default=str))


def execute_python_script(script_path, config):
    """
    执行Python脚本，注入数据库连接参数

    Args:
        script_path: Python脚本路径
        config: 数据库配置字典
    """
    if not os.path.exists(script_path):
        print(f"文件不存在: {script_path}")
        return None

    with open(script_path, 'r', encoding='utf-8') as f:
        script_code = f.read()

    local_namespace = {
        'DB_HOST': config.get('host'),
        'DB_PORT': config.get('port'),
        'DB_USER': config.get('user'),
        'DB_PASSWORD': config.get('password'),
        'DB_DATABASE': config.get('database'),
        'DB_CHARSET': config.get('charset', 'utf8mb4'),
        'pymysql': pymysql,
        'print': print
    }

    try:
        exec(script_code, local_namespace)
        return True
    except Exception as e:
        print(f"Python脚本执行错误: {e}")
        import traceback
        traceback.print_exc()
        return False


def main():
    parser = argparse.ArgumentParser(
        description='MySQL数据库查询工具',
        formatter_class=argparse.RawDescriptionHelpFormatter
    )

    parser.add_argument('sql', nargs='?', help='要执行的SQL语句')
    parser.add_argument('--file', '-f', help='从SQL文件执行（支持多SQL，用分号分隔）')
    parser.add_argument('--python', '-py', help='执行Python脚本（注入DB_*环境变量）')
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

    # 获取数据库配置
    config = None

    if args.host:
        config = {
            'host': args.host,
            'port': args.port or 3306,
            'user': args.user or 'root',
            'password': args.password or '',
            'database': args.db or 'test'
        }
    elif args.config:
        config = load_config_from_yaml(args.config)
        if not config:
            print(f"无法从配置文件加载: {args.config}")
            sys.exit(1)
    else:
        project_dir = args.project_dir or os.getcwd()
        config = auto_detect_config(project_dir)
        if not config:
            print("无法自动检测到数据库配置，请使用以下方式之一:")
            print("  1. 使用命令行参数: --host --port --user --password --db")
            print("  2. 指定配置文件: --config application-druid.yml")
            print("  3. 设置环境变量: MYSQL_HOST, MYSQL_PORT等")
            sys.exit(1)

    print(f"连接: {config['user']}@{config['host']}:{config['port']}/{config['database']}")
    print("-" * 60)

    # 创建工具实例
    tool = MySQLQueryTool(**config)

    try:
        # 执行Python脚本模式
        if args.python:
            print(f"执行Python脚本: {args.python}")
            print("-" * 60)
            execute_python_script(args.python, config)
            return

        # 执行SQL文件模式
        if args.file:
            print(f"执行SQL文件: {args.file}")
            print("-" * 60)
            tool.execute_file(args.file, limit=args.limit)
            return

        # 单条SQL模式
        sql = args.sql
        if not sql:
            parser.print_help()
            sys.exit(0)

        print(f"SQL: {sql[:100]}{'...' if len(sql) > 100 else ''}")
        print("-" * 60)

        if not tool.connect():
            sys.exit(1)

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
