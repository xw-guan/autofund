package com.xwguan.autofund.entity.plan.tactic;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.service.handler.EntityHandler;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.PlanTacticHandler;

/**
 * 交易上下限值策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-04-10
 */
@Unimplement
public class TradeLimitTactic extends PlanTactic {
    
    

    @Override
    public Class<? extends EntityHandler<? extends Handleable>> handlerClass() {
        return null;
    }

    @Override
    public PlanTacticHandler handler() {
        return null;
    }

}
