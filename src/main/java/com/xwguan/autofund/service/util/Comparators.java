package com.xwguan.autofund.service.util;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.xwguan.autofund.entity.common.Historical;
import com.xwguan.autofund.entity.fund.AssetAllocation;
import com.xwguan.autofund.entity.fund.Fund;
import com.xwguan.autofund.entity.fund.FundCompany;
import com.xwguan.autofund.entity.fund.FundManager;
import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.entity.plan.rule.RangeCondition;

/**
 * Comparator工厂类
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-22
 */
public class Comparators {
    /**
     * 比较两个{@code Fund}对象的{@code String code}字段, {@code null}被认为是最小值
     * <ul>
     * <li>{@code int = 0}, if {@code t1.code} is equal to {@code t2.code} (or both {@code null})</li>
     * <li>{@code int < 0}, if {@code t1.code} is less than {@code t2.code}</li>
     * <li>{@code int > 0}, if {@code t1.code} is greater than {@code t2.code}</li>
     * </ul>
     * 
     * @see org.apache.commons.lang3.StringUtils
     * @param t1 不为null的Fund对象
     * @param t2 不为null的Fund对象
     * @return 当{@code t1.code}等于(或都为{@code null}), 小于, 大于{@code t2.code}时, 返回值分别等于0, 小于0, 大于0
     */
    public static final Comparator<Fund> FUND_CODE_COMPARATOR = (t1, t2) -> StringUtils
        .compare(t1.getCode(), t2.getCode());

    /**
     * 比较两个{@code FundCompany}对象的{@code String code}字段, {@code null}被认为是最小值
     * <ul>
     * <li>{@code int = 0}, if {@code t1.code} is equal to {@code t2.code} (or both {@code null})</li>
     * <li>{@code int < 0}, if {@code t1.code} is less than {@code t2.code}</li>
     * <li>{@code int > 0}, if {@code t1.code} is greater than {@code t2.code}</li>
     * </ul>
     * 
     * @see org.apache.commons.lang3.StringUtils
     * @param t1 不为null的FundCompany对象
     * @param t2 不为null的FundCompany对象
     * @return 当{@code t1.code}等于(或都为{@code null}), 小于, 大于{@code t2.code}时, 返回值分别等于0, 小于0, 大于0
     */
    public static final Comparator<FundCompany> FUND_COMPANY_CODE_COMPARATOR = (t1, t2) -> StringUtils
        .compare(t1.getCode(), t2.getCode());

    /**
     * 比较两个{@code FundManager}对象的{@code String code}字段, {@code null}被认为是最小值
     * <ul>
     * <li>{@code int = 0}, if {@code t1.code} is equal to {@code t2.code} (or both {@code null})</li>
     * <li>{@code int < 0}, if {@code t1.code} is less than {@code t2.code}</li>
     * <li>{@code int > 0}, if {@code t1.code} is greater than {@code t2.code}</li>
     * </ul>
     * 
     * @see org.apache.commons.lang3.StringUtils
     * @param t1 不为null的FundManager对象
     * @param t2 不为null的FundManager对象
     * @return 当{@code t1.code}等于(或都为{@code null}), 小于, 大于{@code t2.code}时, 返回值分别等于0, 小于0, 大于0
     */
    public static final Comparator<FundManager> FUND_MANAGER_CODE_COMPARATOR = (t1, t2) -> StringUtils
        .compare(t1.getCode(), t2.getCode());

    /**
     * 比较两个{@code AssetAllocation}对象的{@code Integer fundId}字段, {@code null}被认为是最小值
     * <ul>
     * <li>{@code int = 0}, if {@code t1.code} is equal to {@code t2.code} (or both {@code null})</li>
     * <li>{@code int < 0}, if {@code t1.code} is less than {@code t2.code}</li>
     * <li>{@code int > 0}, if {@code t1.code} is greater than {@code t2.code}</li>
     * </ul>
     * 
     * @see org.apache.commons.lang3.StringUtils
     * @param t1 不为null的AssetAllocation对象
     * @param t2 不为null的AssetAllocation对象
     * @return 当{@code t1.fundId}等于(或都为{@code null}), 小于, 大于{@code t2.fundId}时, 返回值分别等于0, 小于0, 大于0
     */
    public static final Comparator<AssetAllocation> ASSET_ALLOCATION_FUND_ID_COMPARATOR = (t1, t2) -> {
        Integer fundId1 = t1.getFundId();
        Integer fundId2 = t2.getFundId();
        if (fundId1 == fundId2) {
            return 0;
        }
        if (fundId1 == null) {
            return -1;
        }
        if (fundId2 == null) {
            return 1;
        }
        return fundId1.compareTo(fundId2);
    };

    /**
     * 比较两个{@code Operation}对象的{@code Double tradeValue}字段, {@code null}被认为是0
     * 
     * @param t1 不为null的Operation对象
     * @param t2 不为null的Operation对象
     */
    public static final Comparator<Operation> OPERATION_TRADE_VALUE_COMPARATOR = (t1, t2) -> {
        Double tradeValue1 = t1.getTradeValue();
        Double tradeValue2 = t2.getTradeValue();
        if (tradeValue1 == tradeValue2) {
            return 0;
        }
        if (tradeValue1 == null) {
            tradeValue1 = 0D;
            t1.setTradeValue(tradeValue1);
        }
        if (tradeValue2 == null) {
            tradeValue2 = 0D;
            t2.setTradeValue(tradeValue2);
        }
        return tradeValue1.compareTo(tradeValue2);
    };

    /**
     * 比较两个{@code Operation}对象的{@code Double tradeValue}字段, {@code null}被认为是最小值
     * 
     * @param t1 不为null的RangeCondition对象
     * @param t2 不为null的RangeCondition对象
     */
    public static final Comparator<RangeCondition> RANGE_CONDITION_LEFT_BOUNDARY_COMPARATOR = (t1, t2) -> {
        Double boundaryLeft1 = t1.getBoundaryLeft();
        Double boundaryLeft2 = t2.getBoundaryLeft();
        if (boundaryLeft1 == boundaryLeft2) {
            return 0;
        }
        if (boundaryLeft1 == null) {
            return -1;
        }
        if (boundaryLeft2 == null) {
            return 1;
        }
        return boundaryLeft1.compareTo(boundaryLeft2);
    };

    /**
     * 比较两个{@code Historical}对象的{@code LocalDate getDate()}方法的返回值, 
     * 若任一{@code LocalDate getDate()}的返回值为{@code null}抛NPE
     * 
     * @param h1 不为null且getDate方法返回值不为null的Historical对象
     * @param h2 不为null且getDate方法返回值不为null的Historical对象
     */
    public static final Comparator<Historical> HISTORICAL_DATE_COMPARATOR = (h1, h2) -> {
        LocalDate d1 = h1.getDate();
        LocalDate d2 = h2.getDate();
        Objects.requireNonNull(d1);
        Objects.requireNonNull(d2);
        return d1.compareTo(d2);
    };

}
