package com.xwguan.autofund.entity.account;

import java.time.LocalDate;

import com.xwguan.autofund.entity.common.Historical;

/**
 * 资产历史
 * <p>相关计算公式
 * <ul>
 * <li>持仓资产 = 持仓份额 * 单位净值
 * <li>持仓收益 = 持仓资产 - 持仓成本
 * <li>持仓成本 = 总买入 - 总卖出 + 历史总收益 || P持仓成本 + 买入 || P持仓成本 - 因卖出减少持仓的成本
 * <p>注: 为方便计算, 默认红利再投资, 因此不必减去总分红
 * <li>因卖出减少的持仓成本 = P持仓成本价 * 卖出份额 || 卖出金额 - 卖出收益(卖出收益计入历史总收益而不是减少成本)
 * <li>卖出收益 = 卖出份额 * (单位净值 - P持仓成本价)
 * <li>历史总收益 = P历史总收益 + 卖出收益
 * <li>持仓成本价 = 持仓成本 / 持仓份额
 * <li>持仓收益率 = 持仓收益 / 持仓成本
 * <li>总成本 = 总买入
 * <li>累计总收益 = 历史总收益 + 持仓收益
 * <li>累计收益率 = 累计总收益 / 总成本
 * </ul>
 * 注: P(previous)表示前一交易日值
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public class AssetHistory implements Historical {

    /**
     * 资产历史id
     */
    private Long id;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 持有资产(元)
     */
    private Double posAsset;

    /**
     * 持有收益(元)
     */
    private Double posIncome;

    /**
     * 持有收益率(百分比)
     */
    private Double posIncomeRatePct;

    /**
     * 持仓成本(元)
     */
    private Double posCost;

    /**
     * 持仓份额
     */
    private Double posShare;

    /**
     * 持仓成本价(单位持仓成本)
     */
    private Double posPrice;

    /**
     * 累计总成本(元)
     */
    private Double totalCost;

    /**
     * 累计总收益(元), 包含持有收益和历史总收益
     */
    private Double totalIncome;

    /**
     * 累计收益率(百分比)
     */
    private Double totalIncomeRatePct;

    /**
     * 历史总收益(元), 不包含持有收益
     */
    private Double totalHistoryIncome;

    public AssetHistory() {
        super();
    }

    public AssetHistory(Long id, Long accountId, LocalDate date, Double posAsset, Double posIncome,
        Double posIncomeRatePct, Double posCost, Double posShare, Double posPrice, Double totalCost, Double totalIncome,
        Double totalIncomeRatePct, Double totalHistoryIncome) {
        this.id = id;
        this.accountId = accountId;
        this.date = date;
        this.posAsset = posAsset;
        this.posIncome = posIncome;
        this.posIncomeRatePct = posIncomeRatePct;
        this.posCost = posCost;
        this.posShare = posShare;
        this.posPrice = posPrice;
        this.totalCost = totalCost;
        this.totalIncome = totalIncome;
        this.totalIncomeRatePct = totalIncomeRatePct;
        this.totalHistoryIncome = totalHistoryIncome;
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

    public Double getPosAsset() {
        return posAsset;
    }

    public void setPosAsset(Double posAsset) {
        this.posAsset = posAsset;
    }

    public Double getPosIncome() {
        return posIncome;
    }

    public void setPosIncome(Double posIncome) {
        this.posIncome = posIncome;
    }

    public Double getPosIncomeRatePct() {
        return posIncomeRatePct;
    }

    public void setPosIncomeRatePct(Double posIncomeRatePct) {
        this.posIncomeRatePct = posIncomeRatePct;
    }

    public Double getPosCost() {
        return posCost;
    }

    public void setPosCost(Double posCost) {
        this.posCost = posCost;
    }

    public Double getPosShare() {
        return posShare;
    }

    public void setPosShare(Double posShare) {
        this.posShare = posShare;
    }

    public Double getPosPrice() {
        return posPrice;
    }

    public void setPosPrice(Double posPrice) {
        this.posPrice = posPrice;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalIncomeRatePct() {
        return totalIncomeRatePct;
    }

    public void setTotalIncomeRatePct(Double totalIncomeRatePct) {
        this.totalIncomeRatePct = totalIncomeRatePct;
    }

    public Double getTotalHistoryIncome() {
        return totalHistoryIncome;
    }

    public void setTotalHistoryIncome(Double totalHistoryIncome) {
        this.totalHistoryIncome = totalHistoryIncome;
    }

    @Override
    public String toString() {
        return "AssetHistory [id=" + id + ", accountId=" + accountId + ", date=" + date + ", posAsset=" + posAsset
            + ", posIncome=" + posIncome + ", posIncomeRatePct=" + posIncomeRatePct + ", posCost=" + posCost
            + ", posShare=" + posShare + ", posPrice=" + posPrice + ", totalCost=" + totalCost + ", totalIncome="
            + totalIncome + ", totalIncomeRatePct=" + totalIncomeRatePct + ", totalHistoryIncome=" + totalHistoryIncome
            + "]";
    }

}
