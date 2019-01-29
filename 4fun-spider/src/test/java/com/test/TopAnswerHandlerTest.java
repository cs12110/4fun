package com.test;

import java.util.List;

import com.pkgs.service.AnswerService;
import org.junit.Test;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.handler.AbstractHandler;
import com.pkgs.handler.TopAnswerHandler;
import com.pkgs.util.SysUtil;

public class TopAnswerHandlerTest {

    private AnswerService topAnswerService = new AnswerService();

    @Test
    public void test() throws Exception {

        String url = SysUtil.getAnswersUrlOfTopic("19555513", 0, 10);

        AbstractHandler<Integer, List<AnswerEntity>> h = new TopAnswerHandler();
        h.setValue(34);
        List<AnswerEntity> list = h.get(url);

        for (AnswerEntity t : list) {
            topAnswerService.saveIfNotExists(t);
        }

    }
}
