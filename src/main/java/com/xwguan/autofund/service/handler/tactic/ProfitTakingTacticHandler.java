package com.xwguan.autofund.service.handler.tactic;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.service.mapper.tactic.CleanCopyProfitTakingTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.ProfitTakingTacticMapper;

@Component
@Scope("prototype")
public class ProfitTakingTacticHandler extends AbstractPlanTacticHandler {

    @Autowired
    private AccountHandler accountHandler;

    @Autowired
    private DateTimeService dateTimeService;
    
    @Autowired
    private ProfitTakingTacticMapper mapper;
    
    @Autowired
    private CleanCopyProfitTakingTacticMapper cleanCopyMapper;

    public ProfitTakingTacticHandler() {
        super();
    }

    @Override
    public ProfitTakingTacticHandler handle(PlanTactic tactic) {
        super.handle(tactic);
        return this;
    }

    @Override
    public Optional<Rule> getRuleMetCondition(LocalDate date) {
        return needNotHandle(date)
            ? Optional.empty()
            : accountHandler.handle(getPlan().getAccount())
                .getAssetHistory(dateTimeService.prevTradeDay(date)) // date日期还没有资产历史, 使用前一交易日结算后的交易历史
                .flatMap(history -> getRuleMetCondition(date, history.getPosIncomeRatePct()));
    }

    @Override
    public Optional<ActivatedTactic> getActivatedTactic(LocalDate date) {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        LocalDate prevDate = dateTimeService.prevTradeDay(date); // date日期还没有资产历史, 使用前一交易日结算后的交易历史
        Optional<AssetHistory> latestHistory = accountHandler.handle(getPlan().getAccount()).getAssetHistory(prevDate);
        // latestHistory为null表示还没有资产历史, 也就不存在止盈, 返回EMPTY
        Optional<Rule> rule = latestHistory
            .flatMap(history -> getRuleMetCondition(date, history.getPosIncomeRatePct()));
        return Optional.ofNullable(
            mapper.toActivatedProfitTakingTactic(getTactic(), rule.orElse(null), latestHistory.orElse(null)));
    }

    @Override
    public ProfitTakingTactic getTactic() {
        return (ProfitTakingTactic) super.getTactic();
    }

    @Override
    public ProfitTakingTactic cleanCopy() {
        return cleanCopyMapper.cleanCopy(getTactic());
    }

}
