package com.shuogesha.cms.action.admin;

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

import com.shuogesha.cms.entity.Content;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.service.ContentService;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.RequestUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/content/")
public class ContentAct {
	
	@RequestMapping(value = "/v_category.do")
	public String v_category(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		return "content/v_category";
	}

	@RequestMapping(value = "/v_list.do")
	public String v_list(Integer child,String channelId,String name, Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = contentService.getPage(name,channelId,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		model.addAttribute("channelId", channelId);
		model.addAttribute("child", child);
		return "content/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(String channelId,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.addAttribute("channel", channelService.findById(channelId));
		model.addAttribute("dateline", RequestUtils.getNow());
		return "content/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object o_save(Content bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		contentService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", contentService.findById(id));
		return "content/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object o_update(Content bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		contentService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		contentService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@Autowired
	public ContentService contentService;
	@Autowired
	public ChannelService channelService;
}
