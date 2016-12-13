package com.shuogesha.cms.action.directive;

import static com.shuogesha.cms.web.freemarker.DirectiveUtils.OUT_LIST;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuogesha.cms.action.directive.abs.AbstractContentDirective;
import com.shuogesha.cms.web.freemarker.DirectiveUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ContentListDirective extends AbstractContentDirective  {

	public static final String TPL_NAME = "content_list";

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		List<Thread> threadList =  getList(params, env);
		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(threadList));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		body.render(env.getOut());
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
	

	@SuppressWarnings("unchecked")
	protected List<Thread> getList(Map<String, TemplateModel> params,
			Environment env) throws TemplateException {
		return (List<Thread>) super.getData(params, env);			 
	}

	@Override
	protected boolean isPage() {
		return false;
	}

}
