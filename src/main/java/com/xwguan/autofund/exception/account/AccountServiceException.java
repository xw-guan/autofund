package com.xwguan.autofund.exception.account;

/**
 * 账户服务异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-16
 */
public class AccountServiceException extends Exception {

    private static final long serialVersionUID = -258068792293015194L;

    public AccountServiceException() {
        super();
    }

    public AccountServiceException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AccountServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountServiceException(String message) {
        super(message);
    }

    public AccountServiceException(Throwable cause) {
        super(cause);
    }

}
