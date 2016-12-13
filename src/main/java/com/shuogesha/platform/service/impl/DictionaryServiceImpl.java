package com.shuogesha.platform.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.platform.dao.DictionaryDao;
import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.service.DictionaryService;
import com.shuogesha.platform.web.mongo.Pagination;


@Service
public class DictionaryServiceImpl implements DictionaryService{
	
	@Autowired
	private DictionaryDao dao;

	@Override
	public Dictionary findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String ctgId, String name,int pageNo, int pageSize) {
		return dao.getPage(ctgId, name, pageNo,pageSize);
	}

	@Override
	public void save(Dictionary bean) {
		 dao.saveEntity(bean);
	}

	@Override
	public void update(Dictionary bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 update.set("value", bean.getValue());
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
	public List<Dictionary> findByCtgId(String id) {
		return dao.findByCtgId(id);
	}

	@Override
	public Dictionary findByCtgAndValue(String ctgId, String value) {
		return dao.findByCtgAndValue(ctgId,value);
	}

}
