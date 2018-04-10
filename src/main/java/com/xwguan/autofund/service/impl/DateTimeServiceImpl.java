package com.xwguan.autofund.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xwguan.autofund.constant.StockConstant;
import com.xwguan.autofund.dao.date.NonWeekendHolidayDao;
import com.xwguan.autofund.manager.api.DateManager;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.util.DateUtils;

/**
 * 日期时间服务实现, 提供交易日列表, 提供某日是否为交易日的判断
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-30
 */
@Service
public class DateTimeServiceImpl implements DateTimeService {

    @Override
    public String toString() {
        return "DateTimeServiceImpl [nonWeekendHolidayDao=" + nonWeekendHolidayDao + ", dateManager=" + dateManager
            + "]";
    }

    @Autowired
    private NonWeekendHolidayDao nonWeekendHolidayDao;

    @Autowired
    private DateManager dateManager;

    @Override
    public List<LocalDate> listTradeDay(int year) {
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfYear(year);
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate lastDayOfYear = LocalDate.of(year + 1, 1, 1).minusDays(1);
        return listTradeDayOfDateRange(firstDayOfYear, lastDayOfYear, nonWeekendHolidayList);
    }

    @Override
    public List<LocalDate> listTradeDay(int year, int month) {
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        LocalDate lastDayOfMonth = (month != 12 ? LocalDate.of(year, month + 1, 1).minusDays(1)
            : LocalDate.of(year, month, 31));
        return listTradeDayOfDateRange(firstDayOfMonth, lastDayOfMonth, nonWeekendHolidayList);
    }

