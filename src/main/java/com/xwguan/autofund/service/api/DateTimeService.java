package com.xwguan.autofund.service.api;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 日期时间服务
 * <p>提供交易日列表, 提供某日是否为交易日的判断
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-29
 */
public interface DateTimeService {

    /**
     * 某年的交易日列表
     * 
     * @param year
     * @return 某年的交易日列表
     */
    List<LocalDate> listTradeDay(int year);

    /**
     * 某月的交易日列表
     * 
     * @param year
     * @param month
     * @return 某月的交易日列表
     */
    List<LocalDate> listTradeDay(int year, int month);

    /**
     * 某段时间内的交易日列表, 包含开始和结束日期
     * 
     * @param startDate
     * @param endDate
     * @return 某段时间内的交易日列表, 包含开始和结束日期
     */
    List<LocalDate> listTradeDay(LocalDate startDate, LocalDate endDate);

    /**
     * 某判断某日是否为交易日
     * 
     * @param date
     * @return 某日是否为交易日, 是: true, 否: false
     */
    boolean isTradeDay(LocalDate date);

    /**
     * 某判断某日是否不是交易日
     * 
     * @param date
     * @return 某日是否不是交易日, 不是交易日: true, 是交易日: false
     */
    boolean isNotTradeDay(LocalDate date);

    /**
     * 某判断某日期范围是否有交易日, 开始和结束日期包含在判断范围内
     * 
     * @param startdDate 开始日期
     * @param endDate 结束日期
     * @return 某日期范围是否有交易日, 是: true, 否: false
     */
    boolean containTradeDay(LocalDate startdDate, LocalDate endDate);

    /**
     * 计算断某日期范围内的交易日数, 开始和结束日期包含在判断范围内
     * 
     * @param startdDate 开始日期
     * @param endDate 结束日期
     * @return 某日期范围内的交易日数, 开始和结束日期包含在判断范围内
     */
    int countTradeDay(LocalDate startdDate, LocalDate endDate);

    /**
     * date之后的一个交易日
     * 
     * @param date
     * @return date之后的一个交易日
     */
    LocalDate nextTradeDay(LocalDate date);
    
    /**
     * date之前的一个交易日
     * 
     * @param dat
     * @return date之前的一个交易日
     */
    LocalDate prevTradeDay(LocalDate date);

    /**
     * 日期date之后tradeDays个交易日的日期
     * 
     * @param date 日期
     * @param tradeDays 交易日数
     * @return 日期date之后tradeDays个交易日的日期
     */
    LocalDate tradeDayOfTradeDaysAfterDate(LocalDate date, int tradeDays);

    /**
     * 更新交易日, 实际上是更新non_weekend_holiday表
     * <p><b>管理员应在国务院新的法定假日放假方案公布后及时更新, 其他方法使用non_weekend_holiday表中的数据时没有做校验,
     * 也难以做校验, 因此可能会有错误结果</b>
     * 
     * @return 成功更新数量
     * @throws IOException 获取交易日时的URL错误
     */
    int updateTradeDay() throws IOException;

    /**
     * 根据最新股票数据日期判断该数据是否为最新
     * 
     * @param latestDate 最新股票数据日期
     * @return 当数据最新时返回true, 否则false
     */
    boolean isDataNewest(LocalDate latestDate);

    /**
     * 判断是否是交易时间
     * 
     * @param date 日期
     * @param time 时间
     * @return
     */
    boolean isTradeTime(LocalDate date, LocalTime time);

    /**
     * 判断是否是交易时间
     * 
     * @param dateTime 日期时间
     * @return
     */
    boolean isTradeTime(LocalDateTime dateTime);

    /**
     * 判断是否不是交易时间
     * 
     * @param date 日期
     * @param time 时间
     * @return
     */
    boolean isNotTradeTime(LocalDate date, LocalTime time);

    /**
     * 判断是否不是交易时间
     * 
     * @param dateTime 日期时间
     * @return
     */
    boolean isNotTradeTime(LocalDateTime dateTime);

    /**
     * 判断某日期是否为周末
     * 
     * @param date
     * @return
     */
    boolean isWeekend(LocalDate date);

}
