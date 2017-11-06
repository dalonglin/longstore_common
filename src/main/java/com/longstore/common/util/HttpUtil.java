package com.longstore.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class HttpUtil {
    
    private final static String UNKNOWN = "unknown";
    public static String getIPAddress(HttpServletRequest request) {
        String IPAddress = request.getHeader("x-forwarded-for");
        
        if (StringUtils.isBlank(IPAddress) || UNKNOWN.equalsIgnoreCase(IPAddress)) {
            IPAddress = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(IPAddress) || UNKNOWN.equalsIgnoreCase(IPAddress)) {
            IPAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(IPAddress) || UNKNOWN.equalsIgnoreCase(IPAddress)) {
            IPAddress = request.getRemoteAddr();
        }
        return IPAddress;
    }
    
    public static boolean isAjax(HttpServletRequest request){
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            return true;
        }
        return false;
    }
    
    public static boolean jsonRequest(HttpServletRequest request){
        String accept = request.getHeader("accept");
        if(accept != null && accept.contains("json")){
            return true;
        }
        String contentType = request.getHeader("Content-Type");
        if(contentType != null && contentType.contains("json")){
            return true;
        }
        String uri = request.getRequestURI();
        if (uri != null && uri.lastIndexOf(".json") != -1) {
            return true;
        }
        return false;
    }
    
}
