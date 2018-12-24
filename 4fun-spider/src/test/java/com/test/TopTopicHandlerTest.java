package com.test;

import java.util.List;

import org.junit.Test;

import com.pkgs.entity.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopTopicHandler;
import com.pkgs.mapper.TopicMapper;

public class TopTopicHandlerTest {

	private TopicMapper service = new TopicMapper();

	@Test
	public void test() throws Exception {

		String url = "https://www.zhihu.com/topics";

		AbstractHandler<List<TopicEntity>> h = new TopTopicHandler();
		List<TopicEntity> list = h.get(url);

		for (TopicEntity t : list) {
			System.out.println(t);
		}

		service.save(list);

	}
}
