package com.shuogesha.platform.dao;

import com.shuogesha.platform.entity.UnifiedUserToken;

public interface UnifiedUserTokenDao {

	long count(String appid, String encryptSignature);

	void saveEntity(UnifiedUserToken memberToken);

	UnifiedUserToken findBySignature(String appid,String encryptSignature);

	void removeBySignature(String appid,String encryptSignature);
 
}
