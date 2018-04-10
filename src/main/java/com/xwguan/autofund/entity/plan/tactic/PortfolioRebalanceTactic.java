package com.xwguan.autofund.entity.plan.tactic;

import java.util.List;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.entity.plan.portfolio.PositionPropotion;
import com.xwguan.autofund.service.handler.EntityHandler;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.PlanTacticHandler;

/**
 * 投资组合再平衡策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-05
 */
@Unimplement
public class PortfolioRebalanceTactic extends PlanTactic implements Handleable {

    /**
     * 持仓比例列表
     */
    private List<PositionPropotion> positionPropotionList;

    public List<PositionPropotion> getPositionPropotionList() {
        return positionPropotionList;
    }

    public void setPositionPropotionList(List<PositionPropotion> positionPropotionList) {
        this.positionPropotionList = positionPropotionList;
    }

    @Override
    public Class<? extends EntityHandler<? extends Handleable>> handlerClass() {
        return null;
    }

    @Override
    public PlanTacticHandler handler() {
        // TODO Auto-generated method stub
        return null;
    }

}
