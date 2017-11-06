package com.longstore.common.exception;

/**
 * 数据层异常统一处理
 */
public class DataException extends ExceptionSupper {
    private static final long   serialVersionUID   = -8559448572367908328L;

    public DataException(String code) {
        super(code);
    }
    
    public DataException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public DataException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public DataException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }
}
