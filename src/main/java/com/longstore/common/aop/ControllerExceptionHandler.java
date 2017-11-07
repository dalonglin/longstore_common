package com.longstore.common.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.longstore.common.domain.WebResult;
import com.longstore.common.exception.ExceptionSupper;
import com.longstore.common.util.HttpUtil;
import com.longstore.common.util.JsonUtil;
import com.longstore.common.util.WebResultUtil;

/**
 * 异常统一处理
 */
public class ControllerExceptionHandler implements HandlerExceptionResolver {
    private final static Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private Map<String, String> jumpUrl = new HashMap<String, String>();
    private Map<String, String> params = new HashMap<String, String>();
    private String exPage = "500";
    
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (request.getParameterMap() != null) {
        	if (request.getRequestURL().toString().contains("/file/")) {
                LOGGER.error(request.getRequestURL() + "," + ex.getMessage(), ex);
			}else{
	            LOGGER.error(request.getRequestURL() + "," + JsonUtil.toJson(request.getParameterMap()) + "," + ex.getMessage(), ex);
			}
        }else{
            LOGGER.error(request.getRequestURL() + "," + ex.getMessage(), ex);
        }
        //提取错误信息
        WebResult result = null;
        if (ex instanceof ExceptionSupper) {
            ExceptionSupper e = (ExceptionSupper)ex;
            result = WebResultUtil.failed(e.getCode(), e.getMessage());
        }else {
            result = WebResultUtil.failed();
        }
        //结果返回
        if(HttpUtil.jsonRequest(request)){//判断是否页面（接口）请求
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            try {
                if (StringUtils.isBlank(result.getMsg())) {
                    result.setMsg("系统错误");
                }
                response.getWriter().write(JsonUtil.toJson(result));
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            return new ModelAndView();
        }else{
            Map<String, String> model = new HashMap<String, String>();
            model.put("code", result.getCode());
            model.put("msg", result.getMsg());
            if (params != null && params.size() > 0) {
                for(Map.Entry<String, String> e : params.entrySet()){
                    model.put(e.getKey(), e.getValue());
                }
            }
            String page = jumpUrl.get(result.getCode());
            if (StringUtils.isBlank(page)) {
                page = exPage;
            }
            return new ModelAndView(page, model);
        }
    }

    public void setJumpUrl(Map<String, String> jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public void setExPage(String exPage) {
        this.exPage = exPage;
    }
    
}
