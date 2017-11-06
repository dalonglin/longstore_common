package com.longstore.common.util;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebConfigUtil {
    private static final Logger     logger = LoggerFactory.getLogger(WebConfigUtil.class);
    
    private final static Properties properties;
    static{
        properties = new Properties();
        InputStream in = null;
        try {
            in = WebConfigUtil.class.getResourceAsStream("/web_config.properties");
            properties.load(in);
        } catch (Exception e) {
            logger.error("Do not load properties from the file : web_config.properties", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {

                }
            }
        }
    }

    public static String domain() {
        return get("domain.url");
    }
    public static String home() {
        return get("home.url");
    }
    public static String statics() {
        return get("static.url");
    }
    public static String image() {
        return get("image.url");
    }
    public static String file() {
        return get("file.url");
    }
    private final static char c = '/';
    public static String image(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (c == url.charAt(0)) {
            return get("image.url") + url;
        }
        return get("image.url") + "/" + url;
    }
    public static String file(String url) {
        if (StringUtils.isBlank(url)) {
            return null;
        }
        if (c == url.charAt(0)) {
            return get("file.url") + url;
        }
        return get("file.url") + "/" + url;
    }
    
    public static String web() {
        return get("web.url");
    }
    public static String wap() {
        return get("wap.url");
    }
    public static String api() {
        return get("api.url");
    }
    
    public static String get(String code) {
        if(properties != null){
            return properties.getProperty(code);
        }
        return null;
    }
    
}
