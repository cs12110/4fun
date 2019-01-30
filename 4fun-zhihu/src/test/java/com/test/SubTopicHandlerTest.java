package com.test;

import com.pkgs.entity.zhihu.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.SubTopicHandler;
import com.pkgs.util.SysUtil;
import org.junit.Test;

import java.util.List;

public class SubTopicHandlerTest {

    @Test
    public void test() throws Exception {
        AbstractHandler<Integer, List<TopicEntity>> h = new SubTopicHandler();
        h.setValue(12);

        h.post(SysUtil.SUB_TOPIC_URL, SysUtil.buildSubTopicSearchMap("99", 0));
    }
}
