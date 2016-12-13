package com.shuogesha.platform.action.admin;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shuogesha.platform.entity.Role;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.service.RoleService;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.CookieUtils;
import com.shuogesha.platform.web.mongo.Pagination;

@Controller
@RequestMapping("/role/")
public class RoleAct {

	@RequestMapping(value = "/v_list.do")
	public String v_list(Integer pageNo, String name, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Pagination pagination = roleService.getPage(name,cpn(pageNo),
				CookieUtils.getPageSize(request));
		model.addAttribute("pagination", pagination);
		model.addAttribute("name", name);
		return "role/v_list";
	}

	@RequestMapping(value = "/v_add.do")
	public String v_add(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		return "role/v_add";
	}

	@RequestMapping(value = "/o_save.do", method = RequestMethod.POST)
	public  @ResponseBody Object  o_save(Role bean,String[] perms,
			HttpServletRequest request, HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		Site site = CmsUtils.getSite(request);
		bean.setPerms(splitPerms(perms));
		roleService.save(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/v_edit.do")
	public String v_edit(String id, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("bean", roleService.findById(id));
		return "role/v_edit";
	}

	@RequestMapping(value = "/o_update.do", method = RequestMethod.POST)
	public  @ResponseBody Object  o_update(Role bean, String[] perms, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		bean.setPerms(splitPerms(perms));
		roleService.update(bean);
		Map<String, Object> re = new HashMap<String, Object>();
		return re;
	}

	@RequestMapping(value = "/o_delete.do")
	public String o_delete(String[] ids, HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		roleService.removeByIds(ids);
		return "redirect:v_list.do";
	}
	
	private List<String> splitPerms(String[] perms) {
		List<String> set = new ArrayList<String>();
		if (perms != null) {
			for (String perm : perms) {
				for (String p : StringUtils.split(perm, ',')) {
					if (!StringUtils.isBlank(p)) {
						set.add(p);
					}
				}
			}
		}
		return set;
	}
	
	@Autowired
	public RoleService roleService;
}
