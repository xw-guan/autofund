package com.xwguan.autofund.service.handler.tactic;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.service.mapper.tactic.CleanCopyGainLossTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.GainLossTacticMapper;

@Component
@Scope("prototype")
public class GainLossTacticHandler extends AbstractPositionTacticHandler {


    @Autowired
    private AccountHandler accountHandler;

    @Autowired
    private DateTimeService dateTimeService;
    
    @Autowired
    private GainLossTacticMapper mapper;
    
    @Autowired
    private CleanCopyGainLossTacticMapper cleanCopyMapper;

    public GainLossTacticHandler() {
        super();
    }

    @Override
    public GainLossTacticHandler handle(PositionTactic tactic) {
        super.handle(tactic);
        return this;
    }

    @Override
    public Optional<Rule> getRuleMetCondition(LocalDate date) {
        return needNotHandle(date)
            ? Optional.empty()
            : accountHandler.handle(getPosition().getAccount())
                .getAssetHistory(dateTimeService.prevTradeDay(date)) // date日期还没有资产历史, 使用前一交易日结算后的交易历史
                .flatMap(history -> getRuleMetCondition(date, history.getPosIncomeRatePct()));
    }

    @Override
    public Optional<ActivatedTactic> getActivatedTactic(LocalDate date) {
        if (needNotHandle(date)) {
            return Optional.empty();
        }
        LocalDate prevDate = dateTimeService.prevTradeDay(date); // date日期还没有资产历史, 使用前一交易日结算后的交易历史
        Optional<AssetHistory> history = accountHandler.handle(getPosition().getAccount()).getAssetHistory(prevDate);
        // history为空表示还没有资产历史, 也就不存在止盈, 返回EMPTY
        Optional<Rule> rule = history.flatMap(h -> getRuleMetCondition(date, h.getPosIncomeRatePct()));
        return Optional
            .ofNullable(mapper.toActivatedGainLossTactic(getTactic(), rule.orElse(null), history.orElse(null)));
    }

    @Override
    public GainLossTactic getTactic() {
        return (GainLossTactic) super.getTactic();
    }

    @Override
    public GainLossTactic cleanCopy() {
        return cleanCopyMapper.cleanCopy(getTactic());
    }

}
