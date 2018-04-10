package com.xwguan.autofund.exception.trade;

/**
 * 交易异常
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-28
 */
public class AfterTradeException extends TradeException {

    private static final long serialVersionUID = 125257658395730800L;

    public AfterTradeException() {
        super();
    }

    public AfterTradeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AfterTradeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AfterTradeException(String message) {
        super(message);
    }

    public AfterTradeException(Throwable cause) {
        super(cause);
    }

}
