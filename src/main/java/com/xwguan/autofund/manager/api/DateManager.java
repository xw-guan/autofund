package com.xwguan.autofund.manager.api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.xwguan.autofund.dao.date.NonWeekendHolidayDao;

/**
 * 交易日/节假日获取及判断
 * 
 * @author XWGuan
 * @version 1.0.0 207-10-24
 */
public interface DateManager {

    /**
     * 获取可用年份
     * 
     * @return 可用年份列表
     */
    List<Integer> getAvailableYears();

    /**
     * 获取某年的假期列表
     * <p><b>尚未有实现类</b>
     * 
     * @param year
     * @return
     * @throws IOException
     */
    @Deprecated
    List<LocalDate> listHoliday(int year) throws IOException;

    /**
     * 获取某年某月的假期列表
     * <p><b>尚未有实现类</b>
     * 
     * @param year
     * @return
     * @throws IOException
     */
    @Deprecated
    List<LocalDate> listHoliday(int year, Month month) throws IOException;

    /**
     * 获取某年的非工作日列表
     * 
     * @param year
     * @return
     * @throws IOException
     */
    List<LocalDate> listNonWorkDay(int year) throws IOException;

    /**
     * 获取某年某月的非工作日列表
     * 
     * @param year
     * @return
     * @throws IOException
     */
    List<LocalDate> listNonWorkDay(int year, Month month) throws IOException;

    /**
     * 获取某年的非周末节假日列表
     * <p>此方法因考虑到其他方法尚无免费方便的实现手段而增加, 目前有相对较好的实现, 未来可能会被弃用
     * 
     * @param year
     * @return
     * @throws IOException
     */
    List<LocalDate> listNonWeekendHoliday(int year) throws IOException;

    /**
     * 判断某天是否为交易日
     * <p>效率太低, 应当在初始化后使用{@link NonWeekendHolidayDao}从数据库中获取交易日信息
     * 
     * @param date
     * @return
     * @throws IOException
     */
    @Deprecated
    boolean isTradeDay(LocalDate date) throws IOException;
}
