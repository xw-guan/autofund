package com.xwguan.autofund.enums;

/**
 * 交易状态枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-05
 */
public enum TradeStateEnum {

    SUCCESS("交易成功"), // 成功状态
    SUBMITTED("已提交"), // 待定状态
    CANCELLED("已撤销"), // 无效状态
    TRADE_FAILED("交易失败"), // 无效状态
    CANCEL_FAILED("取消失败"), // 无效状态
    ZERO_TRADE_VALUE("交易值为0"), // 成功状态
    TO_TRADE("准备交易"); // 无效状态

    private String info;

    private TradeStateEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    /**
     * @return true if is SUCCESS or ZERO_TRADE_VALUE or SUBMITTED
     */
    public boolean isValidTradeState() {
        return isSuccessTradeState() || this == SUBMITTED;
    }

    /**
     * @return true if is SUCCESS or SUBMITTED
     */
    public boolean isNonzeroValidTradeState() {
        return this == SUCCESS || this == SUBMITTED;
    }

    /**
     * @return true if is SUCCESS or ZERO_TRADE_VALUE
     */
    public boolean isSuccessTradeState() {
        return this == SUCCESS || this == ZERO_TRADE_VALUE;
    }

}
