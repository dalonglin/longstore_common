package com.longstore.common.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * json 转换处理
 */
public class JsonUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private final static SerializerFeature[] config_useClassName = new SerializerFeature[] { 
            SerializerFeature.WriteClassName
    };

    /**
     * 把对象转换成json
     * 
     * @param object
     * @param useClassName 是否输出class的名称
     */
    public static String toJson(Object object, boolean useClassName) {
        if (object == null) {
            return null;
        }
        String jsonText = null;
        if (useClassName) {
            try {
            	jsonText = JSONObject.toJSONString(object, config_useClassName);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            jsonText = toJson(object);
        }
        return jsonText;
    }

    /**
     * 把对象转换成json，不输出class的名称
     * 
     * @param object
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
        	return JSONObject.toJSONString(object);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 把json转换成对象
     * 
     * @param jsonString
     */
    public static <T> T toObject(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            LOGGER.error(jsonString, e);
            return null;
        }
    }

    /**
     * 把json转换成对象
     * 
     * @param jsonString
     */
    public static <T> List<T> toList(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return JSON.parseArray(jsonString, clazz);
        } catch (Exception e) {
            LOGGER.error(jsonString, e);
            return null;
        }
    }

}
