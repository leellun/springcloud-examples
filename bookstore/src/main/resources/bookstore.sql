/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : bookstore

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 14/07/2021 23:43:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `parent_id` bigint(0) NULL DEFAULT NULL,
  `price` bigint(0) NULL DEFAULT NULL,
  `is_book` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, '高等数学', 5, 10, b'1');
INSERT INTO `t_book` VALUES (2, '线性代数', 5, 20, b'1');
INSERT INTO `t_book` VALUES (3, '唐诗', 6, 15, b'1');
INSERT INTO `t_book` VALUES (4, '宋词', 6, 14, b'1');
INSERT INTO `t_book` VALUES (5, '数学类', 7, -3, b'0');
INSERT INTO `t_book` VALUES (6, '语文类', 7, -2, b'0');
INSERT INTO `t_book` VALUES (7, '教材类', NULL, -5, b'0');

SET FOREIGN_KEY_CHECKS = 1;
