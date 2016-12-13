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

import com.shuogesha.cms.dao.ChannelDao;
import com.shuogesha.cms.entity.Channel;
import com.shuogesha.platform.web.mongo.MongodbBaseDao;
import com.shuogesha.platform.web.mongo.Pagination;
@Repository
public class ChannelDaoImpl extends MongodbBaseDao implements ChannelDao{

	@Override
	protected Class getEntityClass() {
		return Channel.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Channel bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Channel findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Channel) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String pId, String name, int pageNo, int pageSize) {
		Query query = new Query();
		if(StringUtils.isNotBlank(pId)){
			query.addCriteria(Criteria.where("parent.$id").is(pId));
		}
		if(StringUtils.isNotBlank(name)){
			query.addCriteria(Criteria.where("name").regex(name));
		}
		query.with(new Sort(Sort.Direction.ASC,"sort"));
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}


	@Override
	public List<Channel> getChildList(String parentId, String siteId) {
		 Query query = new Query();
		 if(parentId!=null){
				query.addCriteria(Criteria.where("parent.$id").is(parentId));
		 }
		 query.with(new Sort(Sort.Direction.ASC,"sort"));
		 return mongoTemplate.find(query, getEntityClass());
	}


	@Override
	public Channel findByPath(String path) {
		Query query = new Query(Criteria.where("path").is(path));
		return (Channel) mongoTemplate.findOne(query, getEntityClass());
	}
 

}