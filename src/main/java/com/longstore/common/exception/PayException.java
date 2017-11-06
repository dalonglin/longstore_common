package com.longstore.common.exception;

/**
 * 支付异常统一处理
 */
public class PayException extends ExceptionSupper {
    private static final long   serialVersionUID   = -8559448572367908328L;

    public PayException(String code) {
        super(code);
    }
    
    public PayException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public PayException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public PayException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }
}
