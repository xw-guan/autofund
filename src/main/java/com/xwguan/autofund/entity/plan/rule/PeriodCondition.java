package com.xwguan.autofund.entity.plan.rule;

import java.time.LocalDate;

import com.xwguan.autofund.enums.AutoInvestPeriodEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.rule.PeriodConditionHandler;
import com.xwguan.autofund.util.IocUtils;

/**
 * 周期条件, PeriodCondition对象本身为null, 或属性period或dayOfPeriod为null则表示所有交易日都满足条件
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-08
 */
public class PeriodCondition extends Condition implements Handleable {

    /**
     * 所属策略id
     */
    private Long tacticId;

    /**
     * 策略类型
     */
    private TacticTypeEnum tacticType;

    /**
     * 定投周期
     */
    private AutoInvestPeriodEnum period;

    /**
     * 定投日, 位于定投周期中的第几天
     * 按周定投时取值为1-7, 对应java.time.DayOfWeek的getValue()值;
     * 按月定投时取值为1-28, 排除可能不存在的28号以后日期.
     */
    private Integer dayOfPeriod;

    /**
     * 上一次定投日期
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastInvestDate;

    /**
     * 下一次定投日期, 等于nextInvestDateInTheory或遇到非交易日往后顺延的日期
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate nextInvestDate;

    /**
     * 下一次理论定投日期, 不考虑非交易日
     */
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate nextInvestDateInTheory;

    public PeriodCondition() {
        super();
    }

    public PeriodCondition(Long id, Long tacticId, TacticTypeEnum tacticType, AutoInvestPeriodEnum period,
        Integer dayOfPeriod) {
        super(id);
        this.tacticId = tacticId;
        this.tacticType = tacticType;
        this.period = period;
        this.dayOfPeriod = dayOfPeriod;
    }

    public Long getTacticId() {
        return tacticId;
    }

    public void setTacticId(Long tacticId) {
        this.tacticId = tacticId;
    }

    public TacticTypeEnum getTacticType() {
        return tacticType;
    }

    public void setTacticType(TacticTypeEnum tacticType) {
        this.tacticType = tacticType;
    }

    public AutoInvestPeriodEnum getPeriod() {
        return period;
    }

    public void setPeriod(AutoInvestPeriodEnum period) {
        this.period = period;
    }

    public Integer getDayOfPeriod() {
        return dayOfPeriod;
    }

    public void setDayOfPeriod(Integer dayOfPeriod) {
        this.dayOfPeriod = dayOfPeriod;
    }

    public LocalDate getLastInvestDate() {
        return lastInvestDate;
    }

    public void setLastInvestDate(LocalDate lastInvestDate) {
        this.lastInvestDate = lastInvestDate;
    }

    public LocalDate getNextInvestDate() {
        return nextInvestDate;
    }

    public void setNextInvestDate(LocalDate nextInvestDate) {
        this.nextInvestDate = nextInvestDate;
    }

    public LocalDate getNextInvestDateInTheory() {
        return nextInvestDateInTheory;
    }

    public void setNextInvestDateInTheory(LocalDate nextInvestDateInTheory) {
        this.nextInvestDateInTheory = nextInvestDateInTheory;
    }

    @Override
    public String toString() {
        return "PeriodCondition [id=" + getId() + ", tacticId=" + tacticId + ", tacticType=" + tacticType + ", period="
            + period + ", dayOfPeriod=" + dayOfPeriod + ", lastInvestDate=" + lastInvestDate + ", nextInvestDate="
            + nextInvestDate + ", nextInvestDateInTheory=" + nextInvestDateInTheory + "]";
    }

    @Override
    public Class<PeriodConditionHandler> handlerClass() {
        return PeriodConditionHandler.class;
    }

    @Override
    public PeriodConditionHandler handler() {
        return IocUtils.getBean(handlerClass()).handle(this);
    }

}
