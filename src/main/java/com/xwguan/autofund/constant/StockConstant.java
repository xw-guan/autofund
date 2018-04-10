package com.xwguan.autofund.constant;

import java.time.LocalDate;
import java.time.LocalTime;

public final class StockConstant {
    
    /**
     * 默认数据开始时间
     */
    public static final LocalDate DEFAULT_DATA_START_DATE = LocalDate.parse("1990-12-01");
    
    /**
     * 开盘时间
     */
    public static final LocalTime OPEN_TIME = LocalTime.of(9, 30, 0);
    
    /**
     * 收盘时间
     */
    public static final LocalTime CLOSE_TIME = LocalTime.of(15, 0, 0);
    
}
