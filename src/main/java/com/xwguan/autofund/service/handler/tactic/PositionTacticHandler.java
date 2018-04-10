package com.xwguan.autofund.service.handler.tactic;

import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.tactic.PositionTactic;
import com.xwguan.autofund.exception.handler.EntitiesNotMatchException;

/**
 * 持仓策略处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
public interface PositionTacticHandler extends TacticHandler<PositionTactic> {
    /**
     * 处理持仓策略
     * 
     * @param tactic 待处理的策略对象, 非null, 业务相关属性不能为null
     * @param position 待处理的持仓对象, 非null, 业务相关属性不能为null
     * @return
     * @throws EntitiesNotMatchException tactic和position的id不匹配时
     */
    PositionTacticHandler handle(PositionTactic tactic, Position position) throws EntitiesNotMatchException;

    /**
     * 处理持仓策略
     * 
     * @param tactic 待处理的策略对象, 非null, 业务相关属性不能为null
     * @return
     * @throws EntitiesNotMatchException tactic和position的id不匹配时
     */
    PositionTacticHandler handle(Position position) throws EntitiesNotMatchException;

    /**
     * 获取被处理的持仓, 可能为null
     * 
     * @return
     */
    Position getPosition();
}
