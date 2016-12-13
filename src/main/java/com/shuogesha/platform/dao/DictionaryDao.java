package com.shuogesha.platform.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.platform.entity.Dictionary;

public interface DictionaryDao {
	void saveEntity(Dictionary bean);

	Dictionary findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String ctgId, String name, int pageNo, int pageSize);

	void removeById(String id);

	List<Dictionary> findByCtgId(String id);

	Dictionary findByCtgAndValue(String ctgId, String value);
}