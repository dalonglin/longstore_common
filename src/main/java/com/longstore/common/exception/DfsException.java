package com.longstore.common.exception;

/**
 * 分布式存储异常统一处理
 */
public class DfsException extends ExceptionSupper {
    private static final long serialVersionUID = 2344394700857589460L;
    
    public DfsException(String code) {
        super(code);
    }
    
    public DfsException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public DfsException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public DfsException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }
}
