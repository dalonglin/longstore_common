package com.longstore.common.fs;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longstore.common.exception.DfsException;

public interface IFileStore {
    public final static Logger LOGGER = LoggerFactory.getLogger(IFileStore.class);
    
    /**
     * 上传文件
     */
    public FSResult upload(File file) throws DfsException;
    
    /**
     * 上传文件
     */
    public FSResult upload(byte[] file, String fileName) throws DfsException;

    /**
     * 下载文件
     */
    public byte[] download(String url) throws DfsException;
    
    /**
     * 下载文件
     */
    public boolean download(String url, String storeFile) throws DfsException;
    
    /**
     * 删除文件
     */
    public boolean remove(String name) throws DfsException;
    
}
