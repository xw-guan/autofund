package com.xwguan.autofund.entity.stock;

import java.time.LocalDate;
import java.time.LocalTime;

import com.xwguan.autofund.enums.MAEnum;

public class RealTimeMA {

    /**
     * 对应Stock对象在数据库中的主键
     */
    private Integer stockId;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 交易日日期
     */
    private LocalDate date;

    /**
     * 时间
     */
    private LocalTime time;

    private Double ma5;

    private Double ma10;

    private Double ma20;

    private Double ma30;

    private Double ma60;

    private Double ma120;

    private Double ma250;

    public RealTimeMA() {
        super();
    }

    /**
     * 按均线日数设置均线值
     * 
     * @param maDays 均线日数
     * @param maValue 均线值
     */
    public void setNDayMA(MAEnum maDays, Double maValue) {
        switch (maDays) {
        case MA5:
            ma5 = maValue;
            break;
        case MA10:
            ma10 = maValue;
            break;
        case MA20:
            ma20 = maValue;
            break;
        case MA30:
            ma30 = maValue;
            break;
        case MA60:
            ma60 = maValue;
            break;
        case MA120:
            ma120 = maValue;
            break;
        case MA250:
            ma250 = maValue;
            break;
        default:
            break;
        }
    }

    /**
     * 按均线日数获取均线值, 可能为null
     * 
     * @param maDays 均线日数
     * @return 均线值
     */
    public Double getMAValue(MAEnum maDays) {
        switch (maDays) {
        case MA5:
            return ma5;
        case MA10:
            return ma10;
        case MA20:
            return ma20;
        case MA30:
            return ma30;
        case MA60:
            return ma60;
        case MA120:
            return ma120;
        case MA250:
            return ma250;
        default:
            return null;
        }
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Double getMa5() {
        return ma5;
    }

    public void setMa5(Double ma5) {
        this.ma5 = ma5;
    }

    public Double getMa10() {
        return ma10;
    }

    public void setMa10(Double ma10) {
        this.ma10 = ma10;
    }

    public Double getMa20() {
        return ma20;
    }

    public void setMa20(Double ma20) {
        this.ma20 = ma20;
    }

    public Double getMa30() {
        return ma30;
    }

    public void setMa30(Double ma30) {
        this.ma30 = ma30;
    }

    public Double getMa60() {
        return ma60;
    }

    public void setMa60(Double ma60) {
        this.ma60 = ma60;
    }

    public Double getMa120() {
        return ma120;
    }

    public void setMa120(Double ma120) {
        this.ma120 = ma120;
    }

    public Double getMa250() {
        return ma250;
    }

    public void setMa250(Double ma250) {
        this.ma250 = ma250;
    }

    @Override
    public String toString() {
        return "RealTimeMA [stockId=" + stockId + ", symbol=" + symbol + ", date=" + date + ", time=" + time + ", ma5="
            + ma5 + ", ma10=" + ma10 + ", ma20=" + ma20 + ", ma30=" + ma30 + ", ma60=" + ma60 + ", ma120=" + ma120
            + ", ma250=" + ma250 + "]";
    }

}
