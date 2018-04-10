package com.xwguan.autofund.entity.fund;

import java.time.LocalDate;

import com.xwguan.autofund.entity.common.Historical;

/**
 * 基金历史数据
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-05
 */
public class FundHistory implements Historical{

    private Long id;

    /**
     * 对应基金在数据库中的id
     */
    private Integer fundId;

    /**
     * 数据日期
     */
    private LocalDate date;

    /**
     * 净值net assert value
     */
    private Double nav;

    /**
     * 累积净值accumulated net assert value
     */
    private Double accNav;

    /**
     * 净值回报(%), 红利再投资
     */
    private Double equityReturnPct;

    /**
     * 每份派送金
     */
    private String unitMoney;

    public FundHistory() {
        super();
    }

    public FundHistory(LocalDate date, Double nav, Double accNav, Double equityReturnPct, String unitMoney) {
        this.date = date;
        this.nav = nav;
        this.accNav = accNav;
        this.equityReturnPct = equityReturnPct;
        this.unitMoney = unitMoney;
    }

    public FundHistory(Integer fundId, LocalDate date, Double nav, Double accNav, Double equityReturnPct,
        String unitMoney) {
        this.fundId = fundId;
        this.date = date;
        this.nav = nav;
        this.accNav = accNav;
        this.equityReturnPct = equityReturnPct;
        this.unitMoney = unitMoney;
    }

    public FundHistory(Long id, Integer fundId, LocalDate date, Double nav, Double accNav, Double equityReturnPct,
        String unitMoney) {
        this.id = id;
        this.fundId = fundId;
        this.date = date;
        this.nav = nav;
        this.accNav = accNav;
        this.equityReturnPct = equityReturnPct;
        this.unitMoney = unitMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFundId() {
        return fundId;
    }

    public void setFundId(Integer fundId) {
        this.fundId = fundId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getNav() {
        return nav;
    }

    public void setNav(Double nav) {
        this.nav = nav;
    }

    public Double getAccNav() {
        return accNav;
    }

    public void setAccNav(Double accNav) {
        this.accNav = accNav;
    }

    public Double getEquityReturnPct() {
        return equityReturnPct;
    }

    public void setEquityReturnPct(Double equityReturnPct) {
        this.equityReturnPct = equityReturnPct;
    }

    public String getUnitMoney() {
        return unitMoney;
    }

    public void setUnitMoney(String unitMoney) {
        this.unitMoney = unitMoney;
    }

    @Override
    public String toString() {
        return "FundHistory [id=" + id + ", fundId=" + fundId + ", date=" + date + ", nav=" + nav + ", accNav=" + accNav
            + ", equityReturnPct=" + equityReturnPct + ", unitMoney=" + unitMoney + "]";
    }

}
