package com.pkgs.entity;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/2/13 15:26
 * <p>
 * since: 1.0.0
 */
@Data
public class ColumnInfoEntity {

    private String name;

    private String type;

    private String remark;

    private int len;

    private boolean isPrimaryKey;

    private boolean isNullable;

    private boolean isAutoIncrement;

    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }
}
