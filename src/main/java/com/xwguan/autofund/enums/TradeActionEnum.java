package com.xwguan.autofund.enums;

/**
 * 策略操作枚举
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-23
 */
public enum TradeActionEnum {

    SELL("卖出"), IGNORE("不操作"), BUY("买入");

    private String info;

    private TradeActionEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
