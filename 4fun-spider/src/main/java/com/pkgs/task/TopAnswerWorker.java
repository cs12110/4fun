package com.pkgs.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkgs.entity.ProcessEntity;
import com.pkgs.entity.TopAnswerEntity;
import com.pkgs.entity.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.service.ProcessService;
import com.pkgs.service.TopAnswerService;
import com.pkgs.service.TopicService;
import com.pkgs.util.DateUtil;
import com.pkgs.util.PropertiesUtil;
import com.pkgs.util.SysUtil;

/**
 * 获取top answer
 *
 * 
 * @author cs12110 at 2018年12月10日下午9:39:12
 *
 */
public class TopAnswerWorker implements Runnable {

	private Logger logger = LoggerFactory.getLogger(TopAnswerWorker.class);

	private long minUpvoteNum = Long.parseLong(PropertiesUtil.get("upvote.minNum", "10000"));

	@Override
	public void run() {

		while (true) {
			logger.info("start working at get top answer");
			long start = System.currentTimeMillis();

			execute();
			removeAllLog();

			long end = System.currentTimeMillis();
			logger.info("get top answer is done,spend:{}", (end - start));
			// 休息一个小时
			try {
				Thread.sleep(1000 * 120);
			} catch (Exception e) {
			}
		}
	}

	private void execute() {
		TopAnswerService topAnswerService = new TopAnswerService();
		// 获取需要爬取的话题
		List<TopicEntity> remainList = getNeedToSpiderTopics();

		// 处理每一个话题
		for (TopicEntity t : remainList) {

			logger.info("get top answer of: {} -> {} ", t.getId(), t.getName());
			boolean gt = true;
			String topicIdOfZhihu = getIdFromUrl(t.getLink());
			// 获取前200条数据
			long start = System.currentTimeMillis();
			int count = 0;
			for (int index = 0; index < 20; index++) {
				String url = SysUtil.getTopicAnswerUrlOfTopic(topicIdOfZhihu, index * 10, 10);
				AbstractHandler<List<TopAnswerEntity>> h = new TopAnswerHandler(t.getId());
				List<TopAnswerEntity> list = h.get(url);

				// 处理每一条数据
				for (TopAnswerEntity a : list) {
					// 点赞数小于1k
					if (a.getUpvoteNum() < minUpvoteNum) {
						gt = false;
						break;
					}
					count += topAnswerService.saveIfNotExists(a) ? 1 : 0;
				}
				if (!gt) {
					break;
				}
			}

			logger.info("get top answer of: {} -> {} is done ,add: {}", t.getId(), t.getName(), count);
			insertLog(t.getId(), start, System.currentTimeMillis());
		}

	}

	/**
	 * 获取需要爬取的话题
	 * 
	 * @return List
	 */
	private List<TopicEntity> getNeedToSpiderTopics() {
		TopicService topicService = new TopicService();
		ProcessService processService = new ProcessService();

		// 获取出来所有的话题,减去已经完成的话题
		Map<Integer, String> existsMap = new HashMap<Integer, String>();
		List<ProcessEntity> processList = processService.query(null);
		for (ProcessEntity p : processList) {
			existsMap.put(p.getTopicId(), "");
		}

		List<TopicEntity> topicList = topicService.query(null);
		List<TopicEntity> remainList = new ArrayList<>();
		for (TopicEntity e : topicList) {
			// 顶级话题
			if (e.getLink() == null) {
				continue;
			}
			if (existsMap.get(e.getId()) == null) {
				remainList.add(e);
			}
		}

		return remainList;
	}

	/**
	 * 从url里面获取Id
	 * 
	 * @param url
	 *            url连接
	 * @return String
	 */
	private String getIdFromUrl(String url) {
		if (url == null) {
			return "";
		}
		int last = url.lastIndexOf("/");
		return url.substring(last + 1);
	}

	/**
	 * 插入进度日志
	 * 
	 * @param topicId
	 *            话题id
	 * @param startAt
	 *            开始时间
	 * @param endAt
	 *            结束时间
	 */
	private void insertLog(Integer topicId, long startAt, long endAt) {
		ProcessService processService = new ProcessService();
		ProcessEntity entity = new ProcessEntity();
		entity.setTopicId(topicId);
		entity.setStartAt(DateUtil.format(startAt));
		entity.setEndAt(DateUtil.format(endAt));
		entity.setDone(1);
		processService.saveIfNotExists(entity);
	}

	/**
	 * 删除所有日志
	 */
	private void removeAllLog() {
		logger.info("remove all log of process");
		ProcessService processService = new ProcessService();
		processService.deleteAll();
	}

}
