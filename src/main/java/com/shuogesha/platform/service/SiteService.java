package com.shuogesha.platform.service;

import java.util.List;

import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.web.mongo.Pagination;

public interface SiteService {
	Pagination getPage(String siteId,int pageNo, int pageSize);

	Site findById(String id);

	void save(Site bean);

	void update(Site bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);
	
	List<Site> findAll();

	Site findMaster();
	
	Site findByTplSolution(String path);

	void updateCountCopyTime(String dateStr, String id);

	void updateCountClearTime(String dateStr, String id);
}