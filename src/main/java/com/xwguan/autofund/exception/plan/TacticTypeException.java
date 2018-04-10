package com.xwguan.autofund.exception.plan;

/**
 * 策略类型异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-08
 */
public class TacticTypeException extends Exception{

    private static final long serialVersionUID = -8788609565877463794L;

    public TacticTypeException() {
        super();
    }

    public TacticTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TacticTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TacticTypeException(String message) {
        super(message);
    }

    public TacticTypeException(Throwable cause) {
        super(cause);
    }

}
