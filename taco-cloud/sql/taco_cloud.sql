/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 127.0.0.1:3306
 Source Schema         : taco_cloud

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 08/12/2021 20:01:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ingredient
-- ----------------------------
DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE `ingredient` (
  `id` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ingredient
-- ----------------------------
BEGIN;
INSERT INTO `ingredient` VALUES ('FLTO', 'Flour Tortilla', 'WRAP');
INSERT INTO `ingredient` VALUES ('COTO', 'Corn Tortilla', 'WRAP');
INSERT INTO `ingredient` VALUES ('GRBF', 'Ground Beef', 'PROTEIN');
INSERT INTO `ingredient` VALUES ('CARN', 'Carnitas', 'PROTEIN');
INSERT INTO `ingredient` VALUES ('TMTO', 'Diced Tomatoes', 'VEGGIES');
INSERT INTO `ingredient` VALUES ('LETC', 'Lettuce', 'VEGGIES');
INSERT INTO `ingredient` VALUES ('CHED', 'Cheddar', 'CHEESE');
INSERT INTO `ingredient` VALUES ('JACK', 'Monterrey Jack', 'CHEESE');
INSERT INTO `ingredient` VALUES ('SLSA', 'Salsa', 'SAUCE');
INSERT INTO `ingredient` VALUES ('SRCR', 'Sour Cream', 'SAUCE');
COMMIT;

-- ----------------------------
-- Table structure for movies
-- ----------------------------
DROP TABLE IF EXISTS `movies`;
CREATE TABLE `movies` (
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movies
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for taco
-- ----------------------------
DROP TABLE IF EXISTS `taco`;
CREATE TABLE `taco` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of taco
-- ----------------------------
BEGIN;
INSERT INTO `taco` VALUES (63, 'taco 1', '2021-04-12 06:01:16');
INSERT INTO `taco` VALUES (64, 'taco 2', '2021-04-12 06:02:17');
INSERT INTO `taco` VALUES (65, 'taco 2', '2021-04-12 06:02:31');
INSERT INTO `taco` VALUES (66, '123drf', '2021-04-20 02:46:29');
INSERT INTO `taco` VALUES (67, 'dddddd', '2021-04-20 03:15:27');
INSERT INTO `taco` VALUES (68, 'test', '2021-04-20 03:17:53');
INSERT INTO `taco` VALUES (69, 'sssss', '2021-04-29 09:58:26');
INSERT INTO `taco` VALUES (70, 'dddddd', '2021-04-29 09:59:40');
INSERT INTO `taco` VALUES (71, 'ssssss', '2021-04-29 10:34:28');
INSERT INTO `taco` VALUES (72, 'ssssss', '2021-04-29 11:07:09');
INSERT INTO `taco` VALUES (78, 'test 2', '2021-06-18 07:31:22');
INSERT INTO `taco` VALUES (79, 'test 1', '2021-06-18 07:32:07');
INSERT INTO `taco` VALUES (80, 'test 3', '2021-06-18 07:33:54');
INSERT INTO `taco` VALUES (81, 'test 4', '2021-06-18 08:01:47');
INSERT INTO `taco` VALUES (82, 'test 5', '2021-06-18 08:02:15');
INSERT INTO `taco` VALUES (83, 'test 6', '2021-06-18 08:08:01');
COMMIT;

-- ----------------------------
-- Table structure for taco_ingredients
-- ----------------------------
DROP TABLE IF EXISTS `taco_ingredients`;
CREATE TABLE `taco_ingredients` (
  `taco` bigint NOT NULL,
  `ingredient` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of taco_ingredients
-- ----------------------------
BEGIN;
INSERT INTO `taco_ingredients` VALUES (63, 'FLTO');
INSERT INTO `taco_ingredients` VALUES (63, 'COTO');
INSERT INTO `taco_ingredients` VALUES (63, 'TMTO');
INSERT INTO `taco_ingredients` VALUES (63, 'SLSA');
INSERT INTO `taco_ingredients` VALUES (64, 'FLTO');
INSERT INTO `taco_ingredients` VALUES (64, 'COTO');
INSERT INTO `taco_ingredients` VALUES (64, 'TMTO');
INSERT INTO `taco_ingredients` VALUES (64, 'SLSA');
INSERT INTO `taco_ingredients` VALUES (65, 'COTO');
INSERT INTO `taco_ingredients` VALUES (65, 'CHED');
INSERT INTO `taco_ingredients` VALUES (65, 'JACK');
INSERT INTO `taco_ingredients` VALUES (65, 'TMTO');
INSERT INTO `taco_ingredients` VALUES (65, 'LETC');
INSERT INTO `taco_ingredients` VALUES (66, 'SRCR');
INSERT INTO `taco_ingredients` VALUES (67, 'SRCR');
INSERT INTO `taco_ingredients` VALUES (68, 'JACK');
INSERT INTO `taco_ingredients` VALUES (68, 'SLSA');
INSERT INTO `taco_ingredients` VALUES (69, 'COTO');
INSERT INTO `taco_ingredients` VALUES (69, 'TMTO');
INSERT INTO `taco_ingredients` VALUES (69, 'LETC');
INSERT INTO `taco_ingredients` VALUES (70, 'COTO');
INSERT INTO `taco_ingredients` VALUES (70, 'CARN');
INSERT INTO `taco_ingredients` VALUES (71, 'COTO');
INSERT INTO `taco_ingredients` VALUES (71, 'CARN');
INSERT INTO `taco_ingredients` VALUES (71, 'JACK');
INSERT INTO `taco_ingredients` VALUES (72, 'FLTO');
INSERT INTO `taco_ingredients` VALUES (72, 'CARN');
INSERT INTO `taco_ingredients` VALUES (78, 'FLTO');
INSERT INTO `taco_ingredients` VALUES (79, 'COTO');
INSERT INTO `taco_ingredients` VALUES (79, 'CHED');
INSERT INTO `taco_ingredients` VALUES (79, 'TMTO');
INSERT INTO `taco_ingredients` VALUES (80, 'COTO');
INSERT INTO `taco_ingredients` VALUES (80, 'CHED');
INSERT INTO `taco_ingredients` VALUES (80, 'JACK');
INSERT INTO `taco_ingredients` VALUES (81, 'COTO');
INSERT INTO `taco_ingredients` VALUES (81, 'CHED');
INSERT INTO `taco_ingredients` VALUES (82, 'COTO');
INSERT INTO `taco_ingredients` VALUES (82, 'CHED');
INSERT INTO `taco_ingredients` VALUES (82, 'TMTO');
INSERT INTO `taco_ingredients` VALUES (83, 'COTO');
INSERT INTO `taco_ingredients` VALUES (83, 'CHED');
COMMIT;

-- ----------------------------
-- Table structure for taco_order
-- ----------------------------
DROP TABLE IF EXISTS `taco_order`;
CREATE TABLE `taco_order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `street` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `state` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `zip` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ccNumber` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ccExpiration` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ccCVV` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `placedAt` timestamp NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of taco_order
-- ----------------------------
BEGIN;
INSERT INTO `taco_order` VALUES (38, 'hhh', 'asd', 'asd', 'as', '123', '6227612145830440', '01/11', '12', '2021-04-12 06:02:06');
INSERT INTO `taco_order` VALUES (39, 'hhh', 'asd', 'asd', 'as', '123', '6227612145830440', '02/33', '11', '2021-04-12 06:02:46');
INSERT INTO `taco_order` VALUES (40, 'test 3', 'hhhhhhhhh', 'asfdas', '12', '1122e332', '6227612145830440', '11/22', '222', '2021-06-18 07:30:35');
INSERT INTO `taco_order` VALUES (41, 'test 2', 'hhhhhhhhh', 'asfdas', '11', '1122e332', '6227612145830440', '12/22', '111', '2021-06-18 07:31:33');
INSERT INTO `taco_order` VALUES (42, 'test 1', 'hhhhhhhhh', 'asfdas', '33', '1122e332', '6227612145830440', '12/11', '22', '2021-06-18 07:32:18');
INSERT INTO `taco_order` VALUES (43, 'test 3', 'hhhhhhhhh', 'asfdas', '22', '1122e332', '6227612145830440', '12/32', '11', '2021-06-18 07:34:04');
INSERT INTO `taco_order` VALUES (44, 'test 4', 'hhhhhhhhh', 'asfdas', '22', '1122e332', '6227612145830440', '11/22', '11', '2021-06-18 08:01:59');
INSERT INTO `taco_order` VALUES (45, 'test 5', 'hhhhhhhhh', 'asfdas', '44', '1122e332', '6227612145830440', '11/33', '22', '2021-06-18 08:02:28');
INSERT INTO `taco_order` VALUES (46, 'test 6', 'hhhhhhhhh', 'asfdas', '22', '1122e332', '6227612145830440', '12/32', '11', '2021-06-18 08:08:18');
COMMIT;

-- ----------------------------
-- Table structure for taco_order_tacos
-- ----------------------------
DROP TABLE IF EXISTS `taco_order_tacos`;
CREATE TABLE `taco_order_tacos` (
  `tacoOrder` bigint NOT NULL,
  `taco` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of taco_order_tacos
-- ----------------------------
BEGIN;
INSERT INTO `taco_order_tacos` VALUES (38, 63);
INSERT INTO `taco_order_tacos` VALUES (39, 64);
INSERT INTO `taco_order_tacos` VALUES (39, 65);
INSERT INTO `taco_order_tacos` VALUES (40, 77);
INSERT INTO `taco_order_tacos` VALUES (41, 78);
INSERT INTO `taco_order_tacos` VALUES (42, 79);
INSERT INTO `taco_order_tacos` VALUES (43, 80);
INSERT INTO `taco_order_tacos` VALUES (44, 81);
INSERT INTO `taco_order_tacos` VALUES (45, 82);
INSERT INTO `taco_order_tacos` VALUES (46, 83);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `fullname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `street` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `state` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `zip` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phonenumber` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (2, 'hhh', '$2a$10$zM.f63e1YPGMpFoto0gYWOua4XxUGihWhir6U5Jq.nm7.iVjGitd2', 'hhh', 'hhh', 'hhh', 'hhh', 'hhh', 123456789);
INSERT INTO `user` VALUES (3, 'lyp', '$2a$10$.iSJuHwguwE231uXUwSfVO0k3ryqsrCKmYsdHU8qtR4alPDFxuGyW', 'hhh', 'hhh', 'hhh', 'hhh', 'hhh', 123456789);
INSERT INTO `user` VALUES (5, 'hhhddddd', '$2a$10$gkj3z2HG8uj4du5QPWra5euOFQo5sAaqjpzP1RqlQhwJkyehFdunu', 'hhh', 'hhh', 'hhh', 'hhh', 'hhh', 123456789);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
