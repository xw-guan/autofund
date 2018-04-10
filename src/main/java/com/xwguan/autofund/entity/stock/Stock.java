package com.xwguan.autofund.entity.stock;

import java.util.List;

/**
 * 股票类
 * <P>包含股票基本信息, 历史数据, 以及MA数据 也可适用于股指, private Boolean stockIndex标记是否为指数
 * 
 * @author XWGuan
 * @version 1.0.0 2017-10-10
 */
public class Stock {
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
     * 是否为指数
     */
    private Boolean index;

    /**
     * 股票数据列表
     */
    private List<StockData> stockDataList;

    /**
     * MA数据列表
     */
    private List<MA> maList;

    /**
     * 是否为指数
     * 
     * @return 是指数返回true, 否则为false
     */
    public boolean isStockIndex() {
        return index;
    }

    public Stock() {
        super();
    }

    public Stock(Integer id, String symbol, String name, Boolean index, List<StockData> stockDataList,
        List<MA> maList) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.index = index;
        this.stockDataList = stockDataList;
        this.maList = maList;
    }

    public Stock(String symbol, String name, Boolean index, List<StockData> stockDataList, List<MA> maList) {
        this.symbol = symbol;
        this.name = name;
        this.index = index;
        this.stockDataList = stockDataList;
        this.maList = maList;
    }

    public Stock(String symbol, String name, Boolean index) {
        this.symbol = symbol;
        this.name = name;
        this.index = index;
    }

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

    public Boolean getIndex() {
        return index;
    }

    public void setIndex(Boolean index) {
        this.index = index;
    }

    public List<StockData> getStockDataList() {
        return stockDataList;
    }

    public void setStockDataList(List<StockData> stockDataList) {
        this.stockDataList = stockDataList;
    }

    public List<MA> getMaList() {
        return maList;
    }

    public void setMaList(List<MA> maList) {
        this.maList = maList;
    }

    @Override
    public String toString() {
        return "Stock [id=" + id + ", symbol=" + symbol + ", name=" + name + ", index=" + index + ", stockDataList="
            + stockDataList + ", maList=" + maList + "]";
    }

}
