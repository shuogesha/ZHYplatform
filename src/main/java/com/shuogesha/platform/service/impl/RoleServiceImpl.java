package com.shuogesha.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.platform.dao.RoleDao;
import com.shuogesha.platform.entity.Role;
import com.shuogesha.platform.service.RoleService;
import com.shuogesha.platform.web.mongo.Pagination;


@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleDao dao;

	@Override
	public Role findById(String id) {
		return dao.findById(id);
	}
	
	@Override
	public Pagination getPage(String name, int pageNo, int pageSize) {
		return dao.getPage(name,pageNo,pageSize);
	}

	@Override
	public void save(Role bean) {
		 if(bean.isAllPerms()){
			 bean.setPerms(null);
		 }
		 dao.saveEntity(bean);
	}

	@Override
	public void update(Role bean) {
		 Update update = new Update();
		 update.set("name", bean.getName());
		 update.set("perms", bean.getPerms());
		 update.set("description", bean.getDescription());
		 if (bean.isAllPerms()) {
			 update.set("perms", null);
		 }
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
	public List<Role> getList() {
		return dao.findAll();
	}
	
	@Override
	public List<String> getPerms(List<Role> roles) {
		if(roles==null){
			return null;
		}
		List<String> jsonArray = new ArrayList<String>();
		for (Role role : roles) {
			if (role!=null&&role.getPerms()!=null) {
				List<String> perms= role.getPerms();
				jsonArray.addAll(perms);
			}
		}
		return jsonArray;
	}

}
