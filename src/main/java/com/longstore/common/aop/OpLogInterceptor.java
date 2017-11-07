package com.longstore.common.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.longstore.common.annotation.OpLog;
import com.longstore.common.constants.ParamsConstants;
import com.longstore.common.domain.msg.LogMsg;
import com.longstore.common.util.JsonUtil;

/**
 * 记录日志
 */
public abstract class OpLogInterceptor implements HandlerInterceptor {
    protected final static Logger log = LoggerFactory.getLogger(OpLogInterceptor.class);
    protected final static Logger LOGGER = LoggerFactory.getLogger("user.operation.log");
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Method method = ((HandlerMethod) handler).getMethod();
        //判断方法是否有注解
    	OpLog opLog = method.getAnnotation(OpLog.class);
        if (opLog == null) {
        	opLog = method.getDeclaringClass().getAnnotation(OpLog.class);
		}
        if (opLog == null || OpLog.NO.equals(opLog.isLog())) {
            return true;
		}
        //取用户信息
        try {
	        Integer userId = (Integer)request.getAttribute(ParamsConstants.USER_ID);
	        LogMsg msg = new LogMsg();
	        msg.setUid(userId);
	        msg.setTt(opLog.type());
	        msg.setTn(opLog.targetName());
	        if (StringUtils.isNotBlank(opLog.targetParam())) {
	        	String tid = request.getParameter(opLog.targetParam());
	        	if (StringUtils.isNotBlank(tid)) {
	                try {
	                	msg.setTid(Integer.valueOf(tid));
	        		} catch (Exception e) {
	        			log.error(e.getMessage(), e);
	        		}
				}
			}
	        msg.setU(request.getRequestURI());
	        msg.setP(request.getParameterMap());
	        msg.setC(request.getCookies());
	        msg.setTs(System.currentTimeMillis());
	        LOGGER.error(JsonUtil.toJson(msg));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    }
    
}
