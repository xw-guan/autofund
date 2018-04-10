package com.xwguan.autofund.exception.io;

/**
 * 输入为空异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-07
 */
public class EmptyInputException extends InvalidParamException {

    private static final long serialVersionUID = 2953296710256828336L;

    public EmptyInputException() {
        super();
    }

    public EmptyInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyInputException(String message) {
        super(message);
    }

    public EmptyInputException(Throwable cause) {
        super(cause);
    }

}
