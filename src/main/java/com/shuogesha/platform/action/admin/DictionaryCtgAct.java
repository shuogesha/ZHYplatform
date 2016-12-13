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
import com.shuogesha.platform.entity.DictionaryCtg;
import com.shuogesha.platform.service.DictionaryCtgService;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.ResponseUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/dictionaryCtg/")
public class DictionaryCtgAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(String name, Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = dictionaryCtgService.getPage(name,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		return "dictionaryCtg/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {

		return "dictionaryCtg/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object o_save(DictionaryCtg bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		dictionaryCtgService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", dictionaryCtgService.findById(id));
		return "dictionaryCtg/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object o_update(DictionaryCtg bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		dictionaryCtgService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		dictionaryCtgService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@RequestMapping(value = "/v_check_code.do")
	public void checkUsername(HttpServletRequest request, HttpServletResponse response) {
		String code=RequestUtils.getQueryParam(request,"code");
		String pass;
		if (StringUtils.isBlank(code)) {
			pass = "false";
		} else {
			pass = dictionaryCtgService.codeNotExist(code) ? "true" : "false";
		}
		ResponseUtils.renderJson(response, pass);
	}
	
	
	@Autowired
	public DictionaryCtgService dictionaryCtgService;
}
