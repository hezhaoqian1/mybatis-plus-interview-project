/*
 Navicat Premium Data Transfer

 Source Server         : 本地环境
 Source Server Type    : MySQL
 Source Server Version : 50744
 Source Host           : 192.168.2.13:3306
 Source Schema         : test1

 Target Server Type    : MySQL
 Target Server Version : 50744
 File Encoding         : 65001
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `pid` bigint(20) NULL DEFAULT NULL COMMENT '上级ID',
  `pids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所有上级ID，用逗号分开',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `sort` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '排序',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid`) USING BTREE,
  INDEX `idx_sort`(`sort`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1067246875800000062, 1067246875800000063, '1067246875800000066,1067246875800000063', '技术部', 2, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');
INSERT INTO `sys_dept` VALUES (1067246875800000063, 1067246875800000066, '1067246875800000066', '长沙分公司', 1, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');
INSERT INTO `sys_dept` VALUES (1067246875800000064, 1067246875800000066, '1067246875800000066', '上海分公司', 0, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');
INSERT INTO `sys_dept` VALUES (1067246875800000065, 1067246875800000064, '1067246875800000066,1067246875800000064', '市场部', 0, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');
INSERT INTO `sys_dept` VALUES (1067246875800000066, 0, '0', '杭州宏汉软件技术有限公司', 0, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');
INSERT INTO `sys_dept` VALUES (1067246875800000067, 1067246875800000064, '1067246875800000066,1067246875800000064', '销售部', 0, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');
INSERT INTO `sys_dept` VALUES (1067246875800000068, 1067246875800000063, '1067246875800000066,1067246875800000063', '产品部', 1, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `head_url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '性别   0：男   1：女    2：保密',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `super_admin` tinyint(3) UNSIGNED NULL DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `creator` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(20) NULL DEFAULT NULL COMMENT '更新者',
  `update_date` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE,
  INDEX `idx_create_date`(`create_date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1067246875800000001, 'admin', '$2a$10$efI8EkuPAZVsEJ4vbNJw9.ftXL4GSkYsDKma7vmRnNOjWAEOC.CKe', '管理员', NULL, 0, 'root@renren.io', '13612345678', 1067246875800000066, 1, 1, 1067246875800000001, '2022-10-12 09:14:41', 1067246875800000001, '2022-10-12 09:14:41');

SET FOREIGN_KEY_CHECKS = 1;
