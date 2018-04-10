package com.xwguan.autofund.service.template.plan;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.RangeCondition;
import com.xwguan.autofund.enums.ConditionUnitEnum;

/**
 * 范围条件模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
@Component
public class RangeConditionTemplate {

    /**
     * 默认总是满足条件
     */
    public RangeCondition defaultRangeCondition() {
        return alwaysMet();
    }

    public List<RangeCondition> conditionsFromBaseAndInterval(double base, double interval,
        int plusTimes, int minusTimes, ConditionUnitEnum unit) {
        List<Double> valueList = generateValueListFromBaseAndInterval(base, interval, plusTimes, minusTimes);
        return conditionsFromValues(valueList, unit);
    }

    public List<RangeCondition> conditionsFromBaseAndIntervalWithMaxValue(double base, double interval,
        int plusTimes, int minusTimes, ConditionUnitEnum unit) {
        List<Double> valueList = generateValueListFromBaseAndInterval(base, interval, plusTimes, minusTimes);
        valueList.add(Double.MAX_VALUE);
        valueList.add(-Double.MAX_VALUE);
        return conditionsFromValues(valueList, unit);
    }

    public List<RangeCondition> conditionsFromValues(List<Double> values, ConditionUnitEnum unit) {
        List<RangeCondition> conditionList = new ArrayList<>();
        if (values.size() < 2) {
            values.add(Double.MAX_VALUE);
            values.add(-Double.MAX_VALUE);
        }
        values.sort(Double::compareTo);
        for (int i = 0; i < values.size() - 1; i++) {
            conditionList.add(of(values.get(i), values.get(i + 1), unit));
        }
        return conditionList;
    }

    public RangeCondition pct(double left, double right) {
        return of(left, right, ConditionUnitEnum.PERCENT);
    }

    public RangeCondition alwaysMet() {
        return of(Double.MAX_VALUE, -Double.MAX_VALUE, ConditionUnitEnum.PERCENT);
    }

    public RangeCondition of(double boundaryLeft, double boundaryRight, ConditionUnitEnum rangeUnit) {
        return new RangeCondition(-1L, -1L, boundaryLeft, boundaryRight, rangeUnit);
    }

    private List<Double> generateValueListFromBaseAndInterval(double base, double interval, int plusTimes,
        int minusTimes) {
        List<Double> valueList = new ArrayList<>();
        valueList.add(base);
        for (int i = 1; i <= plusTimes; i++) {
            valueList.add(base + interval * i);
        }
        for (int i = 1; i <= minusTimes; i++) {
            valueList.add(base - interval * i);
        }
        return valueList;
    }
}
