package com.pkgs.entity;

import com.alibaba.fastjson.JSON;

/**
 * 执行结果实体类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/1/19 8:38
 * <p>
 * since: 1.0.0
 */
public class ExecResult {

    private boolean success;
    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
