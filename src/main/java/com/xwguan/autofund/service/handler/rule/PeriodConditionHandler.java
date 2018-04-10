package com.xwguan.autofund.service.handler.rule;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.enums.AutoInvestPeriodEnum;
import com.xwguan.autofund.service.api.DateTimeService;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.mapper.rule.CleanCopyPeriodConditionMapper;

/**
 * 周期条件检查者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
@Component
@Scope("prototype")
public class PeriodConditionHandler extends AbstractConditionHandler<PeriodCondition>
    implements CleanlyCopyable<PeriodCondition> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DateTimeService dateTimeService;

    @Autowired
    private CleanCopyPeriodConditionMapper mapper;

//    @Autowired
//    private PeriodConditionTemplate template;

    public PeriodConditionHandler() {
        super();
    }

    @Override
    public PeriodConditionHandler handle(PeriodCondition entity) {
        super.handle(entity);
        return this;
    }

    /**
     * 是否是定投日. 条件为null或条件未初始化时, 只要是交易日就返回true. 传入的日期为null时返回false
     * 
     * @param date 日期
     * @return
     */
    public boolean isInvestDate(LocalDate date) {
        if (needNotHandle() || hasNotInit()) {
            return dateTimeService.isTradeDay(date);
        }
        return date.equals(getCondition().getNextInvestDate());
    }

    /**
     * 计算SuppressCondition, 将activatingDate设为lastInvestDate, 计算下一个定投日并设为nextInvestDate.
     * 若条件尚未初始化, 则执行initPeriodCondition方法初始化, activatingDate设为初始化日期.
     * 若Condition对象本身为null或必要的属性为null, 则返回Optional.Empty
     * 
     * @param activatingDate 发生条件激活的日期
     * @return Optional包装的PeriodCondition对象
     */
    public Optional<PeriodCondition> calcPeriodCondition(LocalDate activatingDate) {
        if (needNotHandle()) {
            return Optional.empty();
        }
        if (hasNotInit()) {
            return initPeriodCondition(activatingDate);
        }
        if (activatingDate.isBefore(getCondition().getNextInvestDate())) {
            logger.warn("calcPeriodCondition when the condition has not activated");
            return Optional.empty(); // 可能会在没到激活日期前被调用, 这实际上是错误调用
        }
        getCondition().setLastInvestDate(activatingDate);
        LocalDate nextInvestDateInTheory = getCondition().getNextInvestDateInTheory();
        ChronoUnit unit = getCondition().getPeriod().getUnit();
        int amount = getCondition().getPeriod().getAmount();
        nextInvestDateInTheory = nextInvestDateInTheory.plus(amount, unit); // 增加一个周期
        getCondition().setNextInvestDateInTheory(nextInvestDateInTheory);
        LocalDate nextInvestDate = calcNextInvestDate(nextInvestDateInTheory); // 非交易日往后顺延
        getCondition().setNextInvestDate(nextInvestDate);
        return getConditionOptional();
    }

    /**
     * 初始化周期条件. 若Condition对象本身为null或必要的属性为null, 则返回Optional.Empty
     * 
     * @param initDate 初始化日期
     * @return
     */
    public Optional<PeriodCondition> initPeriodCondition(LocalDate initDate) {
        if (needNotHandle()) {
            return Optional.empty();
        }
        AutoInvestPeriodEnum period = getCondition().getPeriod();
        int dayOfPeriod = getCondition().getDayOfPeriod();
        int initDayOfPeriod;
        switch (period) {
        case WEEKLY:
        case BIWEEKLY:
            initDayOfPeriod = initDate.getDayOfWeek().getValue();
            break;
        case MONTHLY:
        case BIMONTHLY:
        case QUATERLY:
        case SEMIANNUALLY:
            initDayOfPeriod = initDate.getDayOfMonth();
            break;
        case ANNUALLY:
            initDayOfPeriod = initDate.getDayOfYear();
            break;
        default:
            initDayOfPeriod = 1; // DAILY
        }
        LocalDate nextInvestDateInTheory = initDayOfPeriod < dayOfPeriod
            ? initDate.plusDays(dayOfPeriod - initDayOfPeriod) // 激活日在定投日前
            : initDate.minusDays(initDayOfPeriod - dayOfPeriod).plus(1, period.getUnit()); // 激活日已过定投日
        getCondition().setNextInvestDateInTheory(nextInvestDateInTheory);
        LocalDate nextInvestDate = calcNextInvestDate(nextInvestDateInTheory); // 非交易日往后顺延
        getCondition().setNextInvestDate(nextInvestDate);
        return getConditionOptional();
    }

    /**
     * 重置周期条件, 按参数设置周期和定投日, 不清空激活历史, 不改变id
     * 
     * @param period 周期
     * @param dayInPeriod 定投日在周期的第几天
     * @param initDate 重置的初始化日期
     * @return
     */
    public Optional<PeriodCondition> resetPeriodCondition(AutoInvestPeriodEnum period, int dayInPeriod,
        LocalDate initDate) {
        return getConditionOptional().flatMap(condition -> {
            condition.setPeriod(period);
            condition.setDayOfPeriod(dayInPeriod);
            return initPeriodCondition(initDate);
        });
    }

    /**
     * 重置周期条件, 不清空激活历史, 不改变周期和定投日, 不改变id
     * 
     * @param initDate 重置的初始化日期
     * @return
     */
    public Optional<PeriodCondition> resetPeriodCondition(LocalDate initDate) {
        return initPeriodCondition(initDate);
    }

    /**
     * 重置周期条件, 并清空激活历史, 不改变周期和定投日, 不改变id
     * 
     * @param initDate 重置的初始化日期
     * @return
     */
    public Optional<PeriodCondition> resetAndClearPeriodCondition(LocalDate initDate) {
        return getConditionOptional().flatMap(condition -> {
            condition.setLastInvestDate(null);
            return initPeriodCondition(initDate);
        });
    }

    /**
     * 使用理论下一定投日计算实际下一定投日, 非交易日往后顺延
     */
    private LocalDate calcNextInvestDate(LocalDate nextInvestDateInTheory) {
        return dateTimeService.isTradeDay(nextInvestDateInTheory)
            ? nextInvestDateInTheory
            : dateTimeService.nextTradeDay(nextInvestDateInTheory);
    }

    @Override
    public PeriodCondition cleanCopy() {
        return mapper.cleanCopy(getCondition());
    }

    public PeriodCondition cleanCopyAndReset(LocalDate initDate) {
        return cleanCopy().handler().initPeriodCondition(initDate).orElse(null);
    }

    /**
     * Condition对象本身为null或period属性或dayInPeriod属性为null
     */
    @Override
    public boolean needNotHandle() {
        return isEntityNull() || getCondition().getPeriod() == null || getCondition().getDayOfPeriod() == null;
    }

    /**
     * 是否尚未初始化
     * 
     * @return
     */
    private boolean hasNotInit() {
        return getCondition().getNextInvestDate() == null;
    }

}
