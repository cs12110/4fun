package com.pkgs.entity;

import com.alibaba.fastjson.JSON;

/**
 * 话题实体类
 *
 * 
 * @author cs12110 at 2018年12月10日下午9:52:00
 *
 */
public class TopicEntity {

	private Integer id;

	private Integer parentId;

	private String dataId;

	private String name;

	private String link;

	private String desc;

	private String updateTime;

	private Integer done;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
