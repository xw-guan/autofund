package com.xwguan.autofund.entity.plan.scheme.activatedTactic;

/**
 * 激活的计划止盈策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-17
 */
public class ActivatedProfitTakingTactic extends ActivatedTactic {

    /**
     * 持有收入百分比
     */
    private Double posIncomeRatePct;

    public ActivatedProfitTakingTactic() {
        super();
    }

    public Double getPosIncomeRatePct() {
        return posIncomeRatePct;
    }

    public void setPosIncomeRatePct(Double posIncomeRatePct) {
        this.posIncomeRatePct = posIncomeRatePct;
    }

}
