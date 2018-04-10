package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.Handleable;

/**
 * 策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-16
 */
public abstract class Tactic implements Handleable {
    /**
     * 策略id
     */
    private Long id;

    /**
     * 策略从属的计划id
     */
    private Long planId;

    /**
     * 周期条件
     */
    private PeriodCondition periodCondition;

    /**
     * 是否激活
     */
    private Boolean activated;

    /**
     * 规则
     */
    private List<Rule> ruleList;

    public Tactic() {
        super();
    }

    public Tactic(Long id, Long planId, PeriodCondition periodCondition, Boolean activated, List<Rule> ruleList) {
        this.id = id;
        this.planId = planId;
        this.periodCondition = periodCondition;
        this.activated = activated;
        this.ruleList = ruleList;
    }

//    /**
//     * @return 是否激活, null或false视为未激活
//     */
//    public boolean isTacticActivated() {
//        return activated != null && activated;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public PeriodCondition getPeriodCondition() {
        return periodCondition;
    }

    public void setPeriodCondition(PeriodCondition periodCondition) {
        this.periodCondition = periodCondition;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    
    @Override
    public String toString() {
        return "Tactic [id=" + id + ", planId=" + planId + ", periodCondition=" + periodCondition + ", activated="
            + activated + ", ruleList=" + ruleList + "]";
    }

    public String toStringMultiLine() {
        StringBuilder sb = new StringBuilder("Tactic [id=" + id + ", planId=" + planId + ", periodCondition="
            + periodCondition + ", activated=" + activated + ", ruleList=[");
        if (getRuleList().size() != 0) {
            for (Rule rule : getRuleList()) {
                sb.append("\n").append(rule).append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append("]]");
        return sb.toString();
    }

}
