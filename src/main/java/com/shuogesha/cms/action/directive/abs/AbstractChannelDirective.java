package com.shuogesha.cms.action.directive.abs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.shuogesha.cms.service.ChannelService;
import com.shuogesha.cms.web.freemarker.DirectiveUtils;

import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class AbstractChannelDirective implements TemplateDirectiveModel {
	
	/**
	 * 输入参数，父栏目ID。存在时，获取该栏目的子栏目，不存在时获取顶级栏目。
	 */
	public static final String PARAM_PARENT_ID = "parentId";
	
	@Autowired
	public ChannelService channelService;
}
