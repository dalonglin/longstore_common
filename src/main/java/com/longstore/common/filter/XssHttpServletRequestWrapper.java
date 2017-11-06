package com.longstore.common.filter;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        if (parameter.startsWith("_j_")) {
			return values;
		}
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        if (parameter.startsWith("_j_")) {
			return value;
		}
        return cleanXSS(value);
    }

    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        if (name.startsWith("_j_")) {
			return value;
		}
        return cleanXSS(value);
    }

    private String cleanXSS(String value) {
    	value = Pattern.compile("(&lt;|<)+script(.*?)(&gt;|>)+(.*?)(&lt;|<)+/script(&gt;|>)+", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");  
    	value = Pattern.compile("(&lt;|<)+/script(&gt;|>)+", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");  
    	value = Pattern.compile("(&lt;|<)+script(.*?)(&gt;|>)+", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).replaceAll("");  
    	value = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).replaceAll("");  
    	value = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).replaceAll("");  
    	value = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");  
    	value = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE).matcher(value).replaceAll("");  
    	value = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL).matcher(value).replaceAll("");  
		return value;
    }
    
}
