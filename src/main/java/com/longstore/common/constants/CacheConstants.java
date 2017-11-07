package com.longstore.common.constants;

/**
 * 缓存常量
 */
public interface CacheConstants {
    
    public static final int EXPIRE_NONE = Integer.MAX_VALUE;
    public static final int EXPIRE_15M = 15 * 60 ;
    public static final int EXPIRE_30M = 30 * 60 ;
    public static final int EXPIRE_HOUR = 60 * 60 ;
    public static final int EXPIRE_HOUR_2 = 2 * 60 * 60 ;
    public static final int EXPIRE_HOUR_3 = 3 * 60 * 60 ;
    public static final int EXPIRE_HOUR_6 = 6 * 60 * 60 ;
    public static final int EXPIRE_DAY = 24 * 60 * 60 ;
    public static final int EXPIRE_DAY_7 = 7 * 24 * 60 * 60;
    
    public static final String USER_SESSION_PREFIX = "user_session_";
    public static final String USER_FORM_TOKEN_PREFIX = "form_token_";

    public static final String GENERATE_IMG_CODE = "code_img";

}
