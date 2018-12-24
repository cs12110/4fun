package com.pkgs.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统工具类
 *
 * 
 * @author cs12110 at 2018年12月10日下午11:05:05
 *
 */
public class SysUtil {
	/**
	 * 话题广场url
	 */
	public static final String TOPIC_URL = "https://www.zhihu.com/topics";

	/**
	 * 子话题url
	 */
	public static final String SUB_TOPIC_URL = "https://www.zhihu.com/node/TopicsPlazzaListV2";

	/**
	 * 建立查询参数map
	 * 
	 * @param dataId
	 *            父级话题id
	 * @param offset
	 *            偏移量
	 * @return Map
	 */
	public static Map<String, String> buildSubTopicSearchMap(String dataId, int offset) {
		String params = "{\"topic_id\":" + dataId + ",\"offset\":" + offset
				+ ",\"hash_id\":\"e93e70a5b1b8de26108cbf8cad759536\"}";
		Map<String, String> map = new HashMap<>();
		map.put("method", "next");
		map.put("params", params);
		return map;
	}

	/**
	 * 获取话题精华回答
	 * 
	 * @param topicId
	 *            话题Id
	 * @param offset
	 *            偏移量
	 * @param limit
	 *            获取长度
	 * @return String
	 */
	public static String getAnswersUrlOfTopic(String topicId, int offset, int limit) {
		return "https://www.zhihu.com/api/v4/topics/" + topicId + "/feeds/essence?limit=" + limit + "&offset=" + offset;
	}

	/**
	 * 获取回答url
	 * 
	 * @param questionId
	 *            问题id
	 * @param answerId
	 *            回答Id
	 * @return String
	 */
	public static String getAnswerLink(String questionId, String answerId) {
		return "https://www.zhihu.com/question/" + questionId + "/answer/" + answerId;
	}
}
