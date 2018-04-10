package com.xwguan.autofund.entity.plan;

import java.time.LocalDate;

import com.xwguan.autofund.enums.PlanStateEnum;

/**
 * 计划历史, 每次计划状态改变时生成记录, 不每日记录
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-25
 */
public class PlanHistory {

    /**
     * 计划历史id
     */
    private Long id;

    /**
     * 所属计划id
     */
    private Long planId;

    /**
     * 计划开始日期, 指计划由中止状态(用户中止, 止盈中止)转为运行状态的日期, 不是计划创建日期
     */
    private LocalDate startDate;

    /**
     * 历史日期, 计划状态改变的日期
     */
    private LocalDate historyDate;

    /**
     * 计划状态
     */
    private PlanStateEnum planState;

    /**
     * 计划周期收入, 指计划在当前周期(开始至今)内的收入
     */
    private Double planPeriodIncome;

    /**
     * 计划总收入, 指计划创建以来至当前记录时间时的总收入
     */
    private Double planTotalIncome;

    public PlanHistory() {
        super();
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(LocalDate historyDate) {
        this.historyDate = historyDate;
    }

    public PlanStateEnum getPlanState() {
        return planState;
    }

    public void setPlanState(PlanStateEnum planState) {
        this.planState = planState;
    }

    public Double getPlanPeriodIncome() {
        return planPeriodIncome;
    }

    public void setPlanPeriodIncome(Double planPeriodIncome) {
        this.planPeriodIncome = planPeriodIncome;
    }

    public Double getPlanTotalIncome() {
        return planTotalIncome;
    }

    public void setPlanTotalIncome(Double planTotalIncome) {
        this.planTotalIncome = planTotalIncome;
    }

    @Override
    public String toString() {
        return "PlanHistory [id=" + id + ", planId=" + planId + ", startDate=" + startDate + ", historyDate="
            + historyDate + ", planState=" + planState + ", planPeriodIncome=" + planPeriodIncome + ", planTotalIncome="
            + planTotalIncome + "]";
    }

}
