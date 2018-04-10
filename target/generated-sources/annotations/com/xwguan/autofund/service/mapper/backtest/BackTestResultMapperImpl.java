package com.xwguan.autofund.service.mapper.backtest;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.backtest.BackTestResult;
import com.xwguan.autofund.entity.plan.backtest.Drawdown;
import com.xwguan.autofund.entity.plan.backtest.PlanBackTestResult;
import com.xwguan.autofund.entity.plan.backtest.PositionBackTestResult;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class BackTestResultMapperImpl implements BackTestResultMapper {

    @Override
    public BackTestResult toBackTestResult(LocalDate startDate, LocalDate endDate, double annualizedReturnPct, Drawdown maxDrawdown, AssetHistory latestAssetHistory, TradeDetail latestTradeDetail, Account account) {

        BackTestResult backTestResult = new BackTestResult();

        if ( startDate != null ) {
            backTestResult.setStartDate( startDate );
        }
        if ( endDate != null ) {
            backTestResult.setEndDate( endDate );
        }
        if ( maxDrawdown != null ) {
            backTestResult.setMaxDrawdown( maxDrawdown );
        }
        if ( latestAssetHistory != null ) {
            if ( latestAssetHistory.getTotalIncome() != null ) {
                backTestResult.setTotalIncome( latestAssetHistory.getTotalIncome() );
            }
            if ( latestAssetHistory.getTotalIncomeRatePct() != null ) {
                backTestResult.setTotalIncomeRatePct( latestAssetHistory.getTotalIncomeRatePct() );
            }
        }
        if ( latestTradeDetail != null ) {
            if ( latestTradeDetail.getBuySum() != null ) {
                backTestResult.setTotalInvestment( latestTradeDetail.getBuySum() );
            }
        }
        if ( account != null ) {
            List<AssetHistory> list = account.getAssetHistoryList();
            if ( list != null ) {
                backTestResult.setAssetHistoryList( new ArrayList<AssetHistory>( list ) );
            }
            else {
                backTestResult.setAssetHistoryList( new ArrayList<AssetHistory>() );
            }
            List<TradeDetail> list1 = account.getTradeDetailList();
            if ( list1 != null ) {
                backTestResult.setTradeDetailList( new ArrayList<TradeDetail>( list1 ) );
            }
            else {
                backTestResult.setTradeDetailList( new ArrayList<TradeDetail>() );
            }
        }
        backTestResult.setAnnualizedReturnPct( annualizedReturnPct );

        return backTestResult;
    }

    @Override
    public PlanBackTestResult toPlanBackTestResult(LocalDate startDate, LocalDate endDate, double annualizedReturnPct, Drawdown maxDrawdown, AssetHistory latestAssetHistory, TradeDetail latestTradeDetail, Plan plan, List<PositionBackTestResult> positionBackTestResultList) {

        PlanBackTestResult planBackTestResult = new PlanBackTestResult();

        if ( startDate != null ) {
            planBackTestResult.setStartDate( startDate );
        }
        if ( endDate != null ) {
            planBackTestResult.setEndDate( endDate );
        }
        if ( maxDrawdown != null ) {
            planBackTestResult.setMaxDrawdown( maxDrawdown );
        }
        if ( latestAssetHistory != null ) {
            if ( latestAssetHistory.getTotalIncome() != null ) {
                planBackTestResult.setTotalIncome( latestAssetHistory.getTotalIncome() );
            }
            if ( latestAssetHistory.getTotalIncomeRatePct() != null ) {
                planBackTestResult.setTotalIncomeRatePct( latestAssetHistory.getTotalIncomeRatePct() );
            }
        }
        if ( latestTradeDetail != null ) {
            if ( latestTradeDetail.getBuySum() != null ) {
                planBackTestResult.setTotalInvestment( latestTradeDetail.getBuySum() );
            }
        }
        if ( plan != null ) {
            List<AssetHistory> assetHistoryList = planAccountAssetHistoryList( plan );
            List<AssetHistory> list = assetHistoryList;
            if ( list != null ) {
                planBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>( list ) );
            }
            else {
                planBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>() );
            }
            List<TradeDetail> tradeDetailList = planAccountTradeDetailList( plan );
            List<TradeDetail> list1 = tradeDetailList;
            if ( list1 != null ) {
                planBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>( list1 ) );
            }
            else {
                planBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>() );
            }
        }
        if ( positionBackTestResultList != null ) {
            List<PositionBackTestResult> list2 = positionBackTestResultList;
            if ( list2 != null ) {
                planBackTestResult.setPositionBackTestResultList( new ArrayList<PositionBackTestResult>( list2 ) );
            }
            else {
                planBackTestResult.setPositionBackTestResultList( new ArrayList<PositionBackTestResult>() );
            }
        }
        planBackTestResult.setAnnualizedReturnPct( annualizedReturnPct );

        return planBackTestResult;
    }

    @Override
    public PlanBackTestResult toPlanBackTestResult(BackTestResult backTestResult, Plan plan, List<PositionBackTestResult> positionBackTestResultList) {

        PlanBackTestResult planBackTestResult = new PlanBackTestResult();

        if ( backTestResult != null ) {
            planBackTestResult.setStartDate( backTestResult.getStartDate() );
            planBackTestResult.setEndDate( backTestResult.getEndDate() );
            planBackTestResult.setAnnualizedReturnPct( backTestResult.getAnnualizedReturnPct() );
            planBackTestResult.setMaxDrawdown( backTestResult.getMaxDrawdown() );
            planBackTestResult.setTotalIncome( backTestResult.getTotalIncome() );
            planBackTestResult.setTotalInvestment( backTestResult.getTotalInvestment() );
            planBackTestResult.setTotalIncomeRatePct( backTestResult.getTotalIncomeRatePct() );
            List<AssetHistory> list = backTestResult.getAssetHistoryList();
            if ( list != null ) {
                planBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>( list ) );
            }
            else {
                planBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>() );
            }
            List<TradeDetail> list1 = backTestResult.getTradeDetailList();
            if ( list1 != null ) {
                planBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>( list1 ) );
            }
            else {
                planBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>() );
            }
        }
        if ( positionBackTestResultList != null ) {
            List<PositionBackTestResult> list2 = positionBackTestResultList;
            if ( list2 != null ) {
                planBackTestResult.setPositionBackTestResultList( new ArrayList<PositionBackTestResult>( list2 ) );
            }
            else {
                planBackTestResult.setPositionBackTestResultList( new ArrayList<PositionBackTestResult>() );
            }
        }

        return planBackTestResult;
    }

    @Override
    public PositionBackTestResult toPositionBackTestResult(LocalDate startDate, LocalDate endDate, double annualizedReturnPct, Drawdown maxDrawdown, AssetHistory latestAssetHistory, TradeDetail latestTradeDetail, Position position) {

        PositionBackTestResult positionBackTestResult = new PositionBackTestResult();

        if ( startDate != null ) {
            positionBackTestResult.setStartDate( startDate );
        }
        if ( endDate != null ) {
            positionBackTestResult.setEndDate( endDate );
        }
        if ( maxDrawdown != null ) {
            positionBackTestResult.setMaxDrawdown( maxDrawdown );
        }
        if ( latestAssetHistory != null ) {
            if ( latestAssetHistory.getTotalIncome() != null ) {
                positionBackTestResult.setTotalIncome( latestAssetHistory.getTotalIncome() );
            }
            if ( latestAssetHistory.getTotalIncomeRatePct() != null ) {
                positionBackTestResult.setTotalIncomeRatePct( latestAssetHistory.getTotalIncomeRatePct() );
            }
        }
        if ( latestTradeDetail != null ) {
            if ( latestTradeDetail.getBuySum() != null ) {
                positionBackTestResult.setTotalInvestment( latestTradeDetail.getBuySum() );
            }
        }
        if ( position != null ) {
            positionBackTestResult.setFundId( position.getFundId() );
            List<AssetHistory> assetHistoryList = positionAccountAssetHistoryList( position );
            List<AssetHistory> list = assetHistoryList;
            if ( list != null ) {
                positionBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>( list ) );
            }
            else {
                positionBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>() );
            }
            List<TradeDetail> tradeDetailList = positionAccountTradeDetailList( position );
            List<TradeDetail> list1 = tradeDetailList;
            if ( list1 != null ) {
                positionBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>( list1 ) );
            }
            else {
                positionBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>() );
            }
            positionBackTestResult.setRefIndexId( position.getRefIndexId() );
        }
        positionBackTestResult.setAnnualizedReturnPct( annualizedReturnPct );

        return positionBackTestResult;
    }

    @Override
    public PositionBackTestResult toPositionBackTestResult(BackTestResult backTestResult, Position position) {

        PositionBackTestResult positionBackTestResult = new PositionBackTestResult();

        if ( backTestResult != null ) {
            positionBackTestResult.setStartDate( backTestResult.getStartDate() );
            positionBackTestResult.setEndDate( backTestResult.getEndDate() );
            positionBackTestResult.setAnnualizedReturnPct( backTestResult.getAnnualizedReturnPct() );
            positionBackTestResult.setMaxDrawdown( backTestResult.getMaxDrawdown() );
            positionBackTestResult.setTotalIncome( backTestResult.getTotalIncome() );
            positionBackTestResult.setTotalInvestment( backTestResult.getTotalInvestment() );
            positionBackTestResult.setTotalIncomeRatePct( backTestResult.getTotalIncomeRatePct() );
            List<AssetHistory> list = backTestResult.getAssetHistoryList();
            if ( list != null ) {
                positionBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>( list ) );
            }
            else {
                positionBackTestResult.setAssetHistoryList( new ArrayList<AssetHistory>() );
            }
            List<TradeDetail> list1 = backTestResult.getTradeDetailList();
            if ( list1 != null ) {
                positionBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>( list1 ) );
            }
            else {
                positionBackTestResult.setTradeDetailList( new ArrayList<TradeDetail>() );
            }
        }
        if ( position != null ) {
            positionBackTestResult.setFundId( position.getFundId() );
            positionBackTestResult.setRefIndexId( position.getRefIndexId() );
        }

        return positionBackTestResult;
    }

    private List<AssetHistory> planAccountAssetHistoryList(Plan plan) {
        if ( plan == null ) {
            return null;
        }
        Account account = plan.getAccount();
        if ( account == null ) {
            return null;
        }
        List<AssetHistory> assetHistoryList = account.getAssetHistoryList();
        if ( assetHistoryList == null ) {
            return null;
        }
        return assetHistoryList;
    }

    private List<TradeDetail> planAccountTradeDetailList(Plan plan) {
        if ( plan == null ) {
            return null;
        }
        Account account = plan.getAccount();
        if ( account == null ) {
            return null;
        }
        List<TradeDetail> tradeDetailList = account.getTradeDetailList();
        if ( tradeDetailList == null ) {
            return null;
        }
        return tradeDetailList;
    }

    private List<AssetHistory> positionAccountAssetHistoryList(Position position) {
        if ( position == null ) {
            return null;
        }
        Account account = position.getAccount();
        if ( account == null ) {
            return null;
        }
        List<AssetHistory> assetHistoryList = account.getAssetHistoryList();
        if ( assetHistoryList == null ) {
            return null;
        }
        return assetHistoryList;
    }

    private List<TradeDetail> positionAccountTradeDetailList(Position position) {
        if ( position == null ) {
            return null;
        }
        Account account = position.getAccount();
        if ( account == null ) {
            return null;
        }
        List<TradeDetail> tradeDetailList = account.getTradeDetailList();
        if ( tradeDetailList == null ) {
            return null;
        }
        return tradeDetailList;
    }
}
