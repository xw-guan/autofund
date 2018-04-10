package com.xwguan.autofund.dto.fund;

import com.xwguan.autofund.entity.fund.FundHistory;

public class LatestFundDto {
    private Integer id;

    /**
     * 基金代码, 唯一
     */
    private String code;

    /**
     * 基金名称, 可能相同
     */
    private String name;

    /**
     * 基金历史数据, 包含净值等
     */
    private FundHistory latestFundHistory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FundHistory getLatestFundHistory() {
        return latestFundHistory;
    }

    public void setLatestFundHistory(FundHistory latestFundHistory) {
        this.latestFundHistory = latestFundHistory;
    }

    @Override
    public String toString() {
        return "LatestFundDto [id=" + id + ", code=" + code + ", name=" + name + ", latestFundHistory="
            + latestFundHistory + "]";
    }

}
