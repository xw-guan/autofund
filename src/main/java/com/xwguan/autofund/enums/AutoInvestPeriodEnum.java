package com.xwguan.autofund.enums;

import java.time.temporal.ChronoUnit;

/**
 * 定投周期枚举, DAILY, WEEKLY, MONTHLY, BIWEEKLY, BIMONTHLY, QUATERLY, SEMIANNUALLY, ANNUALLY
 * 
 * @author XWGuan
 * @version 0.1 2017-10-09
 */
public enum AutoInvestPeriodEnum {

    DAILY(ChronoUnit.DAYS, 1, "每交易日"), 
    WEEKLY(ChronoUnit.WEEKS, 1, "每周"), 
    BIWEEKLY(ChronoUnit.WEEKS, 2, "每两周"), 
    MONTHLY(ChronoUnit.MONTHS, 1, "每月"), 
    BIMONTHLY(ChronoUnit.MONTHS, 2, "每两月"), 
    QUATERLY(ChronoUnit.MONTHS, 3, "每季度"),
    SEMIANNUALLY(ChronoUnit.MONTHS, 6, "每半年"),
    ANNUALLY(ChronoUnit.YEARS, 1, "每年");

    private ChronoUnit unit;
    
    private int amount;
    
    private String info;

    private AutoInvestPeriodEnum(ChronoUnit unit, int amount, String info) {
        this.unit = unit;
        this.amount = amount;
        this.info = info;
    }

    public ChronoUnit getUnit() {
        return unit;
    }

    public int getAmount() {
        return amount;
    }

    public String getInfo() {
        return info;
    }

}
