package com.shuogesha.cms.action.directive.abs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.shuogesha.cms.service.ContentService;
import com.shuogesha.cms.web.FrontUtils;
import com.shuogesha.cms.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class AbstractContentDirective implements TemplateDirectiveModel {
	
	 
	/**
	 * 输入参数，排序方式。
	 * <ul>
	 * <li>0：默认降序
	 * <li>1：默认升序
	 * </ul>
	 */
	public static final String PARAM_ORDER_BY = "orderBy";
	
	public static final String PARAM_CHANNELID = "channelId";
	
	protected int getOrderBy(Map<String, TemplateModel> params)
			throws TemplateException {
		Integer orderBy = DirectiveUtils.getInt(PARAM_ORDER_BY, params);
		if (orderBy == null) {
			return 0;
		} else {
			return orderBy;
		}
	}
	
	protected String[] getChannelIds(Map<String, TemplateModel> params)
			throws TemplateException {
		String[] channelIds = DirectiveUtils.getStringArray(PARAM_CHANNELID, params);
		return channelIds;
	}
	protected Object getData(Map<String, TemplateModel> params, Environment env)
			throws TemplateException {
		int orderBy = getOrderBy(params);
		int count = FrontUtils.getCount(params);
		String[]  channelIds = getChannelIds(params);
		int pageNo = 1;
		if (isPage()) {
			pageNo = FrontUtils.getPageNo(env);
			return contentService.getPage(channelIds,orderBy,pageNo, count);
		}else {
			int first = FrontUtils.getFirst(params);
			return contentService.getList(channelIds, orderBy, first, count);
		}
	}
	
	abstract protected boolean isPage();
	
	@Autowired
	public ContentService contentService;
}
