package com.xwguan.autofund.dto.plan.tactic;

public class NavChangeTacticDto extends TacticDto {

    /**
     * 采样时间范围, 即在n个交易日内. 最小为1, 若值为null或小于1则设为1.
     * 应用场景例如: 3交易日内净值增加10%
     */
    private Integer inTradeDays;

    public Integer getInTradeDays() {
        return inTradeDays;
    }

    public void setInTradeDays(Integer inTradeDays) {
        this.inTradeDays = inTradeDays;
    }

    @Override
    public String toString() {
        return "NavChangeTacticDto [inTradeDays=" + inTradeDays + ", getName()=" + getName() + ", getTypeCode()="
            + getTypeCode() + ", isPlanTactic()=" + isPlanTactic() + ", getId()=" + getId() + ", getPlanId()="
            + getPlanId() + ", getPeriodCondition()=" + getPeriodCondition() + ", getActivated()=" + getActivated()
            + ", getRuleList()=" + getRuleList() + "]";
    }

}
