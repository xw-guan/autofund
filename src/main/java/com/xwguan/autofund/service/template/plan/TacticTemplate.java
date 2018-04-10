package com.xwguan.autofund.service.template.plan;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.entity.plan.tactic.NavChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;
import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.MAEnum;
import com.xwguan.autofund.enums.TacticTemplateEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;

/**
 * 策略模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
@Component
public class TacticTemplate {

    @Autowired
    private RuleTemplate ruleTemplate;

    @Autowired
    private PeriodConditionTemplate periodConditionTemplate;

    public Tactic getTemplate(TacticTemplateEnum template, LocalDate initDate) {
        switch (template) {
        case GAIN_LOSS_TACTIC:
            return gainLossTactic(initDate);
        case MA_TACTIC_250:
            return maTactic(initDate);
        case INDEX_CHANGE_TACTIC_22:
            return indexChangeTactic(initDate);
        case NAV_CHANGE_TACTIC_22:
            return navChangeTactic(initDate);
        case PERIOD_BUY_TACTIC:
            return periodBuyTactic(initDate);
        case PLAN_PROFIT_TAKING_TACTIC:
            return planProfitTakingTactic(initDate);
        case POSITION_PROFIT_TAKING_TACTIC:
            return positionProfitTakingTactic(initDate);
        default:
            return null;
        }
    }

    /**
     * Period: monthly <br>
     * Rules:
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
    public MATactic maTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.defaultRuleList(TacticTypeEnum.MA_TACTIC);
        PeriodCondition periodCondition = periodConditionTemplate.monthly(TacticTypeEnum.MA_TACTIC, initDate);
        return new MATactic(-1L, -1L, periodCondition, true, rules, MAEnum.MA250);
    }

    /**
     * Period: monthly <br>
     * Rules:
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
    public GainLossTactic gainLossTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.defaultRuleList(TacticTypeEnum.GAIN_LOSS_TACTIC);
        PeriodCondition periodCondition = periodConditionTemplate.monthly(TacticTypeEnum.GAIN_LOSS_TACTIC, initDate);
        return new GainLossTactic(-1L, -1L, periodCondition, true, rules);
    }

    /**
     * Period: weekly <br>
     * InTradeDays: 22 <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * <tr><td>10.0<td>20.0<td>-10.0
     * </table>
     */
    public IndexChangeTactic indexChangeTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.changeIn22TradeDaysRuleList(TacticTypeEnum.INDEX_CHANGE_TACTIC);
        Integer inTradeDays = 22;
        PeriodCondition periodCondition = periodConditionTemplate.weekly(TacticTypeEnum.INDEX_CHANGE_TACTIC, initDate);
        return new IndexChangeTactic(-1L, -1L, periodCondition, true, rules, inTradeDays);
    }

    /**
     * Period: weekly <br>
     * InTradeDays: 22 <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * <tr><td>10.0<td>20.0<td>-10.0
     * </table>
     */
    public NavChangeTactic navChangeTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.changeIn22TradeDaysRuleList(TacticTypeEnum.NAV_CHANGE_TACTIC);
        Integer inTradeDays = 22;
        PeriodCondition periodCondition = periodConditionTemplate.weekly(TacticTypeEnum.NAV_CHANGE_TACTIC, initDate);
        return new NavChangeTactic(-1L, -1L, periodCondition, true, rules, inTradeDays);
    }

    /**
     * Period: weekly <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>40.0<td>max<td>-100.0
     * <tr><td>30.0<td>40.0<td>-50.0
     * <tr><td>20.0<td>30.0<td>-30.0
     * </table>
     */
    public GainLossTactic positionProfitTakingTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.positionProfitTakingRuleList(TacticTypeEnum.GAIN_LOSS_TACTIC);
        PeriodCondition periodCondition = periodConditionTemplate.weekly(TacticTypeEnum.GAIN_LOSS_TACTIC, initDate);
        return new GainLossTactic(-1L, -1L, periodCondition, true, rules);
    }

    /**
     * Period: weekly <br>
     * Rules:
     * <table>
     * <tr><th>boundaryLeft<th>boundaryRight<th>tradeValue(pct)
     * <tr><td>30.0<td>max<td>-100.0
     * </table>
     */
    public ProfitTakingTactic planProfitTakingTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.planProfitTakingRuleList(TacticTypeEnum.PROFIT_TAKING_TACTIC);
        PeriodCondition periodCondition = periodConditionTemplate.weekly(TacticTypeEnum.PROFIT_TAKING_TACTIC, initDate);
        return new ProfitTakingTactic(-1L, -1L, periodCondition, true, rules);
    }

    /**
     * Period: monthly <br>
     * Rules: buy 1000 yuan
     */
    public PeriodBuyTactic periodBuyTactic(LocalDate initDate) {
        List<Rule> rules = ruleTemplate.constantBuyRuleList(TacticTypeEnum.PERIOD_BUY_TACTIC);
        PeriodCondition periodCondition = periodConditionTemplate.monthly(TacticTypeEnum.PERIOD_BUY_TACTIC, initDate);
        return new PeriodBuyTactic(-1L, -1L, periodCondition, true, rules);
    }

}
