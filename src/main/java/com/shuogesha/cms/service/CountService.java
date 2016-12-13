package com.shuogesha.cms.service;

import java.text.ParseException;

import com.shuogesha.cms.entity.Count;

import net.sf.ehcache.Ehcache;


public interface CountService {

	Count findByid(String id);

	int freshCacheToDB(Ehcache cache) throws ParseException ;

	void saveEntity(Count count);
	
	long count(String id);

	Count saveCount(String referId, String refer);

	void removeById(String string);

}
