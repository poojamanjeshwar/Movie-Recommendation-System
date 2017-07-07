package com.recommenderrest.utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletConfigAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component
public class ServletContainer implements ServletConfigAware{
	
	@Autowired
	private ServletContext servletContext;

	private ServletConfig servletConfig;
	
	
	public ServletConfig getServletConfig() {
		return this.servletConfig;
	}
	
	public ServletContext getServletContext() {
		return this.servletContext;
	}
	
	public WebApplicationContext getWebApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(this.servletContext) ;
	}

	@Override
	public void setServletConfig(ServletConfig servletConfig) {
		this.servletConfig = servletConfig;
		
	}

}
