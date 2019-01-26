package com.pkgs.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 保存关系实体类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 9:42
 * <p>
 * since: 1.0.0
 */
@Data
public class MapTopicAnswerEntity {

    private Integer id;

    private Integer topicId;

    private Integer answerId;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
