/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 127.0.0.1:3306
 Source Schema         : mybatis_plus

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 08/12/2021 16:40:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for children
-- ----------------------------
DROP TABLE IF EXISTS `children`;
CREATE TABLE `children` (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `user_id` bigint DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of children
-- ----------------------------
BEGIN;
INSERT INTO `children` VALUES (1, 'Jone', 1);
INSERT INTO `children` VALUES (2, 'Jack', 1);
INSERT INTO `children` VALUES (3, 'Jack2', 1);
INSERT INTO `children` VALUES (4, 'Jack', 15);
INSERT INTO `children` VALUES (5, 'Billie', 15);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1412707687287951405 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'Jone', 18, 'ab@c.c', NULL, '2021-12-08 08:27:09');
INSERT INTO `user` VALUES (2, 'mp', NULL, 'miemie2@baomidou.com', NULL, '2021-12-08 08:27:09');
INSERT INTO `user` VALUES (5, 'Billie', 24, 'test5@baomidou.com', NULL, NULL);
INSERT INTO `user` VALUES (10086, 'miemie', 3, 'miemie@baomidou.com', '2021-12-08 08:27:09', '2021-12-08 08:27:09');
INSERT INTO `user` VALUES (10088, 'miemie', 3, 'miemie@baomidou.com', '2021-12-08 08:27:09', '2021-12-08 08:27:09');
INSERT INTO `user` VALUES (1412707687287951404, '靓仔', 18, NULL, '2021-12-08 08:27:09', '2021-12-08 08:27:09');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
