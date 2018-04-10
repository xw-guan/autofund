package com.xwguan.autofund.dto.plan.tactic;

import com.xwguan.autofund.enums.MAEnum;

public class MATacticDto extends TacticDto {

    /**
     * 使用的均线, 默认为MA250
     */
    private MAEnum ma;

    public MAEnum getMa() {
        return ma;
    }

    public void setMa(MAEnum ma) {
        this.ma = ma;
    }

    @Override
    public String toString() {
        return "MATacticDto [ma=" + ma + ", getName()=" + getName() + ", getTypeCode()=" + getTypeCode()
            + ", isPlanTactic()=" + isPlanTactic() + ", getId()=" + getId() + ", getPlanId()=" + getPlanId()
            + ", getPeriodCondition()=" + getPeriodCondition() + ", getActivated()=" + getActivated()
            + ", getRuleList()=" + getRuleList() + "]";
    }
    
    
}
