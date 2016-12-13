package com.shuogesha.platform.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.platform.dao.AppDao;
import com.shuogesha.platform.entity.App;
import com.shuogesha.platform.web.mongo.MongodbBaseDao;
@Repository
public class AppDaoImpl extends MongodbBaseDao implements AppDao{

	@Override
	protected Class getEntityClass() {
		return App.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(App bean) {
		mongoTemplate.save(bean);
	}
	
	@Override
	public App findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (App) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String name, int pageNo, int pageSize) {
		Query query = new Query();
		if(StringUtils.isNotBlank(name)){
			query.addCriteria(Criteria.where("name").regex(name));
		}
		return getPage(pageNo, pageSize, query);
	}


	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}


	@Override
	public long countById(String appid) {
		Query query = new Query(Criteria.where("_id").is(appid));
		return mongoTemplate.count(query, getEntityClass());
	}
 

}