/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 127.0.0.1:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 08/12/2021 19:20:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1047 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` VALUES (1, 'test', '437225121@qq.com', 24);
INSERT INTO `student` VALUES (2, 'hhh', '4323@qq.com', 42);
INSERT INTO `student` VALUES (4, 'hhh', '4323@qq.com', 432);
INSERT INTO `student` VALUES (1008, 'asda', 'ragrgwaerf', 21);
INSERT INTO `student` VALUES (1046, 'asd', 'fasegawef', 23);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
