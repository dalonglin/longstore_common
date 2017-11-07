package com.longstore.common.fs;

import java.io.Serializable;

public class FSResult implements Serializable{
    private static final long serialVersionUID = 1993541215671561798L;
    
    public String key;
    public String path;
    public int width;
    public int height;
    public long size;
    
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
}
