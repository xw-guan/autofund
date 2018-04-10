package com.xwguan.autofund.exception;

/**
 * 冲突数据异常, 如交易日没有净值等
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-09
 */
public class ConflictingDataException extends Exception {

    private static final long serialVersionUID = -1424419326319973225L;

    public ConflictingDataException() {
        super();
    }

    public ConflictingDataException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ConflictingDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConflictingDataException(String message) {
        super(message);
    }

    public ConflictingDataException(Throwable cause) {
        super(cause);
    }

}
