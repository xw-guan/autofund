package com.xwguan.autofund.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.xwguan.autofund.dao.fund.FundHistoryDao;
import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.fund.FundHistory;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.enums.RoundScaleEnum;
import com.xwguan.autofund.enums.TradeStateEnum;
import com.xwguan.autofund.exception.trade.AfterTradeException;
import com.xwguan.autofund.exception.trade.TradeException;
import com.xwguan.autofund.service.api.AccountService;
import com.xwguan.autofund.service.api.PositionService;
import com.xwguan.autofund.service.api.TacticService;
import com.xwguan.autofund.service.api.TradeService;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.service.handler.rule.PeriodConditionHandler;
import com.xwguan.autofund.service.mapper.account.AccountMapper;
import com.xwguan.autofund.service.util.Comparators;
import com.xwguan.autofund.util.FinanceUtils;
import com.xwguan.autofund.util.MathUtils;

/**
 * 抽象交易服务实现, 实现{@link TradeService}部分方法, 这些方法均不对数据库进行操作.
 * protected方法提供可能的交易计算和数据库操作, 但不保证事务, 事务由子类用声明式事务实现.
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-25
 */
public abstract class AbstractTradeServiceImpl implements TradeService {

    @Autowired
    private FundHistoryDao fundHistoryDao;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private TacticService tacticService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private PositionService positionService;
    
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 按方案交易计划执行交易.
     * 对其中的每个持仓执行{@code executeTrade(PositionTradeScheme positionTradeScheme)}方法.
     * 也就是说若子类中重载了{@code executeTrade(PositionTradeScheme positionTradeScheme)}方法,
     * 执行本方法时会按重载后的子类方法执行.
     */
    @Override
    public List<TradeDetail> executeTrade(PlanTradeScheme planTradeScheme) {
        return planTradeScheme.getPositionTradeSchemeList().parallelStream()
            .map(positionScheme -> executeTrade(positionScheme))
            .collect(Collectors.toList());
    }

    /**
     * 按持仓交易计划执行交易, 不连接数据库
     */
    @Override
    public TradeDetail executeTrade(PositionTradeScheme positionTradeScheme) {
        boolean connDb = false;
        return executeTrade(positionTradeScheme, connDb);
    }

    /**
     * 按持仓交易计划执行交易
     */
    protected TradeDetail executeTrade(PositionTradeScheme positionTradeScheme, boolean connDb) {
        double tradeValue = positionTradeScheme.getTradeValue();
        LocalDate tradeDate = positionTradeScheme.getDate();
        Position position = positionTradeScheme.getPosition();
        TradeDetail newDetail = calcAndPutPositionTradeDetailBeforeTrade(position.getAccount(),
            tradeValue, tradeDate, connDb);
        // 这里的状态可能是TO_TRADE或ZERO_TRADE_VALUE, TO_TRADE提交
        if (newDetail.getTradeState() == TradeStateEnum.TO_TRADE) {
            newDetail.setTradeState(TradeStateEnum.SUBMITTED);
        }
        return newDetail;
    }

    /**
     * 不连接数据库
     */
    @Override
    public void executeAfterTrade(PlanTradeScheme planTradeScheme) throws TradeException {
        boolean connDb = false;
        executeAfterTrade(planTradeScheme, connDb);
    }

    protected void executeAfterTrade(PlanTradeScheme planTradeScheme, boolean connDb) throws TradeException {
        LocalDate tradeDate = planTradeScheme.getDate();
        calcTradeDetailsAndAssetHistoriesAfterTrade(planTradeScheme.getPlan(), tradeDate, connDb);
        calcAndPutNextPeriodConditionAfterTrade(planTradeScheme.getPlan(), tradeDate, connDb);
    }

    /**
     * 简单直接设状态为CANCELLED
     */
    @Override
    public TradeDetail cancelTrade(Position position, TradeDetail positionTradeDetail) {
        boolean connDb = false;
        return cancelTrade(position, positionTradeDetail, connDb);
    }
    
    /**
     * 简单直接设状态为CANCELLED
     */
    protected TradeDetail cancelTrade(Position position, TradeDetail positionTradeDetail, boolean connDb) {
        positionTradeDetail.setTradeState(TradeStateEnum.CANCELLED);
        if (connDb) {
            accountService.updateTradeDetail(positionTradeDetail);
        }
        return positionTradeDetail;
    }
    

