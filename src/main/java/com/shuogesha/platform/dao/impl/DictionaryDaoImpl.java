package com.shuogesha.platform.dao.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.shuogesha.platform.web.mongo.Pagination;

import com.shuogesha.platform.dao.DictionaryDao;
import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.web.mongo.MongodbBaseDao;
@Repository
public class DictionaryDaoImpl extends MongodbBaseDao implements DictionaryDao{

	@Override
	protected Class getEntityClass() {
		return Dictionary.class;
	}
	

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}


	@Override
	public void saveEntity(Dictionary bean) {
		bean.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		mongoTemplate.save(bean);
	}
	
	@Override
	public Dictionary findById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		return (Dictionary) mongoTemplate.findOne(query, getEntityClass());
	}


	@Override
	public void update(Update update, String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public Pagination getPage(String ctgId, String name, int pageNo, int pageSize) {
		Query query = new Query();
		query.addCriteria(Criteria.where("ctgId").is(ctgId));
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
	public List<Dictionary> findByCtgId(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("ctgId").is(id));
		return mongoTemplate.find(query, getEntityClass());
	}


	@Override
	public Dictionary findByCtgAndValue(String ctgId, String value) {
		Query query = new Query();
		query.addCriteria(Criteria.where("ctgId").is(ctgId));
		query.addCriteria(Criteria.where("value").is(value));
		return mongoTemplate.findOne(query, getEntityClass());
	}
 

}