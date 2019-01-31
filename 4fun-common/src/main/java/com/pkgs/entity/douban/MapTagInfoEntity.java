package com.pkgs.entity.douban;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 13:37
 * <p>
 * since: 1.0.0
 */
@Data
public class MapTagInfoEntity {

    private Integer tagId;

    private Integer bookId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
