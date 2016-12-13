package com.shuogesha.platform.dao;

import java.util.Date;

import com.shuogesha.platform.entity.Authentication;


public interface AuthenticationDao {

	void removeById(String id);

	void saveEntity(Authentication bean);

	Authentication findById(String id);

	void deleteExpire(long date);


}
