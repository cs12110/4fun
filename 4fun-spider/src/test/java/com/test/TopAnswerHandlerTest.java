package com.test;

import java.util.List;

import org.junit.Test;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.mapper.AnswerMapper;
import com.pkgs.util.SysUtil;

public class TopAnswerHandlerTest {

	AnswerMapper topAnswerService = new AnswerMapper();

	@Test
	public void test() throws Exception {

		String url = SysUtil.getAnswersUrlOfTopic("19555513", 0, 10);

		AbstractHandler<List<AnswerEntity>> h = new TopAnswerHandler();
		h.setValue(34);
		List<AnswerEntity> list = h.get(url);

		for (AnswerEntity t : list) {
			topAnswerService.saveIfNotExists(t);
		}

	}
}
