package com.longstore.common.domain.msg;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志消息
 */
public class LogMsg implements Serializable{
	private static final long serialVersionUID = 9012047040571576876L;

	/**
	 * 用户类型，1：前台，2：后台 
	 */
	private Integer userType;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 操作类型，view：访问，add：新加，2：up，del：删除
	 */
	private String opType;
	/**
	 * 被操作对象类型
	 */
	private String targetType;
	/**
	 * 被操作对象ID
	 */
	private Integer targetId;
	/**
	 * 链接，不带域名
	 */
	private String url;
	/**
	 * 参数，json
	 */
	private String params;
	/**
	 * cookie
	 */
	private String cookie;
	/** 
	 * 添加时间 
	 */
    private Date addTime;

	public LogMsg() {
	}

	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType){
		this.opType = opType;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType){
		this.targetType = targetType;
	}
	public Integer getTargetId() {
		return targetId;
	}
	public void setTargetId(Integer targetId){
		this.targetId = targetId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params){
		this.params = params;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie){
		this.cookie = cookie;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
    
}
