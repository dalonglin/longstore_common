package com.longstore.common.exception;

import com.longstore.common.constants.ExceptionConstants;

/**
 * 异常超类
 */
public class ExceptionSupper extends RuntimeException {
    private static final long serialVersionUID = 1345659121694845655L;
    private String code = null;

    public ExceptionSupper(String code) {
        super(code);
        this.code = code;
    }
    
    public ExceptionSupper(String code, String errorMessage) {
        super(errorMessage);
        this.code = code;
    }

    public ExceptionSupper(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
        this.code = ExceptionConstants.ERROR_CODE_DEFAULT;
    }

    public ExceptionSupper(String code, String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    
}
