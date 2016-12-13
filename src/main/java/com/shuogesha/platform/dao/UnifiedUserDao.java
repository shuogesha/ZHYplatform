package com.shuogesha.platform.dao;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.platform.entity.UnifiedUser;
import com.shuogesha.platform.web.mongo.Pagination;

public interface UnifiedUserDao {
	void saveEntity(UnifiedUser bean);

	UnifiedUser findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String name, int pageNo, int pageSize);

	UnifiedUser findByUsername(String username);

	void removeById(String id);

	long countByUsername(String username);
}