    @Override
    public void setRealTradeDetail(TradeDetail realPositionTradeDetail) throws TradeException {
        boolean updateDB = false;
        setRealTradeDetail(realPositionTradeDetail, updateDB);
    }
    
    /**
     * 设置真实交易细节, 并据此更新后续资产历史和交易细节.
     * updateDB为true时更新数据库
     * 
     * @param realPositionTradeDetail
     * @param updateDB 是否更新数据库
     * @throws TradeException 根据realPositionTradeDetail无法获取到account和position时
     */
    protected void setRealTradeDetail(TradeDetail realPositionTradeDetail, boolean updateDB) throws TradeException {
        Account updatedAccount = updateAccountByRealTradeDetail(realPositionTradeDetail);
        if (updateDB) {
            accountService.updateAssetHistory(updatedAccount.getAssetHistoryList());
            accountService.updateTradeDetail(updatedAccount.getTradeDetailList());
        }
    }

    /**
     * 交易后根据更新的净值计算交易细节和资产历史, 并放入相应对象中.
     * 若connDB为true, 将会从数据库中获取数据, 并将结果写入数据库
     * 
     * @param plan
     * @param tradeDate
     * @param connDb
     * @throws TradeException
     */
    protected void calcTradeDetailsAndAssetHistoriesAfterTrade(Plan plan, LocalDate tradeDate, boolean connDb)
        throws TradeException {
        List<TradeDetail> tradeDetailsOfAllPositionsOnDate = new ArrayList<>(); // 用于计划的TradeDetail计算
        List<AssetHistory> assetHistoriesOfAllPositionsOnDate = new ArrayList<>(); // 用于计划的AssetHistory计算
        for (Position pst : plan.getPositionList()) {
            double nav = getNavWithRetry(pst.getFundId(), tradeDate);
            calcAndUpdatePositionTradeDetailAfterTrade(pst.getAccount(), tradeDate, nav, connDb)
                .ifPresent(pstNewDetail -> tradeDetailsOfAllPositionsOnDate.add(pstNewDetail));
            calcAndPutPositionHistoryAfterTrade(pst.getAccount(), tradeDate, nav, connDb)
                .ifPresent(pstNewHistory -> assetHistoriesOfAllPositionsOnDate.add(pstNewHistory));
        }
        if (CollectionUtils.isNotEmpty(tradeDetailsOfAllPositionsOnDate)) {
            TradeDetail plNewDetail = calcAndPutPlanTradeDetailAfterTrade(plan.getAccount(),
                tradeDetailsOfAllPositionsOnDate, tradeDate).get(); // not null in this case
            if (connDb) {
                accountService.insertTradeDetail(plNewDetail);
                accountService.updateTradeDetail(tradeDetailsOfAllPositionsOnDate);
            }
        }
        if (CollectionUtils.isNotEmpty(assetHistoriesOfAllPositionsOnDate)) {
            AssetHistory plNewHistory = calcAndPutPlanAssetHistoryAfterTrade(plan.getAccount(),
                assetHistoriesOfAllPositionsOnDate, tradeDate).get(); // not null in this cas
            if (connDb) {
                accountService.insertAssetHistory(plNewHistory);
                accountService.insertAssetHistory(assetHistoriesOfAllPositionsOnDate);
            }
        }
    }

    /**
     * 对应Plan对象的所有策略, 若date满足周期条件, 则计算下一周期条件并对Plan对象进行更改.
     * 若connDB为true, 将计算得到的周期条件写入数据库
     * 
     * @param plan
     * @param date
     * @param connDb
     * @return may be empty
     */
    protected List<PeriodCondition> calcAndPutNextPeriodConditionAfterTrade(Plan plan, LocalDate date, boolean connDb) {
        List<PeriodCondition> periodConditions = plan.handler().setConnDbForData(false).getAllTactics().stream()
            .map(Tactic::getPeriodCondition)
            .map(PeriodCondition::handler)
            .filter(pcHandler -> pcHandler.isInvestDate(date)) // 判断date是否满足周期条件
            .peek(pcHandler -> pcHandler.calcPeriodCondition(date))
            .map(PeriodConditionHandler::getCondition)
            .collect(Collectors.toList());
        if (connDb && CollectionUtils.isNotEmpty(periodConditions)) {
            tacticService.updatePeriodCondition(periodConditions);
        }
        return periodConditions;
    }

