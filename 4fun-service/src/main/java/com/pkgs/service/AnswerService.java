package com.pkgs.service;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO:
 * <p>
 * Author: cs12110 create at: 2019/1/6 19:56
 * Since: 1.0.0
 */
@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    public void query() {

        Map<String, Object> map = new HashMap<>(1);
        map.put("topicId", "123");

        List<AnswerEntity> entities = answerMapper.selectByMap(map);

        for (AnswerEntity a : entities) {
            System.out.println(a.toString());
        }
    }
}
