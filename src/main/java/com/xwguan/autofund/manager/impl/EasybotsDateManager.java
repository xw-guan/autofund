package com.xwguan.autofund.manager.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.dao.date.NonWeekendHolidayDao;
import com.xwguan.autofund.exception.io.AbnormalDataSourceException;
import com.xwguan.autofund.manager.api.DateManager;
import com.xwguan.autofund.manager.util.DateCriterion;
import com.xwguan.autofund.manager.util.DateCriterions;
import com.xwguan.autofund.util.DateUtils;

/**
 * 交易日/节假日获取及判断
 * <p><a href=
 * "http://www.easybots.cn/holiday_api.net">http://www.easybots.cn/</a>提供的节假日判断,
 * 用法如下: <ul>
 * <li>检查一个日期是否为节假日: http://www.easybots.cn/api/holiday.php?d=20130101
 * <li>检查多个日期是否为节假日:
 * http://www.easybots.cn/api/holiday.php?d=20130101,20130103,20130105,20130201
 * <li>获取2012年1月份节假日: http://www.easybots.cn/api/holiday.php?m=201201
 * <li>获取2013年1/2月份节假日:
 * http://www.easybots.cn/api/holiday.php?m=201301,201302<br>
 * </ul>
 * <p>返回json格式数据, 形如<ul>
 * <li>{"20130101":"2"}
 * <li>{"201701":{"01":"2","02":"1","07":"1","08":"1","14":"1","15":"1","21":"1","27":"2","28":"2","29":"2","30":"1","31":"1"}}
 * </ul>
 * <p>工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
 * <p><b>支持2010-2017年数据</b>
 * <p><b>个人用户需要向网站申请授权码, 否则返回可能为乱码</b>
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-24
 */
@Component
public class EasybotsDateManager implements DateManager {

    private static final String BASE_URL = "http://www.easybots.cn/api/holiday.php";

    private static final int AVAILABLE_START_YEAR = 2010;

    /**
     * yyyyMMdd
     */
    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

    @Override
    public List<Integer> getAvailableYears() {
        List<Integer> yearList = new ArrayList<>();
        for (int year = AVAILABLE_START_YEAR; year <= LocalDate.now().getYear(); year++) {
            yearList.add(year);
        }
        return yearList;
    }

    @Unimplement
    @Override
    public List<LocalDate> listHoliday(int year) throws IOException {
        URL url = generateYearURL(year);
        return listDateFromUrl(url, DateCriterions.ALL_DATE_PASS);
    }

    @Unimplement
    @Override
    public List<LocalDate> listHoliday(int year, Month month) throws IOException {
        URL url = generateMonthURL(year, month);
        return listDateFromUrl(url, DateCriterions.ALL_DATE_PASS);
    }

    @Override
    public List<LocalDate> listNonWorkDay(int year) throws IOException {
        URL url = generateYearURL(year);
        return listDateFromUrl(url, DateCriterions.ALL_DATE_PASS);
    }

    @Override
    public List<LocalDate> listNonWorkDay(int year, Month month) throws IOException {
        URL url = generateMonthURL(year, month);
        return listDateFromUrl(url, DateCriterions.ALL_DATE_PASS);
    }

    @Override
    public List<LocalDate> listNonWeekendHoliday(int year) throws IOException {
        URL url = generateYearURL(year);
        return listDateFromUrl(url, DateCriterions.NOT_WEEKEND);
    }

    @Deprecated
    @Override
    public boolean isTradeDay(LocalDate date) throws IOException {
        /*
         * 判断为周末直接返回false, 不从网络获取节假日数据, 提高效率
         * 当前设备条件下, 非节假日判断需要约1 ms,
         * 节假日一次获取整年需要约350 ms, 一次获取当月需要约330 ms, 说明影响效率主要是网络延迟而不是json解析
         */
        if (DateUtils.isWeekend(date)) {
            return false;
        }
        List<LocalDate> holidayList = listNonWorkDay(date.getYear(), date.getMonth());
        if (CollectionUtils.isNotEmpty(holidayList)) {
            // 不为周末的情况下, 不是节假日就是交易日
            return !holidayList.contains(date);
        } else {
            throw new AbnormalDataSourceException("Fail to get holidays from easybots.cn");
        }
    }

    private URL generateYearURL(int year) throws MalformedURLException {
        StringBuilder sb = new StringBuilder(BASE_URL).append("?m=");
        for (int m = 1; m <= 12; m++) {
            sb.append(year).append(String.format("%02d", m)).append(",");
        }
        sb.deleteCharAt(sb.length() - 1); // 删除最后一个多余的逗号
        return new URL(sb.toString());
    }

    private URL generateMonthURL(int year, Month month) throws MalformedURLException {
        StringBuilder sb = new StringBuilder(BASE_URL).append("?m=").append(year)
            .append(String.format("%02d", month.getValue()));
        return new URL(sb.toString());
    }

    /**
     * 根据传入url返回的json数据获得非工作日列表
     * <p>返回的json数据格式外层key为年月, 内层key为日, 形如:
     * {"201701":{"01":"2","02":"1","07":"1","08":"1","14":"1","15":"1","21":"1","27":"2","28":"2","29":"2","30":"1","31":"1"}}
     * <p><b>从测试来看, 对应休息日的结果为1和对应节假日的结果2随机出现, 但对key值为非工作日的dayOfMonth</b>
     * 
     * @param url
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    private List<LocalDate> listDateFromUrl(URL url, DateCriterion dateCriterion)
        throws JsonParseException, JsonMappingException, IOException {
        // parse json
        Map<?, ?> resultMap = new ObjectMapper().readValue(url.openStream(), Map.class);
        if (MapUtils.isEmpty(resultMap)) {
            throw new AbnormalDataSourceException("Fail to get holidays from easybots.cn, the url is " + url.getPath());
        }
        // 解析结果
        List<LocalDate> holidayList = new ArrayList<>();
        Set<?> yearMonthSet = resultMap.keySet();
        for (Object yyyyMM : yearMonthSet) {
            Map<?, ?> dayMap = (Map<?, ?>) resultMap.get(yyyyMM);
            if (MapUtils.isEmpty(dayMap)) {
                continue;
            }
            Set<?> daySet = dayMap.keySet();
            for (Object dd : daySet) {
                LocalDate date = LocalDate.parse((String) yyyyMM + (String) dd, DATE_FORMATTER);
                if (dateCriterion.isMet(date)) {
                    holidayList.add(date);
                }
            }
        }
        return holidayList;
    }

    /**
     * 没有网站授权码的情况下, 返回值为乱码.
     * 应当在初始化后使用{@link NonWeekendHolidayDao}从数据库中获取交易日信息
     * 
     * @param date
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unused")
    @Deprecated
    private boolean isTradeDayUsingEasybotsUrl(LocalDate date) throws IOException {
        String dateStr = date.format(DATE_FORMATTER);
        StringBuilder sb = new StringBuilder(BASE_URL).append("?d=").append(dateStr);
        URL url = new URL(sb.toString());
        Map<?, ?> resultMap = new ObjectMapper().readValue(url.openStream(), Map.class);
        String result = (String) resultMap.get(dateStr);
        return "0".equals(result);
    }

}
