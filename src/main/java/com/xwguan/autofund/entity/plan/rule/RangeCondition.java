package com.xwguan.autofund.entity.plan.rule;

import com.xwguan.autofund.enums.ConditionUnitEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.rule.RangeConditionHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 范围条件, 在该范围内则满足条件, 范围边界值为null被认为是INFINITY
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-16
 */
public class RangeCondition extends Condition implements Handleable {

    public static final Class<RangeConditionHandler> HANDLER_CLASS = RangeConditionHandler.class;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 符合条件的范围的左边界值, null值被认为是Double.MAX_VALUE
     */
    private Double boundaryLeft;

    /**
     * 符合条件的范围的右边界值, null值被认为是-Double.MAX_VALUE
     */
    private Double boundaryRight;

    /**
     * 范围的单位
     */
    private ConditionUnitEnum rangeUnit;

    public RangeCondition() {
        super();
    }

    public RangeCondition(Long id, Long ruleId, Double boundaryLeft, Double boundaryRight,
        ConditionUnitEnum rangeUnit) {
        super(id);
        this.ruleId = ruleId;
        this.boundaryLeft = boundaryLeft;
        this.boundaryRight = boundaryRight;
        this.rangeUnit = rangeUnit;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Double getBoundaryLeft() {
        return boundaryLeft;
    }

    public void setBoundaryLeft(Double boundaryLeft) {
        this.boundaryLeft = boundaryLeft;
    }

    public Double getBoundaryRight() {
        return boundaryRight;
    }

    public void setBoundaryRight(Double boundaryRight) {
        this.boundaryRight = boundaryRight;
    }

    public ConditionUnitEnum getRangeUnit() {
        return rangeUnit;
    }

    public void setRangeUnit(ConditionUnitEnum unit) {
        this.rangeUnit = unit;
    }

    @Override
    public String toString() {
        return "RangeCondition [ruleId=" + ruleId + ", boundaryLeft=" + boundaryLeft + ", boundaryRight="
            + boundaryRight + ", rangeUnit=" + rangeUnit + ", getId()=" + getId() + "]";
    }

    @Override
    public Class<RangeConditionHandler> handlerClass() {
        return HANDLER_CLASS;
    }

    @Override
    public RangeConditionHandler handler() {
        return IocUtils.getBean(HANDLER_CLASS).handle(this);
    }

}
