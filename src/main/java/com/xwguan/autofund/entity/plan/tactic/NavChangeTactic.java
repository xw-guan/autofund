package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.NavChangeTacticHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 净值变化策略, 例如15天内净值增加30%, 则卖出30%, 应用于投资组合中的每一个持仓, 一般为辅助策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-17
 */
public class NavChangeTactic extends PositionTactic implements Handleable {

    public static final Class<NavChangeTacticHandler> HANDLER_CLASS = NavChangeTacticHandler.class;

    /**
     * 采样时间范围, 即在n个交易日内. 最小为1, 若值为null或小于1则设为1.
     * 应用场景例如: 3交易日内净值增加10%
     */
    private Integer inTradeDays;

    public NavChangeTactic() {
        super();
    }
    
    public NavChangeTactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated,
        List<Rule> ruleList, Integer inTradeDays) {
        super(id, planId, periodCondition, activated, ruleList);
        this.inTradeDays = inTradeDays;
    }

    public Integer getInTradeDays() {
        return inTradeDays;
    }

    public void setInTradeDays(Integer inTradeDays) {
        this.inTradeDays = inTradeDays;
    }

    @Override
    public String toString() {
        return "NavChangeTactic [inTradeDays=" + inTradeDays + ", " + "id=" + getId() + ", planId=" + getPlanId()
            + ", periodCondition=" + getPeriodCondition() + ", activated=" + getActivated() + ", ruleList="
            + getRuleList() + "]";
    }

    @Override
    public String toStringMultiLine() {
        StringBuilder sb = new StringBuilder(
            "NavChangeTactic [inTradeDays=" + inTradeDays + ", " + "id=" + getId() + ", planId=" + getPlanId()
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
    public Class<NavChangeTacticHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public NavChangeTacticHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}
