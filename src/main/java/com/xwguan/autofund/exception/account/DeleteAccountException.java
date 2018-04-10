package com.xwguan.autofund.exception.account;

/**
 * 账户删除异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-23
 */
public class DeleteAccountException extends AccountServiceException {

    private static final long serialVersionUID = -3671128807347096909L;

    public DeleteAccountException() {
        super();
    }

    public DeleteAccountException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DeleteAccountException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteAccountException(String message) {
        super(message);
    }

    public DeleteAccountException(Throwable cause) {
        super(cause);
    }
    
}
