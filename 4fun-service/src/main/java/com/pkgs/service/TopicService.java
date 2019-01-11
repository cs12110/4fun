package com.pkgs.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pkgs.entity.TopicEntity;
import com.pkgs.mapper.TopicMapper;

/**
 * TODO:
 * <p>
 *
 * @author cs12110 create at: 2019/1/6 19:56
 * Since: 1.0.0
 */
@Service
public class TopicService {

    @Autowired
    private TopicMapper tpoicMapper;

    public List<TopicEntity> query(Integer pageIndex, Integer pageSize) {
        Page<TopicEntity> page = new Page<>(pageIndex, pageSize);


        return tpoicMapper.selectByMap(page, new HashMap<>(1));
    }


    public List<TopicEntity> queryTopTopics() {
        return tpoicMapper.queryTopTopics();
    }


    public List<TopicEntity> queryTopics(String parentId) {
        return tpoicMapper.queryTopics(parentId);
    }
}
