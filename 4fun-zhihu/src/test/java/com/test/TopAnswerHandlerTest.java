package com.test;

import com.pkgs.entity.zhihu.AnswerEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.service.AnswerService;
import com.pkgs.util.SysUtil;
import org.junit.Test;

import java.util.List;

public class TopAnswerHandlerTest {

    private AnswerService topAnswerService = new AnswerService();

    @Test
    public void test() {

        String url = SysUtil.getAnswersUrlOfTopic("19555513", 0, 10);

        AbstractHandler<Integer, List<AnswerEntity>> handler = new TopAnswerHandler();
        handler.setValue(34);
        List<AnswerEntity> list = handler.get(url);

        for (AnswerEntity t : list) {
            topAnswerService.saveOrUpdate(t);
        }

    }
}
