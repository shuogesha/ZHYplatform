package com.shuogesha.platform.action.admin;

import static com.shuogesha.platform.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
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

import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.entity.UnifiedUser;
import com.shuogesha.platform.service.UnifiedUserService;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.ResponseUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/unifiedUser/")
public class UnifiedUserAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(String name,Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = unifiedUserService.getPage(name, cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		return "unifiedUser/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {

		return "unifiedUser/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object  o_save(UnifiedUser bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		String now = RequestUtils.getNow();
		bean.setRegisterIp(RequestUtils.getIpAddr(request));
		bean.setRegisterTime(now);
		unifiedUserService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", unifiedUserService.findById(id));
		return "unifiedUser/v_edit";
	}
	
	@RequestMapping(value = "/v_show.do")
	public String v_show(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", unifiedUserService.findById(id));
		return "unifiedUser/v_show";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object  o_update(UnifiedUser bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		unifiedUserService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		unifiedUserService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@RequestMapping(value = "/v_check_username.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String username=RequestUtils.getQueryParam(request,"username");
		String pass;
		if (StringUtils.isBlank(username)) {
			pass = "false";
		} else {
			pass = unifiedUserService.usernameNotExist(username) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}
	
	@RequestMapping(value = "/o_user_enable.do", method = RequestMethod.POST)
	public @ResponseBody Object  o_user_enable(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		unifiedUserService.updateStatus(id,false);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}
	
	@RequestMapping(value = "/o_user_disable.do", method = RequestMethod.POST)
	public @ResponseBody Object  o_user_disable(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		unifiedUserService.updateStatus(id,true);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}
	
	@Autowired
	public UnifiedUserService unifiedUserService;
}
