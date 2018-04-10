package com.xwguan.autofund.exception.io;

/**
 * 股票代码格式不合法
 * 
 * @author XWGuan
 * @version 1.0.0 
 */
public class InvalidTickerSymbolException extends InvalidParamException {

    private static final long serialVersionUID = 5057079369481776616L;

    public InvalidTickerSymbolException() {
        super();
    }

    public InvalidTickerSymbolException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidTickerSymbolException(String message) {
        super(message);
    }

    public InvalidTickerSymbolException(Throwable cause) {
        super(cause);
    }

}
