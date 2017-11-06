package com.longstore.common.exception;

/**
 * http请求异常统一处理
 */
public class HttpException extends ExceptionSupper {
    private static final long serialVersionUID = -2036598483216236694L;

    public HttpException(String code) {
        super(code);
    }
    
    public HttpException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public HttpException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public HttpException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }


    
}
