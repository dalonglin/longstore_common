package com.longstore.common.domain;

import java.io.Serializable;

/**
 * 封装接口json返回结果
 */
public class WebResult implements Serializable{
    private static final long serialVersionUID = -6076007353896635350L;
    
    /** 
     * 接口调用是否成功 
     *     true：成功
     *     false：失败
     */
    private boolean state;
    /** 错误代码 */
    private String code;
    /** 信息 */
    private String msg;
    /** form表单token */
    private String formToken;
    /** 返回结果 */
    private Object result;

    public WebResult(){
        
    }
    
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    public boolean getState() {
        return state;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
		this.code = code;
	}
    public String getMsg() {
        return msg;
    }
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getFormToken() {
		return formToken;
	}
	public void setFormToken(String formToken) {
		this.formToken = formToken;
	}
	
}
