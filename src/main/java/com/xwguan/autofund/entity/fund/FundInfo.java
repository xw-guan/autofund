package com.xwguan.autofund.entity.fund;

import java.time.LocalDate;

/**
 * 基金信息
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-25
 */
public class FundInfo {

    private Integer id;

    /**
     * 所属基金代码
     */
    private Integer fundId;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 当前经理代码
     */
    private String managerCode;

    /**
     * 当前经理工作时间
     */
    private String managerWorkTime;

    /**
     * 所属基金公司代码
     */
    private String companyCode;

    public FundInfo() {
        super();
    }

    public FundInfo(LocalDate date, String managerCode, String managerWorkTime, String companyCode) {
        this.date = date;
        this.managerCode = managerCode;
        this.managerWorkTime = managerWorkTime;
        this.companyCode = companyCode;
    }

    public FundInfo(Integer fundId, LocalDate date, String managerCode, String managerWorkTime, String companyCode) {
        this.fundId = fundId;
        this.date = date;
        this.managerCode = managerCode;
        this.managerWorkTime = managerWorkTime;
        this.companyCode = companyCode;
    }

    public FundInfo(Integer id, Integer fundId, LocalDate date, String managerCode, String managerWorkTime,
        String companyCode) {
        this.id = id;
        this.fundId = fundId;
        this.date = date;
        this.managerCode = managerCode;
        this.managerWorkTime = managerWorkTime;
        this.companyCode = companyCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getManagerWorkTime() {
        return managerWorkTime;
    }

    public void setManagerWorkTime(String managerWorkTime) {
        this.managerWorkTime = managerWorkTime;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    @Override
    public String toString() {
        return "FundInfo [id=" + id + ", fundId=" + fundId + ", date=" + date + ", managerCode=" + managerCode
            + ", managerWorkTime=" + managerWorkTime + ", companyCode=" + companyCode + "]";
    }

}
