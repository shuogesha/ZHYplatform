package com.shuogesha.cms.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Count {
	@Id
	private String id;
	public java.lang.String referId;
	public java.lang.String refer;
	public int views;//总访问量
	public int viewsmonth;
	public int viewsweek;
	public int viewsday;
	public int comments;
	public int commentsmonth;
	public int commentsweek;
	public int commentsday;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public java.lang.String getReferId() {
		return referId;
	}
	public void setReferId(java.lang.String referId) {
		this.referId = referId;
	}
	public java.lang.String getRefer() {
		return refer;
	}
	public void setRefer(java.lang.String refer) {
		this.refer = refer;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getViewsmonth() {
		return viewsmonth;
	}
	public void setViewsmonth(int viewsmonth) {
		this.viewsmonth = viewsmonth;
	}
	public int getViewsweek() {
		return viewsweek;
	}
	public void setViewsweek(int viewsweek) {
		this.viewsweek = viewsweek;
	}
	public int getViewsday() {
		return viewsday;
	}
	public void setViewsday(int viewsday) {
		this.viewsday = viewsday;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public int getCommentsmonth() {
		return commentsmonth;
	}
	public void setCommentsmonth(int commentsmonth) {
		this.commentsmonth = commentsmonth;
	}
	public int getCommentsweek() {
		return commentsweek;
	}
	public void setCommentsweek(int commentsweek) {
		this.commentsweek = commentsweek;
	}
	public int getCommentsday() {
		return commentsday;
	}
	public void setCommentsday(int commentsday) {
		this.commentsday = commentsday;
	}
	
	
}
