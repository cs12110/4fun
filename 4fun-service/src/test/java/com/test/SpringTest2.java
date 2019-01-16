package com.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.App;
import com.pkgs.entity.AnswerEntity;
import com.pkgs.service.AnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/8 15:41
 * <p>
 * since: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class SpringTest2 {

    @Autowired
    private AnswerService service;


    @Test
    public void test() {
        Page<AnswerEntity> page = new Page<>(10, 10);
        service.queryWithTopic("1", page).forEach(System.out::println);
    }
}
