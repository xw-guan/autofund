package com.xwguan.autofund.service.handler.tactic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.rule.PeriodConditionHandler;
import com.xwguan.autofund.service.handler.rule.RuleHandler;
import com.xwguan.autofund.service.template.plan.OperationTemplate;
import com.xwguan.autofund.service.template.plan.RangeConditionTemplate;
import com.xwguan.autofund.service.template.plan.SuppressConditionTemplate;

/**
 * 默认策略处理器
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
public abstract class AbstractTacticHandler<T extends Tactic> extends AbstractEntityHandler<T>
    implements TacticHandler<T> {

    @Autowired
    private RuleHandler ruleHandler;

    @Autowired
    private PeriodConditionHandler periodConditionHandler;
    
    @SuppressWarnings("unused")
    @Autowired
    private RangeConditionTemplate rangeConditionTemplate;
    
    @SuppressWarnings("unused")
    @Autowired
    private SuppressConditionTemplate suppressConditionTemplate;
    
    @Autowired
    private OperationTemplate operationTemplate;

    public AbstractTacticHandler() {
        super();
    }

    @Override
    public TacticHandler<T> handle(T entity) {
        super.handle(entity);
        return this;
    }

    protected Optional<Rule> getRuleMetCondition(LocalDate date, Double value) {
        return getTactic().getRuleList().stream()
            .filter(r -> ruleHandler.handle(r).isConditionsMet(date, value))
            .peek(r -> {
                if (r.getOperation() == null) {
                    r.setOperation(operationTemplate.defaultOperation());
                }
            }).findFirst();
    }

    /**
     * 目前来看不会有多个Rule同时满足, 暂时保留此方法
     */
    @Deprecated
    protected List<Rule> listRuleMetCondition(LocalDate date, Double value) {
        List<Rule> resultList = new ArrayList<>();
        List<Rule> ruleList = getTactic().getRuleList();
        for (Rule rule : ruleList) {
            ruleHandler.handle(rule);
            if (ruleHandler.isConditionsMet(date, value)) {
                resultList.add(rule);
            }
        }
        return resultList;
    }

    @Override
    public boolean isActivated() {
        return getTactic().getActivated();
    }

    @Override
    public boolean isPeriodConditionMet(LocalDate date) {
        periodConditionHandler.handle(getTactic().getPeriodCondition());
        return periodConditionHandler.isInvestDate(date);
    }

    @Override
    public boolean needNotHandle(LocalDate date) {
        return !isActivated() || !isPeriodConditionMet(date);
    }

    @Override
    public Tactic getTactic() {
        return getEntity();
    }
}
