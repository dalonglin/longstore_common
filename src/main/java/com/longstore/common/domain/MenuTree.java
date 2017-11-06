package com.longstore.common.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单树
 */
public class MenuTree implements Serializable{
    private static final long serialVersionUID = 8907100280456593211L;

    private int id;
    /** 权限名称 */
    private String name;
    /** 权限的值 */
    private String value;
    /** 是否是活动的 */
    private boolean active;
    /** 子权限 */
    private List<MenuTree> child;
    
    public MenuTree(){
        
    }
    
    public MenuTree(int id, String name, String value){
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
    public boolean getActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public List<MenuTree> getChild() {
        return child;
    }
    public void setChild(List<MenuTree> child) {
        this.child = child;
    }
    
    
}