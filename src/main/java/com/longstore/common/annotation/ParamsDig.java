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
    
	/**
     * 对哪种请求方式进行解密操作，默认对所有方法进行加密解密
     *     no：不对任何方法进行加密解密
     *     空：对任何方法进行加密解密
     *     其他：指定方法，使用大写，多个以英文逗号分隔。
     */
    String method() default "";
    
}
