package com.longstore.common.exception;

/**
 * 缓存异常
 */
public class CacheException extends ExceptionSupper {
    private static final long serialVersionUID = 2784776370805061363L;

    public CacheException(String code) {
        super(code);
    }
    
    public CacheException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public CacheException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public CacheException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }


    
}
