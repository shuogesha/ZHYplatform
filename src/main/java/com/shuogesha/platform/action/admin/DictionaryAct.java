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

import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.service.DictionaryCtgService;
import com.shuogesha.platform.service.DictionaryService;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/dictionary/")
public class DictionaryAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(String name,String ctgId, Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = dictionaryService.getPage(ctgId,name,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("dictionaryCtg", dictionaryCtgService.findById(ctgId));
		model.addAttribute("ctgId", ctgId);
		return "dictionary/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(String ctgId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.addAttribute("ctgId", ctgId);
		return "dictionary/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object o_save(Dictionary bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		dictionaryService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", dictionaryService.findById(id));
		return "dictionary/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object o_update(Dictionary bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		dictionaryService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		dictionaryService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@Autowired
	public DictionaryService dictionaryService;
	@Autowired
	public DictionaryCtgService dictionaryCtgService;
}
