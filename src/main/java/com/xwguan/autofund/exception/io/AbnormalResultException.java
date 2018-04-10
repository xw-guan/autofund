package com.xwguan.autofund.exception.io;

import java.io.IOException;

/**
 * 不正常的结果异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-07
 */
public class AbnormalResultException extends IOException {

    private static final long serialVersionUID = 5898352744808746332L;

    public AbnormalResultException() {
        super();
    }

    public AbnormalResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbnormalResultException(String message) {
        super(message);
    }

    public AbnormalResultException(Throwable cause) {
        super(cause);
    }

}
