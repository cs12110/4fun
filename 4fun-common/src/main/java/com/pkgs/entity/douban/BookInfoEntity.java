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

    private Float rating;
    private Integer ratingSum;
    private Float price;

    private String isbn;
    private String name;
    private String author;
    private String summary;
    private String publish;
    private String paperNum;
    private String translator;


    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }
}
