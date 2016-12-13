package com.shuogesha.cms.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.ChannelDao;
import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.platform.web.mongo.Pagination;


@Service
public class ChannelServiceImpl implements ChannelService{
	
	@Autowired
	private ChannelDao dao;

	@Override
	public Channel findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String pId, String name,int pageNo, int pageSize) {
		return dao.getPage(pId, name, pageNo,pageSize);
	}

	@Override
	public void save(Channel bean) {
		 dao.saveEntity(bean);
	}

	@Override
	public void update(Channel bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 update.set("path", bean.getPath());
		 update.set("sort", bean.getSort());
		 dao.update(update, bean.getId());
	}

	@Override
	public void removeById(String id) {
		dao.removeById(id);
	}

	@Override
	public void removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			removeById(ids[i]);
		}
	}

	@Override
	public List<Channel> getChildList(String parentId, String siteId) {
		return dao.getChildList(parentId,siteId);
	}

	@Override
	public Channel findByPath(String path) {
		return dao.findByPath(path);
	}

}
