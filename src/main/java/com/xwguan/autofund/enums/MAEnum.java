package com.xwguan.autofund.enums;

/**
 * 均线日数枚举类
 * 
 * @author XWGuan
 * @version 1.0.1 2017-10-21
 */
public enum MAEnum {
    MA5(5), MA10(10), MA20(20), MA30(30), MA60(60), MA120(120), MA250(250);

    private int dayNumber;

    private MAEnum(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public static MAEnum maOf(int dayNumber) {
        for (MAEnum m : MAEnum.values()) {
            if (m.getDayNumber() == dayNumber) {
                return m;
            }
        }
        return null;
    }
}
