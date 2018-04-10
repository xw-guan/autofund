package com.xwguan.autofund.dto.stock;

import com.xwguan.autofund.enums.UpdateStateEnum;

/**
 * 股票更新状态
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-10-30
 */
public class StockUpdateState {

    private int id;

    private String symbol;

    private UpdateStateEnum stockDataState;

    private Long stockDataSuccess;

    private UpdateStateEnum maState;

    private Long maSuccess;

    public StockUpdateState() {
        super();
    }

    public StockUpdateState(int id, String symbol) {
        this.id = id;
        this.symbol = symbol;
    }

    public StockUpdateState(int id, String symbol, UpdateStateEnum stockDataState, Long stockDataSuccess,
        UpdateStateEnum maState, Long maSuccess) {
        this.id = id;
        this.symbol = symbol;
        this.stockDataState = stockDataState;
        this.stockDataSuccess = stockDataSuccess;
        this.maState = maState;
        this.maSuccess = maSuccess;
    }

    /**
     * 设置stock_data更新状态并返回对象本身
     * 
     * @param stockDataState stock_data更新状态
     * @param stockDataSuccess 更新成功条目数
     * @return 本对象
     */
    public StockUpdateState stockDataUpdateState(UpdateStateEnum stockDataState, Long stockDataSuccess) {
        this.stockDataState = stockDataState;
        this.stockDataSuccess = stockDataSuccess;
        return this;
    }

    /**
     * 设置ma更新状态并返回对象本身
     * 
     * @param maState ma更新状态
     * @param maSuccess 更新成功条目数
     * @return 本对象
     */
    public StockUpdateState maUpdateState(UpdateStateEnum maState, Long maSuccess) {
        this.maState = maState;
        this.maSuccess = maSuccess;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public UpdateStateEnum getStockDataState() {
        return stockDataState;
    }

    public void setStockDataState(UpdateStateEnum stockDataState) {
        this.stockDataState = stockDataState;
    }

    public Long getStockDataSuccess() {
        return stockDataSuccess;
    }

    public void setStockDataSuccess(Long stockDataSuccess) {
        this.stockDataSuccess = stockDataSuccess;
    }

    public UpdateStateEnum getMaState() {
        return maState;
    }

    public void setMaState(UpdateStateEnum maState) {
        this.maState = maState;
    }

    public Long getMaSuccess() {
        return maSuccess;
    }

    public void setMaSuccess(Long maSuccess) {
        this.maSuccess = maSuccess;
    }

    @Override
    public String toString() {
        return "StockUpdateState [id=" + id + ", symbol=" + symbol + ", stockDataState=" + stockDataState
            + ", stockDataSuccess=" + stockDataSuccess + ", maState=" + maState + ", maSuccess=" + maSuccess + "]";
    }

}
