package com.longstore.common.util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * base 64 加密解密
 */
public class Base64Util {
    private final static Logger LOGGER = LoggerFactory.getLogger(Base64Util.class);

    public static byte[] decode(String content){
        try {
			return Base64.decodeBase64(content);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
        return null;
    }
    public static String encode(byte[] content){
        return Base64.encodeBase64String(content);
    }
}
