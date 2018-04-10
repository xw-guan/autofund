package com.xwguan.autofund.service.handler.tactic;

import java.util.Objects;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.exception.handler.EntitiesNotMatchException;

/**
 * 应用于计划的持仓策略处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-18
 */
public abstract class AbstractPlanTacticHandler extends AbstractTacticHandler<PlanTactic> implements PlanTacticHandler {

    /**
     * 计划
     */
    private Plan plan;

    public AbstractPlanTacticHandler() {
        super();
    }

    @Override
    public PlanTacticHandler handle(PlanTactic tactic, Plan plan) {
        super.handle(tactic);
        return handle(plan);
    }

    @Override
    public PlanTacticHandler handle(Plan plan)  {
        this.plan = Objects.requireNonNull(plan);
        throwExceptionIfEntitiesNotMatch();
        return this;
    }

    /**
     * 判断plan和tactic是否不匹配, 即tactic所属的计划id和plan的id是否不相同
     * 
     * @return true: 不匹配, false: 匹配
     */
    private boolean isPlanTacticNotMatch() {
        return plan.getId() == null || getTactic().getPlanId() == null
            || !plan.getId().equals(getTactic().getPlanId());
    }

    private void throwExceptionIfEntitiesNotMatch() {
        if (isPlanTacticNotMatch()) {
            throw new EntitiesNotMatchException(
                "planIds of plan (" + plan.getId() + ") and tactic (" + getTactic().getPlanId() + ") not match");
        }
    }
    
    @Override
    public PlanTactic getTactic() {
        return getEntity();
    }

    public Plan getPlan() {
        return plan;
    }

}
