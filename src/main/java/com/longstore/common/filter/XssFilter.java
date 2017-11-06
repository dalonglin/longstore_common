package com.longstore.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.longstore.common.util.ApplicationContextUtil;

public class XssFilter extends AbstractFilter {

	private MultipartResolver multipartResolver = null;
	
    @Override
    public void init() throws ServletException {

    }
    
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XssHttpServletRequestWrapper(getRequest(request)), response);
    }

    private HttpServletRequest getRequest(HttpServletRequest request) {
    	String enctype = request.getContentType();  
        if (StringUtils.isNotBlank(enctype) && enctype.contains("multipart/form-data")){
            // 返回 MultipartHttpServletRequest 用于获取 multipart/form-data 方式提交的请求中 上传的参数  
            return getMultipartResolver().resolveMultipart(request);
        }
        return request;
    }
    private MultipartResolver getMultipartResolver() {
    	if (multipartResolver == null) {
        	multipartResolver = (MultipartResolver) ApplicationContextUtil.getContext().getBean(CommonsMultipartResolver.class);
		}
    	return multipartResolver;
    }
    
}
