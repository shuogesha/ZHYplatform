package com.shuogesha.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.cms.dao.ContentDao;
import com.shuogesha.cms.entity.Content;
import com.shuogesha.cms.entity.Count;
import com.shuogesha.cms.service.ContentService;
import com.shuogesha.cms.service.CountService;
import com.shuogesha.platform.web.mongo.Pagination;


@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private ContentDao dao;
	@Autowired
	private CountService countService;

	@Override
	public Content findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String name,String channelId,int pageNo, int pageSize) {
		return dao.getPage(name,channelId, pageNo,pageSize);
	}

	@Override
	public void save(Content bean) {
		 dao.saveEntity(bean);
		 saveCount(bean.getId(),Content.class.getSimpleName());
	}
	
	public void saveCount(String referId,String refer) {
		if (countService.count(referId)<=0) {
			Count count = countService.saveCount(referId,refer);
			Update update = new Update();
			update.set("count", count);
			dao.update(update,referId);
		}
	}

	@Override
	public void update(Content bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 update.set("description", bean.getDescription());
		 update.set("dateline", bean.getDateline());
		 update.set("content", bean.getContent());
		 update.set("source", bean.getSource());
		 update.set("sourceUrl", bean.getSourceUrl());
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
			countService.removeById(ids[i]);
		}
	}

	@Override
	public Pagination getPage(String[] channelIds, int orderBy, int pageNo, int count) {
		return dao.getPage(channelIds,orderBy,pageNo,count);
	}

	@Override
	public List<Content> getList(String[] channelIds, int orderBy, int pageNo, int count) {
		return dao.getList(channelIds,orderBy,pageNo,count);
	}

}
