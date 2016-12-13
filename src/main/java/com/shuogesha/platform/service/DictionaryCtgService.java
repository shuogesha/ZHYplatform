package com.shuogesha.platform.service;

import com.shuogesha.platform.web.mongo.Pagination;

import java.util.List;

import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.entity.DictionaryCtg;

public interface DictionaryCtgService {
	Pagination getPage(String name, int pageNo, int pageSize);

	DictionaryCtg findById(String id);

	void save(DictionaryCtg bean);

	void update(DictionaryCtg bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	boolean codeNotExist(String code);

	List<Dictionary> findByCode(String code);

	String findBycodeToName(String code, String value);
}