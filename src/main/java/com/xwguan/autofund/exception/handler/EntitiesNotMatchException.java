package com.xwguan.autofund.exception.handler;

/**
 * 处理的多个实体不匹配异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
public class EntitiesNotMatchException extends RuntimeException {

    private static final long serialVersionUID = 5788788264307523266L;

    public EntitiesNotMatchException() {
        super();
    }

    public EntitiesNotMatchException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EntitiesNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntitiesNotMatchException(String message) {
        super(message);
    }

    public EntitiesNotMatchException(Throwable cause) {
        super(cause);
    }

}
