package com.xwguan.autofund.service.template.plan;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.enums.TradeUnitEnum;

/**
 * 操作模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
@Component
public class OperationTemplate {

    /**
     * 默认不操作
     */
    public Operation defaultOperation() {
        return ignore();
    }

    public Operation sellAll() {
        return pct(-100);
    }

    public Operation ignore() {
        return pct(0);
    }

    public Operation sell30Pct() {
        return pct(-30);
    }

    public Operation sell50Pct() {
        return pct(-50);
    }

    public Operation sell20Pct() {
        return pct(-20);
    }

    public Operation sell10Pct() {
        return pct(-10);
    }

    public Operation pct(double pct) {
        return of(pct, TradeUnitEnum.PERCENT);
    }

    public Operation yuan(double yuan) {
        return of(yuan, TradeUnitEnum.YUAN);
    }

    public Operation share(double share) {
        return of(share, TradeUnitEnum.SHARE);
    }

    public List<Operation> operationsFromBaseAndInterval(double base, double interval, int plusTimes,
        int minusTimes, TradeUnitEnum unit) {
        List<Double> valueList = new ArrayList<>();
        valueList.add(base);
        for (int i = 1; i <= plusTimes; i++) {
            valueList.add(base + interval * i);
        }
        for (int i = 1; i <= minusTimes; i++) {
            valueList.add(base - interval * i);
        }
        return operationsFromValues(valueList, unit);
    }

    public List<Operation> operationsFromValues(List<Double> values, TradeUnitEnum unit) {
        List<Operation> operationList = new ArrayList<>();
        if (CollectionUtils.isEmpty(values)) {
            operationList.add(of(0, unit));
            return operationList;
        }
        values.stream().sorted().forEachOrdered(value -> operationList.add(of(value, unit))); // TODO to test
        // values.sort(Double::compareTo);
        // for (Double d : values) {
        // operationList.add(ofParams(d, unit));
        // }
        return operationList;
    }

    public Operation of(double tradeValue, TradeUnitEnum unit) {
        return new Operation(-1L, -1L, tradeValue, unit);
    }

}
