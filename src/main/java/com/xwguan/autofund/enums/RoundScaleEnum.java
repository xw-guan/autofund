package com.xwguan.autofund.enums;

/**
 * 小数保留位数
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public enum RoundScaleEnum {
    /**
     * 显示的钱数小数位数, 2
     */
    MONEY_ROUND_SCALE(2),
    /**
     * 显示的净值小数位数, 4
     */
    NAV_ROUND_SCALE(4),
    /**
     * 显示的份额小数位数, 2
     */
    SHARE_ROUND_SCALE(2),
    /**
     * 显示的百分比数值的小数位数, 2
     */
    PCT_ROUND_SCALE(2),
    /**
     * 股票数据小数位数
     */
    STOCK_ROUND_SCALE(4);

    private int scale;

    private RoundScaleEnum(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }
}
