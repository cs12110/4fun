package com.test;

import java.util.List;

import org.junit.Test;

import com.pkgs.entity.TopAnswerEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.service.TopAnswerService;
import com.pkgs.util.SysUtil;

public class TopAnswerHandlerTest {

	TopAnswerService topAnswerService = new TopAnswerService();

	@Test
	public void test() throws Exception {

		String url = SysUtil.getTopicAnswerUrlOfTopic("19555513", 0, 10);

		AbstractHandler<List<TopAnswerEntity>> h = new TopAnswerHandler(34);
		List<TopAnswerEntity> list = h.get(url);

		for (TopAnswerEntity t : list) {
			topAnswerService.saveIfNotExists(t);
		}

	}
}
