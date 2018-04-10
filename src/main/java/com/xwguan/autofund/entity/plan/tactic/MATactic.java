package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.enums.MAEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.MATacticHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 均线策略, 应用于投资组合中的每一个持仓, 一般为主要策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-16
 */
public class MATactic extends PositionTactic implements Handleable {

    public static final Class<MATacticHandler> HANDLER_CLASS = MATacticHandler.class;

    /**
     * 使用的均线, 默认为MA250
     */
    private MAEnum ma;

    public MATactic() {
        super();
    }

    public MATactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated, List<Rule> ruleList,
        MAEnum ma) {
        super(id, planId, periodCondition, activated, ruleList);
        this.ma = ma;
    }

    public MAEnum getMa() {
        return ma;
    }

    public void setMa(MAEnum ma) {
        this.ma = ma;
    }

    @Override
    public String toString() {
        return "MATactic [ma=" + ma + ", " + "id=" + getId() + ", planId=" + getPlanId() + ", periodCondition="
            + getPeriodCondition() + ", activated=" + getActivated() + ", ruleList=" + getRuleList() + "]";
    }

    @Override
    public String toStringMultiLine() {
        StringBuilder sb = new StringBuilder("MATactic [ma=" + ma + ", " + "id=" + getId() + ", planId=" + getPlanId()
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
    public Class<MATacticHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public MATacticHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}
