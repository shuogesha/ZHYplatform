package com.shuogesha.cms.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.shuogesha.cms.dao.CountDao;
import com.shuogesha.cms.entity.Count;
import com.shuogesha.platform.web.mongo.MongodbBaseDao;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Repository
public class CountDaoImpl extends MongodbBaseDao implements CountDao {

	@Autowired
	@Qualifier("mongoTemplate")
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		super.mongoTemplate = mongoTemplate;
	}

	@Override
	protected Class getEntityClass() {
		return Count.class;
	}

	@Override
	public Count findByid(String Threadid) {
		Query query = new Query(Criteria.where("_id").is(Threadid));
		return (Count) mongoTemplate.findOne(query, getEntityClass());
	}

	@Override
	public int freshCacheToDB(Ehcache cache) {
		List<String> keys = cache.getKeys();
		if (keys.size() <= 0) {
			return 0;
		}
		Element e;
		Integer views;
		int i = 0;
		for (String id : keys) {
			e = cache.get(id);
			if (e != null) {
				views = (Integer) e.getValue();
				if (views != null) {
					Query query = new Query();
					Update update = new Update();
					update.inc("views", views);
					update.inc("viewsday", views);
					update.inc("viewsweek", views);
					update.inc("viewsmonth", views);
					query.addCriteria(Criteria.where("_id").is(id));
					mongoTemplate.updateMulti(query, update, getEntityClass());
					Count count = (Count) mongoTemplate.findOne(query,
							getEntityClass());
					if(count!=null){
						i++;
						//开始更新原集合数据
						 query = new Query(Criteria.where("_id").is(count.getReferId()));
			        	 update = new Update();
			      		 update.set("views", count.getViews());
			             mongoTemplate.updateFirst(query, update, (count.getRefer()).toLowerCase());
					}
				}
			}
		}
		return i;
	}

	@Override
	public void saveEntity(Count count) {
		mongoTemplate.save(count);
	}

	@Override
	public int clearCount(boolean week, boolean month) {
		Query query = new Query();
		Update update = new Update();
		update.set("viewsday", 0);
		if (week) {
			update.set("viewsweek", 0);
		}
		if (month) {
			update.set("viewsmonth", 0);
		}
		mongoTemplate.updateMulti(query, update, getEntityClass());
		return 0;
	}
	
	@Override
	public int copyCount() {
		//2016年7月23日 此方法已经被废弃
//		Query query = new Query(Criteria.where("refer").in(new String[]{"Topic","Thread","Media","Person","News"}));
//		mongoTemplate.executeQuery(query,"count",new DocumentCallbackHandler() {
//           //处理自己的逻辑，这种为了有特殊需要功能的留的开放接口命令模式
//           public void processDocument(DBObject dbObject) throws MongoException,
//                  DataAccessException {
//        	   System.out.println(dbObject.toString());
//        	  Query query = new Query(Criteria.where("_id").is(dbObject.get("referId")));
//        	  Update update = new Update();
//      		  update.set("views", dbObject.get("views"));
//              mongoTemplate.updateFirst(query, update, ((String)dbObject.get("refer")).toLowerCase());
//           }
//
//        });
		return 0;
	}

	@Override
	public void update(Update update, String topicid) {
		Query query = new Query(Criteria.where("_id").is(topicid));
		mongoTemplate.updateFirst(query, update, getEntityClass());
	}
	
	@Override
	public long count(String topicid) {
		Query query = new Query(Criteria.where("_id").is(topicid));
		return mongoTemplate.count(query, getEntityClass());
	}

	@Override
	public long count(String referId, String refer) {
		Query query = new Query(Criteria.where("referId").is(referId).and("refer").is(refer));
		return mongoTemplate.count(query, getEntityClass());
	}

	@Override
	public Count findByRefer(String referId, String refer) {
		Query query = new Query(Criteria.where("referId").is(referId).and("refer").is(refer));
		return (Count) mongoTemplate.findOne(query, getEntityClass());
	}
	
	private void appendOrder(Query query, int orderBy) {
		switch (orderBy) {
		case 0:
			// 文章数量
			query.with(new Sort(Sort.Direction.DESC,"threads"));
			break;
		}
		
	}
	@Override
	public String[] findIds(int orderBy,int count, String refer) {
		Query query = new Query();
		if(StringUtils.isNotBlank(refer)){
			query.addCriteria(Criteria.where("refer").is(refer));
		}
		appendOrder(query,orderBy);
		query.skip(0);// skip相当于从那条记录开始
		query.limit(count);
		List<Count> list= mongoTemplate.find(query, getEntityClass());
		String[] ids = new String[]{};
		if(list!=null){
			ids = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ids[i]=list.get(i).getReferId();
			}
		}
		return ids;
	}
	
	

	@Override
	public void removeById(String id) {
		Query query = new Query(Criteria.where("_id").is(id));
		mongoTemplate.remove(query, getEntityClass());
	}

}
