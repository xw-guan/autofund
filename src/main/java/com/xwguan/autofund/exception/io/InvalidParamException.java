package com.xwguan.autofund.exception.io;

import java.io.IOException;

/**
 * 参数不合法
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-23
 */
public class InvalidParamException extends IOException{

    private static final long serialVersionUID = -4657556041551463167L;

    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidParamException(String message) {
        super(message);
    }

    public InvalidParamException(Throwable cause) {
        super(cause);
    }
    
}
