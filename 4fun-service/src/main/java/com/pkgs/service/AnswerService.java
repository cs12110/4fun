package com.pkgs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
 *
 * @author: cs12110 create at: 2019/1/6 19:56
 * Since: 1.0.0
 */
@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    public List<AnswerEntity> query(Integer pageIndex, Integer pageSize) {
        Page<AnswerEntity> page = new Page<>(pageIndex, pageSize);
        List<AnswerEntity> entities = answerMapper.selectByMap(page, new HashMap<>(1));

        for (AnswerEntity a : entities) {
            System.out.println(a.toString());
        }

        return entities;
    }
}
