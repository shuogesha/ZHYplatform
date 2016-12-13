package com.shuogesha.platform.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Update;

import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.web.mongo.Pagination;

public interface SiteDao {
	void saveEntity(Site bean);

	Site findById(String id);

	void update(Update update, String id);
	
	Pagination getPage(String siteId,int pageNo, int pageSize);

	void removeById(String id);
	
	List<Site> findAll();

	Site findMaster();

	Site findByTplSolution(String path);
}