package com.xwguan.autofund.entity.fund;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 基金实时估值
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-21
 */
public class RealTimeFundData {
    /**
     * 对应Stock对象在数据库中的主键
     */
    private Integer fundId;

    /**
     * 股票代码
     */
    private String fundCode;

    /**
     * 交易日日期
     */
    private LocalDate date;

    /**
     * 时间
     */
    private LocalTime time;

    /**
     * 净值实时估值
     */
    private Double estimatedNav;

    /**
     * 估值变化率百分比
     */
    private Double estimatedChangeRatePct;

    public RealTimeFundData() {
        super();
    }

    public RealTimeFundData(String fundCode, LocalDate date, LocalTime time, Double estimatedNav,
        Double estimatedChangeRatePct) {
        this.fundCode = fundCode;
        this.date = date;
        this.time = time;
        this.estimatedNav = estimatedNav;
        this.estimatedChangeRatePct = estimatedChangeRatePct;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Double getEstimatedNav() {
        return estimatedNav;
    }

    public void setEstimatedNav(Double estimatedNav) {
        this.estimatedNav = estimatedNav;
    }

    public Double getEstimatedChangeRatePct() {
        return estimatedChangeRatePct;
    }

    public void setEstimatedChangeRatePct(Double estimatedChangeRatePct) {
        this.estimatedChangeRatePct = estimatedChangeRatePct;
    }

    @Override
    public String toString() {
        return "RealTimeFundData [fundId=" + fundId + ", fundCode=" + fundCode + ", date=" + date + ", time=" + time
            + ", estimatedNav=" + estimatedNav + ", estimatedChangeRatePct=" + estimatedChangeRatePct + "]";
    }

}
