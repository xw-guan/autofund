package com.xwguan.autofund.exception;

/**
 * 初始化失败异常
 * 
 * @author XWGuan
 * @version 1.0.0
 */
public class FailInitializationException extends RuntimeException {

    private static final long serialVersionUID = 8815360182989845023L;

    public FailInitializationException() {
        super();
    }

    public FailInitializationException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FailInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailInitializationException(String message) {
        super(message);
    }

    public FailInitializationException(Throwable cause) {
        super(cause);
    }

}
