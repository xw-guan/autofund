package com.xwguan.autofund.dto.plan;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.dto.fund.LatestFundDto;
import com.xwguan.autofund.dto.stock.LatestStockDto;

/**
 * 用于获取持仓信息
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-04-03
 */
public class LatestPositionDto {

    /**
     * 持仓id
     */
    private Long id;

    /**
     * 从属的计划id
     */
    private Long planId;

    private LatestAccountDto latestAccount;

    private LatestFundDto latestFund;

    private LatestStockDto latestRefIndex;

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

    public LatestAccountDto getLatestAccount() {
        return latestAccount;
    }

    public void setLatestAccount(LatestAccountDto latestAccount) {
        this.latestAccount = latestAccount;
    }

    public LatestFundDto getLatestFund() {
        return latestFund;
    }

    public void setLatestFund(LatestFundDto latestFund) {
        this.latestFund = latestFund;
    }

    public LatestStockDto getLatestRefIndex() {
        return latestRefIndex;
    }

    public void setLatestRefIndex(LatestStockDto latestRefIndex) {
        this.latestRefIndex = latestRefIndex;
    }

    @Override
    public String toString() {
        return "LatestPositionDto [id=" + id + ", planId=" + planId + ", latestAccount=" + latestAccount
            + ", latestFund=" + latestFund + ", latestRefIndex=" + latestRefIndex + "]";
    }

}
