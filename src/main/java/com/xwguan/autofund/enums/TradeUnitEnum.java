package com.xwguan.autofund.enums;

/**
 * 交易相关计量单位
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-15
 */
public enum TradeUnitEnum {

    SHARE("份额"), PERCENT("%"), YUAN("元");

    private String info;

    private TradeUnitEnum(String desc) {
        this.info = desc;
    }

    public String getInfo() {
        return info;
    }

}
