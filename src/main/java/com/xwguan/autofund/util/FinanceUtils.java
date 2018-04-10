package com.xwguan.autofund.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.entity.plan.backtest.Drawdown;
import com.xwguan.autofund.enums.RoundScaleEnum;
import com.xwguan.autofund.exception.OverMaxIterationNumberException;

public class FinanceUtils {
    /*
     * <li>持仓资产 = 持仓份额 * 单位净值
     * <li>持仓收益 = 持仓资产 - 持仓成本
     * <li>持仓成本 = 总买入 - 总卖出 + 历史总收益 || P持仓成本 + 买入 || P持仓成本 - 因卖出减少的持仓成本
     * <p>注: 为方便计算, 默认红利再投资, 因此不必减去总分红
     * <li>因卖出减少持仓的成本 = 卖出份额 * P持仓成本价 || 卖出金额 - 卖出收益(卖出收益计入历史总收益而不是减少成本)
     * <li>卖出收益 = 卖出份额 * (单位净值 - P持仓成本价)
     * <li>历史总收益 = P历史总收益 + 卖出收益
     * <li>持仓成本价 = 持仓成本 / 持仓份额
     * <li>持仓收益率 = 持仓收益 / 持仓成本
     * <li>总成本 = 总买入
     * <li>累计总收益 = 历史总收益 + 持仓收益
     * <li>累计收益率 = 累计总收益 / 总成本
     * </ul>
     */

    /**
     * 持有资产, posShare * nav
     * 
     * @param posShare 持仓份额
     * @param nav 单位净值
     * @return 持有资产
     */
    public static double posAsset(double posShare, double nav) {
        return MathUtils.round(posShare * nav, RoundScaleEnum.MONEY_ROUND_SCALE);
    }

    /**
     * 持有收入, posAsset - posCost
     * 
     * @param posAsset 持有资产
     * @param posCost 持有成本
     * @return 持有收入
     */
    public static double posIncome(double posAsset, double posCost) {
        return MathUtils.round(posAsset - posCost, RoundScaleEnum.MONEY_ROUND_SCALE);
    }

    /**
     * 持有收益百分比, posIncome / posCost, posCost为0时持有收益率也为0
     * 
     * @param posIncome 持有收入
     * @param posCost 持有成本
     * @return 持有收益百分比
     */
    public static double posIncomeRatePct(double posIncome, double posCost) {
        return posCost != 0 ? MathUtils.decimalToPct(posIncome / posCost) : 0;
    }

    /**
     * 持有成本, buySum - sellSum
     * 
     * @param buySum 总买入, 非负值
     * @param sellSum 总卖出, 非负值
     * @return 持有成本
     */
    public static double posCost(double buySum, double sellSum, double totalHistoryIncome) {
        return MathUtils.round(buySum - sellSum + totalHistoryIncome, RoundScaleEnum.MONEY_ROUND_SCALE);
    }

    /**
     * 持有成本, prevCost + costChange
     * 
     * @param prevCost 此前的成本
     * @param costChange 成本变化量, 买入时是买入值, 卖出时是sellPosCostReduce(sellShare, prevPosPrice)的结果取负值
     * @return
     */
    public static double posCost(double prevCost, double costChange) {
        return MathUtils.round(prevCost + costChange, RoundScaleEnum.MONEY_ROUND_SCALE);
    }

    /**
     * 持仓成本价|单位持仓成本, posCost / posShare, posShare为0时持仓成本价也为0
     * 
     * @param posCost 持仓成本
     * @param posShare 持仓份额
     * @return 单位持仓成本
     */
    public static double posPrice(double posCost, double posShare) {
        return posShare != 0 ? MathUtils.round(posCost / posShare, RoundScaleEnum.NAV_ROUND_SCALE) : 0;
    }

    /**
     * 总收入, totalHistoryIncome + posIncome
     * 
     * @param totalHistoryIncome 历史总收入
     * @param posIncome 持有收入
     * @return 总收入
     */
    public static double totalIncome(double totalHistoryIncome, double posIncome) {
        return MathUtils.round(totalHistoryIncome + posIncome, RoundScaleEnum.MONEY_ROUND_SCALE);
    }

    /**
     * 总收益率, totalIncome / totalCost, totalCost为0时总收益率也为0
     * 
     * @param totalIncome 总收益
     * @param totalCost 总成本, 即总买入
     * @return 总收益率
     */
    public static double totalIncomePct(double totalIncome, double totalCost) {
        return totalCost != 0 ? MathUtils.decimalToPct(totalIncome / totalCost) : 0;
    }

    /**
     * 历史总收益, prevTotalHistoryIncome + sellIncome
     * 
     * @param prevTotalHistoryIncome 交易执行前的历史总收益
     * @param sellIncome 总卖出
     * @return
     */
    public static double totalHistoryIncome(double prevTotalHistoryIncome, double sellIncome) {
        return MathUtils.round(prevTotalHistoryIncome + sellIncome, RoundScaleEnum.MONEY_ROUND_SCALE);
    }

