package com.shuogesha.platform.service;

import java.util.List;

import com.shuogesha.platform.entity.Role;
import com.shuogesha.platform.web.mongo.Pagination;

import net.sf.json.JSONArray;

public interface RoleService {
	Pagination getPage(String name, int pageNo, int pageSize);

	Role findById(String id);

	void save(Role bean);

	void update(Role bean);

	void removeById(String id);
	
	void removeByIds(String[] ids);

	List<Role> getList();

	List<String> getPerms(List<Role> roles);
}