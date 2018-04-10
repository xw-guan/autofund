package com.xwguan.autofund.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.backtest.BackTestResult;
import com.xwguan.autofund.entity.plan.backtest.Drawdown;
import com.xwguan.autofund.entity.plan.backtest.PlanBackTestResult;
import com.xwguan.autofund.entity.plan.backtest.PositionBackTestResult;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.enums.HistoryScopeEnum;
import com.xwguan.autofund.exception.OverMaxIterationNumberException;
import com.xwguan.autofund.exception.plan.BackTestServiceException;
import com.xwguan.autofund.exception.trade.TradeException;
import com.xwguan.autofund.service.api.BackTestService;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.api.PlanService;
import com.xwguan.autofund.service.api.TradeService;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.service.handler.plan.PlanHandler;
import com.xwguan.autofund.service.mapper.backtest.BackTestResultMapper;
import com.xwguan.autofund.util.IocUtils;

/**
 * 回测服务实现类
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-05
 */
@Service
public class BackTestServiceImpl implements BackTestService {

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    @Qualifier("tradeServiceBackTestImpl")
    private TradeService tradeService;

    @Autowired
    private BackTestResultMapper backTestResultMapper;

    @Autowired
    private PlanService planService;

    @Override
    public PlanBackTestResult backTest(Plan plan, LocalDate startDate, LocalDate endDate)
        throws BackTestServiceException {
        checkParams(plan, startDate, endDate);
        Plan cleanPlan = plan.handler().cleanCopyAndReset(startDate);
        logger.debug("plan to execute back test: \n" + plan);
        executeBackTest(cleanPlan, startDate, endDate);
        return getPlanBackTestResult(cleanPlan, startDate, endDate);
        // checkParams(plan, startDate, endDate);
        // executeBackTest(plan, startDate, endDate);
        // return getPlanBackTestResult(plan, startDate, endDate);
    }

    @Override
    public PlanBackTestResult backTest(Long planId, LocalDate startDate, LocalDate endDate)
        throws BackTestServiceException {
        Plan plan = planService.getFullPlanByPlanId(planId, HistoryScopeEnum.NONE);
        return backTest(plan, startDate, endDate);
    }

    private void executeBackTest(Plan plan, LocalDate startDate, LocalDate endDate) throws BackTestServiceException {
        PlanHandler handler = plan.handler().setConnDbForData(false); // 回测不连数据库获取plan相关
        List<LocalDate> tradeDateList = dateTimeService.listTradeDay(startDate, endDate).stream()
            .filter(date -> !date.isAfter(LocalDate.now())) // 去掉当日之后的日期
            .collect(Collectors.toList());
        for (LocalDate date : tradeDateList) {
            try {
                PlanTradeScheme planTradeScheme = handler.generateTradeScheme(date).orElse(null);
                if (planTradeScheme == null) {
                    continue;
                }
                tradeService.executeTrade(planTradeScheme);
                tradeService.executeAfterTrade(planTradeScheme);
            } catch (TradeException e) {
                throw new BackTestServiceException(e.getMessage(), e);
            } catch (IOException e) {
                throw new BackTestServiceException(e.getMessage(), e); // 一般情况下这些异常不会发生, 发生了肯定是程序有错误
            }
        }
    }

    /**
     * 获取计划回测结果, 必须先执行calcTradeDetailAndAssetHistory
     * 
     * @param plan 计划
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 计划回测结果
     * @throws BackTestServiceException
     */
    private PlanBackTestResult getPlanBackTestResult(Plan plan, LocalDate startDate, LocalDate endDate)
        throws BackTestServiceException {
        List<PositionBackTestResult> positionResultList = new ArrayList<>();
        AccountHandler handler = IocUtils.getBean(AccountHandler.class).setConnDbForData(false); // 回测不连数据库
        for (Position position : plan.getPositionList()) {
            handler.handle(position.getAccount());
            BackTestResult backTestResult = getBackTestResult(handler, startDate, endDate);
            positionResultList.add(backTestResultMapper.toPositionBackTestResult(backTestResult, position));
        }
        handler.handle(plan.getAccount());
        BackTestResult backTestResult = getBackTestResult(handler, startDate, endDate);
        return backTestResultMapper.toPlanBackTestResult(backTestResult, plan, positionResultList);
    }

    /**
     * 获取BackTestResult, 根据AccountHandler计算, 不考虑Plan或是Position. 必须先执行calcTradeDetailAndAssetHistory
     * 
     * @param handler 账户处理者
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return BackTestResult
     * @throws BackTestServiceException
     */
    private BackTestResult getBackTestResult(AccountHandler handler, LocalDate startDate, LocalDate endDate)
        throws BackTestServiceException {
        double xirr = 0;
        try {
            xirr = handler.calcXirrPct();
        } catch (OverMaxIterationNumberException e) {
            throw new BackTestServiceException(e);
        }
        Drawdown maxDrawdown = handler.calcMaxDrawdownPct();
        AssetHistory latestAssetHistory = handler.getLatestAssetHistory().orElse(null);
        TradeDetail latestTradeDetail = handler.getLatestTradeDetail().orElse(null);
        Account account = handler.getAccount();
        return backTestResultMapper.toBackTestResult(startDate, endDate, xirr, maxDrawdown,
            latestAssetHistory, latestTradeDetail, account);
    }

    /**
     * 校验参数
     * 
     * @param plan 计划
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @throws BackTestServiceException 回测异常
     */
    private void checkParams(Plan plan, LocalDate startDate, LocalDate endDate) throws BackTestServiceException {
        if (endDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new BackTestServiceException("Invalid date, startDate: " + startDate + ", endDate: " + endDate);
        }
        if (plan == null) {
            throw new BackTestServiceException("Invalid plan, plan: " + plan);
        }
        if (plan.getAccount() == null) {
            throw new BackTestServiceException(
                "Invalid plan, the plan has an invalid account, account: " + plan.getAccount());
        }
        if (CollectionUtils.isEmpty(plan.getPositionList())) {
            throw new BackTestServiceException("The plan has an empty portfilio");
        }
        for (Position position : plan.getPositionList()) {
            if (position.getAccount() == null) {
                throw new BackTestServiceException("Some positions have invalid accounts, position: " + position);
            }
        }
    }

}
