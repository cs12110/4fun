/*
Navicat MySQL Data Transfer

Source Server         : 47.98.104.252
Source Server Version : 50718
Source Host           : 47.98.104.252:3306
Source Database       : 4fun_db

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-08-01 23:40:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_douban_book_info
-- ----------------------------
DROP TABLE IF EXISTS `t_douban_book_info`;
CREATE TABLE `t_douban_book_info` (
  `id` int(18) NOT NULL AUTO_INCREMENT,
  `rating` float DEFAULT NULL,
  `rating_sum` int(18) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `isbn` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `author` varchar(256) DEFAULT NULL,
  `summary` varchar(1024) DEFAULT NULL,
  `publish` varchar(256) DEFAULT NULL,
  `paper_num` varchar(256) DEFAULT NULL,
  `translator` varchar(256) DEFAULT NULL,
  `link` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='book_info_t';

-- ----------------------------
-- Table structure for t_douban_book_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_douban_book_tag`;
CREATE TABLE `t_douban_book_tag` (
  `id` int(18) NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `books` int(18) DEFAULT NULL,
  `page` int(18) DEFAULT NULL,
  `status` int(18) DEFAULT NULL,
  `link` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='book_tag_t';

-- ----------------------------
-- Table structure for t_douban_book_tag_info
-- ----------------------------
DROP TABLE IF EXISTS `t_douban_book_tag_info`;
CREATE TABLE `t_douban_book_tag_info` (
  `tag_id` int(18) DEFAULT NULL,
  `book_id` int(18) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='map_tag_info_t';

-- ----------------------------
-- Table structure for t_prime_sentence
-- ----------------------------
DROP TABLE IF EXISTS `t_prime_sentence`;
CREATE TABLE `t_prime_sentence` (
  `id` int(18) NOT NULL AUTO_INCREMENT,
  `author` varchar(256) DEFAULT NULL,
  `sentence` varchar(256) DEFAULT NULL,
  `idx` int(18) DEFAULT NULL,
  `wallpaper` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='sentence_t';

-- ----------------------------
-- Table structure for t_zhihu_top_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_zhihu_top_answer`;
CREATE TABLE `t_zhihu_top_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `question` varchar(256) DEFAULT NULL COMMENT '题目',
  `author` varchar(256) DEFAULT NULL COMMENT '作者',
  `author_img` varchar(256) DEFAULT NULL COMMENT '作者头像',
  `link` varchar(256) DEFAULT NULL COMMENT '连接',
  `upvote_num` int(8) DEFAULT NULL COMMENT '点赞数',
  `comment_num` int(6) DEFAULT NULL COMMENT '留言数',
  `summary` longtext COMMENT '总结',
  `create_at` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `update_at` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `question_id` varchar(64) DEFAULT NULL,
  `answer_id` varchar(64) DEFAULT NULL,
  `steal_at` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `linkIndex` (`link`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='知乎高赞回答表';

-- ----------------------------
-- Table structure for t_zhihu_topic
-- ----------------------------
DROP TABLE IF EXISTS `t_zhihu_topic`;
CREATE TABLE `t_zhihu_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL COMMENT '父级',
  `data_id` varchar(6) DEFAULT NULL COMMENT '知乎话题dataId',
  `name` varchar(256) DEFAULT NULL COMMENT '话题名称',
  `link` varchar(256) DEFAULT NULL COMMENT '连接',
  `desc` varchar(256) DEFAULT NULL COMMENT '描述',
  `update_time` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `done` tinyint(2) DEFAULT '0' COMMENT '0:尚未爬取,1:已爬取',
  `times` int(6) DEFAULT '0' COMMENT '爬取次数',
  PRIMARY KEY (`id`),
  KEY `data_id` (`data_id`) COMMENT 'dataIdIndex',
  KEY `name` (`name`(255)) COMMENT 'nameIndex'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='话题表';

-- ----------------------------
-- Table structure for t_zhihu_topic_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_zhihu_topic_answer`;
CREATE TABLE `t_zhihu_topic_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) DEFAULT NULL,
  `answer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topicIdIndex` (`topic_id`),
  KEY `answerIdIndex` (`answer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
