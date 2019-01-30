package com.test;

import com.pkgs.entity.zhihu.TopicEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopTopicHandler;
import com.pkgs.service.TopicService;
import org.junit.Test;

import java.util.List;

public class TopTopicHandlerTest {

    private TopicService service = new TopicService();

    @Test
    public void test() {

        String url = "https://www.zhihu.com/topics";

        AbstractHandler<Object, List<TopicEntity>> h = new TopTopicHandler();
        List<TopicEntity> list = h.get(url);

        for (TopicEntity t : list) {
            System.out.println(t);
        }

        for (TopicEntity e : list) {
            service.saveIfNotExists(e);


        }
    }
}
