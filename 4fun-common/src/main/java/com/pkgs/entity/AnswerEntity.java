package com.pkgs.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 精华回答实体类
 *
 *
 * <p>
 *
 * @author cs12110 2018年12月11日
 * @since 1.0
 */
@Data
public class AnswerEntity {

    private Integer id;
    private Integer topicId;
    private String question;
    private String questionId;
    private String answerId;
    private String author;
    private String link;
    private Integer upvoteNum;
    private Integer commentNum;
    private String summary;
    private String createAt;
    private String updateAt;
    private String stealAt;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
