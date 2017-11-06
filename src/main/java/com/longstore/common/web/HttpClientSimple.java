package com.longstore.common.web;

import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.longstore.common.exception.HttpException;

public class HttpClientSimple {
    protected final static Logger LOGGER = LoggerFactory.getLogger(HttpClientSimple.class);

    private CloseableHttpClient httpClient;
    
    /**
     * 创建httpClient连接池，并初始化httpclient
     * 
     * @param maxTotal             连接池线程最大数量
     * @param defaultMaxPerRoute   单个路由最大的连接线程数量
     * @param socketTimeout        socket 超时
     * @param connectTimeout       连接超时
     * @param requestTimeout       请求超时
     * 
     */
    public HttpClientSimple(int maxTotal, int defaultMaxPerRoute, int socketTimeout, int connectTimeout, int requestTimeout){
    	SSLConnectionSocketFactory sslsf = null;
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            sslsf = new SSLConnectionSocketFactory(builder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
        } catch (Exception e) {
        	LOGGER.error(e.getMessage(), e);
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
        httpClientConnectionManager.setDefaultConnectionConfig(connectionConfig);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(requestTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .build();
        //初始化httpclient客户端
        httpClient = HttpClients.custom()
        		.setSSLSocketFactory(sslsf)
                .setConnectionManager(httpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .build();
    }
    
    /** 
     * get 请求
     * 
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     *   
     */
    public String get(String url, Map<String, String> params, String cookie) throws HttpException{
        if(StringUtils.isBlank(url)) {
            throw new HttpException("url is empty!");
        }
        if (params != null && params.size() > 0) {
            StringBuilder sb = new StringBuilder(url);
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            boolean bo = true;
            if (url.indexOf("?") != -1) {
                bo = false;
            }
            for (Map.Entry<String, String> e : entrySet) {
                if (StringUtils.isNotBlank(e.getKey())) {
                    try {
                        if (bo) {
                            sb.append("?").append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), "UTF-8"));
                        } else {
                            sb.append("&").append(e.getKey()).append("=").append(URLEncoder.encode(e.getValue(), "UTF-8"));
                        }
                        bo = false;
                    }catch(Exception ex){
                        throw new HttpException("500", "value encode failed, value=" + e.getValue());
                    }
                }
            }
            url = sb.toString();
        }
        HttpGet httpGet = new HttpGet(url);
        if (StringUtils.isNotBlank(cookie)) {
            httpGet.addHeader("Cookie",cookie);
        }
        return doRequest(httpGet);
    }

    /** 
     * post 请求  
     * 
     * @param url    请求地址
     * @param params 请求参数，会对参数值进行编码
     * @param cookie cookie，格式：aa=111;bb=22
     * 
     */
    public String post(String url, Map<String, String> params, String cookie) throws HttpException{
        if(StringUtils.isBlank(url)) {
            throw new HttpException("url is empty!");
        }
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> pairs = null;
        if (params != null && params.size() > 0) {
            pairs = new ArrayList<NameValuePair>(params.size());
            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> e : entrySet) {
                if (StringUtils.isNotBlank(e.getKey())) {
                    NameValuePair pair = new BasicNameValuePair(e.getKey(), e.getValue());
                    pairs.add(pair);
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, Consts.UTF_8));
        }
        if (StringUtils.isNotBlank(cookie)) {
            httpPost.addHeader("Cookie",cookie);
        }
        return doRequest(httpPost);
    }
    
    private String doRequest(HttpRequestBase httpRequestBase) throws HttpException{
        try {
            return doRequestBase(httpRequestBase);
        } catch (HttpException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error(httpRequestBase.getRequestLine() + "\n" + e.getMessage(), e);
            throw new HttpException("Http resquest failed.", e);
        }
    }
    private String doRequestBase(HttpRequestBase httpRequestBase) throws Exception {
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        try {
            response = httpClient.execute(httpRequestBase);
            //获得响应的消息实体
            entity = response.getEntity();
            //获取响应状态码
            int statusCode = response.getStatusLine().getStatusCode();
            //根据响应状态码进行处理
            switch (statusCode) {
                case HttpStatus.SC_OK:
                    String result = EntityUtils.toString(entity, Consts.UTF_8);
                    return result;
                default:
                    LOGGER.debug("== do request failed, code=" + statusCode + ", url=" + httpRequestBase.getRequestLine());
                    throw new HttpException(String.valueOf(statusCode), "Http resquest failed, code=" + statusCode + ", url=" + httpRequestBase.getRequestLine());
            }
        } finally {
            if (entity != null) {
                try {
                    EntityUtils.consume(entity);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if(httpRequestBase != null){
                try {
                    httpRequestBase.releaseConnection();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }
    
}
