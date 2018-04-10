package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.PeriodBuyTacticHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 定期买入策略, 应用于投资组合中的每一个持仓, 一般为主要策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public class PeriodBuyTactic extends PositionTactic implements Handleable {

    public static final Class<PeriodBuyTacticHandler> HANDLER_CLASS = PeriodBuyTacticHandler.class;

    public PeriodBuyTactic() {
        super();
    }

    public PeriodBuyTactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated,
        List<Rule> ruleList) {
        super(id, planId, periodCondition, activated, ruleList);
    }

    @Override
    public String toString() {
        return "PeriodBuyTactic [id=" + getId() + ", planId=" + getPlanId() + ", periodCondition="
            + getPeriodCondition()
            + ", activated=" + getActivated() + ", ruleList=" + getRuleList() + "]";
    }

    @Override
    public String toStringMultiLine() {
        StringBuilder sb = new StringBuilder("PeriodBuyTactic [id=" + getId() + ", planId=" + getPlanId()
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
    public Class<PeriodBuyTacticHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public PeriodBuyTacticHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}