    /**
     * 计算交易前的持仓交易细节, 并放入Position对象中.
     * 若connDB为true, 将会从数据库中获取数据.
     * <ul>
     * <li>买入时TradeDetail对象中的tradeYuan有值, tradeShare为null, tradeState为TO_TRADE
     * <li>卖出时TradeDetail对象中的tradeYuan为null, tradeShare有值, tradeState为TO_TRADE
     * <li>交易值为0时TradeDetail对象中的tradeYuan和tradeShare值都为0, tradeState为ZERO_TRADE_VALUE
     * </ul>
     * 返回的TradeDetail对象中的buySum和sellSum为前一日数据
     * 
     * @param tradeValue 交易值, 正值为买入, 单位元, 负值为卖出, 单位份额
     * @param tradeDate 交易日期
     * @param connDb 是否连接数据库
     * @return 持仓交易细节
     */
    protected TradeDetail calcAndPutPositionTradeDetailBeforeTrade(Account positionAccount, 
        double tradeValue, LocalDate tradeDate, boolean connDb) {
        TradeDetail prevDetail = positionAccount.handler().setConnDbForData(connDb)
            .getPrevTradeDetailOrDefault(tradeDate); // not null
        TradeDetail newDetail = calcPositionTradeDetailBeforeTrade(tradeValue, tradeDate, prevDetail);
        positionAccount.getTradeDetailList().add(newDetail);
        return newDetail;
    }

    /**
     * 计算交易前的持仓交易细节
     * <ul>
     * <li>买入时TradeDetail对象中的tradeYuan有值, tradeShare为null, tradeState为TO_TRADE
     * <li>卖出时TradeDetail对象中的tradeYuan为null, tradeShare有值, tradeState为TO_TRADE
     * <li>交易值为0时TradeDetail对象中的tradeYuan和tradeShare值都为0, tradeState为ZERO_TRADE_VALUE
     * </ul>
     * TradeDetail对象中的buySum和sellSum为前一日数据
     * 
     * @param tradeValue 交易值, 正值为买入, 单位元, 负值为卖出, 单位份额
     * @param tradeDate 交易日期
     * @param prevDetail 前一交易细节, not null
     * @return 持仓交易细节
     */
    protected TradeDetail calcPositionTradeDetailBeforeTrade(double tradeValue,
        LocalDate tradeDate, TradeDetail prevDetail) {
        // prevDetail只用buySum和sellSum不用过滤交易状态是否有效
        Long accountId = prevDetail.getAccountId();
        Double tradeYuan = null;
        Double tradeShare = null;
        Double buySum = prevDetail.getBuySum();
        Double sellSum = prevDetail.getSellSum();
        TradeStateEnum tradeState = TradeStateEnum.TO_TRADE;
        if (tradeValue > 0) {
            tradeYuan = tradeValue; // 买入金额确定, 但还不知道份额是多少(因为净值, 手续费)
        } else if (tradeValue < 0) {
            tradeShare = tradeValue; // 卖出份额确定, 但还不知道卖出到账金额是多少
        } else {
            tradeYuan = 0D;
            tradeShare = 0D;
            tradeState = TradeStateEnum.ZERO_TRADE_VALUE;
        }
        TradeDetail newDetail = new TradeDetail(-1L, accountId, tradeDate, tradeYuan, tradeShare, buySum,
            sellSum, tradeState);
        return newDetail;
    }

    /**
     * 计算并更新交易后的持仓交易细节, 需要净值, 暂时不考虑手续费.
     * 若connDB为true, 将会从数据库中获取数据.
     * newDetail为空, 即当日没有交易时, 返回Optional.EMPTY
     * 
     * @param tradeDate 交易日期
     * @param nav 对应日期的净值
     * @param connDb 是否连接数据库
     * @return 持仓交易细节, newDetail为空, 即当日没有交易时, 返回Optional.EMPTY
     */
    protected Optional<TradeDetail> calcAndUpdatePositionTradeDetailAfterTrade(Account positionAccount,
        LocalDate tradeDate, double nav, boolean connDb) {
        TradeDetail newDetail = positionAccount.handler().setConnDbForData(connDb)
            .getTradeDetailOrNull(tradeDate); // 该日期不一定有交易, 可能为null
        Optional<TradeDetail> updatedDeatil = calcAndUpdatePositionTradeDetailAfterTrade(tradeDate, nav,
            newDetail);
        return updatedDeatil;

    }

