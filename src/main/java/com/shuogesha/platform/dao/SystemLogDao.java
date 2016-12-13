package com.shuogesha.platform.dao;

import org.springframework.data.mongodb.core.query.Update;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.platform.entity.SystemLog;

public interface SystemLogDao {
	void saveEntity(SystemLog bean);

	SystemLog findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String name, int pageNo, int pageSize);

	void removeById(String id);
}