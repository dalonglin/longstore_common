package com.longstore.common.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;

import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;

import com.longstore.common.exception.ExceptionSupper;

/**
 * 加密工具
 */
public final class MD5util {
    
    private MD5util() {
    }
    private static final Charset utf8 = Charset.forName(CharEncoding.UTF_8);
    private static final String  MD5          = "MD5";
    private static final char[]  hexDigits    = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    private static MessageDigest messageDigest;
    private static MessageDigest getMessageDigest(){
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance(MD5);
            } catch (final Exception e) {
                throw new ExceptionSupper(e.getMessage(), e);
            }
        }
        return messageDigest;
    }
    
    /** 加密并转16进度字符串 */
    public static String digest(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            throw new NullPointerException("symbol must be not null or empty!");
        }
        byte[] bytes_utf8 = symbol.getBytes(utf8);
        byte[] bytes_md5 = getMessageDigest().digest(bytes_utf8);
        char str[] = new char[16 * 2];
        int k = 0;
        for (int i = 0; i < 16; i++) {
            byte byte0 = bytes_md5[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
    
    /** 加密并使用base64转成字符串 */
    public static String digestBase64(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            throw new NullPointerException("symbol must be not null or empty!");
        }
        byte[] bytes_utf8 = symbol.getBytes(utf8);
        byte[] bytes_md5 = getMessageDigest().digest(bytes_utf8);
        return Base64Util.encode(bytes_md5);
    }
    
}
