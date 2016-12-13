package com.shuogesha.platform.action.admin;

import static com.shuogesha.platform.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.entity.Role;
import com.shuogesha.platform.entity.User;
import com.shuogesha.platform.service.DictionaryCtgService;
import com.shuogesha.platform.service.RoleService;
import com.shuogesha.platform.service.SiteService;
import com.shuogesha.platform.service.UserService;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.ResponseUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/user/")
public class UserAct {
	
	@RequestMapping(value = "/v_list.do")
	public String v_list(String name,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = userService.getPage(name,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		return "user/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		List<Role> roleList = roleService.getList();
		model.put("roleList", roleList);
		return "user/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object  o_save(User bean,String username, String password, String email,String[] roleIds,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		String ip = RequestUtils.getIpAddr(request);
		bean.setSite(CmsUtils.getSite(request));
		bean.setDateline(RequestUtils.getNow());
		userService.saveAdmin(bean,username,password,email,ip,roleIds);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		User user = userService.findById(id);
		model.put("bean", user);
		List<Role> roleList = roleService.getList();
		model.put("roleList", roleList);
		return "user/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object  o_update(User bean, String password, String email,String[] roleIds,String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		userService.update(bean,password,email,roleIds,bean.getId());
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		userService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	@RequestMapping(value = "/v_check_username.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = userService.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}
	
	@RequestMapping(value = "/v_chpwd.do")
	public String v_chpwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
			
		return "user/v_chpwd";
	}
	
	@RequestMapping(value = "/o_chpwd.do")
	public String o_chpwd(String oldPwd,String newPwd, HttpServletRequest request,
			HttpServletResponse response, ModelMap model){
		
		return "redirect:v_chpwd.do";
	}
	
	@Autowired
	public UserService userService;
	@Autowired
	public RoleService roleService;
	@Autowired
	public SiteService siteService;
	@Autowired
	public DictionaryCtgService dictionaryCtgService;
}
