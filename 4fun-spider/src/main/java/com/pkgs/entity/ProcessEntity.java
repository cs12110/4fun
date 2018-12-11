package com.pkgs.entity;

import com.alibaba.fastjson.JSON;

/**
 * 爬取进度
 * 
 *
 * <p>
 *
 * @author cs12110 2018年12月11日
 * @see
 * @since 1.0
 */
public class ProcessEntity {

	private Integer id;
	private Integer topicId;
	private String startAt;
	private String endAt;
	private Integer done;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTopicId() {
		return topicId;
	}
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	public String getStartAt() {
		return startAt;
	}
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	public String getEndAt() {
		return endAt;
	}
	public void setEndAt(String endAt) {
		this.endAt = endAt;
	}
	public Integer getDone() {
		return done;
	}
	public void setDone(Integer done) {
		this.done = done;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
