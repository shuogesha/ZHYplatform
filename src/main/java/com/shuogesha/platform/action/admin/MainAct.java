package com.shuogesha.platform.action.admin;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shuogesha.platform.entity.User;
import com.shuogesha.platform.web.CmsUtils;

@Controller
public class MainAct {

	@RequestMapping(value = "/index.do")
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("user", CmsUtils.getUser(request));
		model.put("site", CmsUtils.getSite(request));
		return "index";
	}
	
	@RequestMapping(value = "/welcome.do")
	public String welcome(HttpServletRequest request,
			HttpServletResponse response, ModelMap model)
			throws UnsupportedEncodingException {
		model.put("site", CmsUtils.getSite(request));
		return "welcome";
	}

}
