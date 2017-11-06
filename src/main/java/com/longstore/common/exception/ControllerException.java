package com.longstore.common.exception;

/**
 * controller异常统一处理
 */
public class ControllerException extends ExceptionSupper {
    private static final long serialVersionUID = -2036598483216236694L;

    public ControllerException(String code) {
        super(code);
    }
    
    public ControllerException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public ControllerException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public ControllerException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }


    
}
