package com.shuogesha.platform.service;

import com.shuogesha.platform.entity.UnifiedUser;
import com.shuogesha.platform.web.exception.BadCredentialsException;
import com.shuogesha.platform.web.exception.UsernameNotFoundException;
import com.shuogesha.platform.web.mongo.Pagination;

public interface UnifiedUserService {
	UnifiedUser login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException,
			BadCredentialsException;

	public UnifiedUser findByUsername(String username);

	void activeLogin(UnifiedUser unifiedUser, String ip);

	void updateLoginInfo(String uid, String ip);

	UnifiedUser save(String username, String password, String email,String phone, String ip);

	void updatePassword(String password, String uid);

	void logout(String appid, String uid);

	Pagination getPage(String name, int pageNo, int pageSize);

	UnifiedUser findById(String id);

	void save(UnifiedUser bean);

	void update(UnifiedUser bean);

	void removeById(String id);

	void removeByIds(String[] ids);

	boolean usernameNotExist(String username);

	void updateStatus(String id, boolean disable);
}