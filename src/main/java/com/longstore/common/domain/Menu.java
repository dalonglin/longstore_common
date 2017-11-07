package com.longstore.common.domain;

import java.io.Serializable;

/**
 * 菜单
 */
public class Menu implements Serializable{
    private static final long serialVersionUID = -4320772054285049399L;
    
    private int id;
    /** 权限名称 */
    private String name;
    /** 权限链接 */
    private String url;
    
    public Menu(){
        
    }
    
    public Menu(int id, String name, String url){
        this.id = id;
        this.name = name;
        this.url = url;
    }
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
}