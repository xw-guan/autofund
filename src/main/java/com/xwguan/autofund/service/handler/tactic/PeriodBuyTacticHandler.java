package com.xwguan.autofund.service.handler.tactic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.service.mapper.tactic.CleanCopyPeriodBuyTacticMapper;
import com.xwguan.autofund.service.mapper.tactic.PeriodBuyTacticMapper;

@Component
@Scope("prototype")
public class PeriodBuyTacticHandler extends AbstractPositionTacticHandler {

    @Autowired
    private PeriodBuyTacticMapper mapper;
    
    @Autowired
    private CleanCopyPeriodBuyTacticMapper cleanCopyMapper;

    public PeriodBuyTacticHandler() {
        super();
    }

    @Override
    public PeriodBuyTacticHandler handle(PositionTactic tactic) {
        super.handle(tactic);
        return this;
    }

    @Override
    public Optional<Rule> getRuleMetCondition(LocalDate date) throws IOException {
        return needNotHandle(date) ? Optional.empty() : getRuleMetCondition(date, null);
    }

    @Override
    public Optional<ActivatedTactic> getActivatedTactic(LocalDate date) throws IOException {
        return getRuleMetCondition(date).map(rule -> mapper.toActivatedTactic(getTactic(), rule));
    }

    @Override
    public PeriodBuyTactic getTactic() {
        return (PeriodBuyTactic) super.getTactic();
    }

    @Override
    public PeriodBuyTactic cleanCopy() {
        return cleanCopyMapper.cleanCopy(getTactic());
    }
}
