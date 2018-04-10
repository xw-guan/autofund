package com.xwguan.autofund.service.template.plan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.entity.plan.rule.RangeCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.rule.SuppressCondition;
import com.xwguan.autofund.enums.ConditionUnitEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.enums.TradeUnitEnum;
import com.xwguan.autofund.service.util.Comparators;

/**
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
@Component
public class RuleTemplate {

    @Autowired
    private OperationTemplate operationTemplate;

    @Autowired
    private RangeConditionTemplate rangeConditionTemplate;

    @Autowired
    private SuppressConditionTemplate suppressConditionTemplate;

    /**
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(buy yuan, sell pct)
     * <tr><td>40.0<td>max<td>-100.0
     * <tr><td>30.0<td>40.0<td>-30.0
     * <tr><td>20.0<td>30.0<td>0.0
     * <tr><td>10.0<td>20.0<td>600.0
     * <tr><td>0.0<td>10.0<td>800.0
     * <tr><td>-10.0<td>0.0<td>1000.0
     * <tr><td>-20.0<td>-10.0<td>1200.0
     * <tr><td>-30.0<td>-20.0<td>1400.0
     * <tr><td>-40.0<td>-30.0<td>1600.0
     * <tr><td>-50.0<td>-40.0<td>1800.0
     * <tr><td>-60.0<td>-50.0<td>2000.0
     * <tr><td>-70.0<td>-60.0<td>2200.0
     * <tr><td>-80.0<td>-70.0<td>2400.0
     * <tr><td>-90.0<td>-80.0<td>2600.0
     * <tr><td>-100.0<td>-90.0<td>2800.0
     * <tr><td>-max<td>-100.0<td>3000.0
     * </table>
     */
    public List<Rule> defaultRuleList(TacticTypeEnum tacticType) {
        List<Rule> ruleList = new ArrayList<>();
        List<RangeCondition> rangeConditions = rangeConditionTemplate.conditionsFromBaseAndIntervalWithMaxValue(0, 10,
            4, 10, ConditionUnitEnum.PERCENT);
        Collections.reverse(rangeConditions);
        List<Operation> operations = operationTemplate.operationsFromBaseAndInterval(1000, 200, 10, 2,
            TradeUnitEnum.YUAN);
        operations.add(operationTemplate.ignore());
        operations.add(operationTemplate.sell30Pct());
        operations.add(operationTemplate.sellAll());
        operations.sort(Comparators.OPERATION_TRADE_VALUE_COMPARATOR);
        for (int i = 0; i < rangeConditions.size(); i++) {
            ruleList.add(of(tacticType, rangeConditions.get(i), null, operations.get(i)));
        }
        return ruleList;
    }

    /**
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>20.0<td>max<td>-50.0
     * <tr><td>10.0<td>20.0<td>-30.0
     * </table>
     */
    public List<Rule> changeIn10TradeDaysRuleList(TacticTypeEnum tacticType) {
        List<Rule> ruleList = new ArrayList<>();
        SuppressCondition suppress5 = suppressConditionTemplate.of(5);
        RangeCondition pctGT20 = rangeConditionTemplate.pct(20, Double.MAX_VALUE);
        Operation sell50 = operationTemplate.sell50Pct();
        ruleList.add(of(tacticType, pctGT20, suppress5, sell50));
        RangeCondition pct10to20 = rangeConditionTemplate.pct(10, 20);
        Operation sell30 = operationTemplate.sell30Pct();
        ruleList.add(of(tacticType, pct10to20, suppress5, sell30));
        return ruleList;
    }

    /**
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * <tr><td>10.0<td>20.0<td>-10.0
     * </table>
     */
    public List<Rule> changeIn22TradeDaysRuleList(TacticTypeEnum tacticType) {
        List<Rule> ruleList = new ArrayList<>();
        SuppressCondition suppress10 = suppressConditionTemplate.tradeDays10();
        RangeCondition pctGT30 = rangeConditionTemplate.pct(30, Double.MAX_VALUE);
        Operation sell50 = operationTemplate.sell50Pct();
        ruleList.add(of(tacticType, pctGT30, suppress10, sell50));
        RangeCondition pct20To30 = rangeConditionTemplate.pct(20, 30);
        Operation sell30 = operationTemplate.sell30Pct();
        ruleList.add(of(tacticType, pct20To30, suppress10, sell30));
        RangeCondition pct10To20 = rangeConditionTemplate.pct(20, 30);
        Operation sell10 = operationTemplate.sell10Pct();
        ruleList.add(of(tacticType, pct10To20, suppress10, sell10));
        return ruleList;
    }

    /**
     * buy 1000 yuan
     */
    public List<Rule> constantBuyRuleList(TacticTypeEnum tacticType) {
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(of(tacticType, null, null, operationTemplate.yuan(1000)));
        return ruleList;
    }

    /**
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>40.0<td>max<td>-100.0
     * <tr><td>30.0<td>40.0<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * </table>
     */
    public List<Rule> positionProfitTakingRuleList(TacticTypeEnum tacticType) {
        List<Rule> ruleList = new ArrayList<>();
        SuppressCondition suppressMonth = suppressConditionTemplate.month();
        RangeCondition pctGT40 = rangeConditionTemplate.pct(40, Double.MAX_VALUE);
        Operation sellAll = operationTemplate.sellAll();
        ruleList.add(of(tacticType, pctGT40, suppressMonth, sellAll));
        RangeCondition pct30To40 = rangeConditionTemplate.pct(30, 40);
        Operation sell50 = operationTemplate.sell50Pct();
        ruleList.add(of(tacticType, pct30To40, suppressMonth, sell50));
        RangeCondition pct20To30 = rangeConditionTemplate.pct(20, 30);
        Operation sell30 = operationTemplate.sell30Pct();
        ruleList.add(of(tacticType, pct20To30, suppressMonth, sell30));
        return ruleList;
    }
    
    /**
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-100.0
     * </table>
     */
    public List<Rule> planProfitTakingRuleList(TacticTypeEnum tacticType) {
        List<Rule> ruleList = new ArrayList<>();
        RangeCondition pctGT30 = rangeConditionTemplate.pct(30, Double.MAX_VALUE);
        SuppressCondition suppressMonth = suppressConditionTemplate.month();
        Operation sellAll = operationTemplate.sellAll();
        ruleList.add(of(tacticType, pctGT30, suppressMonth, sellAll));
        return ruleList;
    }

    /**
     * do nothing
     */
    public Rule ignore(TacticTypeEnum tacticType) {
        return of(tacticType, null, null, operationTemplate.ignore());
    }

    public Rule of(TacticTypeEnum tacticType, RangeCondition rangeCondition, SuppressCondition suppressCondition,
        Operation operation) {
        return new Rule(-1L, -1L, tacticType, rangeCondition, suppressCondition, operation);
    }

}
