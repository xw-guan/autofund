package com.xwguan.autofund.enums;

/**
 * 证券交易所枚举
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-19
 */
public enum StockExchangeEnum {
    SH("上海证券交易所", "上交所"), SZ("深圳证券交易所", "深交所");

    private String name;
    private String abbrName;

    private StockExchangeEnum(String name, String abbrName) {
        this.name = name;
        this.abbrName = abbrName;
    }

    public String getName() {
        return name;
    }

    public String getAbbrName() {
        return abbrName;
    }
}
