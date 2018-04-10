package com.xwguan.autofund.manager.impl;

import com.opencsv.bean.CsvCustomBindByName;
import com.xwguan.autofund.manager.converter.ConverterStringToDoubleOrNull;

/**
 * CSV与StockData的中间对象, 用于存放CSV直接解析的内容, 目前为临时方案
 * <p>网易API获取的CSV内容不能直接赋值给StockData类, 写入数据库, 原因如下:
 * <ol>
 * <li>opencsv无法解析日期为LocalDate对象
 * <li>网易API获得的股票代码未指明是上交所还是深交所, 可能不唯一, 因此需要进一步将日期字符串转为Local对象,
 * 将股票代码前多余的'字符去掉并在股票代码后加上.SZ或.SH
 * </ol>
 * 
 * @author XWGuan
 * @version 0.1 2017-10-12
 */
public class NeteaseCsvStockData {

    /**
     * 交易日日期, 需要进一步转为LocalDate类型
     */
    @CsvCustomBindByName(column = "日期", converter = ConverterStringToDoubleOrNull.class)
    private String date;

    /**
     * 股票代码
     */
    @CsvCustomBindByName(column = "股票代码", converter = ConverterStringToDoubleOrNull.class)
    private String symbol;

    // 其余可正常赋值
    /**
     * 股票名称
     */
    @CsvCustomBindByName(column = "名称", converter = ConverterStringToDoubleOrNull.class)
    private String name;

    /**
     * 收盘价
     */
    @CsvCustomBindByName(column = "收盘价", converter = ConverterStringToDoubleOrNull.class)
    private Double close;

    /**
     * 最高价
     */
    @CsvCustomBindByName(column = "最高价", converter = ConverterStringToDoubleOrNull.class)
    private Double high;

    /**
     * 最低价
     */
    @CsvCustomBindByName(column = "最低价", converter = ConverterStringToDoubleOrNull.class)
    private Double low;

    /**
     * 开盘价
     */
    @CsvCustomBindByName(column = "开盘价", converter = ConverterStringToDoubleOrNull.class)
    private Double open;

    // 数据源中若某项无数据可能采用"None"等字符表示, 默认方法会抛出运行时异常,
    // ConverterStringToDoubleOrNull.class用于将这类值转化为null
    /**
     * 前收盘previous close, 与前一日数据相关, 可以为null
     */
    @CsvCustomBindByName(column = "前收盘", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double pclose;

    /**
     * 涨跌额change, 与前一日数据相关, 可以为null
     */
    @CsvCustomBindByName(column = "涨跌额", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double chg;

    /**
     * 涨跌幅percent change, 与前一日数据相关, 可以为null
     */
    @CsvCustomBindByName(column = "涨跌幅", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double pchg;

    /**
     * 成交量
     * <p>受限于数据源, 结果可能为null
     */
    @CsvCustomBindByName(column = "成交量", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double voturnover;

    /**
     * 成交金额
     * <p>受限于数据源, 结果可能为null
     */
    @CsvCustomBindByName(column = "成交金额", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double vaturnover;

    /**
     * 换手率, 指数无此项
     */
    @CsvCustomBindByName(column = "换手率", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double turnover;

    /**
     * 总市值total capitalisation, 指数无此项
     */
    @CsvCustomBindByName(column = "总市值", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double tcap;

    /**
     * 流通市值, 指数无此项
     */
    @CsvCustomBindByName(column = "流通市值", required = false, converter = ConverterStringToDoubleOrNull.class)
    private Double mcap;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getPclose() {
        return pclose;
    }

    public void setPclose(Double pclose) {
        this.pclose = pclose;
    }

    public Double getChg() {
        return chg;
    }

    public void setChg(Double chg) {
        this.chg = chg;
    }

    public Double getPchg() {
        return pchg;
    }

    public void setPchg(Double pchg) {
        this.pchg = pchg;
    }

    public Double getVoturnover() {
        return voturnover;
    }

    public void setVoturnover(Double voturnover) {
        this.voturnover = voturnover;
    }

    public Double getVaturnover() {
        return vaturnover;
    }

    public void setVaturnover(Double vaturnover) {
        this.vaturnover = vaturnover;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public Double getTcap() {
        return tcap;
    }

    public void setTcap(Double tcap) {
        this.tcap = tcap;
    }

    public Double getMcap() {
        return mcap;
    }

    public void setMcap(Double mcap) {
        this.mcap = mcap;
    }

    @Override
    public String toString() {
        return "NeteaseCsvStockData [date=" + date + ", symbol=" + symbol + ", name=" + name + ", close=" + close
            + ", high=" + high + ", low=" + low + ", open=" + open + ", pclose=" + pclose + ", chg=" + chg + ", pchg="
            + pchg + ", voturnover=" + voturnover + ", vaturnover=" + vaturnover + ", turnover=" + turnover + ", tcap="
            + tcap + ", mcap=" + mcap + "]";
    }

}
