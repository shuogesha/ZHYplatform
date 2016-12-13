package com.shuogesha.cms.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.cms.dao.ContentDao;
import com.shuogesha.cms.entity.Content;
import com.shuogesha.platform.web.mongo.MongodbBaseDao;
@Repository
public class ContentDaoImpl extends MongodbBaseDao implements ContentDao{

	@Override
	protected Class getEntityClass() {
		return Content.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Content bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Content findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Content) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String name,String channelId, int pageNo, int pageSize) {
		Query query = new Query();
		if(StringUtils.isNotBlank(name)){
			query.addCriteria(Criteria.where("name").regex(name));
		}
		if(StringUtils.isNotBlank(channelId)){
			query.addCriteria(Criteria.where("channel.$id").is(channelId));
		}
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}
	
	
	private void appendOrder(Query query, int orderBy) {
		switch (orderBy) {
		case 0:
			// 发布时间降序
			query.with(new Sort(Sort.Direction.DESC,"dateline"));
			break;
		case 1:
			// 热门
			query.with(new Sort(Sort.Direction.DESC,"dateline"));
			break;
		}
	}

	@Override
	public Pagination getPage(String[] channelIds, int orderBy, int pageNo, int pageSize) {
		Query query = new Query();
		if(channelIds!=null){
			query.addCriteria(Criteria.where("channel.$id").in(channelIds));
		}
		appendOrder(query,0);
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public List<Content> getList(String[] channelIds, int orderBy, int pageNo, int count) {
		Query query = new Query();
		if(channelIds!=null){
			query.addCriteria(Criteria.where("channel.$id").in(channelIds));
		}
		appendOrder(query,0);
		query.skip(pageNo).limit(count);
		return mongoTemplate.find(query, getEntityClass());
	}
 

}