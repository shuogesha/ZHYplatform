package com.shuogesha.platform.action.admin;

import static com.shuogesha.platform.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.entity.App;
import com.shuogesha.platform.service.AppService;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/app/")
public class AppAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(String name, Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = appService.getPage(name,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		return "app/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {

		return "app/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object o_save(App bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		appService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", appService.findById(id));
		return "app/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object o_update(App bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		appService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		appService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@Autowired
	public AppService appService;
}
