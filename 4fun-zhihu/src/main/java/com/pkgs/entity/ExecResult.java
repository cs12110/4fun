package com.pkgs.entity;

import com.alibaba.fastjson.JSON;
import com.pkgs.enums.OperationEnum;
import lombok.Data;

/**
 * 执行结果实体类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/19 8:38
 * <p>
 * since: 1.0.0
 */
@Data
public class ExecResult {

    /**
     * 操作是否成功
     */
    private boolean success;

    /**
     * 操作类型
     */
    private OperationEnum op;

    /**
     * 相关消息
     */
    private String msg;


    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }
}
