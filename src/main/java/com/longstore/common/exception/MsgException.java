package com.longstore.common.exception;

/**
 * 消息请求异常统一处理
 */
public class MsgException extends ExceptionSupper {
    private static final long serialVersionUID = -8995587507639075166L;

    public MsgException(String code) {
        super(code);
    }
    
    public MsgException(String code, String errorMessage) {
        super(code, errorMessage);
    }

    public MsgException(String errorMessage, Throwable clauses) {
        super(errorMessage, clauses);
    }

    public MsgException(String code, String errorMessage, Throwable clauses) {
        super(code, errorMessage, clauses);
    }


    
}
