package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.GainLossTacticHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 盈亏策略, 基于当前持仓的盈亏百分比状态, 应用于每一个持仓, 可为主要策略或辅助策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-16
 */
public class GainLossTactic extends PositionTactic implements Handleable {

    public static final Class<GainLossTacticHandler> HANDLER_CLASS = GainLossTacticHandler.class;

    public GainLossTactic() {
        super();
    }

    public GainLossTactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated,
        List<Rule> ruleList) {
        super(id, planId, periodCondition, activated, ruleList);
    }

    @Override
    public String toString() {
        return "GainLossTactic [id=" + getId() + ", planId=" + getPlanId() + ", periodCondition=" + getPeriodCondition()
            + ", activated=" + getActivated() + ", ruleList=" + getRuleList() + "]";
    }

    @Override
    public String toStringMultiLine() {
        StringBuilder sb = new StringBuilder("GainLossTactic [id=" + getId() + ", planId=" + getPlanId()
            + ", periodCondition=" + getPeriodCondition() + ", activated=" + getActivated() + ", ruleList=[");
        if (getRuleList().size() != 0) {
            for (Rule rule : getRuleList()) {
                sb.append("\n").append(rule).append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append("]]");
        return sb.toString();
    }

    @Override
    public Class<GainLossTacticHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public GainLossTacticHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}