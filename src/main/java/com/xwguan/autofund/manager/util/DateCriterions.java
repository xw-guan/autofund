package com.xwguan.autofund.manager.util;

import com.xwguan.autofund.util.DateUtils;

/**
 * 几个常用DateCriterion的获取工厂方法
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-29
 */
public final class DateCriterions {
    
    public static final DateCriterion ALL_DATE_PASS = date -> true;

    public static final DateCriterion NOT_WEEKEND = date -> !DateUtils.isWeekend(date);

}
