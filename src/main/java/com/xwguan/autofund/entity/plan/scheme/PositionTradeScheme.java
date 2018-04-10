package com.xwguan.autofund.entity.plan.scheme;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.util.ToStringUtils;

/**
 * 持仓交易方案细节
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-17
 */
public class PositionTradeScheme {

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 对持仓进行的交易值, 是所有满足的策略的总和. 大于0是买入, 单位为元; 小于0是卖出, 单位为份额
     */
    private Double tradeValue;

    /**
     * 持仓
     */
    private Position position;

    /**
     * 激活的策略列表, 包括计划策略和持仓策略, 包含每个策略满足的条件和操作
     */
    private List<ActivatedTactic> activatedTacticList;

    public PositionTradeScheme() {
        super();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Double getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(Double tradeValue) {
        this.tradeValue = tradeValue;
    }

    public List<ActivatedTactic> getActivatedTacticList() {
        return activatedTacticList;
    }

    public void setActivatedTacticList(List<ActivatedTactic> activatedTacticList) {
        this.activatedTacticList = activatedTacticList;
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean unfoldActivatedTacticList) {
        return new StringBuilder("PositionTradeScheme [")
            .append("date=").append(date)
            .append(", tradeValue=").append(tradeValue)
            .append(", position=").append(position)
            .append(ToStringUtils.unfoldCollectionIfNeeded(", activatedTacticList=", activatedTacticList, true))
            .append("]")
            .toString();
    }

}
