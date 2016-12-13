package com.shuogesha.cms.service;

import com.shuogesha.platform.web.mongo.Pagination;

import java.util.List;

import com.shuogesha.cms.entity.Content;

public interface ContentService {
	Pagination getPage(String name,String channelId, int pageNo, int pageSize);

	Content findById(String id);

	void save(Content bean);

	void update(Content bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	Pagination getPage(String[] channelIds, int orderBy, int pageNo, int count);

	List<Content> getList(String[] channelIds, int orderBy, int pageNo, int count);
}