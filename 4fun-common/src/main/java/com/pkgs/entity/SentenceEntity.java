package com.pkgs.entity;

import com.alibaba.fastjson.JSON;

/**
 * 句子
 * <p/>
 *
 * @author cs12110 created at: 2019/1/16 8:36
 * <p>
 * since: 1.0.0
 */
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
    private Integer order;

    /**
     * 壁纸
     */
    private String wallpaper;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
