package com.shuogesha.platform.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Site {
	@Id
	private String id;
	private String name;
	private String tongjiCode;
	private String description;
	private java.lang.String imageUrl;
	private java.lang.String tplSolution;
	private java.lang.String solutionPath;
	
	private java.lang.String countClearTime;
	private java.lang.String countCopyTime;

	@DBRef
	private Site parent; //父

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public java.lang.String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Site getParent() {
		return parent;
	}

	public void setParent(Site parent) {
		this.parent = parent;
	}

	public String getTongjiCode() {
		return tongjiCode;
	}

	public void setTongjiCode(String tongjiCode) {
		this.tongjiCode = tongjiCode;
	}

	public java.lang.String getTplSolution() {
		return tplSolution;
	}

	public void setTplSolution(java.lang.String tplSolution) {
		this.tplSolution = tplSolution;
	}

	public java.lang.String getSolutionPath() {
		return "/WEB-INF/template/front/"+ tplSolution;
	}

	public void setSolutionPath(java.lang.String solutionPath) {
		this.solutionPath = solutionPath;
	}

	public java.lang.String getCountClearTime() {
		return countClearTime;
	}

	public void setCountClearTime(java.lang.String countClearTime) {
		this.countClearTime = countClearTime;
	}

	public java.lang.String getCountCopyTime() {
		return countCopyTime;
	}

	public void setCountCopyTime(java.lang.String countCopyTime) {
		this.countCopyTime = countCopyTime;
	}
	
}
