package com.xwguan.autofund.entity.plan.backtest;

import java.time.LocalDate;

/**
 * 回撤
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-03
 */
public class Drawdown {
    /**
     * 最大回撤高点日期
     */
    private LocalDate highDate;

    /**
     * 最大回撤低点日期
     */
    private LocalDate lowDate;

    /**
     * 最大回撤率(%)
     */
    private Double drawdownPct;

    public Drawdown() {
        super();
    }

    public Drawdown(LocalDate highDate, LocalDate lowDate, Double drawdownPct) {
        this.highDate = highDate;
        this.lowDate = lowDate;
        this.drawdownPct = drawdownPct;
    }

    public LocalDate getHighDate() {
        return highDate;
    }

    public void setHighDate(LocalDate highDate) {
        this.highDate = highDate;
    }

    public LocalDate getLowDate() {
        return lowDate;
    }

    public void setLowDate(LocalDate lowDate) {
        this.lowDate = lowDate;
    }

    public Double getDrawdownPct() {
        return drawdownPct;
    }

    public void setDrawdownPct(Double drawdownPct) {
        this.drawdownPct = drawdownPct;
    }

    @Override
    public String toString() {
        return "Drawdown [highDate=" + highDate + ", lowDate=" + lowDate + ", drawdownPct=" + drawdownPct + "]";
    }

}
