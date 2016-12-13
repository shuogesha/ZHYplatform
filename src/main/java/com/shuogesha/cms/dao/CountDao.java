package com.shuogesha.cms.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import net.sf.ehcache.Ehcache;

import com.shuogesha.cms.entity.Count;

public interface CountDao {
	
	Count findByid(String id);

	Count findByRefer(String referId, String refer);

	int freshCacheToDB(Ehcache cache);

	void saveEntity(Count count);

	int clearCount(boolean week, boolean month);

	void update(Update update, String id);

	long count(String id);

	long count(String referId, String refer);

	String[] findIds(int orderBy,int count, String refer);

	int copyCount();

	void removeById(String id);

}
