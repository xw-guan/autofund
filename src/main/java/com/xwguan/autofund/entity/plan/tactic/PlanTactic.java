package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.tactic.PlanTacticHandler;

/**
 * 计划策略, 判断是否激活及获取激活规则时只需要用到计划相关的指标, 不用考虑持仓, 激活时作用于每个持仓
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-15
 */
public abstract class PlanTactic extends Tactic {

    public PlanTactic() {
        super();
    }

    public PlanTactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated, List<Rule> ruleList) {
        super(id, planId, periodCondition, activated, ruleList);
    }

    public abstract PlanTacticHandler handler();

}
