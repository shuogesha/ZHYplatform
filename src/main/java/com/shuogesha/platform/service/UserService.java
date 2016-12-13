package com.shuogesha.platform.service;

import java.util.List;

import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.entity.User;
import com.shuogesha.platform.web.mongo.Pagination;

public interface UserService {

	User findById(String uid);
	
	void saveAdmin(User bean,String username, String password, String email,String ip,String[] roleIds);

	void removeByIds(String[] ids);
	
	void removeById(String id);

	void update(User bean,String password, String email,String[] roleIds,String uid);

	boolean usernameNotExist(String username);

	Pagination getPage(String name,int pageNo, int pageSize);

}
