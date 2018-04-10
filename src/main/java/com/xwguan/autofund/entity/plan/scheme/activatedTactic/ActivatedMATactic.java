package com.xwguan.autofund.entity.plan.scheme.activatedTactic;

import com.xwguan.autofund.enums.MAEnum;

/**
 * 激活的MA策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-17
 */
public class ActivatedMATactic extends ActivatedTactic {

    /**
     * 指数值
     */
    private Double indexValue;

    /**
     * N日均线
     */
    private MAEnum ma;

    /**
     * ma值
     */
    private Double maValue;

    /**
     * 指数和ma值差距百分比
     */
    private Double deviationPct;

    public Double getIndexValue() {
        return indexValue;
    }

    public void setIndexValue(Double indexValue) {
        this.indexValue = indexValue;
    }

    public MAEnum getMa() {
        return ma;
    }

    public void setMa(MAEnum ma) {
        this.ma = ma;
    }

    public Double getMaValue() {
        return maValue;
    }

    public void setMaValue(Double maValue) {
        this.maValue = maValue;
    }

    public Double getDeviationPct() {
        return deviationPct;
    }

    public void setDeviationPct(Double comparePct) {
        this.deviationPct = comparePct;
    }

    @Override
    public String toString() {
        return "ActivatedMATactic [indexValue=" + indexValue + ", ma=" + ma + ", maValue=" + maValue + ", deviationPct="
            + deviationPct + ", periodCondition()=" + getPeriodCondition() + ", rangeCondition()=" + getRangeCondition()
            + ", operation()=" + getOperation() + "]";
    }

}
