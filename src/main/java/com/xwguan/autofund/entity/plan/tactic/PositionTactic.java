package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.tactic.PositionTacticHandler;

/**
 * 持仓策略, 判断是否激活及获取激活规则时需要具体持仓相关指标, 激活时按照持仓具体情况作用于每个持仓
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-15
 */
public abstract class PositionTactic extends Tactic {

    public PositionTactic() {
        super();
    }

    public PositionTactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated,
        List<Rule> ruleList) {
        super(id, planId, periodCondition, activated, ruleList);
    }

    public abstract PositionTacticHandler handler();

}
