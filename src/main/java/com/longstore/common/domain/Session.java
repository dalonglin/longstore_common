package com.longstore.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 页面session参数
 */
public class Session implements Serializable{
    private static final long serialVersionUID = -1441771783383208454L;

    private Integer id;//用户ID
    private String name;//用户名称
    private String avatar;//用户头像
    
    private List<Integer> scIds;//权限ID

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<Integer> getScIds() {
		return scIds;
	}

	public void setScIds(List<Integer> scIds) {
		this.scIds = scIds;
	}

}
