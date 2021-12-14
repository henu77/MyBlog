/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 12/12/2021 22:53:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
	
DROP DATABASE IF EXISTS `myblog`;
CREATE DATABASE `myblog`;
use myblog;
-- ----------------------------
-- Table structure for t_advertisement
-- ----------------------------
DROP TABLE IF EXISTS `t_advertisement`;
CREATE TABLE `t_advertisement`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `des` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mini_des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` int(0) NOT NULL,
  `submit_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_adv_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_adv_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_advertisement
-- ----------------------------

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文章内容',
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '首图地址',
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文章类型，原创、转载',
  `views` int(0) NOT NULL COMMENT '浏览次数',
  `appreciation` bit(1) NOT NULL COMMENT '是否开启赞赏',
  `commentabled` bit(1) NOT NULL COMMENT '是否开启评论',
  `published` bit(1) NOT NULL COMMENT '是否发布',
  `recommend` bit(1) NOT NULL COMMENT '是否推荐',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '描述',
  `type_id` int(0) NOT NULL COMMENT '分类编号',
  `user_id` int(0) NOT NULL COMMENT '用户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_blog_type`(`type_id`) USING BTREE,
  INDEX `fk_blog_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_blog_type` FOREIGN KEY (`type_id`) REFERENCES `t_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_blog_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_blog
-- ----------------------------

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(0) NOT NULL COMMENT '回复人Id',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '评论内容',
  `create_time` datetime(0) NOT NULL COMMENT '评论时间',
  `blog_id` int(0) NOT NULL COMMENT '博客编号',
  `replied_user_id` int(0) NULL DEFAULT NULL COMMENT '被回复人Id',
  `parent_comment_id` int(0) NOT NULL COMMENT '父评论编号 ',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_blog`(`blog_id`) USING BTREE,
  CONSTRAINT `fk_blog` FOREIGN KEY (`blog_id`) REFERENCES `t_blog` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_comment
-- ----------------------------

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_message_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_message_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_message
-- ----------------------------

-- ----------------------------
-- Table structure for t_province
-- ----------------------------
DROP TABLE IF EXISTS `t_province`;
CREATE TABLE `t_province`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `count` int(0) NOT NULL DEFAULT 0,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_province
-- ----------------------------
INSERT INTO `t_province` VALUES ('上海市', 2);
INSERT INTO `t_province` VALUES ('云南省', 0);
INSERT INTO `t_province` VALUES ('内蒙古自治区', 0);
INSERT INTO `t_province` VALUES ('北京市', 4);
INSERT INTO `t_province` VALUES ('台湾省', 0);
INSERT INTO `t_province` VALUES ('吉林省', 0);
INSERT INTO `t_province` VALUES ('四川省', 0);
INSERT INTO `t_province` VALUES ('天津市', 0);
INSERT INTO `t_province` VALUES ('宁夏回族自治区', 0);
INSERT INTO `t_province` VALUES ('安徽省', 0);
INSERT INTO `t_province` VALUES ('山东省', 0);
INSERT INTO `t_province` VALUES ('山西省', 0);
INSERT INTO `t_province` VALUES ('广东省', 0);
INSERT INTO `t_province` VALUES ('广西壮族自治区', 0);
INSERT INTO `t_province` VALUES ('新疆维吾尔自治区', 0);
INSERT INTO `t_province` VALUES ('江苏省', 0);
INSERT INTO `t_province` VALUES ('江西省', 0);
INSERT INTO `t_province` VALUES ('河北省', 0);
INSERT INTO `t_province` VALUES ('河南省', 24);
INSERT INTO `t_province` VALUES ('浙江省', 0);
INSERT INTO `t_province` VALUES ('海南省', 0);
INSERT INTO `t_province` VALUES ('湖北省', 5);
INSERT INTO `t_province` VALUES ('湖南省', 0);
INSERT INTO `t_province` VALUES ('澳门特别行政区', 0);
INSERT INTO `t_province` VALUES ('甘肃省', 0);
INSERT INTO `t_province` VALUES ('福建省', 0);
INSERT INTO `t_province` VALUES ('西藏自治区', 0);
INSERT INTO `t_province` VALUES ('贵州省', 0);
INSERT INTO `t_province` VALUES ('辽宁省', 0);
INSERT INTO `t_province` VALUES ('重庆市', 0);
INSERT INTO `t_province` VALUES ('陕西省', 0);
INSERT INTO `t_province` VALUES ('青海省', 0);
INSERT INTO `t_province` VALUES ('香港特别行政区', 0);
INSERT INTO `t_province` VALUES ('黑龙江省', 0);

-- ----------------------------
-- Table structure for t_social_user
-- ----------------------------
DROP TABLE IF EXISTS `t_social_user`;
CREATE TABLE `t_social_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `access_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_social_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_social_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_social_user_auth`;
CREATE TABLE `t_social_user_auth`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_id` int(0) NOT NULL,
  `social_user_id` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_userauth_socialuser`(`social_user_id`) USING BTREE,
  INDEX `fk_userauth_user`(`user_id`) USING BTREE,
  CONSTRAINT `fk_userauth_socialuser` FOREIGN KEY (`social_user_id`) REFERENCES `t_social_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_userauth_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_social_user_auth
-- ----------------------------

-- ----------------------------
-- Table structure for t_trafficstatics
-- ----------------------------
DROP TABLE IF EXISTS `t_trafficstatics`;
CREATE TABLE `t_trafficstatics`  (
  `date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `views` int(0) NOT NULL,
  PRIMARY KEY (`date`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_trafficstatics
-- ----------------------------
INSERT INTO `t_trafficstatics` VALUES ('2021-12-09', 10);
INSERT INTO `t_trafficstatics` VALUES ('2021-12-12', 3);

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_type
-- ----------------------------

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键，编号',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '昵称',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '邮箱地址',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '头像地址',
  `role` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户角色',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 57 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (56, 'root', 'root', '63a9f0ea7bb98050796b649e85481845', '', 'D:\\home\\uploads\\MyBlog\\/defaultIcon/defaultIcon4.png', 'root');
INSERT INTO `t_user` VALUES (57, '不坠青云之志', 'user', 'e10adc3949ba59abbe56e057f20f883e', '1289596706@qq.com', 'D:\\home\\uploads\\MyBlog\\/defaultIcon/defaultIcon1.png', 'user');

SET FOREIGN_KEY_CHECKS = 1;
