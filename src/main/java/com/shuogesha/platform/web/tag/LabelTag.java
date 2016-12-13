package com.shuogesha.platform.web.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.service.DictionaryCtgService;
@Component 
public class LabelTag extends BodyTagSupport {    
	private String code = "";
    private String value;

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
    public int doEndTag() throws JspException {
		Dictionary dict = new Dictionary();
        JspWriter out = pageContext.getOut();
        try {
        	ServletContext servletContext = this.pageContext.getServletContext();
        	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
        	/* 从上下文中获取指定的Bean */
        	DictionaryCtgService dictionaryCtgService = (DictionaryCtgService) wac.getBean("dictionaryCtgServiceImpl"); 
            out.write(dictionaryCtgService.findBycodeToName(code,value));
        } catch (IOException e) {
            throw new JspException(e);
        }
        return TagSupport.EVAL_PAGE;
    }   
}
