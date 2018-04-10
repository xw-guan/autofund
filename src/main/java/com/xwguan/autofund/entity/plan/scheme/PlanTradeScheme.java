package com.xwguan.autofund.entity.plan.scheme;

import java.time.LocalDate;
import java.util.List;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.util.ToStringUtils;

/**
 * 计划交易方案
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-16
 */
public class PlanTradeScheme {

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 对应计划
     */
    private Plan plan;

    /**
     * 方案总的买入值
     */
    private Double totalBuy;

    /**
     * 激活的计划策略列表, 只作为标识, 实际交易数值包含在持仓交易方案中
     */
    private List<ActivatedTactic> activatedPlanTacticList;

    /**
     * 持仓交易方案列表
     */
    private List<PositionTradeScheme> positionTradeSchemeList;

    public PlanTradeScheme() {
        super();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Double getTotalBuy() {
        return totalBuy;
    }

    public void setTotalBuy(Double totalBuy) {
        this.totalBuy = totalBuy;
    }

    public List<ActivatedTactic> getActivatedPlanTacticList() {
        return activatedPlanTacticList;
    }

    public void setActivatedPlanTacticList(List<ActivatedTactic> activatedPlanTacticList) {
        this.activatedPlanTacticList = activatedPlanTacticList;
    }

    public List<PositionTradeScheme> getPositionTradeSchemeList() {
        return positionTradeSchemeList;
    }

    public void setPositionTradeSchemeList(List<PositionTradeScheme> positionTradeSchemeList) {
        this.positionTradeSchemeList = positionTradeSchemeList;
    }

    @Override
    public String toString() {
        return toString(true, true);
    }

    public String toString(boolean unfoldActivatedPlanTaticList, boolean unfoldPositionTradeSchemeList) {
        return new StringBuilder("PlanTradeScheme [")
            .append("date=").append(date)
            .append(", planId=").append(plan.getId()) // 只有id, 其他plan的属性没有必要
            .append(", totalBuy=").append(totalBuy)
            .append(ToStringUtils.unfoldCollectionIfNeeded(", activatedPlanTacticList=", activatedPlanTacticList,
                unfoldActivatedPlanTaticList))
            .append(ToStringUtils.unfoldCollectionIfNeeded(", positionTradeSchemeList=", positionTradeSchemeList,
                unfoldPositionTradeSchemeList))
            .append("] PlanTradeScheme toString End")
            .toString();
    }

}
