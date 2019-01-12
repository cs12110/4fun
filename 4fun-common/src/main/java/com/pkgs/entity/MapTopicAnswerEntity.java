package com.pkgs.entity;

import com.alibaba.fastjson.JSON;

/**
 * 保存关系实体类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 9:42
 * <p>
 * since: 1.0.0
 */
public class MapTopicAnswerEntity {

    private Integer id;

    private Integer topicId;

    private Integer answerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
