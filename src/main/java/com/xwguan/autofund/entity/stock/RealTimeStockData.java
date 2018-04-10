package com.xwguan.autofund.entity.stock;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 实时股票数据
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-19
 */
public class RealTimeStockData {

    /**
     * 股票id
     */
    private Integer stockId;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 时间
     */
    private LocalTime time;

    /**
     * 价格
     */
    private Double price;

    public RealTimeStockData() {
        super();
    }

    public RealTimeStockData(String symbol, LocalDate date, LocalTime time, Double price) {
        this.symbol = symbol;
        this.date = date;
        this.time = time;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "RealTimeStockData [stockId=" + stockId + ", symbol=" + symbol + ", date=" + date + ", time=" + time
            + ", price=" + price + "]";
    }

}
