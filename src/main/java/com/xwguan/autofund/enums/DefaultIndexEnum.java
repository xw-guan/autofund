package com.xwguan.autofund.enums;

/**
 * 默认指数
 * @author XWGuan
 */
public enum DefaultIndexEnum {
    SH000001("000001.SH", "上证指数"),
    SZ399001("399001.SZ", "深证成指"),
    SH000300("000300.SH", "沪深300"),
    SH000016("000016.SH", "上证50"),
    SH000095("000905.SH", "中证500"),
    SZ399006("399006.SZ", "创业板指"),
    SZ399005("399005.SZ", "中小板指"),
    SZ399678("399678.SZ", "深次新股");
    
    private String symbol;
    private String name;
    
    private DefaultIndexEnum(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
    }
    public String getSymbol() {
        return symbol;
    }
    public String getName() {
        return name;
    }
}
