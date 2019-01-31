package com.pkgs.entity.douban;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 书籍类型
 * <p/>
 *
 * @author cs12110 created at: 2019/1/30 16:01
 * <p>
 * since: 1.0.0
 */
@Data
public class BookTypeEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 书籍数量
     */
    private Integer books;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
