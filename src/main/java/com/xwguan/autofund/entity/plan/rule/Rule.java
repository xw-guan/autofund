package com.xwguan.autofund.entity.plan.rule;

import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.rule.RuleHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 规则
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-20
 */
public class Rule implements Handleable {

    /**
     * 规则id
     */
    private Long id;

    /**
     * 策略id
     */
    private Long tacticId;

    /**
     * 策略类型
     */
    private TacticTypeEnum tacticType;

    /**
     * 区间条件
     */
    private RangeCondition rangeCondition;

    /**
     * 抑制条件
     */
    private SuppressCondition suppressCondition;

    /**
     * 操作
     */
    private Operation operation;

    public Rule() {
        super();
    }

    public Rule(Long id, Long tacticId, TacticTypeEnum tacticType, RangeCondition rangeCondition,
        SuppressCondition suppressCondition, Operation operation) {
        this.id = id;
        this.tacticId = tacticId;
        this.tacticType = tacticType;
        this.rangeCondition = rangeCondition;
        this.suppressCondition = suppressCondition;
        this.operation = operation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTacticId() {
        return tacticId;
    }

    public void setTacticId(Long tacticId) {
        this.tacticId = tacticId;
    }

    public TacticTypeEnum getTacticType() {
        return tacticType;
    }

    public void setTacticType(TacticTypeEnum tacticType) {
        this.tacticType = tacticType;
    }

    public RangeCondition getRangeCondition() {
        return rangeCondition;
    }

    public void setRangeCondition(RangeCondition rangeCondition) {
        this.rangeCondition = rangeCondition;
    }

    public SuppressCondition getSuppressCondition() {
        return suppressCondition;
    }

    public void setSuppressCondition(SuppressCondition suppressCondition) {
        this.suppressCondition = suppressCondition;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Rule [id=" + id + ", tacticId=" + tacticId + ", tacticType=" + tacticType + ", rangeCondition="
            + rangeCondition + ", suppressCondition=" + suppressCondition + ", operation=" + operation + "]";
    }

    @Override
    public Class<RuleHandler> handlerClass() {
        return RuleHandler.class;
    }

    @Override
    public RuleHandler handler() {
        return IocUtils.getBean(handlerClass()).handle(this);
    }
}
