package com.xwguan.autofund.entity.account;

import java.time.LocalDate;

import com.xwguan.autofund.entity.common.Historical;
import com.xwguan.autofund.enums.TradeStateEnum;

/**
 * 交易历史
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-06
 */
public class TradeDetail implements Historical {

    /**
     * 交易历史id
     */
    private Long id;

    /**
     * 交易账户id
     */
    private Long accountId;

    /**
     * 执行日期
     */
    private LocalDate date;

    /**
     * 交易值(元), 正为买入, 负为卖出. 卖出值是卖出后的到账钱数, 需要输入, 因为手续费多少并不知道, 分红也计入此处
     */
    private Double tradeYuan;

    /**
     * 交易份额, 正为买入, 负为卖出. 买入份额是基金公司确认的扣除手续费后的份额, 需要输入, 因为手续费多少并不知道
     */
    private Double tradeShare;

    /**
     * 总买入, 正值, 根据买入值计算
     */
    private Double buySum;

    /**
     * 总卖出, 正值, 根据卖出值计算
     */
    private Double sellSum;

    /**
     * 交易状态
     */
    private TradeStateEnum tradeState;

    public TradeDetail() {
        super();
    }

    public TradeDetail(Long id, Long accountId, LocalDate date, Double tradeYuan, Double tradeShare, Double buySum,
        Double sellSum, TradeStateEnum tradeState) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.tradeYuan = tradeYuan;
        this.tradeShare = tradeShare;
        this.buySum = buySum;
        this.sellSum = sellSum;
        this.tradeState = tradeState;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getTradeYuan() {
        return tradeYuan;
    }

    public void setTradeYuan(Double tradeYuan) {
        this.tradeYuan = tradeYuan;
    }

    public Double getTradeShare() {
        return tradeShare;
    }

    public void setTradeShare(Double tradeShare) {
        this.tradeShare = tradeShare;
    }

    public Double getBuySum() {
        return buySum;
    }

    public void setBuySum(Double buySum) {
        this.buySum = buySum;
    }

    public Double getSellSum() {
        return sellSum;
    }

    public void setSellSum(Double sellSum) {
        this.sellSum = sellSum;
    }

    public TradeStateEnum getTradeState() {
        return tradeState;
    }

    public void setTradeState(TradeStateEnum tradeState) {
        this.tradeState = tradeState;
    }

    @Override
    public String toString() {
        return "TradeDetail [id=" + id + ", accountId=" + accountId + ", date=" + date + ", tradeYuan=" + tradeYuan
            + ", tradeShare=" + tradeShare + ", buySum=" + buySum + ", sellSum=" + sellSum + ", tradeState="
            + tradeState + "]";
    }

}