    /**
     * 计算并更新交易后的持仓交易细节, 需要净值, 暂时不考虑手续费
     * newDetail为空, 即当日没有交易时, 返回Optional.EMPTY
     * 
     * @param tradeDate 交易日期
     * @param nav 对应日期的净值
     * @param newDetail 交易前计算得到的新交易细节, 将被更新, nullable
     * @return 持仓交易细节, newDetail为空, 即当日没有交易时, 返回Optional.EMPTY
     */
    protected Optional<TradeDetail> calcAndUpdatePositionTradeDetailAfterTrade(LocalDate tradeDate,
        double nav, TradeDetail newDetail) {
        return Optional.ofNullable(newDetail)
            .filter(td -> td.getTradeState().isValidTradeState())
            .map(td -> {
                Double tradeYuan = newDetail.getTradeYuan();
                Double tradeShare = newDetail.getTradeShare();
                Double buySum = newDetail.getBuySum();
                Double sellSum = newDetail.getSellSum();
                if (tradeShare == null) {
                    // buy
                    tradeShare = MathUtils.round(tradeYuan / nav, RoundScaleEnum.SHARE_ROUND_SCALE);
                    buySum += tradeYuan;
                } else if (tradeYuan == null) {
                    // sell
                    tradeYuan = MathUtils.round(tradeShare * nav, RoundScaleEnum.SHARE_ROUND_SCALE);
                    sellSum -= tradeYuan; // sellSum为正值, tradeYuan为负值
                }
                newDetail.setTradeShare(tradeShare);
                newDetail.setTradeYuan(tradeYuan);
                newDetail.setBuySum(buySum);
                newDetail.setSellSum(sellSum);
                return td;
            });
    }

    /**
     * 计算持仓资产历史, 并放入Position对象的Account中, <b>必须先执行calcPositionTradeDetail方法, 否则结果不正确</b>.
     * 若connDB为true, 将会从数据库中获取数据
     * 
     * @param tradeDate 交易日期
     * @param nav 对应日期的净值
     * @param connDb 是否连接数据库
     * @return 持仓历史, 不为null, Optional为了统一返回值类型
     */
    protected Optional<AssetHistory> calcAndPutPositionHistoryAfterTrade(Account positionAccount,
        LocalDate tradeDate, double nav, boolean connDb) {
        AccountHandler handler = positionAccount.handler().setConnDbForData(connDb);
        // 在此之前必须先对tradeDate执行calcPositionTradeDetailAfterTrade方法, 否则在tradeDate日期有交易时, 无法获取到newDetail
        TradeDetail newOrPrevDetail = handler.getTradeDetailOrNull(tradeDate); // newDetail, possibly null
        if (newOrPrevDetail == null) {
            newOrPrevDetail = handler.getPrevTradeDetailOrDefault(tradeDate); // prevDetail, not null
        }
        AssetHistory prevHistory = handler.getPrevAssetHistoryOrDefault(tradeDate); // not null
        Optional<AssetHistory> newHistory = calcPositionHistoryAfterTrade(tradeDate, nav, newOrPrevDetail, prevHistory);
        newHistory.ifPresent(nh -> positionAccount.getAssetHistoryList().add(nh));
        return newHistory;
    }

    /**
     * 计算持仓资产历史, 并放入Position对象的Account中
     * 
     * @param tradeDate 交易日日期
     * @param nav 对应日期的净值
     * @param newOrPrevDetail 日期为tradeDate的交易细节或tradeDate的前一个交易细节, not null
     * @param Detail tradeDate之前的交易细节, not null
     * @param prevHistory tradeDate之前的资产历史, not null
     * @return 持仓历史, 不为null, Optional为了统一返回值类型
     */
    protected Optional<AssetHistory> calcPositionHistoryAfterTrade(LocalDate tradeDate,
        double nav, TradeDetail newOrPrevDetail, AssetHistory prevHistory) {
        Long accountId = prevHistory.getAccountId();
        double posShare = tradeDate.equals(newOrPrevDetail.getDate())
            ? prevHistory.getPosShare() + newOrPrevDetail.getTradeShare() // tradeDate有交易, 增加新买入的份额
            : prevHistory.getPosShare(); // tradeDate无交易, 持有份额不变
        double buySum = newOrPrevDetail.getBuySum();
        double sellSum = newOrPrevDetail.getSellSum();
        double prevPosPrice = prevHistory.getPosPrice(); // 交易前的持仓价格
        double prevTotalHistoryIncome = prevHistory.getTotalHistoryIncome();
        double posAsset = FinanceUtils.posAsset(posShare, nav);
        double sellIncome = FinanceUtils.sellIncome(posShare, prevPosPrice, nav);
        double totalHistoryIncome = FinanceUtils.totalHistoryIncome(prevTotalHistoryIncome, sellIncome);
        double posCost = FinanceUtils.posCost(buySum, sellSum, totalHistoryIncome);
        double posIncome = FinanceUtils.posIncome(posAsset, posCost);
        double posIncomeRatePct = FinanceUtils.posIncomeRatePct(posIncome, posCost);
        double posPrice = FinanceUtils.posPrice(posCost, posShare);
        double totalIncome = FinanceUtils.totalIncome(totalHistoryIncome, posIncome);
        double totalCost = buySum;
        double totalIncomeRatePct = FinanceUtils.totalIncomePct(totalIncome, totalCost);

        AssetHistory newHistory = new AssetHistory(-1L, accountId, tradeDate, posAsset, posIncome, posIncomeRatePct,
            posCost, posShare, posPrice, totalCost, totalIncome, totalIncomeRatePct, totalHistoryIncome);
        return Optional.of(newHistory);
    }

