package com.xwguan.autofund.entity.plan.backtest;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;

/**
 * 回测结果
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-31
 */
public class BackTestResult {

    /**
     * 回测开始日期
     */
    private LocalDate startDate;

    /**
     * 回测结束日期
     */
    private LocalDate endDate;

    /**
     * xirr年化收益率
     */
    private double annualizedReturnPct;

    /**
     * 最大回撤率, maxDrawdownPct = max((nav[i] - nav[j]) / nav[i]), i为某日, j为i后某一日
     */
    private Drawdown drawdown;

    /**
     * 总收益
     */
    private double totalIncome;

    /**
     * 总投入
     */
    private double totalInvestment;

    /**
     * 总收益率
     */
    private double totalIncomeRatePct;

    /**
     * 资产历史
     */
    private List<AssetHistory> assetHistoryList;

    /**
     * 交易详情历史
     */
    private List<TradeDetail> tradeDetailList;

    public BackTestResult() {
        super();
    }

    public BackTestResult(LocalDate startDate, LocalDate endDate, double annualizedReturnPct, Drawdown drawdown,
        double totalIncome, double totalInvestment, double totalIncomeRatePct, List<AssetHistory> assetHistoryList,
        List<TradeDetail> tradeDetailList) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.annualizedReturnPct = annualizedReturnPct;
        this.drawdown = drawdown;
        this.totalIncome = totalIncome;
        this.totalInvestment = totalInvestment;
        this.totalIncomeRatePct = totalIncomeRatePct;
        this.assetHistoryList = assetHistoryList;
        this.tradeDetailList = tradeDetailList;
    }
    
    public String result() {
        return "startDate=" + startDate + ", endDate=" + endDate + ", annualizedReturnPct="
            + annualizedReturnPct + ", drawdown=" + drawdown + ", totalIncome=" + totalIncome
            + ", totalInvestment=" + totalInvestment + ", totalIncomeRatePct=" + totalIncomeRatePct;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getAnnualizedReturnPct() {
        return annualizedReturnPct;
    }

    public void setAnnualizedReturnPct(double annualizedReturnPct) {
        this.annualizedReturnPct = annualizedReturnPct;
    }


    public Drawdown getMaxDrawdown() {
        return drawdown;
    }

    public void setMaxDrawdown(Drawdown drawdown) {
        this.drawdown = drawdown;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public double getTotalIncomeRatePct() {
        return totalIncomeRatePct;
    }

    public void setTotalIncomeRatePct(double totalIncomeRatePct) {
        this.totalIncomeRatePct = totalIncomeRatePct;
    }

    public List<AssetHistory> getAssetHistoryList() {
        return assetHistoryList;
    }

    public void setAssetHistoryList(List<AssetHistory> assetHistoryList) {
        this.assetHistoryList = assetHistoryList;
    }

    public List<TradeDetail> getTradeDetailList() {
        return tradeDetailList;
    }

    public void setTradeDetailList(List<TradeDetail> tradeDetailList) {
        this.tradeDetailList = tradeDetailList;
    }

    @Override
    public String toString() {
        return "BackTestResult [startDate=" + startDate + ", endDate=" + endDate + ", annualizedReturnPct="
            + annualizedReturnPct + ", drawdown=" + drawdown + ", totalIncome=" + totalIncome
            + ", totalInvestment=" + totalInvestment + ", totalIncomeRatePct=" + totalIncomeRatePct
            + ", assetHistoryList=" + assetHistoryList + ", tradeDetailList=" + tradeDetailList + "]";
    }

}
