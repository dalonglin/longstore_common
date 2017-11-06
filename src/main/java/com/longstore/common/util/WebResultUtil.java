package com.longstore.common.util;

import java.io.Serializable;

import com.longstore.common.constants.ExceptionConstants;
import com.longstore.common.domain.WebResult;

/**
 * 封装接口json返回结果
 */
public class WebResultUtil {

    /** 
     * 调用失败
     * @return 
     */
	public static <T extends Serializable> WebResult<T> failed(){
        WebResult<T> r = new WebResult<T>();
        r.setState(false);
        r.setCode(ExceptionConstants.ERROR_CODE_DEFAULT);
        return r;
    }
    /**
     * 调用失败，产生异常或者不满足业务逻辑要求等情况时调用这个 
     * @param code 错误码
     * @return
     */
    public static <T extends Serializable> WebResult<T> failed(String code){
        WebResult<T> r = new WebResult<T>();
        r.setState(false);
        r.setCode(code);
        return r;
    }
    /**
     * 调用失败，产生异常或者不满足业务逻辑要求等情况时调用这个 
     * @param code  错误或者异常码
     * @param msg   错误或异常信息
     * @return
     */
    public static <T extends Serializable> WebResult<T> failed(String code, String msg){
        WebResult<T> r = new WebResult<T>();
        r.setState(false);
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    /**
     * 调用成功
     */
    public static <T extends Serializable> WebResult<T> success(T result){
        WebResult<T> r = new WebResult<T>();
        r.setState(true);
        r.setResult(result);
        return r;
    }
    /**
     * 调用成功
     */
    public static <T extends Serializable> WebResult<T> success(String code,T result){
        WebResult<T> r = new WebResult<T>();
        r.setCode(code);
        r.setState(true);
        r.setResult(result);
        return r;
    }
    
}
