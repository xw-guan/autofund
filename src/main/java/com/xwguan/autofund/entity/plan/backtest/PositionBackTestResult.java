package com.xwguan.autofund.entity.plan.backtest;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.util.ToStringUtils;

/**
 * 持仓回测结果
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-09
 */
public class PositionBackTestResult extends BackTestResult {

    /**
     * 持仓基金id
     */
    private Integer fundId;

    /**
     * 参考指数id
     */
    private Integer refIndexId;

    public PositionBackTestResult() {
        super();
    }

    public PositionBackTestResult(LocalDate startDate, LocalDate endDate, double annualizedReturnPct,
        Drawdown maxDrawdown, double totalIncome, double totalInvestment, double totalIncomePct,
        List<AssetHistory> assetHistoryList, List<TradeDetail> tradeDetailList, Integer fundId, Integer refIndexId) {
        super(startDate, endDate, annualizedReturnPct, maxDrawdown, totalIncome, totalInvestment, totalIncomePct,
            assetHistoryList, tradeDetailList);
        this.fundId = fundId;
        this.refIndexId = refIndexId;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public Integer getRefIndexId() {
        return refIndexId;
    }

    public void setRefIndexId(Integer refIndexId) {
        this.refIndexId = refIndexId;
    }

    @Override
    public String toString() {
        return toString(true, true);
    }

    public String toString(boolean unfordAssetHistoryList, boolean unfoldTradeDetailList) {
        return new StringBuilder("PositionBackTestResult [")
            .append("fundId=").append(fundId)
            .append(", refIndexId=").append(refIndexId)
            .append(", startDate=").append(getStartDate())
            .append(", endDate=").append(getEndDate())
            .append(", annualizedReturnPct=").append(getAnnualizedReturnPct())
            .append(", maxDrawdownPct=").append(getMaxDrawdown())
            .append(", totalIncome=").append(getTotalIncome())
            .append(", totalInvestment=").append(getTotalInvestment())
            .append(", totalIncomeRatePct=").append(getTotalIncomeRatePct())
            .append(ToStringUtils.unfoldCollectionIfNeeded(",\nassetHistoryList=", getAssetHistoryList(),
                unfordAssetHistoryList))
            .append(ToStringUtils.unfoldCollectionIfNeeded(",\ntradeDetailList=", getTradeDetailList(),
                unfoldTradeDetailList))
            .append("\n] PositionBackTest toString End")
            .toString();
    }

}
