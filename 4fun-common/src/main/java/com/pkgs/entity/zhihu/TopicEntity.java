package com.pkgs.entity.zhihu;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * 话题实体类
 *
 * @author cs12110 at 2018年12月10日下午9:52:00
 */
@Data
public class TopicEntity {

    private Integer id;

    private Integer parentId;

    private String dataId;

    private String name;

    private String link;

    private String desc;

    private String updateTime;

    private Integer done;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
