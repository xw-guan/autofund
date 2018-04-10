package com.xwguan.autofund.service.handler.tactic;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.tactic.PlanTactic;
import com.xwguan.autofund.exception.handler.EntitiesNotMatchException;

/**
 * 持仓策略处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
public interface PlanTacticHandler extends TacticHandler<PlanTactic> {

    /**
     * 处理计划策略
     * 
     * @param tactic 待处理的策略对象, 非null, 业务相关属性不能为null
     * @param plan 待处理的计划对象, 非null, account属性不能为null
     * @return
     * @throws EntitiesNotMatchException tactic和plan的id不匹配时
     */
    PlanTacticHandler handle(PlanTactic tactic, Plan plan) throws EntitiesNotMatchException;

    /**
     * 处理计划策略
     * 
     * @param plan 待处理的计划对象, 非null, account属性不能为null
     * @return
     * @throws EntitiesNotMatchException tactic和plan的id不匹配时
     */
    PlanTacticHandler handle(Plan plan) throws EntitiesNotMatchException;

    /**
     * 获取被处理的计划, 可能为null
     * 
     * @return
     */
    Plan getPlan();
}
