package com.shuogesha.platform.dao;

import org.springframework.data.mongodb.core.query.Update;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.platform.entity.App;

public interface AppDao {
	void saveEntity(App bean);

	App findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String name, int pageNo, int pageSize);

	void removeById(String id);

	long countById(String appid);
}