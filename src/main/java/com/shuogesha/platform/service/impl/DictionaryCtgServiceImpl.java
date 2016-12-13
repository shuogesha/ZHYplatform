package com.shuogesha.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.platform.dao.DictionaryCtgDao;
import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.entity.DictionaryCtg;
import com.shuogesha.platform.service.DictionaryCtgService;
import com.shuogesha.platform.service.DictionaryService;
import com.shuogesha.platform.web.mongo.Pagination;


@Service
public class DictionaryCtgServiceImpl implements DictionaryCtgService{
	
	@Autowired
	private DictionaryCtgDao dao;
	@Autowired
	private DictionaryService dictionaryService;

	@Override
	public DictionaryCtg findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String name, int pageNo, int pageSize) {
		return dao.getPage(name, pageNo,pageSize);
	}

	@Override
	public void save(DictionaryCtg bean) {
		 dao.saveEntity(bean);
	}

	@Override
	public void update(DictionaryCtg bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 update.set("code", bean.getCode());
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
	public boolean codeNotExist(String code) {
		return dao.countByCode(code) <= 0;
	}

	@Override
	public List<Dictionary> findByCode(String code) {
		DictionaryCtg dictionaryCtg = dao.findByCode(code);
		List<Dictionary> dictionaries = new ArrayList<>();
		if (dictionaryCtg!=null) {
			dictionaries=dictionaryService.findByCtgId(dictionaryCtg.getId());
		}
		return dictionaries;
	}

	@Override
	public String findBycodeToName(String code, String value) {
		DictionaryCtg dictionaryCtg = dao.findByCode(code);
		String name="";
		if (dictionaryCtg!=null) {
			name=dictionaryService.findByCtgAndValue(dictionaryCtg.getId(),value).getName();
		}
		return name;
	}

}
