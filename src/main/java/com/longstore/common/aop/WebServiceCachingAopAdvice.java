package com.longstore.common.aop;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.longstore.common.annotation.Cache;
import com.longstore.common.cache.RedisHandler;
import com.longstore.common.domain.WebResult;
import com.longstore.common.util.JsonUtil;

/**
 * 接口数据缓存
 */
public class WebServiceCachingAopAdvice {

    private RedisHandler redisHandler;
    private String prefix = "service_cahce_";

    public void doBefore(JoinPoint jp) {

    }

    public void doAfter(JoinPoint jp) {

    }
    
    public void doAfterResult(JoinPoint jp, Object result) {

    }

    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        if (redisHandler == null) {
            return pjp.proceed();
        }
        //取得注解
        MethodSignature method = (MethodSignature)pjp.getSignature();
        Cache cache = (Cache) method.getMethod().getAnnotation(Cache.class);
        if (cache == null) {
        	cache = method.getClass().getAnnotation(Cache.class);
		}
        if (cache == null) {
            return pjp.proceed();
		}
        //指定对哪种请求进行缓存
        String cmethod = cache.cmethod();
        if (StringUtils.isNotBlank(cmethod)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if(!request.getMethod().equals(cmethod)){
                return pjp.proceed();
            }
		}
        //指定过期时间
        int expire = cache.expire();
        //开始生成key
        StringBuffer key = new StringBuffer();
        //处理类名和方法名
        key.append(method.getDeclaringType().getSimpleName());
        key.append(".").append(method.getName());
        //处理参数
        String[] names = method.getParameterNames();
        Object[] values = pjp.getArgs();
        if (names != null && names.length > 0) {
            boolean isFirst = true;
            for (int i = 0; i < names.length; i++) {
            	if (values[i] == null) {
					continue;
				}
                if (isFirst) {
                	key.append("?").append(names[i]).append("=").append(obj2str(values[i]));
                    isFirst = false;
                }else {
                	key.append("&").append(names[i]).append("=").append(obj2str(values[i]));
                }
            }
        }
        String cacheKey = prefix + key.toString();
        Object value = redisHandler.get(cacheKey);
        if (value != null) {
            return value;
        }
        value = pjp.proceed();
        if (value != null) {
        	if (value instanceof WebResult) {
                @SuppressWarnings("rawtypes")
				WebResult result = (WebResult) value;
                if (result.getState() && result.getResult() != null){
                    redisHandler.put(cacheKey, result, expire);
                }
            }else if (value instanceof Serializable){
                redisHandler.put(cacheKey, value, expire);
            }
        }
        return value;
    }

    public void doThrow(JoinPoint jp, Throwable e) {

    }
    
    private String obj2str(Object obj){
    	if (obj instanceof String  || obj instanceof Boolean 
    			|| obj instanceof Integer || obj instanceof Float || obj instanceof Double) {
			return obj.toString();
		}
    	return JsonUtil.toJson(obj);
    }

    public void setRedisHandler(RedisHandler redisHandler) {
        this.redisHandler = redisHandler;
    }
    public void setPrefix(String prefix) {
        if (StringUtils.isNotBlank(prefix)) {
            this.prefix = prefix.trim();
        }
    }
}
