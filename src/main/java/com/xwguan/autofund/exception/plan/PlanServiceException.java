package com.xwguan.autofund.exception.plan;

/**
 * 计划相关异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-08
 */
public class PlanServiceException extends Exception{

    private static final long serialVersionUID = -8575098279480483263L;

    public PlanServiceException() {
        super();
    }

    public PlanServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public PlanServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanServiceException(String message) {
        super(message);
    }

    public PlanServiceException(Throwable cause) {
        super(cause);
    }

}
