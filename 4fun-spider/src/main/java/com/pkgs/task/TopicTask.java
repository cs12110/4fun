package com.pkgs.task;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkgs.entity.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.SubTopicHandler;
import com.pkgs.handler.TopTopicHandler;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.util.SysUtil;

/**
 * 获取topic
 *
 * 
 * @author cs12110 at 2018年12月10日下午9:39:12
 *
 */
public class TopicTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(TopicTask.class);
	private TopicMapper topicMapper = new TopicMapper();

	@Override
	public void run() {
		while (true) {
			logger.info("start working at get topic from zhihu");
			long start = System.currentTimeMillis();
			execute();
			long end = System.currentTimeMillis();
			logger.info("get topic is done,spend:{}", (end - start));
			try {
				Thread.sleep(1000 * 3600 * 12);
			} catch (Exception e) {
			}
		}
	}

	private void execute() {
		// 1. 首先查询出来所有的父级话题
		AbstractHandler<List<TopicEntity>> topHandler = new TopTopicHandler();
		topHandler.get(SysUtil.TOPIC_URL).forEach(e -> {
			topicMapper.saveIfNotExists(e);
		});

		// 2. 根据父级话题获取所有子话题
		AbstractHandler<List<TopicEntity>> subHandler = new SubTopicHandler();
		List<TopicEntity> topList = topicMapper.queryTopTopic();
		topList.forEach(e -> {
			try {
				// 只获取前50的话题,大概1k个子话题
				subHandler.setValue(e.getId());

				for (int index = 0; index < 50; index++) {
					Map<String, String> map = SysUtil.buildSubTopicSearchMap(e.getDataId(), index * 20);
					Optional
							//
							.ofNullable(subHandler.post(SysUtil.SUB_TOPIC_URL, map))
							//
							.orElse(Collections.emptyList())
							//
							.forEach(sub -> {
								topicMapper.saveIfNotExists(sub);
							});

					Thread.sleep(1000 * 30);
				}
			} catch (Exception ex) {
				logger.error("{}", ex);
			}
		});
	}
}
