package com.xwguan.autofund.service.handler;

import org.springframework.stereotype.Component;
import com.xwguan.autofund.util.IocUtils;

/**
 * 实体对象处理者工厂
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
@Component
@Deprecated
public class EntityHandlerFactory {

    public <U extends EntityHandler<? extends Handleable>> U getHandler(Class<U> handlerClass) {
        return IocUtils.getBean(handlerClass);
    }

//    /**
//     * 获取持仓策略处理者
//     * 
//     * @param tactic 策略
//     * @param position 持仓
//     * @return
//     * @throws EntitiesNotMatchException 持仓和策略的计划id不匹配
//     * @throws EntityAndHandlerNotMatchException
//     */
//    @Deprecated
//    public PositionTacticHandler handle(PositionTactic tactic, Position position)
//        throws EntitiesNotMatchException, EntityAndHandlerNotMatchException {
//        EntityHandler<? extends Handleable> handler = tactic.handler();
//        if (handler instanceof PositionTacticHandler) {
//            return ((PositionTacticHandler) handler).handle(tactic, position);
//        } else {
//            throw new EntityAndHandlerNotMatchException("The tactic is not a position tactic");
//        }
//    }

//    /**
//     * 获取计划策略处理者
//     * 
//     * @param tactic 策略
//     * @param plan 计划
//     * @return
//     * @throws EntitiesNotMatchException 计划的id和策略的计划id不匹配
//     * @throws EntityAndHandlerNotMatchException 未知Tactic类型
//     */
//    @Deprecated
//    public PlanTacticHandler handle(PlanTactic tactic, Plan plan)
//        throws EntitiesNotMatchException, EntityAndHandlerNotMatchException {
//        EntityHandler<? extends Handleable> handler = tactic.handler();
//        if (handler instanceof PlanTacticHandler) {
//            return ((PlanTacticHandler) handler).handle(tactic, plan);
//        } else {
//            throw new EntityAndHandlerNotMatchException("The tactic is not a plan tactic");
//        }
//    }

}
