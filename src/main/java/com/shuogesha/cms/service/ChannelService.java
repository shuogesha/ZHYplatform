package com.shuogesha.cms.service;

import com.shuogesha.platform.web.mongo.Pagination;

import java.util.List;

import com.shuogesha.cms.entity.Channel;

public interface ChannelService {
	Pagination getPage(String pId, String name, int pageNo, int pageSize);

	Channel findById(String id);

	void save(Channel bean);

	void update(Channel bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	List<Channel> getChildList(String parentId, String siteId);

	Channel findByPath(String path);
}