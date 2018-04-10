package com.xwguan.autofund.exception.plan;

/**
 * 回测异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-08
 */
public class BackTestServiceException extends PlanServiceException {

    private static final long serialVersionUID = -2864771582553417058L;

    public BackTestServiceException() {
        super();
    }

    public BackTestServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BackTestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BackTestServiceException(String message) {
        super(message);
    }

    public BackTestServiceException(Throwable cause) {
        super(cause);
    }

}
