package com.xwguan.autofund.entity.plan.rule;

import com.xwguan.autofund.enums.TradeUnitEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.rule.OperationHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 策略操作
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-16
 */
public class Operation implements Handleable{
    /**
     * 条件id
     */
    private Long id;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 买卖值(大于0买入, 小于0卖出, 等于0不操作)
     */
    private Double tradeValue = 0D;

    /**
     * 买卖值单位(份/百分比/元)
     */
    private TradeUnitEnum tradeUnit;

    public Operation() {
        super();
    }

    public Operation(Long id, Long ruleId, Double tradeValue, TradeUnitEnum tradeUnit) {
        this.id = id;
        this.ruleId = ruleId;
        this.tradeValue = tradeValue;
        this.tradeUnit = tradeUnit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Double getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(Double tradeValue) {
        this.tradeValue = tradeValue;
    }

    public TradeUnitEnum getUnit() {
        return tradeUnit;
    }

    public void setUnit(TradeUnitEnum tradeUnit) {
        this.tradeUnit = tradeUnit;
    }

    @Override
    public String toString() {
        return "Operation [id=" + id + ", ruleId=" + ruleId + ", tradeValue=" + tradeValue + ", tradeUnit=" + tradeUnit
            + "]";
    }

    @Override
    public Class<OperationHandler> handlerClass() {
        return OperationHandler.class;
    }

    @Override
    public OperationHandler handler() {
        return IocUtils.getBean(handlerClass()).handle(this);
    }

}
