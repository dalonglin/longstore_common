package com.longstore.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * cookied 操作
 */
public class CookieUtil {
    
    private CookieUtil() {

    }

    public static void put(HttpServletResponse response, String key, String value, int maxAge, boolean httpOnly, String domain) {
    	if (StringUtils.isBlank(key)) {
			return;
		}
    	if (StringUtils.isBlank(value)) {
            remove(response, key, domain);
			return;
		}
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath("/");
        cookie.setDomain(domain);
        if (maxAge > 0) {
        	cookie.setMaxAge(maxAge);
		}
        response.addCookie(cookie);
    }
    
    public static void put(HttpServletResponse response, String key, String value, int maxAge, String domain) {
        put(response, key, value, maxAge, true, domain);
    }

    public static void put(HttpServletResponse response, String key, String value, String domain) {
        put(response, key, value, 0, true, domain);
    }

    public static String get(HttpServletRequest request, String key) {
        if (StringUtils.isNotBlank(key)) {
            Cookie[] cookies = request.getCookies();//为设置时返回null
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (key.equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }
        return null;
    }

    public static void remove(HttpServletResponse response, String key, String domain) {
        if (StringUtils.isNotBlank(key)) {
            Cookie cookie = new Cookie(key, "");
            cookie.setMaxAge(0);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setDomain(domain);
            response.addCookie(cookie);
        }
    }

}
