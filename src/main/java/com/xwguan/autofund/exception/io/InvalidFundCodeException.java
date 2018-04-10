package com.xwguan.autofund.exception.io;

/**
 * 基金代码格式不合法
 * 
 * @author XWGuan
 * @version 1.0.0 
 */
public class InvalidFundCodeException extends InvalidParamException {

    private static final long serialVersionUID = 5057079369481776616L;

    public InvalidFundCodeException() {
        super();
    }

    public InvalidFundCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFundCodeException(String message) {
        super(message);
    }

    public InvalidFundCodeException(Throwable cause) {
        super(cause);
    }

}
