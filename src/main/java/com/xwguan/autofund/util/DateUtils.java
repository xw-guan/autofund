package com.xwguan.autofund.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * 日期工具
 * @author XWGuan
 * @version 1.0.0 2017-10-29
 */
public final class DateUtils {
    /**
     * 判断某日期是否为周末
     * @param date
     * @return
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
    }
}
