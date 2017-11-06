package com.longstore.common.exception;

/**
 * 服务层异常统一处理
 */
public class ServiceException extends ExceptionSupper {
    private static final long   serialVersionUID   = -8559448572367908328L;

    public ServiceException(String code) {
        super(code);
    }
    
    public ServiceException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public ServiceException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public ServiceException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }
}
