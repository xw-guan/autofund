package com.xwguan.autofund.dao.date;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface NonWeekendHolidayDao {

    /**
     * 获取某年某月的非周末节假日
     * @param year
     * @param month
     * @return 
     */
    List<LocalDate> listNonWeekendHolidayOfMonth(@Param("year") int year, @Param("month") int month);
    
    /**
     * 获取某年的非周末节假日
     * @param year
     * @return 
     */
    List<LocalDate> listNonWeekendHolidayOfYear(@Param("year") int year);
    
    /**
     * 将非周末节假日列表插入数据库
     * @param nonWeekendHolidayList
     * @return 成功插入数量
     */
    int insertNonWeekendHoliday(List<LocalDate> nonWeekendHolidayList);
}
