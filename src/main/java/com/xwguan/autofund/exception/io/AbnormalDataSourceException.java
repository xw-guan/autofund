package com.xwguan.autofund.exception.io;

import java.io.IOException;

/**
 * 异常数据来源
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-28
 */
public class AbnormalDataSourceException extends IOException{

    private static final long serialVersionUID = 2320244583303221082L;

    public AbnormalDataSourceException() {
        super();
    }

    public AbnormalDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbnormalDataSourceException(String message) {
        super(message);
    }

    public AbnormalDataSourceException(Throwable cause) {
        super(cause);
    }

}
