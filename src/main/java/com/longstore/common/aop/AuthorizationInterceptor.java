package com.longstore.common.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.longstore.common.annotation.Authorization;
import com.longstore.common.annotation.ParamsDig;
import com.longstore.common.cache.RedisHandler;
import com.longstore.common.constants.CacheConstants;
import com.longstore.common.constants.ParamsConstants;
import com.longstore.common.domain.Session;
import com.longstore.common.util.CookieUtil;
import com.longstore.common.util.MD5util;
import com.longstore.common.util.WebConfigUtil;

/**
 * 安全认证统一验证处理，拦截非法请求
 */
public abstract class AuthorizationInterceptor implements HandlerInterceptor {
    protected final static Logger LOGGER = LoggerFactory.getLogger("user.auth.check");
    private final static String STATIC_VERSION = String.valueOf(System.currentTimeMillis());
    
    // 存放session的缓存
    protected RedisHandler redisHandler;

    //是否检查登陆状态  yes：检查  no：不检查
    private String checkLogin = "no";
    //是否检查具有访问权限  yes：检查  no：不检查
    private String checkSc = "no";
    //检查表单token   yes：检查   no：不检查
    private String checkFormToken = "no";
    private Map<String, String> head = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String token = null;
        String sign = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (ParamsConstants.USER_TOKEN.equals(cookie.getName())) {
                	token = cookie.getValue();
                }else if (ParamsConstants.USER_SIGN.equals(cookie.getName())) {
                    sign = cookie.getValue();
                }
            }
        }
        try {
            if (StringUtils.isBlank(token)) {
            	token = MD5util.digest(UUID.randomUUID().toString() + System.currentTimeMillis());
                CookieUtil.put(response, ParamsConstants.USER_TOKEN, token, WebConfigUtil.domain());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        if (StringUtils.isBlank(sign)) {
            sign = request.getParameter(ParamsConstants.USER_SIGN);
        }
        request.setAttribute(ParamsConstants.USER_TOKEN, token);
        
        Session session = null;
        if(StringUtils.isNotBlank(sign)) {
            session = redisHandler.get(CacheConstants.USER_SESSION_PREFIX + sign);
            request.setAttribute(ParamsConstants.USER_SIGN, sign);
        }
        if (session != null) {
            request.setAttribute(ParamsConstants.USER_ID, session.getId());
            request.setAttribute(ParamsConstants.USER_NAME, session.getName());
            request.setAttribute(ParamsConstants.USER_AVATAR, session.getAvatar());
            request.setAttribute(ParamsConstants.USER_PERMISSION_ALL, session.getSc());
        }
        Method method = ((HandlerMethod) handler).getMethod();
        //判断方法是否有注解
        Authorization authorization = method.getAnnotation(Authorization.class);
        if (authorization == null) {//判断类是否有注解
            authorization = method.getDeclaringClass().getAnnotation(Authorization.class);
        }
        if (authorization != null ) {
        	checkLogin = authorization.checkLogin();
        	checkSc = authorization.checkSc();
        	checkFormToken = authorization.checkFormToken();
        }
        if ("yes".equals(checkLogin)) {
    		if (session == null) {
    			noLogin(request, response);
                return false;
            }
		}
    	if ("yes".equals(checkSc)) {
    		if (session == null) {
    			noLogin(request, response);
                return false;
            }
    		if (!checSc(request, response, session)) {
    			noSec(request, response);
                return false;
            }
		}
    	if ("yes".equals(checkFormToken)) {
    		String formToken = request.getParameter(ParamsConstants.FORM_TOKEN);
    		String formTokenRand = request.getParameter(ParamsConstants.FORM_TOKEN_RAND);
        	if (StringUtils.isBlank(formToken) || StringUtils.isBlank(formTokenRand)) {
        		noFormToken(request, response);
				return false;
			}
        	String fkkey = CacheConstants.USER_FORM_TOKEN_PREFIX + token + "_" + formTokenRand;
            String _formToken = redisHandler.get_str(fkkey);
            if (!formToken.equals(_formToken)) {
        		noFormToken(request, response);
				return false;
			}
            redisHandler.remove_str(fkkey);
		}
        //判断是否需要解密加密
        boolean checkDig = false;
        ParamsDig paramsDig = method.getAnnotation(ParamsDig.class);
        if (paramsDig == null) {//判断类是否有注解
        	paramsDig = method.getDeclaringClass().getAnnotation(ParamsDig.class);
        }
        if (paramsDig != null && ParamsDig.YES.equals(paramsDig.isDig())) {
			checkDig = true;
		}
        if (checkDig) {
        	boolean rsaQuery = request.getAttribute("is_rsa_query") == null ? false : (boolean) request.getAttribute("is_rsa_query");
            if (!rsaQuery) {
            	noDig(request, response);
                return false;
			}
        }
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	if(modelAndView == null){
            return;
        }
        modelAndView.addObject("_static_v", STATIC_VERSION);
        if (params != null && params.size() > 0) {
            for(Map.Entry<String, String> e : params.entrySet()){
                modelAndView.addObject(e.getKey(), e.getValue());
            }
        }
        if (head != null && head.size() > 0) {
            Set<String> keys = head.keySet();
            for (String key : keys) {
                response.setHeader(key, head.get(key));
            }
        }
        modelAndView.addObject(ParamsConstants.USER_TOKEN, request.getAttribute(ParamsConstants.USER_TOKEN));
        modelAndView.addObject(ParamsConstants.USER_ID, request.getAttribute(ParamsConstants.USER_ID));
        modelAndView.addObject(ParamsConstants.USER_NAME, request.getAttribute(ParamsConstants.USER_NAME));
        modelAndView.addObject(ParamsConstants.USER_AVATAR, request.getAttribute(ParamsConstants.USER_AVATAR));
        modelAndView.addObject(ParamsConstants.USER_PERMISSION_ALL, request.getAttribute(ParamsConstants.USER_PERMISSION_ALL));

        request.removeAttribute(ParamsConstants.USER_TOKEN);
        request.removeAttribute(ParamsConstants.USER_SIGN);
        request.removeAttribute(ParamsConstants.USER_ID);
        request.removeAttribute(ParamsConstants.USER_NAME);
        request.removeAttribute(ParamsConstants.USER_AVATAR);
        request.removeAttribute(ParamsConstants.USER_PERMISSION_ALL);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    }

    /** 检查用户权限，true：检查通过，false：检查失败，调用errorForward方法进行请求转发  */
    protected abstract boolean checSc(HttpServletRequest request, HttpServletResponse response, Session session);
    
    /** 
     * 页面重定向到登陆，如果ref为空，默认跳转到登陆页面。
     */
    protected abstract void noLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /** 
     * 页面重定向到没有权限，如果ref为空，默认跳转到登陆页面。
     */
    protected abstract void noSec(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    /** 
     * form token error
     */
    protected abstract void noFormToken(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /** 
     * dig params error
     */
    protected abstract void noDig(HttpServletRequest request, HttpServletResponse response) throws Exception;
    
    public void setRedisHandler(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }
	public void setCheckLogin(String checkLogin) {
		this.checkLogin = checkLogin;
	}
	public void setCheckSc(String checkSc) {
		this.checkSc = checkSc;
	}
	public void setCheckFormToken(String checkFormToken) {
		this.checkFormToken = checkFormToken;
	}
	public void setHead(Map<String, String> head) {
		this.head = head;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
    
}
