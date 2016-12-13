package com.shuogesha.cms.action.front;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.service.CountCacheService;
import com.shuogesha.cms.service.CountService;
import com.shuogesha.platform.web.ResponseUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

@Controller
public class CountAct {
	private static final Logger log = LoggerFactory.getLogger(CountAct.class);
	
	@RequestMapping(value = "/count_view.jhtml", method = RequestMethod.GET)
	public void view(String id, HttpServletRequest request,
			HttpServletResponse response) throws JSONException, ParseException {
		if (StringUtils.isBlank(id)) {
			ResponseUtils.renderJson(response, "[]");
			return;
		}
		int[] counts = countCacheService.viewAndGet(id);
		String json;
		if (counts != null) {
			json = JSONArray.fromObject(counts).toString();
			ResponseUtils.renderJson(response, json);
		} else {
			ResponseUtils.renderJson(response, "[]");
		}
	}
	 
	
	
	@Autowired
	public CountCacheService countCacheService;
	@Autowired
	public CountService countService;
	
}
