package com.longstore.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.longstore.common.annotation.AccessLimit;
import com.longstore.common.cache.RedisHandler;
import com.longstore.common.constants.CacheConstants;
import com.longstore.common.constants.ExceptionConstants;
import com.longstore.common.constants.ParamsConstants;
import com.longstore.common.domain.Session;
import com.longstore.common.domain.WebResult;
import com.longstore.common.util.HttpUtil;
import com.longstore.common.util.JsonUtil;
import com.longstore.common.util.WebConfigUtil;
import com.longstore.common.util.WebResultUtil;

public class AccessLimitInterceptor implements HandlerInterceptor {
    protected final static Logger LOGGER = LoggerFactory.getLogger("user.access.limit");
    
    protected RedisHandler redisHandler;

    private int count = 0;//全局的单位时间内最大访问次数
    private int time = 0;//全局的单位时间，单位秒
    
    private boolean ipLimit(HttpServletRequest request, HttpServletResponse response, Method method, AccessLimit accessLimit) throws Exception {
    	int ipC = count*10;
        int ipT = time;
        if (accessLimit != null) {
        	ipC = accessLimit.ipC();
            ipT = accessLimit.ipT();
		}
        if (ipC < 1 || ipT < 1) {
			return false;
		}
        String ip = HttpUtil.getIPAddress(request);
        String cacheKey = "ACCESSLIMIT_IP_" + method.getDeclaringClass().getName() + "." + method.getName() + "_" + ip;
        long count = redisHandler.incr(cacheKey, 1, ipT);
        if(count > ipC){
            LOGGER.error("-----------访问太快了" + cacheKey);
            limitMsg(request, response);
            return true;
        }
        return false;
    }
    private boolean userLimit(HttpServletRequest request, HttpServletResponse response, Method method, AccessLimit accessLimit) throws Exception {
    	int uidC = count;
        int uidT = time;
        if (accessLimit != null) {
        	uidC = accessLimit.uidC();
        	uidT = accessLimit.uidT();
		}
        if (uidC < 1 || uidT < 1) {
			return false;
		}
        String sign = (String) request.getAttribute(ParamsConstants.USER_SIGN);
        String token = (String) request.getAttribute(ParamsConstants.USER_TOKEN);
        if (StringUtils.isBlank(sign) || StringUtils.isBlank(token)) {
        	Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for(Cookie c : cookies){
                    if (c.getName().equals(ParamsConstants.USER_SIGN)) {
                        sign = c.getValue();
                    }else if (c.getName().equals(ParamsConstants.USER_TOKEN)) {
                    	token = c.getValue();
                    }
                }
            }
            if (StringUtils.isBlank(sign)) {
            	sign = request.getParameter(ParamsConstants.USER_SIGN);
    		}
            if (StringUtils.isBlank(token)) {
            	token = request.getParameter(ParamsConstants.USER_TOKEN);
    		}
		}
    	//取用户信息
        Integer userId = (Integer)request.getAttribute(ParamsConstants.USER_ID);
        if (userId == null) {
        	Session session = redisHandler.get(CacheConstants.USER_SESSION_PREFIX + sign);
        	if (session != null) {
        		userId = session.getId();
			}
		}
        String cacheKey = null;
        if (userId == null) {
            if (StringUtils.isNotBlank(token)) {
            	cacheKey = "ACCESSLIMIT_USER_" + method.getDeclaringClass().getName() + "." + method.getName() + "_" + token;
            }
		}else{
			cacheKey = "ACCESSLIMIT_USER_" + method.getDeclaringClass().getName() + "." + method.getName() + "_" + userId;
		}
        if (StringUtils.isBlank(cacheKey)) {
        	return false;
        }
        long count = redisHandler.incr(cacheKey, 1, uidT);
        if(count > uidC){
            LOGGER.error("----------访问太快了" + cacheKey);
            limitMsg(request, response);
            return true;
        }
        return false;
    }
    private boolean paramsLimit(HttpServletRequest request, HttpServletResponse response, Method method, AccessLimit accessLimit) throws Exception {
        if (accessLimit == null) {
			return false;
		}
    	String params = accessLimit.params();
    	int paramsC = accessLimit.paramsC();
        int paramsT = accessLimit.paramsT();
        if (StringUtils.isBlank(params) || paramsC < 1 || paramsT < 1) {
			return false;
		}
        String cacheKey = null;
    	String[] paramArray = params.split(",");
    	for(String p : paramArray){
    		String v = request.getParameter(p);
    		if (StringUtils.isNotBlank(v)) {
				if (cacheKey == null) {
					cacheKey = p + "=" + v;
				}else{
					cacheKey = cacheKey + "&" + p + "=" + v;
				}
			}
    	}
    	if (cacheKey == null) {
    		cacheKey = "ACCESSLIMIT_PARAMS_" + method.getDeclaringClass().getName() + "." + method.getName();
		}else{
			cacheKey = "ACCESSLIMIT_PARAMS_" + method.getDeclaringClass().getName() + "." + method.getName() + "_" + cacheKey;
		}
        long count = redisHandler.incr(cacheKey, 1, paramsT);
        if(count > paramsC){
            LOGGER.error("---------------访问太快了" + cacheKey);
            limitMsg(request, response);
            return true;
        }
        return false;
    }
    private void limitMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (HttpUtil.jsonRequest(request)) {//ajax 调用
            WebResult webResult = WebResultUtil.failed(ExceptionConstants.ERROR_AUTH_ACCESS_FAILED, "请慢一点");
            response.getWriter().write(JsonUtil.toJson(webResult));
        } else {
            String ref = WebConfigUtil.home() + "/wait";
            response.sendRedirect(ref);
        }
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ((HandlerMethod) handler).getMethod();
        //判断方法是否有注解
        AccessLimit accessLimit = method.getAnnotation(AccessLimit.class);
        if (accessLimit == null) {//判断类是否有注解
        	accessLimit = method.getDeclaringClass().getAnnotation(AccessLimit.class);
        }
        if (accessLimit == null && (count < 1 || time < 1)) {
            return true;
        }
        if (accessLimit != null && AccessLimit.NO.equals(accessLimit.isLimit())) {
            return true;
		}
        //ip访问限制
        boolean limit = ipLimit(request, response, method, accessLimit);
        if (limit) {
			return false;
		}
        //用户访问限制
        limit = userLimit(request, response, method, accessLimit);
        if (limit) {
			return false;
		}
        //自定义参数访问控制
        limit = paramsLimit(request, response, method, accessLimit);
        if (limit) {
			return false;
		}
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
    
    public void setRedisHandler(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }
	public void setCount(int count) {
		this.count = count;
	}
	public void setTime(int time) {
		this.time = time;
	}

}
