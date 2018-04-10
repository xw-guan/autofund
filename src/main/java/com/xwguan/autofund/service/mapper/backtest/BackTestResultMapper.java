package com.xwguan.autofund.service.mapper.backtest;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueMappingStrategy;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.backtest.BackTestResult;
import com.xwguan.autofund.entity.plan.backtest.Drawdown;
import com.xwguan.autofund.entity.plan.backtest.PlanBackTestResult;
import com.xwguan.autofund.entity.plan.backtest.PositionBackTestResult;
import com.xwguan.autofund.entity.plan.portfolio.Position;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface BackTestResultMapper {

    @Mappings({
        @Mapping(source = "latestTradeDetail.buySum", target = "totalInvestment")
    })
    public BackTestResult toBackTestResult(LocalDate startDate, LocalDate endDate,
        double annualizedReturnPct, Drawdown maxDrawdown, AssetHistory latestAssetHistory,
        TradeDetail latestTradeDetail, Account account);

    @Mappings({
        @Mapping(source = "latestTradeDetail.buySum", target = "totalInvestment"),
        @Mapping(source = "plan.account.assetHistoryList", target = "assetHistoryList"),
        @Mapping(source = "plan.account.tradeDetailList", target = "tradeDetailList")
    })
    public PlanBackTestResult toPlanBackTestResult(LocalDate startDate, LocalDate endDate,
        double annualizedReturnPct, Drawdown maxDrawdown, AssetHistory latestAssetHistory,
        TradeDetail latestTradeDetail, Plan plan, List<PositionBackTestResult> positionBackTestResultList);

    public PlanBackTestResult toPlanBackTestResult(BackTestResult backTestResult, Plan plan,
        List<PositionBackTestResult> positionBackTestResultList);

    @Mappings({
        @Mapping(source = "latestTradeDetail.buySum", target = "totalInvestment"),
        @Mapping(source = "position.account.assetHistoryList", target = "assetHistoryList"),
        @Mapping(source = "position.account.tradeDetailList", target = "tradeDetailList"),
        @Mapping(source = "position.fundId", target = "fundId"),
    })
    public PositionBackTestResult toPositionBackTestResult(LocalDate startDate, LocalDate endDate,
        double annualizedReturnPct, Drawdown maxDrawdown, AssetHistory latestAssetHistory,
        TradeDetail latestTradeDetail, Position position);

    public PositionBackTestResult toPositionBackTestResult(BackTestResult backTestResult, Position position);
}
