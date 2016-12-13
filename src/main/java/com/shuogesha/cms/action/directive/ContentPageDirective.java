package com.shuogesha.cms.action.directive;

import static com.shuogesha.cms.web.freemarker.DirectiveUtils.OUT_LIST;
import static com.shuogesha.cms.web.freemarker.DirectiveUtils.OUT_PAGINATION;
import static freemarker.template.ObjectWrapper.DEFAULT_WRAPPER;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.shuogesha.cms.action.directive.abs.AbstractContentDirective;
import com.shuogesha.cms.web.FrontUtils;
import com.shuogesha.cms.web.freemarker.DirectiveUtils;
import com.shuogesha.cms.web.freemarker.DirectiveUtils.InvokeType;
import com.shuogesha.platform.entity.Site;
import com.shuogesha.platform.web.mongo.Pagination;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ContentPageDirective extends AbstractContentDirective {

	public static final String TPL_NAME = "content_page";

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		Site site = FrontUtils.getSite(env);
		Pagination page = (Pagination) super.getData(params, env);

		Map<String, TemplateModel> paramWrap = new HashMap<String, TemplateModel>(
				params);
		paramWrap.put(OUT_PAGINATION, DEFAULT_WRAPPER.wrap(page));
		paramWrap.put(OUT_LIST, DEFAULT_WRAPPER.wrap(page.getDatas()));
		Map<String, TemplateModel> origMap = DirectiveUtils
				.addParamsToVariable(env, paramWrap);
		InvokeType type = DirectiveUtils.getInvokeType(params);
		if (InvokeType.sysDefined == type) {
			
		}else if (InvokeType.userDefined == type) {
			
		} else if (InvokeType.custom == type) {
			
		} else if (InvokeType.body == type) {
			if (body != null) {
				body.render(env.getOut());
				FrontUtils.includePagination(site, params, env);
			}
		} else {
			throw new RuntimeException("invoke type not handled: " + type);
		}
		DirectiveUtils.removeParamsFromVariable(env, paramWrap, origMap);
	}
	
	@Override
	protected boolean isPage() {
		return true;
	}
}
