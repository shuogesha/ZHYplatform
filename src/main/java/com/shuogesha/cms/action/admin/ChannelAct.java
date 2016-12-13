package com.shuogesha.cms.action.admin;

import static com.shuogesha.platform.web.mongo.SimplePage.cpn;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/channel/")
public class ChannelAct {
	
	@RequestMapping(value = "/v_category.do")
	public String v_category(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		return "channel/v_category";
	}

	@RequestMapping(value = "/v_list.do")
	public String v_list(String pId, String name, Integer pageNo, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = channelService.getPage(pId,name,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("pId", pId);
		model.addAttribute("name", name);
		return "channel/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(String pId, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.addAttribute("parent", channelService.findById(pId));
		return "channel/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public @ResponseBody Object o_save(Channel bean,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		bean.setSiteId(CmsUtils.getSiteId(request));
		channelService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", channelService.findById(id));
		return "channel/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public @ResponseBody Object o_update(Channel bean, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		channelService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		channelService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	@RequestMapping(value = "/getNodes.do")
	public @ResponseBody Object getNodes(String id, String type,Integer level, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		List<Map<String,Object>> nodes = new ArrayList<>();
		Map<String, Object> node = new HashMap<String, Object>();
		if(cpn(level)<=1){
			id=null;
			node = new HashMap<String, Object>();
			node.put("id", 0);
			node.put("pId", -1);
			node.put("name", "根目录");
			node.put("open", true);
			nodes.add(node);
		}
		List<Channel> list= channelService.getChildList(id,CmsUtils.getSiteId(request));

		for (Channel channel : list) {
			node = new HashMap<String, Object>();
			node.put("id", channel.getId());
			if(channel.getParent()!=null&&StringUtils.isNotBlank(channel.getParent().getId())){
				node.put("pId", channel.getParent().getId());
			}else{
				node.put("pId", 0);
			}
			node.put("name", channel.getName());
			nodes.add(node);
		}
		return nodes;
	}
	
	@Autowired
	public ChannelService channelService;
}
