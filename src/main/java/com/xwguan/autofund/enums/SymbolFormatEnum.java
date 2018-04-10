package com.xwguan.autofund.enums;

/**
 * 股票代码格式
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-01
 */
public enum SymbolFormatEnum {

    /**
     * 不包含交易所代码的股票代码, 形如000001, 正则表达式为^[0-9]{6}$
     */
    BARE("^[0-9]{6}$", "000001", StockExchangePosition.EMPTY),
    /**
     * 交易所代码为.字母后缀的股票代码, 形如000001.SH或000001.SZ, 对应本项目,
     * 正则表达式为^([0-9]{6})(\\.[SHZ]{2})$
     */
    AUTOFUND("^([0-9]{6})(\\.[SHZ]{2})$", "000001.SH or 000001.SZ", StockExchangePosition.SUFFIX),
    /**
     * 交易所代码为数字后缀的股票代码, 形如0000011或0000012, 对应天天基金网API, 映射关系: 1-SH, 2-SZ
     * 正则表达式为^([0-9]{6})([0-9]{1})$
     */
    EASYMONEY("^([0-9]{6})([0-9]{1})$", "0000011 or 0000012", StockExchangePosition.SUFFIX),
    /**
     * 交易所代码为数字前缀的股票代码, 形如0000001或1000001, 对应网易API, 映射关系: 0-SH, 1-SZ
     * 正则表达式为^([0-9]{1})([0-9]{6})$
     */
    NETEASE("^([0-9]{1})([0-9]{6})$", "0000001 or 1000001", StockExchangePosition.PREFIX),
    /**
     * 交易所代码为小写前缀的股票代码, 形如sh000001或sz000001, 对应新浪API,
     * 正则表达式为^([shz]{2})([0-9]{6})$
     */
    SINA("^([shz]{2})([0-9]{6})$", "sh000001 or sz000001", StockExchangePosition.PREFIX);

    public enum StockExchangePosition {
        PREFIX, SUFFIX, EMPTY
    }

    private String regex;

    private String eg;

    private StockExchangePosition stockExchangePosition;

    private SymbolFormatEnum(String regex, String eg, StockExchangePosition stockExchangePosition) {
        this.regex = regex;
        this.eg = eg;
        this.stockExchangePosition = stockExchangePosition;

    }

    public String getRegex() {
        return regex;
    }

    public String getEg() {
        return eg;
    }

    public StockExchangePosition getStockExchangePosition() {
        return stockExchangePosition;
    }

}
