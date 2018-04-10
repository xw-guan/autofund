package com.xwguan.autofund.exception.plan;

/**
 * 未知模板代码异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-30
 */
public class UnknownTemplateCodeException extends Exception {

    private static final long serialVersionUID = -8328074599884948890L;

    public UnknownTemplateCodeException() {
        super();
    }

    public UnknownTemplateCodeException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UnknownTemplateCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownTemplateCodeException(String message) {
        super(message);
    }

    public UnknownTemplateCodeException(Throwable cause) {
        super(cause);
    }

}
