package com.pkgs.service;

import com.pkgs.entity.AnswerEntity;
import com.pkgs.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<AnswerEntity> entities = answerMapper.selectByMap(null);

        for (AnswerEntity a : entities) {
            System.out.println(a.toString());
        }
    }
}
