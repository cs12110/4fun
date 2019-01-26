package com.pkgs.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 句子
 * <p/>
 *
 * @author cs12110 created at: 2019/1/16 8:36
 * <p>
 * since: 1.0.0
 */
@Data
public class SentenceEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * 作者
     */
    private String author;

    /**
     * 句子
     */
    private String sentence;

    /**
     * 序号
     */
    private Integer idx;

    /**
     * 壁纸
     */
    private String wallpaper;

   
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
