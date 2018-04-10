package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.ProfitTakingTacticHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 全局止盈策略, 计划所属账户盈利超过该止盈线, 则全部赎回, 具有最高优先级, 当ProfitTakingTactic激活时, 其他策略不应用
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-16
 */
public class ProfitTakingTactic extends PlanTactic implements Handleable {

    public static final Class<ProfitTakingTacticHandler> HANDLER_CLASS = ProfitTakingTacticHandler.class;

    public ProfitTakingTactic() {
        super();
    }

    public ProfitTakingTactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated,
        List<Rule> ruleList) {
        super(id, planId, periodCondition, activated, ruleList);
    }

    @Override
    public String toString() {
        return "ProfitTakingTactic [id=" + getId() + ", planId=" + getPlanId() + ", periodCondition="
            + getPeriodCondition()
            + ", activated=" + getActivated() + ", ruleList=" + getRuleList() + "]";
    }

    @Override
    public String toStringMultiLine() {
        StringBuilder sb = new StringBuilder("ProfitTakingTactic [id=" + getId() + ", planId=" + getPlanId()
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
    public Class<ProfitTakingTacticHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public ProfitTakingTacticHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}
