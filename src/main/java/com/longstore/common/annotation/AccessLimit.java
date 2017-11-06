package com.longstore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问限制
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {
    
    /**
     * ip访问单位时间内访问次数限制，默认时间单位15秒
     *   0：不限制
     */
    int ipC() default 0;
    /**
     * ip访问限制之单位时间，单位秒
     */
    int ipT() default 15;
    
    /**
     * 用户访问单位时间内访问次数限制，默认时间单位15秒
     *   0：不限制
     */
    int uidC() default 0;
    /**
     * 用户访问限制之单位时间，单位秒
     */
    int uidT() default 15;
    
    /**
     * 自定义访问限制的参数，可用手机号码
     */
    String params() default "";
    /**
     * 自定义参数访问单位时间内访问次数限制，默认时间单位15秒
     *   0：不限制
     */
    int paramsC() default 0;
    /**
     * 自定义参数访问限制之单位时间，单位秒
     */
    int paramsT() default 15;
    
}
