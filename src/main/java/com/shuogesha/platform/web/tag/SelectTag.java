package com.shuogesha.platform.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.shuogesha.platform.entity.Dictionary;
import com.shuogesha.platform.service.DictionaryCtgService;
@Component 
public class SelectTag extends BodyTagSupport {    
	private String code;
    private boolean defaultValue;
    private String value;
    private String name;
    private String id;
    private String cssClass;
    private String styleClass;
    private String multiple;
    private String onChange;

    
    
    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public String getOnChange() {
        return onChange;
    }

    public void setOnChange(String onChange) {
        this.onChange = onChange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    
    @Override
    public int doEndTag() throws JspException{
    	ServletContext servletContext = this.pageContext.getServletContext();
    	WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    	/* 从上下文中获取指定的Bean */
    	DictionaryCtgService dictionaryCtgService = (DictionaryCtgService) wac.getBean("dictionaryCtgServiceImpl"); 
    	
        List<Dictionary> dict_list = dictionaryCtgService.findByCode(code);
        JspWriter out = pageContext.getOut();
        StringBuffer sb = new StringBuffer();
        sb.append("<select name='"+this.getName()+"' id='"+this.getId()+"'");
        if(!StringUtils.isEmpty(this.getCssClass())){
            sb.append("class=\"" + this.getCssClass() + "\" ");
        }
        if(!StringUtils.isEmpty(this.getStyleClass())){
            sb.append("style=\"" + this.getStyleClass() + "\" ");
        }
        if(!StringUtils.isEmpty(this.getMultiple())){
            sb.append("multiple=\"" + this.getMultiple() + "\" ");
        }
        if(!StringUtils.isEmpty(this.getOnChange())){
            sb.append("onchange=\"" + this.getOnChange() + "\" ");
        }
        sb.append(">");
        if(this.isDefaultValue()){  
            sb.append("<option value=''>--请选择--</option>");  
        }
        for(Dictionary dc:dict_list){
            if(dc.getValue().equals(this.getValue())){
                sb.append("<option value='"+dc.getValue()+"' selected='selected'>");
            }else{
                sb.append("<option value='"+dc.getValue()+"'>");
            }
            sb.append(dc.getName()+"</option>");
        }
        sb.append("</select>");
        try {
            out.write(sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new JspException(e);
        }
        return TagSupport.EVAL_PAGE;
    }
    
}
