package com.longstore.common.domain.msg;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.Cookie;

/**
 * 日志消息
 */
public class LogMsg implements Serializable{
	private static final long serialVersionUID = 9012047040571576876L;

	/**
	 * 用户id
	 */
	private Integer uid;
	/**
	 * 操作类型，a：新加，d：删除，u：更新，v：访问
	 */
	private String tt;
	/**
	 * 被操作对象类型
	 */
	private String tn;
	/**
	 * 指定参数名称以提取被操作对象ID
	 */
	private Integer tid;
	/**
	 * 链接，不带域名
	 */
	private String u;
	/**
	 * 参数
	 */
	private Map<String, String[]> p;
	/**
	 * cookie
	 */
	private Cookie[] c;
	/** 
	 * 添加时间 
	 */
    private long ts;

	public LogMsg() {
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getTt() {
		return tt;
	}

	public void setTt(String tt) {
		this.tt = tt;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getU() {
		return u;
	}

	public void setU(String u) {
		this.u = u;
	}

	public Map<String, String[]> getP() {
		return p;
	}

	public void setP(Map<String, String[]> p) {
		this.p = p;
	}

	public Cookie[] getC() {
		return c;
	}

	public void setC(Cookie[] c) {
		this.c = c;
	}

	public long getTs() {
		return ts;
	}

	public void setTs(long ts) {
		this.ts = ts;
	}

    
}
