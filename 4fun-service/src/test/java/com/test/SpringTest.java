package com.test;

import com.pkgs.App;
import com.pkgs.entity.TopicEntity;
import com.pkgs.service.TopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/8 15:41
 * <p>
 * since: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SpringTest {

    @Autowired
    private TopicService topicService;


    @Test
    public void test() {
        List<TopicEntity> list = topicService.queryTopTopics();
        list.forEach(System.out::println);
    }
}
