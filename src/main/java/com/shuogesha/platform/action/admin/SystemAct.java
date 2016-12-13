package com.shuogesha.platform.action.admin;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.service.SiteService;
import com.shuogesha.platform.web.CmsUtils;

@Controller
@RequestMapping("/system/")
public class SystemAct {

	@RequestMapping(value = "/v_config.do")
	public String v_config(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Site site =CmsUtils.getSite(request);
		model.put("bean", site);
		return "system/v_config";
	}
	
	@RequestMapping(value = "/o_config_update.do", method = RequestMethod.POST)
	public void o_config_update(Site bean,HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		Site site =CmsUtils.getSite(request);
		bean.setId(site.getId());
		siteService.update(bean);
	}
	
	@Autowired
	public SiteService siteService;
}
