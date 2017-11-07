package com.longstore.common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.longstore.common.cache.RedisHandler;
import com.longstore.common.domain.WebResult;
import com.longstore.common.util.ApplicationContextUtil;
import com.longstore.common.util.Base64Util;
import com.longstore.common.util.JsonUtil;
import com.longstore.common.util.MD5util;
import com.longstore.common.util.RSAUtils;
import com.longstore.common.util.WebResultUtil;

public class RSAParamsFilter extends AbstractFilter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RSAParamsFilter.class);

	private String cache;
	private String rsaPk;
	
    @Override
    public void init() throws ServletException {
    	cache = filterConfig.getInitParameter("cache");
    	rsaPk = filterConfig.getInitParameter("rsaPk");
        if (StringUtils.isBlank(cache)) {
        	cache = "redisHandler";
        }
    }
    
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    	request.setAttribute("is_rsa_query", false);
    	String json = null;
    	String jsonKey = null;
        if (getCache() != null) {
        	//取加密过的参数
        	json = request.getParameter("p_json_dig");
            if(StringUtils.isNotBlank(json)){
            	//二次请求校验
            	jsonKey = "request_" + MD5util.digest(json);
            	if (getCache().get(jsonKey) != null) {
            		WebResult result = WebResultUtil.failed("query_secord", "无效请求");
    	            response.getWriter().write(JsonUtil.toJson(result));
    	            return;
				}
    			//防止二次请求
            	getCache().put(jsonKey, "1", 60);
            }
		}
    	JSONObject params = null;
        if (StringUtils.isNotBlank(rsaPk)) {
            try {
    			byte[] data = RSAUtils.decryptByPrivateKey(rsaPk, Base64Util.decode(json));
    			String pjson = new String(data, "UTF-8");
    			params = JSON.parseObject(pjson);
    		} catch (Exception e) {
    			LOGGER.error(e.getMessage(), e);
    		}
            if (params == null) {
            	if (LOGGER.isInfoEnabled()) {
                	LOGGER.info("---解密出错----p_json_dig=" + json + ", privateKey=" + rsaPk);
				}
				WebResult result = WebResultUtil.failed("decrypt_error", "解密出错");
	            response.getWriter().write(JsonUtil.toJson(result));
	            return;
            }
            //请求时间判断
        	long _qtime = params.getLongValue("q_time");
			_qtime = System.currentTimeMillis() - _qtime;
			if (_qtime > 60000 || _qtime < -60000) {
				WebResult result = WebResultUtil.failed("time_out", "请求过期");
	            response.getWriter().write(JsonUtil.toJson(result));
	            return;
			}
        	request.setAttribute("is_rsa_query", true);
		}
    	chain.doFilter(new RSAHttpServletRequestWrapper(request, params), response);
    }

    private RedisHandler redisHandler;
    private RedisHandler getCache(){
    	if (redisHandler == null) {
    		redisHandler = (RedisHandler)ApplicationContextUtil.getContext().getBean(cache);
		}
    	return redisHandler;
    }
}
