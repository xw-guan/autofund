package com.xwguan.autofund.entity.plan.scheme.activatedTactic;

import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.RangeCondition;

/**
 * 激活的策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-17
 */
public class ActivatedTactic {

    /**
     * 满足的周期条件
     */
    private PeriodCondition periodCondition;

    /**
     * 满足的区间条件, AND关系, null则不应用.
     */
    private RangeCondition rangeCondition;

    /**
     * 执行的操作
     */
    private Operation operation;

    public ActivatedTactic() {
        super();
    }

    public PeriodCondition getPeriodCondition() {
        return periodCondition;
    }

    public void setPeriodCondition(PeriodCondition periodCondition) {
        this.periodCondition = periodCondition;
    }

    public RangeCondition getRangeCondition() {
        return rangeCondition;
    }

    public void setRangeCondition(RangeCondition rangeCondition) {
        this.rangeCondition = rangeCondition;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "ActivatedTactic [periodCondition=" + periodCondition + ", rangeCondition=" + rangeCondition
            + ", operation=" + operation + "]";
    }

}
