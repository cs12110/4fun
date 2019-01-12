package com.test;

import java.util.List;

import com.pkgs.service.TopicService;
import org.junit.Test;

import com.pkgs.entity.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopTopicHandler;

public class TopTopicHandlerTest {

    private TopicService service = new TopicService();

    @Test
    public void test() throws Exception {

        String url = "https://www.zhihu.com/topics";

        AbstractHandler<List<TopicEntity>> h = new TopTopicHandler();
        List<TopicEntity> list = h.get(url);

        for (TopicEntity t : list) {
            System.out.println(t);
        }

        for (TopicEntity e : list) {
            service.saveIfNotExists(e);


        }
    }
}
