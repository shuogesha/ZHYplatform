package com.shuogesha.platform.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.platform.entity.User;
import com.shuogesha.platform.web.mongo.Pagination;

public interface UserDao {
	void saveEntity(User bean);

	User findById(String id);

	void update(Update update, String id);

	Pagination getPage(String name,int pageNo, int pageSize);

	void removeById(String id);

	long countByUsername(String username);
}