    /**
     * 卖出收益, sellShare * (nav - prevPosPrice), 若sellShare > 0, 则表示买入, 卖出收益为0
     * 
     * @param sellShare 卖出份额, 应小于0
     * @param prevPosPrice 交易执行前的持有成本价
     * @param nav 净值
     * @return 卖出收益
     */
    public static double sellIncome(double sellShare, double prevPosPrice, double nav) {
        return sellShare < 0
            ? MathUtils.round(sellShare * (nav - prevPosPrice), RoundScaleEnum.MONEY_ROUND_SCALE)
            : 0;
    }

    /**
     * 因卖出的持仓成本减少, 值>=0, sellShare * prevPosPrice, 若sellShare > 0, 则表示买入, 持仓成本减少为0
     * 
     * @param sellShare 卖出份额, 应小于0
     * @param prevPosPrice 交易前的持仓价格
     * @return
     */
    public static double sellPosCostReduce(double sellShare, double prevPosPrice) {
        return sellShare < 0
            ? MathUtils.round(sellShare * prevPosPrice, RoundScaleEnum.MONEY_ROUND_SCALE)
            : 0;
    }

    /**
     * xirr内部收益率(Internal Rate of Return), 用于表征年化收益率, 红利全部再投资, 不考虑交易费用和税收
     * 
     * @param cashFlows 现金流map, key为现金流日期, value为现金流, 必须包含至少一个正现金流和一个负现金流, 其中的值不能为null
     * @return xirr年化收益率
     * @throws OverMaxIterationNumberException 不收敛或超过最大迭代次数
     */
    public static double xirr(TreeMap<LocalDate, Double> cashFlows) throws OverMaxIterationNumberException {
        LocalDate firstDate = cashFlows.firstKey();
        long firstEDate = firstDate.toEpochDay();
        Set<LocalDate> dateSet = cashFlows.keySet();
        double daysOfYear = 365;
        Function<Double, Double> xnpv = rate -> {
            double fx = 0;
            for (LocalDate date : dateSet) {
                double payment = cashFlows.get(date);
                long eDate = date.toEpochDay();
                double fi = payment * Math.pow(1 + rate, (firstEDate - eDate) / daysOfYear);
                fx += fi;
            }
            return fx;
        };
        Function<Double, Double> dxnpv = rate -> {
            double fx = 0;
            for (LocalDate date : dateSet) {
                double payment = cashFlows.get(date);
                long eDate = date.toEpochDay();
                fx += payment * (firstEDate - eDate) / daysOfYear
                    * Math.pow(1 + rate, (firstEDate - eDate) / daysOfYear);
            }
            return fx;
        };
        double guess = 0.1;
        double accuracy = 0.000001;
        int maxIterationNum = 1000;
        return MathUtils.searchZeroPoint(guess, accuracy, maxIterationNum, xnpv, dxnpv);
    }

    /**
     * 最大回撤百分比
     * <p>注: 每天都投入导致每天的持有资产都是增加的情况下, 最大回撤永远是0
     * TODO 算法优化
     * 
     * @param posAssetHistories 持仓资产历史, key为日期, value为持仓资产, 其中的值不能为负或null
     * @return 最大回撤
     */
    public static Drawdown maxDrawdownPct(TreeMap<LocalDate, Double> posAssetHistories) {
        Drawdown maxDrawdown = new Drawdown(null, null, 0D);
        double drawdown = 0;
        LocalDate highDate = null;
        LocalDate lowDate = null;
        List<LocalDate> dates = new ArrayList<>(posAssetHistories.keySet());
        for (int i = 0; i < dates.size() - 1; i++) {
            highDate = dates.get(i);
            for (int j = i + 1; j < dates.size(); j++) {
                lowDate = dates.get(j);
                drawdown = 1 - posAssetHistories.get(lowDate) / posAssetHistories.get(highDate);
                if (drawdown > maxDrawdown.getDrawdownPct()) {
                    maxDrawdown.setDrawdownPct(drawdown); // 这里实际上是小数不是百分数
                    maxDrawdown.setHighDate(highDate);
                    maxDrawdown.setLowDate(lowDate);
                }
            }
        }
        maxDrawdown.setDrawdownPct(MathUtils.decimalToPct(maxDrawdown.getDrawdownPct()));
        return maxDrawdown;
    }

    /**
     * 精度0.000001的情况下num是否等于0
     * 
     * @param num
     * @return
     */
    public static boolean equalZero(double num) {
        return MathUtils.isApproximatelyEqualZero(num, 0.000001);
    }

    /**
     * 波动率
     * TODO 计算波动率是否有意义?
     * 
     * @param assetList
     * @return
     */
    @Unimplement
    public static double volatility(List<Double> assetList) {

        return 0;
    }

    @Unimplement
    public static double sharpeRatio() {

        return 0;
    }

    @Unimplement
    public static double alpha() {

        return 0;
    }

    @Unimplement
    public static double beta() {
        return 0;
    }
}
