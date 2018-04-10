package com.xwguan.autofund.exception;

/**
 * 超出最大迭代次数异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-01
 */
public class OverMaxIterationNumberException extends Exception {

    private static final long serialVersionUID = -2376093196886880365L;

    public OverMaxIterationNumberException() {
        super();
    }

    public OverMaxIterationNumberException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public OverMaxIterationNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverMaxIterationNumberException(String message) {
        super(message);
    }

    public OverMaxIterationNumberException(Throwable cause) {
        super(cause);
    }

}
