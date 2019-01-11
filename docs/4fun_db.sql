/*
Navicat MySQL Data Transfer

Source Server         : 47.98.104.252
Source Server Version : 50718
Source Host           : 47.98.104.252:3306
Source Database       : 4fun_test

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-01-11 13:19:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for map_topic_answer_t
-- ----------------------------
DROP TABLE IF EXISTS `map_topic_answer_t`;
CREATE TABLE `map_topic_answer_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) DEFAULT NULL,
  `answer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Table structure for top_answer_t
-- ----------------------------
DROP TABLE IF EXISTS `top_answer_t`;
CREATE TABLE `top_answer_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) DEFAULT NULL COMMENT '话题id',
  `question` varchar(256) DEFAULT NULL COMMENT '题目',
  `author` varchar(256) DEFAULT NULL COMMENT '作者',
  `link` varchar(256) DEFAULT NULL COMMENT '连接',
  `upvote_num` int(8) DEFAULT NULL COMMENT '点赞数',
  `comment_num` int(6) DEFAULT NULL COMMENT '留言数',
  `summary` longtext COMMENT '总结',
  `create_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `question_id` varchar(64) DEFAULT NULL,
  `answer_id` varchar(64) DEFAULT NULL,
  `steal_at` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='精华回答表';

-- ----------------------------
-- Table structure for topic_t
-- ----------------------------
DROP TABLE IF EXISTS `topic_t`;
CREATE TABLE `topic_t` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父级',
  `data_id` varchar(6) DEFAULT NULL COMMENT '知乎话题dataId',
  `name` varchar(256) DEFAULT NULL COMMENT '话题名称',
  `link` varchar(256) DEFAULT NULL COMMENT '连接',
  `desc` varchar(256) DEFAULT NULL COMMENT '描述',
  `update_time` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `done` int(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `data_id` (`data_id`) COMMENT 'dataIdIndex',
  KEY `name` (`name`(255)) COMMENT 'nameIndex'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='话题表';
