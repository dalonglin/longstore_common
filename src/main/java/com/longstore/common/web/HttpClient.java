package com.longstore.common.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.longstore.common.exception.HttpException;
import com.longstore.common.util.JsonUtil;

/**
 * http 客户端
 */
public class HttpClient {
    private HttpClientSimple httpClientSimple;
    private Map<String, String> urlMap = new HashMap<String, String>();
    
    /** 
     * get 请求
     * 
     * @param urlKey 请求地址
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     * @param clazz  请求结果类型
     */
    public <T> T get(String urlKey, String url, Map<String, String> params, String cookie, Class<T> clazz) throws HttpException{
        String result = get(urlKey, url, params, cookie);
        if (StringUtils.isNotBlank(result)) {
            return JsonUtil.toObject(result, clazz);
        }
        return null;
    }
    /** 
     * get 请求
     * 
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     * @param clazz  请求结果类型
     */
    public <T> T get(String url, Map<String, String> params, String cookie, Class<T> clazz) throws HttpException{
        String result = httpClientSimple.get(url, params, cookie);
        if (StringUtils.isNotBlank(result)) {
            return JsonUtil.toObject(result, clazz);
        }
        return null;
    }
    /** 
     * get 请求
     * 
     * @param urlKey 请求地址
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     */
    public String get(String urlKey, String url, Map<String, String> params, String cookie) throws HttpException{
        if(StringUtils.isBlank(url) || urlMap.get(urlKey) == null) {
            throw new HttpException("url is empty!");
        }
        if(url.charAt(0) == '/'){
            url = urlMap.get(urlKey) + url;
        }else {
            url = urlMap.get(urlKey) + "/" + url;
        }
        return get(url, params, cookie);
    }
    /** 
     * get 请求
     * 
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     */
    public String get(String url, Map<String, String> params, String cookie) throws HttpException{
        return httpClientSimple.get(url, params, cookie);
    }
       
    /** 
     * post 请求 
     * 
     * @param urlKey 请求地址
     * @param url    请求地址
     * @param params 请求参数
     * @param cookie cookie，格式：aa=111;bb=22
     * @param clazz  请求结果类型
     */
    public <T> T post(String urlKey, String url, Map<String, String> params, String cookie, Class<T> clazz) throws HttpException{
        String result = post(urlKey, url, params, cookie);
        if (StringUtils.isNotBlank(result)) {
            return JsonUtil.toObject(result, clazz);
        }
        return null;
    }
    /** 
     * post 请求  
     * 
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     * @param clazz  请求结果类型
     */
    public <T> T post(String url, Map<String, String> params, String cookie, Class<T> clazz) throws HttpException{
        String result = httpClientSimple.post(url, params, cookie);
        if (StringUtils.isNotBlank(result)) {
            return JsonUtil.toObject(result, clazz);
        }
        return null;
    }
    /** 
     * post 请求 
     * 
     * @param urlKey 请求地址
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     */
    public String post(String urlKey, String url, Map<String, String> params, String cookie) throws HttpException{
        if(StringUtils.isBlank(url) || urlMap.get(urlKey) == null) {
            throw new HttpException("url is empty!");
        }
        if(url.charAt(0) == '/'){
            url = urlMap.get(urlKey) + url;
        }else {
            url = urlMap.get(urlKey) + "/" + url;
        }
        return post(url, params, cookie);
    }
    /** 
     * post 请求  
     * 
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     */
    public String post(String url, Map<String, String> params, String cookie) throws HttpException{
        return httpClientSimple.post(url, params, cookie);
    }
    
    public void setUrlMap(Map<String, String> urlMap) {
        this.urlMap = urlMap;
    }
    public void setHttpClientSimple(HttpClientSimple httpClientSimple) {
        this.httpClientSimple = httpClientSimple;
    }

}
