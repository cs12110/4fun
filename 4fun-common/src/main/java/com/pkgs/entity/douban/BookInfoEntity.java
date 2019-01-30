package com.pkgs.entity.douban;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/30 16:10
 * <p>
 * since: 1.0.0
 */
@Data
public class BookInfoEntity {

    private Integer id;
    private String name;
    private String author;
    private float score;
    private String summary;


    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
