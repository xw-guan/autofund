package com.xwguan.autofund.dto.account;

import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.account.TradeDetail;
import com.xwguan.autofund.enums.AccountOwnerTypeEnum;

public class LatestAccountDto {
    /**
     * 账户id
     */
    private Long id;

    /**
     * 所有者id
     */
    private Long ownerId;

    /**
     * 所有者类型
     */
    private AccountOwnerTypeEnum ownerType;

    /**
     * 资产历史
     */
    private AssetHistory latestAssetHistory;

    /**
     * 交易详情历史
     */
    private TradeDetail latestTradeDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public AccountOwnerTypeEnum getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(AccountOwnerTypeEnum ownerType) {
        this.ownerType = ownerType;
    }

    public AssetHistory getLatestAssetHistory() {
        return latestAssetHistory;
    }

    public void setLatestAssetHistory(AssetHistory latestAssetHistory) {
        this.latestAssetHistory = latestAssetHistory;
    }

    public TradeDetail getLatestTradeDetail() {
        return latestTradeDetail;
    }

    public void setLatestTradeDetail(TradeDetail latestTradeDetail) {
        this.latestTradeDetail = latestTradeDetail;
    }

    @Override
    public String toString() {
        return "LatestAccountDto [id=" + id + ", ownerId=" + ownerId + ", ownerType=" + ownerType
            + ", latestAssetHistory=" + latestAssetHistory + ", latestTradeDetail=" + latestTradeDetail + "]";
    }

}