    /**
     * 根据属于Plan的所有持仓在date日期的持仓交易细节, 计算计划交易细节, 并将计算得到的交易细节放入Plan对象的Account中.
     * <p>没有持仓或所有持仓交易都不是有效状态时, 返回Optional.EMPTY.
     * 
     * @param tradeDetailsOfAllPositionsOnDate 属于Plan的所有持仓在date日期的持仓交易细节
     * @param tradeDate 交易日期
     * @param connDb 是否连接数据库
     * @return 计划交易细节, 没有持仓或所有持仓交易都不是有效状态时, 返回Optional.EMPTY
     */
    protected Optional<TradeDetail> calcAndPutPlanTradeDetailAfterTrade(Account planAccount,
        List<TradeDetail> tradeDetailsOfAllPositionsOnDate, LocalDate tradeDate) {
        Optional<TradeDetail> newDetail = calcPlanTradeDetailAfterTrade(planAccount.getId(),
            tradeDetailsOfAllPositionsOnDate, tradeDate);
        newDetail.ifPresent(nd -> planAccount.getTradeDetailList().add(nd));
        return newDetail;
    }

    /**
     * 根据属于Plan的所有持仓在date日期的持仓交易细节, 计算计划交易细节.
     * <p>没有持仓或所有持仓交易都不是有效状态时, 返回Optional.EMPTY.
     * 
     * @param tradeDetailsOfAllPositionsOnDate 属于Plan的所有持仓在date日期的持仓交易细节
     * @param tradeDate 交易日期
     * @return 计划交易细节, 没有持仓或所有持仓交易都不是有效状态时, 返回Optional.EMPTY
     */
    protected Optional<TradeDetail> calcPlanTradeDetailAfterTrade(Long accountId,
        List<TradeDetail> tradeDetailsOfAllPositionsOnDate, LocalDate tradeDate) {
        List<TradeDetail> filteredTradeDetails = tradeDetailsOfAllPositionsOnDate.stream()
            .filter(td -> td.getDate().equals(tradeDate))
            .filter(td -> td.getTradeState().isValidTradeState())
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filteredTradeDetails)) {
            return Optional.empty(); // 没有有效交易或没有持仓时
        }
        double sumTradeYuan = round(filteredTradeDetails.stream().mapToDouble(TradeDetail::getTradeYuan).sum());
        double sumBuySum = round(filteredTradeDetails.stream().mapToDouble(TradeDetail::getBuySum).sum());
        double sumSellSum = round(filteredTradeDetails.stream().mapToDouble(TradeDetail::getSellSum).sum());
        TradeStateEnum tradeState = sumTradeYuan != 0
            ? TradeStateEnum.SUCCESS // TODO 这里SUCCESS可能不对
            : TradeStateEnum.ZERO_TRADE_VALUE;
        TradeDetail newDetail = new TradeDetail(-1L, accountId, tradeDate, sumTradeYuan, null, sumBuySum, sumSellSum,
            tradeState);
        return Optional.of(newDetail);
    }

    /**
     * 根据属于Plan的所有持仓在date日期的持仓资产历史, 计算计划资产历史, 并放入Plan对象的Account中.
     * <p>没有持仓时返回Optional.EMPTY
     * 
     * @param assetHistoriesOfAllPositionsOnDate 属于Plan的所有持仓在date日期的持仓资产历史
     * @param tradeDate 交易日期
     * @return 计划资产历史, 没有持仓时返回Optional.EMPTY
     */
    protected Optional<AssetHistory> calcAndPutPlanAssetHistoryAfterTrade(Account planAccount,
        List<AssetHistory> assetHistoriesOfAllPositionsOnDate, LocalDate tradeDate) {
        Optional<AssetHistory> newHistory = calcPlanAssetHistoryAfterTrade(planAccount.getId(),
            assetHistoriesOfAllPositionsOnDate, tradeDate);
        newHistory.ifPresent(nh -> planAccount.getAssetHistoryList().add(nh));
        return newHistory;
    }

    /**
     * 根据属于Plan的所有持仓在date日期的持仓资产历史, 计算计划资产历史.
     * <p>没有持仓时返回Optional.EMPTY
     * 
     * @param assetHistoriesOfAllPositionsOnDate 属于Plan的所有持仓在date日期的持仓资产历史
     * @param tradeDate 交易日期
     * @return 计划资产历史, 没有持仓时返回Optional.EMPTY
     */
    protected Optional<AssetHistory> calcPlanAssetHistoryAfterTrade(Long accountId,
        List<AssetHistory> assetHistoriesOfAllPositionsOnDate, LocalDate tradeDate) {
        List<AssetHistory> filteredAssetHistories = assetHistoriesOfAllPositionsOnDate.stream()
            .filter(ah -> ah.getDate().equals(tradeDate))
            .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(filteredAssetHistories)) {
            return Optional.empty(); // 没有持仓时
        }
        double posAsset = round(filteredAssetHistories.stream().mapToDouble(AssetHistory::getPosAsset).sum());
        double posCost = round(filteredAssetHistories.stream().mapToDouble(AssetHistory::getPosCost).sum());
        // double posIncome = round(filteredAssetHistories.stream().mapToDouble(AssetHistory::getPosIncome).sum());
        double totalHistoryIncome = round(
            filteredAssetHistories.stream().mapToDouble(AssetHistory::getTotalHistoryIncome).sum());
        double totalCost = round(filteredAssetHistories.stream().mapToDouble(AssetHistory::getTotalCost).sum());
        // double totalIncome = round(filteredAssetHistories.stream().mapToDouble(AssetHistory::getTotalIncome).sum());
        double posIncome = FinanceUtils.posIncome(posAsset, posCost);
        double totalIncome = FinanceUtils.totalIncome(totalHistoryIncome, posIncome);
        double posIncomeRatePct = FinanceUtils.posIncomeRatePct(posIncome, posCost);
        double totalIncomeRatePct = FinanceUtils.totalIncomePct(totalIncome, totalCost);
        AssetHistory newHistory = new AssetHistory(-1L, accountId, tradeDate, posAsset, posIncome, posIncomeRatePct,
            posCost, null, null, totalCost, totalIncome, totalIncomeRatePct, totalHistoryIncome);
        return Optional.of(newHistory);
    }
    
    /**
     * 设置真实交易细节, 并据此更新后续资产历史和交易细节
     * 
     * @throws TradeException
     */
    protected Account updateAccountByRealTradeDetail(TradeDetail realPositionTradeDetail)
        throws TradeException {
        Long accountId = realPositionTradeDetail.getAccountId();
        LocalDate date = realPositionTradeDetail.getDate();
        Account account = accountService.getAccount(accountId, date, LocalDate.now(), null);
        if (account == null || account.getOwnerType() != AccountOwnerTypeEnum.POSITION) {
            throw new TradeException("Not match, cannot get valid account");
        }
        Position position = positionService.getPositionById(account.getOwnerId());
        if (position == null) {
            throw new TradeException("Not match, cannot get valid position");
        }
        Integer fundId = position.getFundId();
        AccountHandler accountHandler = account.handler().setConnDbForData(true);

        /*
         * **变量命名解释**
         * TradeDetail, td
         * curDetail: 当前交易细节
         * prevDetail: 前一交易细节, 不一定是前一交易日, 交易日期不一定有交易
         * AssetHistory, ah
         * curHistory: 当前资产历史
         * prevHistory: 前一交易日资产历史, 每个交易日期都有资产历史
         */

        // update tradeDetail
        TradeDetail prevDetail = accountHandler.getPrevTradeDetailOrDefault(date);
        TradeDetail curDetail = updateTradeDetail(realPositionTradeDetail, prevDetail); // 就是realPositionTradeDetail
        List<TradeDetail> tradeDetailList = account.getTradeDetailList();
        replaceWithTradeDetail(tradeDetailList, curDetail);
        tradeDetailList.sort(Comparators.HISTORICAL_DATE_COMPARATOR);
        prevDetail = curDetail;
        if (tradeDetailList.size() > 1) {
            for (int i = 1; i < tradeDetailList.size(); i++) {
                curDetail = tradeDetailList.get(i);
                updateTradeDetail(curDetail, prevDetail);
                prevDetail = curDetail;
            }
        }
        // update assetHistory
        List<AssetHistory> assetHistoryList = account.getAssetHistoryList();
        assetHistoryList.sort(Comparators.HISTORICAL_DATE_COMPARATOR);
        AssetHistory prevHistory = accountHandler.getPrevAssetHistoryOrDefault(date);
        AssetHistory curHistory;
        TradeDetail newOrPrevDetail;
        double nav;
        LocalDate curDate;
        accountHandler.setConnDbForData(false);
        for (int i = 0; i < assetHistoryList.size(); i++) {
            curHistory = assetHistoryList.get(i);
            curDate = curHistory.getDate();
            newOrPrevDetail = accountHandler.getTradeDetailOrNull(curDate);
            if (newOrPrevDetail == null) {
                newOrPrevDetail = accountHandler.getPrevTradeDetailOrDefault(curDate);
            }
            nav = getNavWithRetry(fundId, curDate);
            updateAssetHistory(curHistory, curDate, nav, newOrPrevDetail, prevHistory);
            prevHistory = curHistory;
        }

        return account;
    }

    @SuppressWarnings("unused") // 待验证
    private boolean newUpdateAccountByRealTradeDetail(TradeDetail realPositionTradeDetail) 
        throws TradeException {
        Long accountId = realPositionTradeDetail.getAccountId();
        LocalDate date = realPositionTradeDetail.getDate();
        Account account = accountService.getAccount(accountId, date, LocalDate.now(), null);
        if (account == null || account.getOwnerType() != AccountOwnerTypeEnum.POSITION) {
            return false;
        }
        Position position = positionService.getPositionById(account.getOwnerId());
        if (position == null) {
            return false;
        }
        Integer fundId = position.getFundId();
        AccountHandler accountHandler = account.handler();

        List<TradeDetail> tradeDetailList = account.getTradeDetailList();
        List<AssetHistory> assetHistoryList = account.getAssetHistoryList();

        // first TradeDetail
        TradeDetail prevDetail = accountHandler.getPrevTradeDetailOrDefault(date);
        TradeDetail curDetail = updateTradeDetail(realPositionTradeDetail, prevDetail); // 就是realPositionTradeDetail
        replaceWithTradeDetail(tradeDetailList, curDetail);
        // first AssetHistory
        double nav = getNavWithRetry(fundId, date);
        AssetHistory prevHistory = accountHandler.getPrevAssetHistoryOrDefault(date);
        AssetHistory curHistory = calcPositionHistoryAfterTrade(date, nav, curDetail, prevHistory).get();
        replaceWithAssetHistory(assetHistoryList, curHistory);

        if (assetHistoryList.size() == 1) {
            return true;
        }

        tradeDetailList.sort(Comparators.HISTORICAL_DATE_COMPARATOR);
        assetHistoryList.sort(Comparators.HISTORICAL_DATE_COMPARATOR);
        Iterator<TradeDetail> itd = tradeDetailList.iterator();
        Iterator<AssetHistory> iah = assetHistoryList.iterator();

        // 第一个元素设为prev, 第二个设为cur
        prevHistory = iah.next();
        curHistory = iah.next();
        // 没有第二个元素, 第一个设为prev和cur, 遍历过程中ah和td的日期永远不会相等
        if (tradeDetailList.size() > 1) {
            prevDetail = itd.next();
            curDetail = itd.next();
        } else {
            curDetail = itd.next();
            prevDetail = curDetail;
        }

        // 从第二个元素开始
        while (true) {
            LocalDate ahDate = curHistory.getDate();
            nav = getNavWithRetry(fundId, ahDate);
            if (ahDate.equals(curDetail.getDate())) {
                if (itd.hasNext()) {
                    prevDetail = curDetail;
                    curDetail = itd.next();
                }
                updateTradeDetail(curDetail, prevDetail);
                updateAssetHistory(curHistory, ahDate, nav, curDetail, prevHistory);
            } else {
                updateAssetHistory(curHistory, ahDate, nav, prevDetail, prevHistory);
            }
            if (!iah.hasNext()) {
                break;
            }
            prevHistory = curHistory;
            curHistory = iah.next();
        }
        return false;
    }

    /**
     * 用新交易细节替换列表中对应日期的交易细节, 不存在则插入
     * 
     * @param tradeDetailList 待更新的交易细节列表
     * @param newTradeDetail 新交易细节, 必须有有效accountId, date, tradeYuan, tradeShare
     */
    private void replaceWithTradeDetail(List<TradeDetail> tradeDetailList, TradeDetail newTradeDetail) {
        LocalDate date = newTradeDetail.getDate();
        tradeDetailList.removeIf(td -> td.getDate().equals(date));
        tradeDetailList.add(newTradeDetail);
    }

    /**
     * 用新资产历史替换列表中对应日期的交易细节, 不存在则插入
     * 
     * @param assetHistoryList 待更新的交易细节列表
     * @param newAssetHistory 真实交易细节, 必须有有效accountId, date, tradeYuan, tradeShare
     */
    private void replaceWithAssetHistory(List<AssetHistory> assetHistoryList, AssetHistory newAssetHistory) {
        LocalDate date = newAssetHistory.getDate();
        assetHistoryList.removeIf(td -> td.getDate().equals(date));
        assetHistoryList.add(newAssetHistory);
    }

    /**
     * 使用前一交易细节的buySum和sellSum更新新交易细节
     * 
     * @param curDetail 当前交易细节, 必须有有效accountId, date, tradeYuan, tradeShare
     * @param prevDetail 当前交易细节的前一个交易细节, 或模板中的默认值, 必须有有效buySum和sellSum
     * @return 更新后的当前交易细节
     */
    private TradeDetail updateTradeDetail(TradeDetail curDetail, TradeDetail prevDetail) {
        Double realTradeYuan = curDetail.getTradeYuan();
        Double buySum = prevDetail.getBuySum();
        Double sellSum = prevDetail.getSellSum();
        if (realTradeYuan > 0) {
            buySum += realTradeYuan;
        } else if (realTradeYuan < 0) {
            sellSum -= realTradeYuan;
        }
        curDetail.setBuySum(buySum);
        curDetail.setSellSum(sellSum);
        curDetail.setTradeState(TradeStateEnum.SUCCESS);
        return curDetail;
    }

    /**
     * 更新当前资产历史
     * 
     * @param curHistory 当前资产历史, 待更新
     * @param tradeDate 交易日期
     * @param nav 净值
     * @param newOrPrevDetail 日期为tradeDate的交易细节或tradeDate的前一个交易细节, not null
     * @param Detail tradeDate之前的交易细节, not null
     * @param prevHistory tradeDate之前的资产历史, not null
     * @return 更新后的当前资产历史
     */
    private AssetHistory updateAssetHistory(AssetHistory curHistory, LocalDate tradeDate,
        double nav, TradeDetail newOrPrevDetail, AssetHistory prevHistory) {
        AssetHistory newHistory = calcPositionHistoryAfterTrade(tradeDate, nav, newOrPrevDetail, prevHistory)
            .get();
        return accountMapper.updateAssetHistoryIgnoreId(newHistory, curHistory);
    }


    // TODO 可能造成大量线程都处于sleep状态
    protected double getNavWithRetry(Integer fundId, LocalDate date)
        throws TradeException {
        Future<Optional<FundHistory>> future = executor.submit(() -> {
            FundHistory fundHistory = null;
            int maxRetryTimes = 1; // TODO 暂时设为1
            for (int i = 0; i < maxRetryTimes; i++) {
                fundHistory = fundHistoryDao.getFundHistoryByFundIdAndDate(fundId, date);
                if (fundHistory == null) {
                    Thread.sleep(1000 * i);
                }
            }
            return Optional.ofNullable(fundHistory);
        });
        double nav;
        try {
            nav = future.get().map(FundHistory::getNav).orElseThrow(
                () -> new AfterTradeException(
                    "Unable to get FundHistory of fundId(" + fundId + ") and date (" + date + ")"));
        } catch (AfterTradeException | InterruptedException | ExecutionException e) {
            throw new TradeException(e);
        }
        return nav;
    }

    /**
     * 保留两位小数
     */
    private double round(double num) {
        int roundScale = 2;
        return MathUtils.round(num, roundScale);
    }

}
