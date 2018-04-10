package com.xwguan.autofund.manager.util;

import java.time.LocalDate;

/**
 * 日期筛选判断标准
 * TODO 改成Predicate接口
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-29
 */
public interface DateCriterion {
    
    boolean isMet(LocalDate date);
    
}
