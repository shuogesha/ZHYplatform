package com.shuogesha.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.shuogesha.platform.dao.UserDao;
import com.shuogesha.platform.entity.Role;
import com.shuogesha.platform.entity.UnifiedUser;
import com.shuogesha.platform.entity.User;
import com.shuogesha.platform.service.UnifiedUserService;
import com.shuogesha.platform.service.UserService;
import com.shuogesha.platform.web.mongo.Pagination;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private UnifiedUserService unifiedUserService;

	@Override
	public User findById(String uid) {
		return dao.findById(uid);
	}

	@Override
	public void saveAdmin(User user,String username, String password, String email,String ip,
			String[] roleIds) {
		// 添加統一会员信息
		UnifiedUser bean = unifiedUserService.save(username, password, email,user.getPhone(),
				ip);
		user.setUsername(username);
		user.setEmail(email);
		user.setId(bean.getId());
		user.setUnifiedUser(bean);
		List<Role> roles = new ArrayList<>();
		if (roleIds != null) {
			for (int i = 0; i < roleIds.length; i++) {
				Role role = new Role();
				role.setId(roleIds[i]);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		dao.saveEntity(user);
	}

	@Override
	public Pagination getPage(String name,int pageNo, int pageSize) {
		return dao.getPage(name,pageNo, pageSize);
	}

	@Override
	public void removeByIds(String[] ids) {
		for (int i = 0, len = ids.length; i < len; i++) {
			removeById(ids[i]);
		}
	}

	@Override
	public void removeById(String id) {
		unifiedUserService.removeById(id);
		dao.removeById(id);
	}

	@Override
	public void update(User user,String password, String email, String[] roleIds, String id) {
		if (StringUtils.isNotBlank(password)) {
			unifiedUserService.updatePassword(password, id);
		}
		Update update = new Update();
		update.set("realname", user.getRealname());
		update.set("email", email);
		update.set("step", user.getStep());
		update.set("phone", user.getPhone());
		update.set("realname", user.getRealname());
		update.set("remark", user.getRemark());
		update.set("selfAdmin", user.isSelfAdmin());
		List<Map<String, Object>> roles= new ArrayList<>();
		if (roleIds != null) {
			for (int i = 0; i < roleIds.length; i++) {
				Map<String, Object> role = new HashMap<>();
				role.put("$id", roleIds[i]);
				role.put("$ref", "role");
				roles.add(role);
			}
		}
		update.set("roles", roles);
		dao.update(update, id);
	}

	@Override
	public boolean usernameNotExist(String username) {
		return dao.countByUsername(username) <= 0;
	}
}
