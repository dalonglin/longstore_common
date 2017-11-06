package com.longstore.common.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.alibaba.fastjson.JSONObject;

public class RSAHttpServletRequestWrapper extends HttpServletRequestWrapper {
    
	private JSONObject params = null;
	private Enumeration<String> paramNames = null;
    public RSAHttpServletRequestWrapper(HttpServletRequest servletRequest, JSONObject params) {
        super(servletRequest);
        this.params = params;
        if (this.params != null && this.params.size() > 0) {
            List<String> v = new ArrayList<String>(32);
        	Enumeration<String> e = super.getParameterNames();
        	if (e != null) {
                while(e.hasMoreElements()){
                	v.add(e.nextElement());
                }
    		}
        	for(String k : this.params.keySet()){
            	v.add(k);
        	}
        	paramNames = Collections.enumeration(v);
		}
    }

    public String[] getParameterValues(String parameter) {
    	if (params != null) {
			String value = params.getString(parameter);
			if (value != null) {
				return new String[]{value};
			}
		}
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = values[i];
        }
        return encodedValues;
    }

    public String getParameter(String parameter) {
    	if (params != null) {
			String value = params.getString(parameter);
			if (value != null) {
				return value;
			}
		}
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        return value;
    }

    public Enumeration<String> getParameterNames() {
    	if (paramNames != null) {
    		return paramNames;
		}
        return super.getParameterNames();
    }

}
