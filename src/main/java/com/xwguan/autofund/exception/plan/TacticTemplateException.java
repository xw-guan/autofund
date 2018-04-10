package com.xwguan.autofund.exception.plan;

/**
 * 策略模板异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-08
 */
public class TacticTemplateException extends Exception{

    private static final long serialVersionUID = 5649991372824664832L;

    public TacticTemplateException() {
        super();
    }

    public TacticTemplateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TacticTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TacticTemplateException(String message) {
        super(message);
    }

    public TacticTemplateException(Throwable cause) {
        super(cause);
    }

}
