/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.200
 Source Server Type    : MySQL
 Source Server Version : 50646
 Source Host           : 192.168.1.200:3307
 Source Schema         : rbac

 Target Server Type    : MySQL
 Target Server Version : 50646
 File Encoding         : 65001

 Date: 04/01/2021 14:29:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
                           `id` bigint(20) NOT NULL,
                           `name` varchar(255) DEFAULT NULL COMMENT '文件名',
                           `hash` varchar(255) DEFAULT NULL COMMENT '文件hash值',
                           `content_type` varchar(20) DEFAULT NULL COMMENT '网络类型',
                           `suffix` varchar(10) DEFAULT NULL COMMENT '后缀名',
                           `visit` varchar(1) DEFAULT NULL COMMENT '是否公开 0 公开 1 私有',
                           `join_table` varchar(25) DEFAULT NULL COMMENT '关联表明',
                           `join_id` bigint(20) DEFAULT NULL COMMENT '关联id',
                           `del_flag` varchar(1) DEFAULT NULL,
                           `create_by` bigint(20) DEFAULT NULL,
                           `update_by` bigint(20) DEFAULT NULL,
                           `create_date` datetime DEFAULT NULL,
                           `update_date` datetime DEFAULT NULL,
                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_acl`;
CREATE TABLE `sys_acl` (
                         `id` bigint(20) NOT NULL,
                         `name` varchar(25) NOT NULL COMMENT '权限名称',
                         `parent_id` bigint(20) DEFAULT '0' COMMENT '父级id',
                         `type` int(2) NOT NULL COMMENT '1:菜单,2:按钮,3:其他',
                         `status` int(1) NOT NULL DEFAULT '0' COMMENT '0:正常,1:冻结',
                         `path` varchar(255) DEFAULT NULL COMMENT '前台资源路径',
                         `icon` varchar(32) DEFAULT NULL COMMENT '图标',
                         `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
                         `url` varchar(255) DEFAULT NULL COMMENT '后台资源路径,请求的url可以填写ant表达式',
                         `method` varchar(20) DEFAULT NULL COMMENT '请求类型 get post put delete',
                         `permission` varchar(255) NOT NULL COMMENT '资源权限标识',
                         `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                         `seq` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
                         `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
                         `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         `create_by` bigint(20) DEFAULT NULL,
                         `update_by` bigint(20) DEFAULT NULL,
                         `del_flag` varchar(1) DEFAULT NULL,
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
                          `id` bigint(20) NOT NULL COMMENT '部门主键',
                          `name` varchar(20) NOT NULL COMMENT '部门名称',
                          `parent_id` bigint(20) NOT NULL COMMENT '部门父级id',
                          `seq` int(10) NOT NULL DEFAULT '0' COMMENT '当前层级排序',
                          `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                          `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
                          `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `create_by` bigint(20) DEFAULT NULL,
                          `update_by` bigint(20) DEFAULT NULL,
                          `del_flag` varchar(1) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                          `id` bigint(20) NOT NULL,
                          `name` varchar(25) NOT NULL COMMENT '角色名称',
                          `status` varchar(2) NOT NULL DEFAULT '0' COMMENT '0:正常,1:冻结',
                          `seq` int(10) NOT NULL DEFAULT '0' COMMENT '排序',
                          `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                          `del_flag` varchar(2) DEFAULT '0',
                          `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
                          `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `create_by` bigint(20) DEFAULT NULL,
                          `update_by` bigint(20) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_acl
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_acl`;
CREATE TABLE `sys_role_acl` (
                              `role_id` bigint(20) NOT NULL,
                              `acl_id` bigint(20) NOT NULL,
                              PRIMARY KEY (`role_id`,`acl_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
                               `role_id` bigint(20) NOT NULL,
                               `user_id` bigint(20) NOT NULL,
                               PRIMARY KEY (`role_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                          `id` bigint(20) NOT NULL COMMENT '用户id',
                          `username` varchar(20) NOT NULL COMMENT '用户名',
                          `phone` varchar(13) NOT NULL COMMENT '用户手机号',
                          `password` varchar(255) NOT NULL COMMENT '用户密码',
                          `dept_id` bigint(20) NOT NULL COMMENT '部门id',
                          `status` varchar(2) NOT NULL DEFAULT '0' COMMENT '0:正常,1:冻结',
                          `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                          `del_flag` varchar(2) DEFAULT '0' COMMENT '删除标识符 0 未删除 1已删除(以yml配置文件属性为主)',
                          `create_date` datetime DEFAULT CURRENT_TIMESTAMP,
                          `update_date` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `create_by` bigint(20) DEFAULT NULL,
                          `update_by` bigint(20) DEFAULT NULL,
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for update_log
-- ----------------------------
DROP TABLE IF EXISTS `update_log`;
CREATE TABLE `update_log` (
                            `id` bigint(20) NOT NULL,
                            `uri` varchar(255) DEFAULT NULL COMMENT '请求的url',
                            `method_name` varchar(255) DEFAULT NULL COMMENT '执行请求的方法',
                            `param_json` varchar(255) DEFAULT NULL COMMENT '参数',
                            `sql_info` varchar(255) DEFAULT NULL COMMENT 'sql信息',
                            `executor_user` bigint(20) DEFAULT NULL COMMENT '执行人',
                            `executor_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '执行时间',
                            `table_name` varchar(255) DEFAULT NULL COMMENT '执行的表明',
                            `sql_type` varchar(10) DEFAULT NULL COMMENT 'sql类型',
                            `table_id` bigint(20) DEFAULT NULL COMMENT '参与修改的主键',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
