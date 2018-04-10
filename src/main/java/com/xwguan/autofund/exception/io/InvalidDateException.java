package com.xwguan.autofund.exception.io;

/**
 * 日期不合法
 * 
 * @author XWGuan
 * @version 1.0.0
 */
public class InvalidDateException extends InvalidParamException {

    private static final long serialVersionUID = 5057079369481776616L;

    public InvalidDateException() {
        super();
    }

    public InvalidDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDateException(String message) {
        super(message);
    }

    public InvalidDateException(Throwable cause) {
        super(cause);
    }

}
