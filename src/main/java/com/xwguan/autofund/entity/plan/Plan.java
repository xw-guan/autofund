package com.xwguan.autofund.entity.plan;

import java.util.List;

import com.xwguan.autofund.entity.account.Account;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.enums.PlanExecutionModeEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.plan.PlanHandler;
import com.xwguan.autofund.util.IocUtils;
import com.xwguan.autofund.util.ToStringUtils;

/**
 * 定投计划
 * 
 * @author XWGuan
 * @version 0.1 2017-10-09
 */
public class Plan implements Handleable {

    /**
     * 计划id, 对应数据库中的id
     */
    private Long id;

    /**
     * 从属用户的id;
     */
    private Long userId;

    /**
     * 计划名称
     */
    private String name;

    /**
     * 账户
     */
    private Account account;

    /**
     * 持仓列表
     */
    private List<Position> positionList;

    /**
     * 计划策略列表, 这些策略优先级最高, 对整个计划的账户进行操作
     */
    private List<PlanTactic> planTacticList;

    /**
     * 持仓策略列表, 这些策略对每一个持仓应用
     */
    private List<PositionTactic> positionTacticList;

    /**
     * 计划历史列表
     */
    private List<PlanHistory> planHistoryList;

    /**
     * 计划执行模式
     */
    private PlanExecutionModeEnum executionMode;

    /**
     * 计划是否为激活状态
     */
    private Boolean activated;

    public Plan() {
        super();
    }

    public Plan(Long id, Long userId, String name, Account account, List<Position> positionList,
        List<PlanTactic> planTacticList, List<PositionTactic> positionTacticList, List<PlanHistory> planHistoryList,
        PlanExecutionModeEnum executionMode, Boolean activated) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.account = account;
        this.positionList = positionList;
        this.planTacticList = planTacticList;
        this.positionTacticList = positionTacticList;
        this.planHistoryList = planHistoryList;
        this.executionMode = executionMode;
        this.activated = activated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

    public List<PlanTactic> getPlanTacticList() {
        return planTacticList;
    }

    public void setPlanTacticList(List<PlanTactic> planTacticList) {
        this.planTacticList = planTacticList;
    }

    public List<PositionTactic> getPositionTacticList() {
        return positionTacticList;
    }

    public void setPositionTacticList(List<PositionTactic> positionTacticList) {
        this.positionTacticList = positionTacticList;
    }

    public PlanExecutionModeEnum getExecutionMode() {
        return executionMode;
    }

    public void setExecutionMode(PlanExecutionModeEnum executionMode) {
        this.executionMode = executionMode;
    }

    public List<PlanHistory> getPlanHistoryList() {
        return planHistoryList;
    }

    public void setPlanHistoryList(List<PlanHistory> planHistoryList) {
        this.planHistoryList = planHistoryList;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    @Override
    public String toString() {
        return toString(true, true, true, true);
    }

    public String toString(boolean unfoldPositionList, boolean unfoldPlanTacticList, boolean unfoldPositionTacticList,
        boolean unfordPlanHistoryList) {
        return new StringBuilder("Plan [")
            .append("id=").append(id)
            .append(", userId=").append(userId)
            .append(", name=").append(name)
            .append(", account=").append(account)
            .append(", executionMode=").append(executionMode)
            .append(", activated=").append(activated)
            .append(ToStringUtils.unfoldCollectionIfNeeded(",\npositionList=", positionList, unfoldPositionList))
            .append(ToStringUtils.unfoldCollectionIfNeeded(",\nplanTacticList=", planTacticList, unfoldPlanTacticList))
            .append(ToStringUtils.unfoldCollectionIfNeeded(",\npositionTacticList=", positionTacticList,
                unfoldPositionTacticList))
            .append(
                ToStringUtils.unfoldCollectionIfNeeded(",\nplanHistoryList=", planHistoryList, unfordPlanHistoryList))
            .append("\n] Plan toString End")
            .toString();
    }

    @Override
    public Class<PlanHandler> handlerClass() {
        return PlanHandler.class;
    }

    @Override
    public PlanHandler handler() {
        return IocUtils.getBean(handlerClass()).handle(this);
    }

}
