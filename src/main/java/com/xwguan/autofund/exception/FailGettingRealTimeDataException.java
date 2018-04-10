package com.xwguan.autofund.exception;

/**
 * 无法获取实时值异常, 通常是网络原因或
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-30
 */
public class FailGettingRealTimeDataException extends Exception {

    private static final long serialVersionUID = 5053107132772760220L;

    public FailGettingRealTimeDataException() {
        super();
    }

    public FailGettingRealTimeDataException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FailGettingRealTimeDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public FailGettingRealTimeDataException(String message) {
        super(message);
    }

    public FailGettingRealTimeDataException(Throwable cause) {
        super(cause);
    }
    
}
