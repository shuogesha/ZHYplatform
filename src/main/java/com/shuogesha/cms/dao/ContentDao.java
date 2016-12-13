package com.shuogesha.cms.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.cms.entity.Content;

public interface ContentDao {
	void saveEntity(Content bean);

	Content findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String name,String channelId, int pageNo, int pageSize);

	void removeById(String id);

	Pagination getPage(String[] channelIds, int orderBy, int pageNo, int count);

	List<Content> getList(String[] channelIds, int orderBy, int pageNo, int count);
}