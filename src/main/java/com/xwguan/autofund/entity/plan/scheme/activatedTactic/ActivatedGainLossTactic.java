package com.xwguan.autofund.entity.plan.scheme.activatedTactic;

/**
 * 激活的持仓盈亏策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-17
 */
public class ActivatedGainLossTactic extends ActivatedTactic {

    /**
     * 持仓盈亏百分比
     */
    private Double posIncomeRatePct;

    public ActivatedGainLossTactic() {
        super();
    }

    public Double getPosIncomeRatePct() {
        return posIncomeRatePct;
    }

    public void setPosIncomeRatePct(Double posIncomeRatePct) {
        this.posIncomeRatePct = posIncomeRatePct;
    }

}
