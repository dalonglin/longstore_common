package com.longstore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数是否需要解密操作
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamsDig {
	
	public static final String YES = "yes";
    public static final String NO = "no";
    
	/**
	 * 是否进行加密解密操作
     *   no：不进行加密解密
     *   yes：进行加密解密
     */
    String isDig() default "yes";
    
}
