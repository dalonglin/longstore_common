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
    /** 权限的值 */
    private String value;
    
    public Menu(){
        
    }
    
    public Menu(int id, String name, String value){
        this.id = id;
        this.name = name;
        this.value = value;
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
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
}