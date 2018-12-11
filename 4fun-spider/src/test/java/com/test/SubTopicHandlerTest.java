package com.test;

import java.util.List;

import org.junit.Test;

import com.pkgs.entity.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.SubTopicHandler;
import com.pkgs.util.SysUtil;

public class SubTopicHandlerTest {

	@Test
	public void test() throws Exception {
		AbstractHandler<List<TopicEntity>> h = new SubTopicHandler(12);

		h.post(SysUtil.SUB_TOPIC_URL, SysUtil.buildSubTopicSearchMap("99", 0));
	}
}
