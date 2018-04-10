package com.xwguan.autofund.dto.stock;

import com.xwguan.autofund.entity.stock.MA;
import com.xwguan.autofund.entity.stock.StockData;

public class LatestStockDto {
    /**
     * 对应数据库中的主键
     */
    private Integer id;

    /**
     * 股票代码
     */
    private String symbol;

    /**
     * 股票名称
     */
    private String name;

    /**
     * 最新股票数据
     */
    private StockData latestStockData;

    /**
     * 最新MA数据
     */
    private MA latestMA;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public StockData getLatestStockData() {
        return latestStockData;
    }

    public void setLatestStockData(StockData latestStockData) {
        this.latestStockData = latestStockData;
    }

    public MA getLatestMA() {
        return latestMA;
    }

    public void setLatestMA(MA latestMA) {
        this.latestMA = latestMA;
    }

    @Override
    public String toString() {
        return "LatestStockDto [id=" + id + ", symbol=" + symbol + ", name=" + name + ", latestStockData="
            + latestStockData + ", latestMA=" + latestMA + "]";
    }

}
