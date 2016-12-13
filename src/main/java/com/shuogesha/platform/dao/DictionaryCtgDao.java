package com.shuogesha.platform.dao;

import org.springframework.data.mongodb.core.query.Update;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.platform.entity.DictionaryCtg;

public interface DictionaryCtgDao {
	void saveEntity(DictionaryCtg bean);

	DictionaryCtg findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String name, int pageNo, int pageSize);

	void removeById(String id);

	long countByCode(String code);

	DictionaryCtg findByCode(String code);
}