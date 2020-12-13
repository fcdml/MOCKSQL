/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : newframework

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 13/12/2020 14:04:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forjdbc
-- ----------------------------
DROP TABLE IF EXISTS `forjdbc`;
CREATE TABLE `forjdbc`  (
  `id` int(10) NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createdate` datetime(0) NULL DEFAULT NULL,
  `enddate` datetime(0) NULL DEFAULT NULL,
  `status` enum('持有中','转让中','已转让') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uu` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mydate` date NULL DEFAULT NULL,
  `afterdate` datetime(0) NULL DEFAULT NULL,
  `aa` datetime(0) NULL DEFAULT NULL,
  `bb` datetime(0) NULL DEFAULT NULL,
  `cc` datetime(0) NULL DEFAULT NULL,
  `dd` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forjdbc
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
