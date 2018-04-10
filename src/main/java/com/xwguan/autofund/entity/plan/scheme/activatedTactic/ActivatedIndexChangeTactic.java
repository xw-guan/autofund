package com.xwguan.autofund.entity.plan.scheme.activatedTactic;

/**
 * 激活的指数变化策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-17
 */
public class ActivatedIndexChangeTactic extends ActivatedTactic {

    /**
     * 指数起始点值
     */
    private Double startValue;

    /**
     * 指数结束点值
     */
    private Double endValue;

    /**
     * 采样时间范围, 即在n个交易日内. 最小为1, 若值为null或小于1则设为1.
     */
    private Integer inTradeDays;

    /**
     * 指数变化百分比
     */
    private Double changePct;

    public ActivatedIndexChangeTactic() {
        super();
    }

    public Double getStartValue() {
        return startValue;
    }

    public void setStartValue(Double startValue) {
        this.startValue = startValue;
    }

    public Double getEndValue() {
        return endValue;
    }

    public void setEndValue(Double endValue) {
        this.endValue = endValue;
    }

    public Integer getInTradeDays() {
        return inTradeDays;
    }

    public void setInTradeDays(Integer inTradeDays) {
        this.inTradeDays = inTradeDays;
    }

    public Double getChangePct() {
        return changePct;
    }

    public void setChangePct(Double changePct) {
        this.changePct = changePct;
    }

    @Override
    public String toString() {
        return "ActivatedIndexChangeTactic [startValue=" + startValue + ", endValue=" + endValue + ", inTradeDays="
            + inTradeDays + ", changePct=" + changePct + ", toString()=" + super.toString() + "]";
    }

}
