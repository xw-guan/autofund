package com.xwguan.autofund.exception.io;

import java.io.IOException;

/**
 * 空结果异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-07
 */
public class EmptyResultException extends IOException {

    private static final long serialVersionUID = 5898352744808746332L;

    public EmptyResultException() {
        super();
    }

    public EmptyResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyResultException(String message) {
        super(message);
    }

    public EmptyResultException(Throwable cause) {
        super(cause);
    }

}
