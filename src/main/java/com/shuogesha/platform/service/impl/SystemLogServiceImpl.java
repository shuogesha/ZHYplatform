package com.shuogesha.platform.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.platform.dao.SystemLogDao;
import com.shuogesha.platform.entity.SystemLog;
import com.shuogesha.platform.entity.User;
import com.shuogesha.platform.service.SystemLogService;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.mongo.Pagination;


@Service
public class SystemLogServiceImpl implements SystemLogService{
	
	@Autowired
	private SystemLogDao dao;

	@Override
	public SystemLog findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage( String name,int pageNo, int pageSize) {
		return dao.getPage(name, pageNo,pageSize);
	}

	@Override
	public void save(SystemLog bean) {
		 dao.saveEntity(bean);
	}

	@Override
	public void update(SystemLog bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
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
	public void log(String uid, String content, String ip, String from) {
		SystemLog bean = new SystemLog();
		bean.setContent(content);
		User user = new User();
		user.setId(uid);
		bean.setUser(user);
		bean.setDateline(RequestUtils.getNow());
		bean.setIp(ip);
		bean.setFrom(from);
		dao.saveEntity(bean);
	}

}
