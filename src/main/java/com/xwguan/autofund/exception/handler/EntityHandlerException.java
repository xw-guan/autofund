package com.xwguan.autofund.exception.handler;

/**
 * 处理者异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
public class EntityHandlerException extends Exception {

    private static final long serialVersionUID = -1081157509592327072L;

    public EntityHandlerException() {
        super();
    }

    public EntityHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntityHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityHandlerException(String message) {
        super(message);
    }

    public EntityHandlerException(Throwable cause) {
        super(cause);
    }

}
