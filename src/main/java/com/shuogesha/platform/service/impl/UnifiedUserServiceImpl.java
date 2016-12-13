package com.shuogesha.platform.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.platform.dao.UnifiedUserDao;
import com.shuogesha.platform.entity.SystemLog;
import com.shuogesha.platform.entity.UnifiedUser;
import com.shuogesha.platform.service.SystemLogService;
import com.shuogesha.platform.service.UnifiedUserService;
import com.shuogesha.platform.service.UnifiedUserTokenService;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.encoder.Md5PwdEncoder;
import com.shuogesha.platform.web.exception.BadCredentialsException;
import com.shuogesha.platform.web.exception.UsernameNotFoundException;
import com.shuogesha.platform.web.mongo.Pagination;

@Service
public class UnifiedUserServiceImpl implements UnifiedUserService {

	@Autowired
	private UnifiedUserDao dao;

	@Autowired
	private Md5PwdEncoder pwdEncoder;
	@Autowired
	private UnifiedUserTokenService unifiedUserTokenService;

	@Override
	public UnifiedUser save(String username, String password, String email,String phone,
			String ip) {
		String now = RequestUtils.getNow();
		UnifiedUser bean = new UnifiedUser();
		bean.setUsername(username);
		bean.setEmail(email);
		bean.setPhone(phone);
		bean.setPassword(pwdEncoder.encodePassword(password));
		bean.setRegisterIp(ip);
		bean.setRegisterTime(now);
		bean.setLastLoginIp(ip);
		bean.setLastLoginTime(now);
		// 不强制验证邮箱直接激活
		bean.setActivation(true);
		dao.saveEntity(bean);
		return bean;
	}

	@Override
	public UnifiedUser login(String username, String password, String ip)
			throws UsernameNotFoundException, BadCredentialsException,
			BadCredentialsException {
		UnifiedUser user = findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("username not found: "
					+ username);
		}
		if (!pwdEncoder.isPasswordValid(user.getPassword(), password)) {
			throw new BadCredentialsException("password invalid");
		}
		if (!user.isActivation()) {
			throw new BadCredentialsException("account not activated");
		}
		updateLoginInfo(user.getId(), ip);
		return user;
	}

	@Override
	public void activeLogin(UnifiedUser user, String ip) {
		updateLoginInfo(user.getId(), ip);
	}

	@Override
	public void updateLoginInfo(String uid, String ip) {
		Update update = new Update();
		update.set("lastLoginTime", new Date().getTime());
		update.set("lastLoginIp", ip);
		dao.update(update, uid);
	}

	@Override
	public UnifiedUser findByUsername(String username) {
		return dao.findByUsername(username);
	}

	@Override
	public void updatePassword(String password, String uid) {
		Update update = new Update();
		update.set("password", pwdEncoder.encodePassword(password));
		dao.update(update, uid);
	}

	@Override
	public void logout(String appid, String uid) {
		String signature = unifiedUserTokenService.encryptSignature(uid);
		unifiedUserTokenService.removeBySignature(appid, signature);
	}

	@Override
	public UnifiedUser findById(String id) {
		return dao.findById(id);
	}

	@Override
	public Pagination getPage(String name, int pageNo, int pageSize) {
		return dao.getPage(name, pageNo, pageSize);
	}

	@Override
	public void save(UnifiedUser bean) {
		bean.setPassword(pwdEncoder.encodePassword(bean.getPassword()));
		// 不强制验证邮箱直接激活
		bean.setActivation(true);
		dao.saveEntity(bean);
	}

	@Override
	public void update(UnifiedUser bean) {
		Update update = new Update();
		if (StringUtils.isNotBlank(bean.getPassword())) {
			update.set("password", pwdEncoder.encodePassword(bean.getPassword()));
		}
		update.set("realname", bean.getRealname());
		update.set("email", bean.getEmail());
		update.set("phone", bean.getPhone());
		update.set("group", bean.getGroup());
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
	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}

	@Override
	public void updateStatus(String id, boolean disable) {
		Update update = new Update();
		update.set("disabled",disable);
		dao.update(update, id);
	}
}
