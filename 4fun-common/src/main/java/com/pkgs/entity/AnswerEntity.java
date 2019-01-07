package com.pkgs.entity;

import com.alibaba.fastjson.JSON;

/**
 * 精华回答实体类
 * 
 *
 * <p>
 *
 * @author cs12110 2018年12月11日
 * @see
 * @since 1.0
 */
public class AnswerEntity {

	private Integer id;
	private Integer topicId;
	private String question;
	private String questionId;
	private String answerId;
	private String author;
	private String link;
	private Integer upvoteNum;
	private Integer commentNum;
	private String summary;
	private String createAt;
	private String updateAt;
	private String stealAt;

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

	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Integer getUpvoteNum() {
		return upvoteNum;
	}
	public void setUpvoteNum(Integer upvoteNum) {
		this.upvoteNum = upvoteNum;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getCreateAt() {
		return createAt;
	}
	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}
	public String getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getStealAt() {
		return stealAt;
	}
	public void setStealAt(String stealAt) {
		this.stealAt = stealAt;
	}
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

}
