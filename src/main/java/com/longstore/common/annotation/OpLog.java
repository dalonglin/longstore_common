package com.longstore.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 操作日志记录
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface OpLog {
	
	public static final String YES = "yes";
    public static final String NO = "no";
    
    public static final String OP_TYPE_ADD = "a";
    public static final String OP_TYPE_DELETE = "d";
	public static final String OP_TYPE_UPDATE = "u";
	public static final String OP_TYPE_VIEW = "v";
    
	/**
	 * 是否记录日志
     *   no：不
     *   yes：需要
     */
    String isLog() default "yes";
    
    /**
	 * 操作类型，a：新加，d：删除，u：更新，v：访问
     */
    String type() default "";
    /**
	 * 被操作对象类型
     */
    String targetName() default "";
    /**
	 * 指定参数名称以提取被操作对象ID
     */
    String targetParam() default "";
    
}
