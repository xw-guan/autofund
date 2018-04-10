package com.xwguan.autofund.entity.fund;

import java.time.LocalDate;
import java.util.List;

/**
 * 资产配置
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-05
 */
public class AssetAllocation {

    // TODO 实际上fundId和id一一对应, id字段可以说是没必要的, 是否要删掉? FundInfo类同理.
    private Integer id;

    /**
     * 所属基金代码
     */
    private Integer fundId;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 股票占净资产比例
     */
    private Double stockPct;

    /**
     * 债券占净资产比例
     */
    private Double debenturePct;

    /**
     * 现金占净资产比例
     */
    private Double cashPct;

    /**
     * 基金占净资产比例
     */
    private Double fundPct;

    /**
     * 净资产(亿元)
     */
    private Double netAsset;

    /**
     * 股票持仓列表
     */
    private List<FundPosition> stockList;

    /**
     * 债券持仓列表
     */
    private List<FundPosition> debentureList;

    /**
     * 基金持仓列表
     */
    private List<FundPosition> fundList;

    public AssetAllocation() {
        super();
    }

    public AssetAllocation(LocalDate date, Double stockPct, Double debenturePct, Double cashPct, Double fundPct,
        Double netAsset) {
        this.date = date;
        this.stockPct = stockPct;
        this.debenturePct = debenturePct;
        this.cashPct = cashPct;
        this.fundPct = fundPct;
        this.netAsset = netAsset;
    }

    public AssetAllocation(Integer fundId, LocalDate date, Double stockPct, Double debenturePct, Double cashPct,
        Double fundPct, Double netAsset) {
        this.fundId = fundId;
        this.date = date;
        this.stockPct = stockPct;
        this.debenturePct = debenturePct;
        this.cashPct = cashPct;
        this.fundPct = fundPct;
        this.netAsset = netAsset;
    }

    public AssetAllocation(Integer id, Integer fundId, LocalDate date, Double stockPct, Double debenturePct,
        Double cashPct, Double fundPct, Double netAsset) {
        this.id = id;
        this.fundId = fundId;
        this.date = date;
        this.stockPct = stockPct;
        this.debenturePct = debenturePct;
        this.cashPct = cashPct;
        this.fundPct = fundPct;
        this.netAsset = netAsset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getStockPct() {
        return stockPct;
    }

    public void setStockPct(Double stockPct) {
        this.stockPct = stockPct;
    }

    public Double getDebenturePct() {
        return debenturePct;
    }

    public void setDebenturePct(Double debenturePct) {
        this.debenturePct = debenturePct;
    }

    public Double getCashPct() {
        return cashPct;
    }

    public void setCashPct(Double cashPct) {
        this.cashPct = cashPct;
    }

    public Double getFundPct() {
        return fundPct;
    }

    public void setFundPct(Double fundPct) {
        this.fundPct = fundPct;
    }

    public Double getNetAsset() {
        return netAsset;
    }

    public void setNetAsset(Double netAsset) {
        this.netAsset = netAsset;
    }

    public List<FundPosition> getStockList() {
        return stockList;
    }

    public void setStockList(List<FundPosition> stockList) {
        this.stockList = stockList;
    }

    public List<FundPosition> getDebentureList() {
        return debentureList;
    }

    public void setDebentureList(List<FundPosition> debentureList) {
        this.debentureList = debentureList;
    }

    public List<FundPosition> getFundList() {
        return fundList;
    }

    public void setFundList(List<FundPosition> fundList) {
        this.fundList = fundList;
    }

    @Override
    public String toString() {
        return "AssetAllocation [id=" + id + ", fundId=" + fundId + ", date=" + date + ", stockPct=" + stockPct
            + ", debenturePct=" + debenturePct + ", cashPct=" + cashPct + ", fundPct=" + fundPct + ", netAsset="
            + netAsset + ", stockList=" + stockList + ", debentureList=" + debentureList + ", fundList=" + fundList
            + "]";
    }

}
