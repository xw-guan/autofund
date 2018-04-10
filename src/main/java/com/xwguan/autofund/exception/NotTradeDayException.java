package com.xwguan.autofund.exception;

/**
 * 非交易日期异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-30
 */
public class NotTradeDayException extends Exception {

    private static final long serialVersionUID = -8001371196211739809L;

    public NotTradeDayException() {
        super();
    }

    public NotTradeDayException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public NotTradeDayException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotTradeDayException(String message) {
        super(message);
    }

    public NotTradeDayException(Throwable cause) {
        super(cause);
    }

}
