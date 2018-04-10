package com.xwguan.autofund.exception.trade;

/**
 * 交易异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-28
 */
public class TradeException extends Exception {

    private static final long serialVersionUID = -8162885263513808658L;

    public TradeException() {
        super();
    }

    public TradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public TradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TradeException(String message) {
        super(message);
    }

    public TradeException(Throwable cause) {
        super(cause);
    }

}