    // TODO 抽出listTradeDay和containTradeDay的共有方法
    @Override
    public List<LocalDate> listTradeDay(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return null;
        }
        List<LocalDate> tradeDayList = new ArrayList<>();
        int year = startDate.getYear();
        int month = startDate.getMonthValue();
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 如果month和当前month不同, 说明日期已经进入下一个月, 需要重新连接数据库获取当月非周末节假日列表
            // 直接用isTradeDay(LocalDate date)会反复连接数据库降低性能
            if (month != date.getMonthValue()) {
                year = date.getYear();
                month = date.getMonthValue();
                nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
            }
            if (checkTradeDay(date, nonWeekendHolidayList)) {
                tradeDayList.add(date);
            }
        }
        return tradeDayList;
    }

    @Override
    public boolean isTradeDay(LocalDate date) {
        if (date == null) {
            return false;
        }
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(date.getYear(),
            date.getMonthValue());
        return checkTradeDay(date, nonWeekendHolidayList);
    }

    @Override
    public boolean isNotTradeDay(LocalDate date) {
        return !isTradeDay(date);
    }

    @Override
    public boolean containTradeDay(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        int year = startDate.getYear();
        int month = startDate.getMonthValue();
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 如果month和当前month不同, 说明日期已经进入下一个月, 需要重新连接数据库获取当月非周末节假日列表
            // 直接用isTradeDay(LocalDate date)会反复连接数据库降低性能
            if (month != date.getMonthValue()) {
                year = date.getYear();
                month = date.getMonthValue();
                nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
            }
            if (checkTradeDay(date, nonWeekendHolidayList)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int countTradeDay(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        int cntTradeDay = 0;
        int year = startDate.getYear();
        int month = startDate.getMonthValue();
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            // 如果month和当前month不同, 说明日期已经进入下一个月, 需要重新连接数据库获取当月非周末节假日列表
            // 直接用isTradeDay(LocalDate date)会反复连接数据库降低性能
            if (month != date.getMonthValue()) {
                year = date.getYear();
                month = date.getMonthValue();
                nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
            }
            if (checkTradeDay(date, nonWeekendHolidayList)) {
                cntTradeDay++;
            }
        }
        return cntTradeDay;
    }

    @Override
    public LocalDate nextTradeDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        for (LocalDate d = date.plusDays(1); true; d = d.plusDays(1)) {
            if (month != d.getMonthValue()) {
                year = d.getYear();
                month = d.getMonthValue();
                nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
            }
            if (checkTradeDay(d, nonWeekendHolidayList)) {
                return d;
            }
        }
    }

    @Override
    public LocalDate prevTradeDay(LocalDate date) {
        if (date == null) {
            return null;
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        for (LocalDate d = date.minusDays(1); true; d = d.minusDays(1)) {
            if (month != d.getMonthValue()) {
                year = d.getYear();
                month = d.getMonthValue();
                nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
            }
            if (checkTradeDay(d, nonWeekendHolidayList)) {
                return d;
            }
        }
    }

    @Override
    public LocalDate tradeDayOfTradeDaysAfterDate(LocalDate date, int tradeDays) {
        if (date == null) {
            return null;
        }
        int year = date.getYear();
        int month = date.getMonthValue();
        List<LocalDate> nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
        int cntTradeDays = 0;
        for (LocalDate d = date.plusDays(1); true; date = date.plusDays(1)) {
            if (month != d.getMonthValue()) {
                year = d.getYear();
                month = d.getMonthValue();
                nonWeekendHolidayList = nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(year, month);
            }
            if (checkTradeDay(d, nonWeekendHolidayList)) {
                cntTradeDays++;
                if (cntTradeDays == tradeDays) {
                    return d;
                }
            }
        }
    }

    @Override
    public int updateTradeDay() throws IOException {
        List<LocalDate> holidayList = new ArrayList<>();
        List<Integer> yearlList = dateManager.getAvailableYears();
        for (int year : yearlList) {
            holidayList.addAll(dateManager.listNonWeekendHoliday(year));
        }
        return nonWeekendHolidayDao.insertNonWeekendHoliday(holidayList);
    }

    @Override
    public boolean isDataNewest(LocalDate latestDate) {
        // latestDate为空时, 必定不是最新的数据
        if (latestDate == null) {
            return false;
        }
        LocalDate nowDate = LocalDate.now();
        // 当latestDate为当日时, 表示数据已是最新
        if (nowDate.isEqual(latestDate)) {
            return true;
        }
        // 此时: latestDate在今日之前
        // 只要latestDate之后至今日之前存在交易日, 数据就不是最新
        if (containTradeDay(latestDate.plusDays(1), nowDate.minusDays(1))) {
            return false;
        }
        // 此时: latestDate和至今日之间没有交易日
        return true;
        /*
         * 注: 考虑到数据源的更新时间, 本项目的数据更新时间为交易日后一天0点,
         * 因此在交易日收盘到第二天0点之间的时间数据仍然认为是最新的.
         * 所以, 在latestDate到今日之间没有交易日时就认为数据是最新的, 直接返回true.
         */
        
        // 今日不是交易日, 则数据已是最新
        // if (!isTradeDay(nowDate)) {
        // return true;
        // }
        // // TODO
        // // 今日是交易日, 当前时间在收盘时间之前(尚未有历史交易数据或是有实时数据, 待测试), 则数据仍是最新, 否则不是
        // return StockConstant.CLOSE_TIME.isAfter(LocalTime.now());
    }

    /**
     * 根据传入的nonWeekendHolidayList列出日期范围内的交易日, 包含开始和结束日期
     * <p><b>注意: 该方法不连接数据库, 也不检查参数是否为null, 在方法内无法知道date和nonWeekendHolidayList是否匹配,
     * 因此调用该方法时必须保证date和nonWeekendHolidayList的匹配性</b>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param nonWeekendHolidayList 非周末节假日
     * @return 日期范围内的交易日
     */
    private List<LocalDate> listTradeDayOfDateRange(LocalDate startDate, LocalDate endDate,
        List<LocalDate> nonWeekendHolidayList) {
        List<LocalDate> tradeDayList = new ArrayList<>();
        for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
            if (checkTradeDay(date, nonWeekendHolidayList)) {
                tradeDayList.add(date);
            }
        }
        return tradeDayList;
    }

    /**
     * 根据传入的nonWeekendHolidayList判断某日是否为交易日, 该方法不连接数据库
     * <p><b>注意: date和nonWeekendHolidayList必须匹配, 即必须是date的当月或当年数据, 否则该方法只是判断是否为周末,
     * 不能正确返回结果</b>
     * 
     * @param date 待判断日期
     * @param nonWeekendHolidayList 非周末节假日
     * @return 某日是否为交易日
     */
    private boolean checkTradeDay(LocalDate date, List<LocalDate> nonWeekendHolidayList) {
        // 周末都不是交易日
        if (isWeekend(date)) {
            return false;
        }
        // 非周末假期表包含传入的日期, 则该日不是交易日
        if (CollectionUtils.isNotEmpty(nonWeekendHolidayList) && nonWeekendHolidayList.contains(date)) {
            return false;
        }
        // 无非周末节假日(null或size=0)或传入的日期不在非周末节假日列表内
        return true;
    }

    @Override
    public boolean isTradeTime(LocalDate date, LocalTime time) {
        if (isNotTradeDay(date)) {
            return false;
        }
        return StockConstant.OPEN_TIME.isBefore(time) && StockConstant.CLOSE_TIME.isAfter(time);
    }

    @Override
    public boolean isTradeTime(LocalDateTime dateTime) {
        return isTradeTime(dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    @Override
    public boolean isNotTradeTime(LocalDate date, LocalTime time) {
        return !isTradeTime(date, time);
    }

    @Override
    public boolean isNotTradeTime(LocalDateTime dateTime) {
        return !isTradeTime(dateTime);
    }

    @Override
    public boolean isWeekend(LocalDate date) {
        return DateUtils.isWeekend(date);
    }
}
