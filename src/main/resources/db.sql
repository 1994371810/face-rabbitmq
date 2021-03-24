/*
 Navicat Premium Data Transfer

 Source Server         : localhost_mysql8
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : db1

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 24/03/2021 16:06:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `app_id` int(11) NULL DEFAULT NULL COMMENT '学校id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES (4, 'in-01', '校门入口1', 1);
INSERT INTO `device` VALUES (5, 'in-02', '校门入口2', 1);
INSERT INTO `device` VALUES (6, 'out-01', '校门出口1', 1);
INSERT INTO `device` VALUES (7, 'out-02', '校门出口2', 1);
INSERT INTO `device` VALUES (8, 'out-12', '校门出口2', 2);
INSERT INTO `device` VALUES (9, 'out-11', '校门出口1', 2);
INSERT INTO `device` VALUES (10, 'in-11', '校门进口1', 2);
INSERT INTO `device` VALUES (11, 'in-12', '校门进口2', 2);

-- ----------------------------
-- Table structure for face_device_log
-- ----------------------------
DROP TABLE IF EXISTS `face_device_log`;
CREATE TABLE `face_device_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NULL DEFAULT NULL COMMENT 'face_manager_id',
  `device_id` int(11) NULL DEFAULT NULL COMMENT '设备表id',
  `status` int(11) NULL DEFAULT NULL COMMENT '1任务上传成功，2任务上传失败，3已下发到人脸机，4人脸添加成功，5人脸添加失败',
  `message` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '人脸处理结果',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `app_id` int(11) NULL DEFAULT NULL COMMENT '学校id',
  `count` int(11) NULL DEFAULT NULL COMMENT '识别次数',
  `send_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of face_device_log
-- ----------------------------
INSERT INTO `face_device_log` VALUES (2, 1, 4, -1, '任务已发送到人脸机', '2021-03-23 16:48:49', 1, 7, NULL);
INSERT INTO `face_device_log` VALUES (3, 100, 4, 0, '任务已发送到人脸机', '2021-03-24 11:43:17', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (4, 100, 4, 0, '任务已发送到人脸机', '2021-03-24 11:43:34', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (5, 200, 4, 0, '任务已发送到人脸机', '2021-03-24 11:45:35', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (6, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:45:35', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (7, 200, 4, 0, '任务已发送到人脸机', '2021-03-24 11:45:49', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (8, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:45:49', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (9, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:05', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (10, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:06', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (11, 200, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:07', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (12, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:07', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (13, 200, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:08', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (14, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:08', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (15, 200, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:09', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (16, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 11:47:09', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (17, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 15:41:32', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (18, 201, 4, 0, '任务已发送到人脸机', '2021-03-24 15:41:33', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (19, 501, 4, 0, '任务已发送到人脸机', '2021-03-24 15:41:33', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (20, 202, 4, 0, '任务已发送到人脸机', '2021-03-24 15:42:04', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (21, 502, 4, 0, '任务已发送到人脸机', '2021-03-24 15:42:04', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (22, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 15:42:32', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (23, 203, 4, 0, '任务已发送到人脸机', '2021-03-24 15:44:10', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (24, 503, 4, 0, '任务已发送到人脸机', '2021-03-24 15:44:10', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (25, 204, 4, 0, '任务已发送到人脸机', '2021-03-24 15:44:12', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (26, 504, 4, 0, '任务已发送到人脸机', '2021-03-24 15:44:12', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (27, 500, 4, 0, '任务已发送到人脸机', '2021-03-24 15:45:31', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (28, 206, 4, 0, '任务已发送到人脸机', '2021-03-24 15:55:30', 1, 0, NULL);
INSERT INTO `face_device_log` VALUES (29, 506, 4, 0, '任务已发送到人脸机', '2021-03-24 15:55:30', 1, 0, NULL);

-- ----------------------------
-- Table structure for face_manager
-- ----------------------------
DROP TABLE IF EXISTS `face_manager`;
CREATE TABLE `face_manager`  (
  `student_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学生id',
  `app_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学校code',
  `face_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT 1 COMMENT '1已上传 2 已下发 0 图片无法识别出人脸',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `face_appid`(`app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of face_manager
-- ----------------------------
INSERT INTO `face_manager` VALUES ('1', '1', 'http://www.baidu.com', 1, 5);
INSERT INTO `face_manager` VALUES ('100', '1', 'http://www.baidu.com', 1, 6);
INSERT INTO `face_manager` VALUES ('200', '1', 'http://www.baidu.com', 1, 7);
INSERT INTO `face_manager` VALUES ('500', '1', 'http://www.baidu.com', 1, 8);
INSERT INTO `face_manager` VALUES ('201', '1', 'http://www.baidu.com', 1, 17);
INSERT INTO `face_manager` VALUES ('501', '1', 'http://www.baidu.com', 1, 18);
INSERT INTO `face_manager` VALUES ('202', '1', 'http://www.baidu.com', 1, 19);
INSERT INTO `face_manager` VALUES ('502', '1', 'http://www.baidu.com', 1, 20);
INSERT INTO `face_manager` VALUES ('203', '1', 'http://www.baidu.com', 1, 21);
INSERT INTO `face_manager` VALUES ('503', '1', 'http://www.baidu.com', 1, 22);
INSERT INTO `face_manager` VALUES ('204', '1', 'http://www.baidu.com', 1, 23);
INSERT INTO `face_manager` VALUES ('504', '1', 'http://www.baidu.com', 1, 24);
INSERT INTO `face_manager` VALUES ('206', '1', 'http://www.baidu.com', 1, 25);
INSERT INTO `face_manager` VALUES ('506', '1', 'http://www.baidu.com', 1, 26);
INSERT INTO `face_manager` VALUES ('207', '1', 'http://www.baidu.com', 1, 27);
INSERT INTO `face_manager` VALUES ('507', '1', 'http://www.baidu.com', 1, 28);
INSERT INTO `face_manager` VALUES ('208', '1', 'http://www.baidu.com', 1, 29);
INSERT INTO `face_manager` VALUES ('508', '1', 'http://www.baidu.com', 1, 30);
INSERT INTO `face_manager` VALUES ('209', '1', 'http://www.baidu.com', 1, 31);
INSERT INTO `face_manager` VALUES ('509', '1', 'http://www.baidu.com', 1, 32);

-- ----------------------------
-- Table structure for face_task_log
-- ----------------------------
DROP TABLE IF EXISTS `face_task_log`;
CREATE TABLE `face_task_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NULL DEFAULT NULL COMMENT '任务类型',
  `body` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '任务json数据',
  `status` int(11) NULL DEFAULT NULL COMMENT '处理状态',
  `count` int(11) NULL DEFAULT NULL COMMENT '发送次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of face_task_log
-- ----------------------------
INSERT INTO `face_task_log` VALUES (5, 1, '[{\"studentId\":1,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":5,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (6, 1, '[{\"studentId\":100,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":6,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (7, 1, '[{\"studentId\":100,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":6,\"status\":1}]', 1, 0);
INSERT INTO `face_task_log` VALUES (8, 1, '[{\"studentId\":200,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":7,\"status\":0},{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (9, 1, '[{\"studentId\":200,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":9,\"status\":0},{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":10,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (10, 1, '[{\"studentId\":200,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":11,\"status\":0},{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":12,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (11, 1, '[{\"studentId\":200,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":13,\"status\":0},{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":14,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (12, 1, '[{\"studentId\":200,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":15,\"status\":0},{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":16,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (13, 1, '[{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":1}]', 1, 0);
INSERT INTO `face_task_log` VALUES (14, 1, '[{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":1}]', 1, 0);
INSERT INTO `face_task_log` VALUES (15, 1, '[{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":1}]', 1, 0);
INSERT INTO `face_task_log` VALUES (16, 1, '[{\"studentId\":201,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":17,\"status\":0},{\"studentId\":501,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":18,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (17, 1, '[{\"studentId\":202,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":19,\"status\":0},{\"studentId\":502,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":20,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (18, 1, '[{\"studentId\":203,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":21,\"status\":0},{\"studentId\":503,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":22,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (19, 1, '[{\"studentId\":204,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":23,\"status\":0},{\"studentId\":504,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":24,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (20, 1, '[{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":1}]', 1, 0);
INSERT INTO `face_task_log` VALUES (21, 1, '[{\"studentId\":206,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":25,\"status\":0},{\"studentId\":506,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":26,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (22, 1, '[{\"studentId\":207,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":27,\"status\":0},{\"studentId\":507,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":28,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (23, 1, '[{\"studentId\":208,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":29,\"status\":0},{\"studentId\":508,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":30,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (24, 1, '[{\"studentId\":209,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":31,\"status\":0},{\"studentId\":509,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":32,\"status\":0}]', 1, 0);
INSERT INTO `face_task_log` VALUES (25, 1, '[{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":1}]', 1, 0);
INSERT INTO `face_task_log` VALUES (26, 1, '[{\"studentId\":500,\"faceUrl\":\"http://www.baidu.com\",\"appId\":1,\"id\":8,\"status\":1}]', 1, 0);

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES (1, '越秀高中');
INSERT INTO `school` VALUES (2, '天河高中');

SET FOREIGN_KEY_CHECKS = 1;
