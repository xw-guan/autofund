package com.xwguan.autofund.exception;

public class UpdateException extends Exception {

    private static final long serialVersionUID = -1672850769131485461L;

    public UpdateException() {
        super();
    }

    public UpdateException(String message, Throwable cause, boolean enableSuppression,
        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }

}
