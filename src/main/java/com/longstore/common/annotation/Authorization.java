package com.longstore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
    
	/**
     * 是否检查登陆状态
     *     yes：检查
     *     no：不检查
     */
    String checkLogin() default "yes";
    
    /**
     * 是否检查具有访问权限
     *     yes：检查
     *     no：不检查
     */
    String checkSc() default "no";
    
    /**
     * 检查表单token
     *     yes：检查
     *     no：不检查
     */
    String checkFormToken() default "no";
    
}
