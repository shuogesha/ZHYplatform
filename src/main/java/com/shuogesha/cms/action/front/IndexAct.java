package com.shuogesha.cms.action.front;

import static com.shuogesha.cms.Constants.TPLDIR_CHANNEL;
import static com.shuogesha.cms.Constants.TPLDIR_CONTENT;
import static com.shuogesha.cms.Constants.TPLDIR_INDEX;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shuogesha.cms.entity.Channel;
import com.shuogesha.cms.entity.Content;
import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.service.ContentService;
import com.shuogesha.cms.web.FrontUtils;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.web.CmsUtils;
import com.shuogesha.platform.web.URLHelper;
import com.shuogesha.platform.web.URLHelper.PageInfo;

@Controller
public class IndexAct {
	private static final Logger log = LoggerFactory.getLogger(IndexAct.class);
	public static final String TPL_INDEX = "tpl.index";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_INDEX, TPL_INDEX);
	}

	@RequestMapping(value = "/index.htm", method = RequestMethod.GET)
	public String jetty(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		return index(request,response, model);
	}
	
	@RequestMapping(value = "/a/{contentid}.htm", method = RequestMethod.GET)
	public String content(@PathVariable("contentid") String contentid,HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		Content content = contentService.findById(contentid);
		model.put("bean", content);
		FrontUtils.frontData(request, model, site);
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
				TPLDIR_CONTENT, "tpl.contentIndex");
	}
	
	@RequestMapping(value = "/**/*", method = RequestMethod.GET)
	public String dynamic(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		// 尽量不要携带太多参数，多使用标签获取数据。
		// 目前已知的需要携带翻页信息。
		// 获得页号和翻页信息吧。
		int pageNo = URLHelper.getPageNo(request);
		String[] params = URLHelper.getParams(request);
		PageInfo info = URLHelper.getPageInfo(request);
		String[] paths = URLHelper.getPaths(request);
		int len = paths.length;
		if (len == 1) {
			// 单页
			return channel(paths[0], pageNo, params, info, request, response,
					model);
		} else if (len == 2) {
			// 栏目页
			return channel(paths[1], pageNo, params, info, request,
						response, model);
		} else {
			log.debug("Illegal path length: {}, paths: {}", len, paths);
		}
		return FrontUtils.pageNotFound(request, response, model);
	}
	
	public String channel(String path, int pageNo, String[] params,
			PageInfo info, HttpServletRequest request,
			HttpServletResponse response, ModelMap model) {
		Site site = CmsUtils.getSite(request);
		Channel channel = channelService.findByPath(path);
		model.addAttribute("bean", channel);
		FrontUtils.frontData(request, model, site);
		FrontUtils.frontPageData(request, model);
		if(channel.getParent()==null){
			return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_CHANNEL, "tpl.channelIndex");
		}
		return FrontUtils.getTplPath(request, site.getSolutionPath(),
					TPLDIR_CHANNEL, "tpl.channelChild");
	}
	
	@Autowired
	public ChannelService channelService;
	@Autowired
	public ContentService contentService;
}
