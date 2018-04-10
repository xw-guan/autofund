package com.xwguan.autofund.service.template.plan;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.enums.AutoInvestPeriodEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.handler.rule.PeriodConditionHandler;

@Component
public class PeriodConditionTemplate {

    @Autowired
    PeriodConditionHandler periodConditionHandler;

    /**
     * 默认为每天, 激活日期设为当日
     */
    public PeriodCondition defaultPeriodCondition(TacticTypeEnum tacticType) {
        return daily(tacticType, LocalDate.now());
    }

    public PeriodCondition bimonthly(TacticTypeEnum tacticType, LocalDate initDate) {
        return of(tacticType, AutoInvestPeriodEnum.BIMONTHLY, initDate);
    }

    public PeriodCondition monthly(TacticTypeEnum tacticType, LocalDate initDate) {
        return of(tacticType, AutoInvestPeriodEnum.MONTHLY, initDate);
    }

    public PeriodCondition biweekly(TacticTypeEnum tacticType, LocalDate initDate) {
        return of(tacticType, AutoInvestPeriodEnum.BIWEEKLY, initDate);
    }

    public PeriodCondition weekly(TacticTypeEnum tacticType, LocalDate initDate) {
        return of(tacticType, AutoInvestPeriodEnum.WEEKLY, initDate);
    }

    public PeriodCondition daily(TacticTypeEnum tacticType, LocalDate initDate) {
        return of(tacticType, AutoInvestPeriodEnum.DAILY, initDate);
    }

    /**
     * 按照设定的获取定投周期获取周期条件, 定投日为周期的第一天
     * 
     * @param period 定投周期
     * @param initDate 初始化日期
     * @return
     */
    public PeriodCondition of(TacticTypeEnum tacticType, AutoInvestPeriodEnum period,
        LocalDate initDate) {
        return of(tacticType, period, 1, initDate);
    }

    /**
     * 按照设定的获取定投周期和定投日获取周期条件
     * 
     * @param period 定投周期
     * @param dayOfPeriod 定投日在周期的第几天
     * @param initDate 初始化日期
     * @return
     */
    public PeriodCondition of(TacticTypeEnum tacticType, AutoInvestPeriodEnum period,
        Integer dayOfPeriod, LocalDate initDate) {
        periodConditionHandler.handle(new PeriodCondition(-1L, -1L, tacticType, period, dayOfPeriod));
        return periodConditionHandler.initPeriodCondition(initDate).get();
    }

}
