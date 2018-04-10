package com.xwguan.autofund.entity.account;

import java.util.List;

import com.xwguan.autofund.enums.AccountOwnerTypeEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.account.AccountHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 账户
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public class Account implements Handleable {

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
    private List<AssetHistory> assetHistoryList;

    /**
     * 交易详情历史
     */
    private List<TradeDetail> tradeDetailList;

    public Account() {
        super();
    }

    public Account(Long ownerId, AccountOwnerTypeEnum ownerType, List<AssetHistory> assetHistoryList,
        List<TradeDetail> tradeDetailList) {
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.assetHistoryList = assetHistoryList;
        this.tradeDetailList = tradeDetailList;
    }

    public Account(Long id, Long ownerId, AccountOwnerTypeEnum ownerType, List<AssetHistory> assetHistoryList,
        List<TradeDetail> tradeDetailList) {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerType = ownerType;
        this.assetHistoryList = assetHistoryList;
        this.tradeDetailList = tradeDetailList;
    }

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

    public List<AssetHistory> getAssetHistoryList() {
        return assetHistoryList;
    }

    public void setAssetHistoryList(List<AssetHistory> assetHistoryList) {
        this.assetHistoryList = assetHistoryList;
    }

    public List<TradeDetail> getTradeDetailList() {
        return tradeDetailList;
    }

    public void setTradeDetailList(List<TradeDetail> tradeDetailList) {
        this.tradeDetailList = tradeDetailList;
    }

    @Override
    public Class<AccountHandler> handlerClass() {
        return AccountHandler.class;
    }

    @Override
    public AccountHandler handler() {
        return IocUtils.getBean(handlerClass()).handle(this);
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", ownerId=" + ownerId + ", ownerType=" + ownerType + ", assetHistoryList="
            + assetHistoryList + ", tradeDetailList=" + tradeDetailList + "]";
    }

}
