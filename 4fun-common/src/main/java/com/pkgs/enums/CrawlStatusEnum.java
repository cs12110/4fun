package com.pkgs.enums;

/**
 * 爬取状态
 *
 * @author cs12110 create at 2019/5/10 9:50
 * @version 1.0.0
 */
public enum CrawlStatusEnum {
    /**
     * 尚未爬取状态,value=0
     */
    NOT_YET("notYet", 0, "尚未爬取"),

    /**
     * 已经爬取状态,value=1
     */
    ALREADY("already", 1, "已爬取");


    final String key;
    final int value;
    final String remark;

    CrawlStatusEnum(String key, int value, String remark) {
        this.key = key;
        this.value = value;
        this.remark = remark;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }

    public String getRemark() {
        return remark;
    }

}