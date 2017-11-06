package com.longstore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存处理
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    
    /**
     * 存放时间，为0不过期。单位秒。默认10分钟
     *     注意，是否生效，取决于所使用的缓存器。
     */
    int expire() default 600;

    /**
     * 哪种请求方法被缓存。GET/POST，必须大写
     */
    String cmethod() default "";
    
}
