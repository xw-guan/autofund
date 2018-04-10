package com.xwguan.autofund.entity.stock;

import java.time.LocalDate;

import com.xwguan.autofund.entity.common.Historical;

/**
 * 股票信息
 * <P>包含某交易日某股票的收盘价/最高价/最低价/前收盘/涨跌额/涨跌幅/成交量/成交金额/换手率/总市值/流通市值
 * <P>指数和股票有相同的代码格式和基本相同的属性, 因此在本对象中不作区分, 若为指数换手率/总市值/流通市值三项为null. 
 * 而{@link LatestStockDto}类中包含private Boolean stockIndex作为是否为指数的标记
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-10
 */
public class StockData implements Historical{
    /**
     * 对应数据库中的主键
     */
    private Long id;
    /**
     * 对应Stock对象在数据库中的主键
     */
    private Integer stockId;
    /**
     * 交易日日期
     */
    private LocalDate date;
    /**
     * 收盘价
     */
    private Double close;
    /**
     * 最高价
     */
    private Double high;
    /**
     * 最低价
     */
    private Double low;
    /**
     * 开盘价
     */
    private Double open;
    /**
     * 前收盘previous close
     */
    private Double pclose;
    /**
     * 涨跌额change
     */
    private Double chg;
    /**
     * 涨跌幅percent change
     */
    private Double pchg;
    /**
     * 成交量
     */
    private Double voturnover;
    /**
     * 成交金额
     */
    private Double vaturnover;
    /**
     * 换手率, 指数无此项
     */
    private Double turnover;
    /**
     * 总市值total capitalisation, 指数无此项
     */
    private Double tcap;
    /**
     * 流通市值, 指数无此项
     */
    private Double mcap;

    public StockData() {
        super();
    }

    public StockData(Integer stockId, LocalDate date, Double close, Double high, Double low, Double open, Double pclose,
        Double chg, Double pchg, Double voturnover, Double vaturnover, Double turnover, Double tcap, Double mcap) {
        this.stockId = stockId;
        this.date = date;
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.pclose = pclose;
        this.chg = chg;
        this.pchg = pchg;
        this.voturnover = voturnover;
        this.vaturnover = vaturnover;
        this.turnover = turnover;
        this.tcap = tcap;
        this.mcap = mcap;
    }

    public StockData(LocalDate date, Double close, Double high, Double low, Double open, Double pclose, Double chg,
        Double pchg, Double voturnover, Double vaturnover, Double turnover, Double tcap, Double mcap) {
        this.date = date;
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.pclose = pclose;
        this.chg = chg;
        this.pchg = pchg;
        this.voturnover = voturnover;
        this.vaturnover = vaturnover;
        this.turnover = turnover;
        this.tcap = tcap;
        this.mcap = mcap;
    }

    public StockData(Long id, Integer stockId, LocalDate date, Double close, Double high, Double low, Double open,
        Double pclose, Double chg, Double pchg, Double voturnover, Double vaturnover, Double turnover, Double tcap,
        Double mcap) {
        this.id = id;
        this.stockId = stockId;
        this.date = date;
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.pclose = pclose;
        this.chg = chg;
        this.pchg = pchg;
        this.voturnover = voturnover;
        this.vaturnover = vaturnover;
        this.turnover = turnover;
        this.tcap = tcap;
        this.mcap = mcap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        return "StockData [id=" + id + ", stockId=" + stockId + ", date=" + date + ", close=" + close + ", high=" + high
            + ", low=" + low + ", open=" + open + ", pclose=" + pclose + ", chg=" + chg + ", pchg=" + pchg
            + ", voturnover=" + voturnover + ", vaturnover=" + vaturnover + ", turnover=" + turnover + ", tcap=" + tcap
            + ", mcap=" + mcap + "]";
    }

}
