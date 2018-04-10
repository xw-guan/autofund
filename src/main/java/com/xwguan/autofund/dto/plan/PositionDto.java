package com.xwguan.autofund.dto.plan;

import javax.validation.constraints.NotNull;

/**
 * 用于创建和修改
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-04-03
 */
public class PositionDto {
    /**
     * 持仓id
     */
    private Long id;

    /**
     * 从属的计划id
     */
    @NotNull
    private Long planId;

    /**
     * 持仓基金id
     */
    @NotNull
    private Integer fundId;

    /**
     * 持仓基金代码
     */
    @NotNull
    private String fundCode;

    /**
     * 基金名称, 可能相同
     */
    private String fundName;

    /**
     * 参考指数id
     */
    @NotNull
    private Integer refIndexId;

    /**
     * 参考指数代码
     */
    @NotNull
    private String refIndexCode;

    /**
     * 参考指数名称
     */
    private String refIndexName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public Integer getRefIndexId() {
        return refIndexId;
    }

    public void setRefIndexId(Integer refIndexId) {
        this.refIndexId = refIndexId;
    }

    public String getRefIndexCode() {
        return refIndexCode;
    }

    public void setRefIndexCode(String refIndexCode) {
        this.refIndexCode = refIndexCode;
    }

    public String getRefIndexName() {
        return refIndexName;
    }

    public void setRefIndexName(String refIndexName) {
        this.refIndexName = refIndexName;
    }

    @Override
    public String toString() {
        return "PositionDto [id=" + id + ", planId=" + planId + ", fundId=" + fundId + ", fundCode=" + fundCode
            + ", fundName=" + fundName + ", refIndexId=" + refIndexId + ", refIndexCode=" + refIndexCode
            + ", refIndexName=" + refIndexName + "]";
    }

}
