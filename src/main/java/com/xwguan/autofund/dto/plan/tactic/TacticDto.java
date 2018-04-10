package com.xwguan.autofund.dto.plan.tactic;

import java.util.List;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.enums.TacticTypeEnum;

public class TacticDto {

    /**
     * @see TacticTypeEnum
     */
    private String name;

    /**
     * @see TacticTypeEnum
     */
    private String typeCode;

    /**
     * @see TacticTypeEnum
     */
    private boolean planTactic;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public boolean isPlanTactic() {
        return planTactic;
    }

    public void setPlanTactic(boolean planTactic) {
        this.planTactic = planTactic;
    }

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


}
