package com.xwguan.autofund.entity.plan.tactic;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.enums.TradeUnitEnum;
import com.xwguan.autofund.service.handler.EntityHandler;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.PlanTacticHandler;

/**
 * 价值平均策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-05
 */
@Unimplement
public class ValueAveragingTactic extends PlanTactic implements Handleable {

    /**
     * 每期资产增加值, 元
     */
    private Double assetIncrease;

    /**
     * 每期资产增加值的增加值, 元或%
     */
    private Double assetIncrIncr;

    /**
     * 每期资产增加值的增加值的单位, 取值只为元和%
     */
    private TradeUnitEnum assetIncrIncrUnit;

    /**
     * 买入上限, 元, 正值. null和其他值不应用
     */
    private Double buyLimit;

    /**
     * 卖出上限, 元, 负值. null和其他值不应用
     */
    private Double sellLimit;

    public ValueAveragingTactic() {
        super();
    }

    public Double getAssetIncrease() {
        return assetIncrease;
    }

    public void setAssetIncrease(Double assetIncrease) {
        this.assetIncrease = assetIncrease;
    }

    public Double getAssetIncreaseIncrease() {
        return assetIncrIncr;
    }

    public void setAssetIncreaseIncrease(Double assetIncrIncr) {
        this.assetIncrIncr = assetIncrIncr;
    }

    public TradeUnitEnum getAssetIncreaseIncreaseUnit() {
        return assetIncrIncrUnit;
    }

    public void setAssetIncreaseIncreaseUnit(TradeUnitEnum assetIncrIncrUnit) {
        this.assetIncrIncrUnit = assetIncrIncrUnit;
    }

    public Double getBuyLimit() {
        return buyLimit;
    }

    public void setBuyLimit(Double buyLimit) {
        this.buyLimit = buyLimit;
    }

    public Double getSellLimit() {
        return sellLimit;
    }

    public void setSellLimit(Double sellLimit) {
        this.sellLimit = sellLimit;
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
