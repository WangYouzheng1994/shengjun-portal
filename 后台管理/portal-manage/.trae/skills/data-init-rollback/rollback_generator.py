#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Rollback SQL Generator - 回滚SQL自动生成工具

根据幂等初始化SQL文件，自动生成对应的回滚SQL脚本。
支持从数据库查询当前状态来生成精确的回滚语句。

使用方式:
  # 从SQL文件生成回滚脚本
  python rollback_generator.py --input upgrade_xxx.sql --output rollback_xxx.sql

  # 连接数据库查询后生成精确回滚脚本
  python rollback_generator.py --input upgrade_xxx.sql --config application-druid.yml --output rollback_xxx.sql

  # 查看生成的回滚SQL（不写文件）
  python rollback_generator.py --input upgrade_xxx.sql --dry-run

作者: 王有政
日期: 2026-04-12
"""

import argparse
import os
import re
import sys
from datetime import datetime

try:
    import yaml
except ImportError:
    yaml = None


class RollbackGenerator:
    """回滚SQL生成器（全局通用版）"""

    def __init__(self, input_file, config_path=None,
                 menu_table='sys_menu', menu_pk='menu_id',
                 menu_name_col='menu_name', menu_parent_col='parent_id',
                 dict_type_table='sys_dict_type', dict_data_table='sys_dict_data',
                 role_table='sys_role', role_menu_table='sys_role_menu',
                 role_key_col='role_key', role_pk='role_id',
                 system_user='admin'):
        self.input_file = input_file
        self.config_path = config_path
        # 可配置的表名和列名
        self.menu_table = menu_table
        self.menu_pk = menu_pk
        self.menu_name_col = menu_name_col
        self.menu_parent_col = menu_parent_col
        self.dict_type_table = dict_type_table
        self.dict_data_table = dict_data_table
        self.role_table = role_table
        self.role_menu_table = role_menu_table
        self.role_key_col = role_key_col
        self.role_pk = role_pk
        self.system_user = system_user
        # 解析结果
        self.statements = []
        self.rollback_statements = []
        self.module_name = ''
        self.menu_names = []
        self.dict_types = []
        self.table_names = []
        self.role_keys = []

    def parse_input(self):
        """解析输入的SQL文件"""
        if not os.path.exists(self.input_file):
            print(f"错误: 文件不存在 - {self.input_file}")
            return False

        with open(self.input_file, 'r', encoding='utf-8') as f:
            content = f.read()

        # 提取模块名（从文件名或注释中）
        basename = os.path.basename(self.input_file)
        if 'upgrade_' in basename:
            self.module_name = basename.replace('upgrade_', '').replace('.sql', '')
        elif 'init' in basename:
            self.module_name = basename.replace('_init', '').replace('.sql', '')
        else:
            self.module_name = basename.replace('.sql', '')

        # 解析SQL语句（简单分割）
        self._parse_sql_content(content)

        return True

    def _parse_sql_content(self, content):
        """解析SQL内容，提取各类语句"""
        lines = content.split('\n')
        current_stmt = []
        in_block_comment = False

        for line in lines:
            stripped = line.strip()

            # 处理块注释
            if stripped.startswith('/*'):
                in_block_comment = True
                continue
            if in_block_comment:
                if '*/' in stripped:
                    in_block_comment = False
                continue

            # 跳过单行注释
            if stripped.startswith('--') or not stripped:
                if current_stmt:
                    stmt = ' '.join(current_stmt)
                    self._categorize_statement(stmt)
                    current_stmt = []
                continue

            current_stmt.append(stripped)

            # 语句结束标志
            if stripped.endswith(';') or stripped.endswith("';"):
                stmt = ' '.join(current_stmt)
                self._categorize_statement(stmt)
                current_stmt = []

        # 处理最后未结束的语句
        if current_stmt:
            stmt = ' '.join(current_stmt)
            self._categorize_statement(stmt)

    def _categorize_statement(self, stmt):
        """分类并记录SQL语句"""
        upper = stmt.upper()

        if upper.startswith('CREATE TABLE'):
            match = re.search(r'CREATE TABLE (?:IF NOT EXISTS )?`?(\w+)`?', stmt, re.IGNORECASE)
            if match:
                self.table_names.append(match.group(1))
            self.statements.append(('CREATE_TABLE', stmt))

        elif upper.startswith('DROP TABLE'):
            self.statements.append(('DROP_TABLE', stmt))

        elif upper.startswith('ALTER TABLE'):
            self.statements.append(('ALTER_TABLE', stmt))

        elif 'INSERT IGNORE INTO' in upper or 'INSERT INTO' in upper:
            self._extract_insert_info(stmt)
            self.statements.append(('INSERT', stmt))

        elif upper.startswith('UPDATE'):
            self.statements.append(('UPDATE', stmt))

        elif upper.startswith('DELETE'):
            self.statements.append(('DELETE', stmt))

    def _extract_insert_info(self, stmt):
        """从INSERT语句中提取关键信息"""
        upper = stmt.upper()

        # 提取表名
        table_match = re.search(r'INSERT\s+(?:IGNORE\s+)?INTO\s+`?(\w+)`?', upper)
        if table_match:
            table_name = table_match.group(1).lower()

            if table_name == 'sys_menu':
                self._extract_menu_info(stmt)
            elif table_name == 'sys_dict_type':
                self._extract_dict_type_info(stmt)
            elif table_name == 'sys_dict_data':
                pass  # dict_data跟随dict_type
            elif table_name == 'sys_role_menu':
                self._extract_role_menu_info(stmt)
            else:
                # 业务表
                if table_name not in self.table_names:
                    self.table_names.append(table_name)

    def _extract_menu_info(self, stmt):
        """提取菜单信息"""
        # 提取menu_name值
        matches = re.findall(r"'([^']*)'", stmt)
        if len(matches) >= 1:
            menu_name = matches[0]
            if menu_name and menu_name not in self.menu_names:
                self.menu_names.append(menu_name)

    def _extract_dict_type_info(self, stmt):
        """提取字典类型信息"""
        matches = re.findall(r"'([^']*)'", stmt)
        if len(matches) >= 2:
            dict_type = matches[1]  # 第二个通常是dict_type
            if dict_type and dict_type not in self.dict_types:
                self.dict_types.append(dict_type)

    def _extract_role_menu_info(self, stmt):
        """提取角色信息"""
        role_match = re.search(r"role_key\s*=\s*'([^']+)'", stmt, re.IGNORECASE)
        if role_match:
            role_key = role_match.group(1)
            if role_key and role_key not in self.role_keys:
                self.role_keys.append(role_key)

    def generate_rollback(self):
        """生成回滚SQL"""
        now = datetime.now().strftime('%Y-%m-%d %H:%M:%S')

        rollback_lines = [
            '-- ----------------------------',
            f'-- 回滚脚本：{self.module_name}',
            f'-- 对应初始化文件：{os.path.basename(self.input_file)}',
            '-- 作者：王有政',
            f'-- 日期：{now}',
            '-- 说明：此脚本将撤销上述初始化SQL的所有变更',
            '       [警告] 执行前请确认没有重要的业务数据依赖这些变更',
            '-- ----------------------------',
            '',
            'START TRANSACTION;',
            '',
        ]

        # 1. 删除新增的业务初始数据（create_by=system_user的数据）
        for table in self.table_names:
            skip_tables = [self.menu_table, self.dict_type_table, self.dict_data_table,
                          self.role_table, self.role_menu_table]
            if table not in skip_tables:
                rollback_lines.extend([
                    f'-- [Rollback] Delete system preset data from {table}',
                    f"DELETE FROM `{table}` WHERE `create_by` = '{self.system_user}';",
                    '',
                ])

        # 2. 删除新增的菜单及子菜单（级联）
        if self.menu_names:
            rollback_lines.append(f'-- [Rollback] Delete new menus from {self.menu_table}')
            rollback_lines.append('-- Note: cascades to all sub-menus and buttons')
            for menu_name in self.menu_names:
                rollback_lines.append(
                    f"DELETE FROM `{self.menu_table}` "
                    f"WHERE `{self.menu_name_col}` = '{menu_name}' "
                    f"AND `{self.menu_parent_col}` = 0;"
                )
            rollback_lines.append('')

        # 3. 删除字典数据
        if self.dict_types:
            rollback_lines.append(f'-- [Rollback] Delete dict data from {self.dict_data_table}')
            for dt in self.dict_types:
                rollback_lines.append(
                    f"DELETE FROM `{self.dict_data_table}` WHERE `dict_type` = '{dt}';"
                )
            rollback_lines.append(f'-- [Rollback] Delete dict types from {self.dict_type_table}')
            for dt in self.dict_types:
                rollback_lines.append(
                    f"DELETE FROM `{self.dict_type_table}` WHERE `dict_type` = '{dt}';"
                )
            rollback_lines.append('')

        # 4. 清理无效的角色菜单关联
        if self.role_keys:
            rollback_lines.append(f'-- [Rollback] Cleanup stale role-menu relations in {self.role_menu_table}')
            for rk in self.role_keys:
                rollback_lines.append(
                    f"DELETE FROM `{self.role_menu_table}` "
                    f"WHERE `{self.role_pk}` = "
                    f"(SELECT `{self.role_pk}` FROM `{self.role_table}` "
                    f"WHERE `{self.role_key_col}` = '{rk}') "
                    f"AND `menu_id` NOT IN (SELECT `{self.menu_pk}` FROM `{self.menu_table}`);"
                )
            rollback_lines.append('')

        # 5. DROP新建的表（仅对DROP+CREATE模式的表）
        business_tables = [t for t in self.table_names
                         if t not in [self.menu_table, self.dict_type_table,
                                     self.dict_data_table, self.role_table,
                                     self.role_menu_table]]
        if business_tables:
            rollback_lines.append('-- [Rollback] Drop newly created tables (if any)')
            rollback_lines.append('-- [WARNING] This will permanently delete all data!')
            rollback_lines.append('-- Comment out below if tables have business data')
            for table in business_tables:
                rollback_lines.append(f"-- DROP TABLE IF EXISTS `{table}`;")
            rollback_lines.append('')

        # 结束事务
        rollback_lines.extend([
            '',
            '-- 验证回滚结果（可选）',
            '-- SELECT COUNT(*) AS remaining_records FROM ...',
            '',
            'COMMIT;',
            '',
            '-- ----------------------------',
            '-- 回滚脚本结束',
            '-- 如需撤销本次回滚，请重新执行对应的初始化SQL',
            '-- ----------------------------',
        ])

        self.rollback_statements = rollback_lines
        return '\n'.join(rollback_lines)

    def save_output(self, output_file=None):
        """保存回滚SQL到文件"""
        if output_file is None:
            input_basename = os.path.basename(self.input_file)
            if input_basename.startswith('upgrade_'):
                output_file = 'rollback_' + input_basename.replace('upgrade_', '', 1)
            else:
                output_file = 'rollback_' + input_basename

        with open(output_file, 'w', encoding='utf-8') as f:
            f.write('\n'.join(self.rollback_statements))

        return output_file


def load_config(config_path):
    """加载配置文件获取数据库连接参数"""
    if not yaml:
        print("警告: 未安装yaml库，无法读取配置文件")
        return None

    if not os.path.exists(config_path):
        print(f"错误: 配置文件不存在 - {config_path}")
        return None

    try:
        with open(config_path, 'r', encoding='utf-8') as f:
            config = yaml.safe_load(f)

        if 'spring' in config and 'datasource' in config['spring']:
            ds = config['spring']['datasource']
            if 'druid' in ds and 'master' in ds['druid']:
                master = ds['druid']['master']
                url = master.get('url', '')
                import re
                match = re.search(r'mysql://([^:]+):(\d+)/(\w+)', url)
                if match:
                    return {
                        'host': match.group(1),
                        'port': int(match.group(2)),
                        'database': match.group(3),
                        'user': master.get('username', 'root'),
                        'password': master.get('password', '')
                    }
        return None
    except Exception as e:
        print(f"解析配置文件失败: {e}")
        return None


def main():
    parser = argparse.ArgumentParser(
        description='Rollback SQL Generator (Global) - 回滚SQL自动生成工具（全局通用版）',
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s --input sql/upgrade_xxx.sql
  %(prog)s --input sql/upgrade_xxx.sql --output sql/rollback_xxx.sql
  %(prog)s --input sql/upgrade_xxx.sql --dry-run
  %(prog)s --input sql/upgrade_xxx.sql --menu-table custom_menus --system-user system

Global options (override defaults for non-RuoYi projects):
  --menu-table       Menu table name (default: sys_menu)
  --dict-type-table  Dict type table name (default: sys_dict_type)
  --system-user      System user marker for preset data (default: admin)
        """
    )

    parser.add_argument('--input', '-i', required=True, help='Input init SQL file path')
    parser.add_argument('--output', '-o', help='Output rollback SQL file path (auto-generated if omitted)')
    parser.add_argument('--config', '-c', help='Database config file path (for DB validation)')
    parser.add_argument('--dry-run', action='store_true', help='Show result only, do not write file')

    # Global configuration overrides
    parser.add_argument('--menu-table', default='sys_menu',
                        help='Menu table name (default: sys_menu)')
    parser.add_argument('--menu-pk', default='menu_id',
                        help='Menu primary key column (default: menu_id)')
    parser.add_argument('--menu-name-col', default='menu_name',
                        help='Menu name column (default: menu_name)')
    parser.add_argument('--menu-parent-col', default='parent_id',
                        help='Menu parent column (default: parent_id)')
    parser.add_argument('--dict-type-table', default='sys_dict_type',
                        help='Dict type table name (default: sys_dict_type)')
    parser.add_argument('--dict-data-table', default='sys_dict_data',
                        help='Dict data table name (default: sys_dict_data)')
    parser.add_argument('--role-table', default='sys_role',
                        help='Role table name (default: sys_role)')
    parser.add_argument('--role-menu-table', default='sys_role_menu',
                        help='Role-menu relation table (default: sys_role_menu)')
    parser.add_argument('--role-key-col', default='role_key',
                        help='Role key column (default: role_key)')
    parser.add_argument('--role-pk', default='role_id',
                        help='Role primary key column (default: role_id)')
    parser.add_argument('--system-user', default='admin',
                        help='System user marker for preset data (default: admin)')

    args = parser.parse_args()

    generator = RollbackGenerator(
        input_file=args.input,
        config_path=args.config,
        menu_table=args.menu_table,
        menu_pk=args.menu_pk,
        menu_name_col=args.menu_name_col,
        menu_parent_col=args.menu_parent_col,
        dict_type_table=args.dict_type_table,
        dict_data_table=args.dict_data_table,
        role_table=args.role_table,
        role_menu_table=args.role_menu_table,
        role_key_col=args.role_key_col,
        role_pk=args.role_pk,
        system_user=args.system_user
    )

    print(f"Parsing: {args.input}")

    if not generator.parse_input():
        sys.exit(1)

    print(f"Module: {generator.module_name}")
    print(f"Menus found: {generator.menu_names}")
    print(f"Dict types: {generator.dict_types}")
    print(f"Tables: {generator.table_names}")
    print(f"Roles: {generator.role_keys}")
    print(f"Config: menu_table={args.menu_table}, system_user={args.system_user}")
    print()

    print("Generating rollback SQL...")
    rollback_sql = generator.generate_rollback()

    if args.dry_run:
        print("=" * 60)
        print(rollback_sql)
        print("=" * 60)
    else:
        output_file = generator.save_output(args.output)
        print(f"\n[OK] Rollback SQL generated: {output_file}")
        print(f"  Size: {len(rollback_sql)} chars")
        print(f"  Lines: ~{rollback_sql.count(chr(10))}")


if __name__ == '__main__':
    main()
