package com.pkgs.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.AnswerEntity;
import com.pkgs.mapper.AnswerMapper;

/**
 * TODO:
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 19:56
 * Since: 1.0.0
 */
@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    public List<AnswerEntity> query(String topicId, Page<AnswerEntity> page) {
        //查询参数
        HashMap<String, Object> searchMap = new HashMap<>(1);
        searchMap.put("topicId", topicId);

        return answerMapper.selectByMap(page, searchMap);
    }
}
