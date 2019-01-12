package com.test;

import com.pkgs.entity.TopicEntity;
import com.pkgs.mapper.TopicMapper;
import com.pkgs.util.ProxyMapperUtil;
import org.junit.Test;

import java.util.List;

public class SeTest {


    @Test
    public void test() {
        TopicMapper topicMapper = ProxyMapperUtil.wrapper(TopicMapper.class);
        List<TopicEntity> entities = topicMapper.queryTopTopics();
        for (TopicEntity e : entities) {
            System.out.println(e);
        }
    }


}
