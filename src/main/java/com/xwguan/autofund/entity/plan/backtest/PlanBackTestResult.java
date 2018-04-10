package com.xwguan.autofund.entity.plan.backtest;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.util.ToStringUtils;

/**
 * 计划回测结果
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-14
 */
public class PlanBackTestResult extends BackTestResult {

    /**
     * 持仓回测结果
     */
    private List<PositionBackTestResult> positionBackTestResultList;

    public PlanBackTestResult() {
        super();
    }

    public PlanBackTestResult(LocalDate startDate, LocalDate endDate, double annualizedReturnPct, Drawdown maxDrawdown,
        double totalIncome, double totalInvestment, double totalIncomePct, List<AssetHistory> assetHistoryList,
        List<TradeDetail> tradeDetailList, List<PositionBackTestResult> positionBackTestResultList) {
        super(startDate, endDate, annualizedReturnPct, maxDrawdown, totalIncome, totalInvestment, totalIncomePct,
            assetHistoryList, tradeDetailList);
        this.positionBackTestResultList = positionBackTestResultList;
    }

    public List<PositionBackTestResult> getPositionBackTestResultList() {
        return positionBackTestResultList;
    }

    public void setPositionBackTestResultList(List<PositionBackTestResult> positionBackTestResultList) {
        this.positionBackTestResultList = positionBackTestResultList;
    }

    @Override
    public String toString() {
        return toString(true, true);
    }

    public String toString(boolean unfoldPositionBackTestResultList, boolean unfoldListsInPositionBackTestResult) {
        return new StringBuilder("PlanBackTestResult [")
            .append("startDate=").append(getStartDate())
            .append(", endDate=").append(getEndDate())
            .append(", annualizedReturnPct=").append(getAnnualizedReturnPct())
            .append(", maxDrawdownPct=").append(getMaxDrawdown())
            .append(", totalIncome=").append(getTotalIncome())
            .append(", totalInvestment=").append(getTotalInvestment())
            .append(", totalIncomeRatePct=").append(getTotalIncomeRatePct())
            .append(", assetHistoryList=").append(getAssetHistoryList())
            .append(ToStringUtils.unfoldCollectionIfNeeded(",\npositionBackTestResultList=", positionBackTestResultList,
                unfoldListsInPositionBackTestResult))
            .append("\n] PlanBackTest toString End")
            .toString();
    }

}
