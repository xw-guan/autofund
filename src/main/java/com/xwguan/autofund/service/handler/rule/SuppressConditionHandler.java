package com.xwguan.autofund.service.handler.rule;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.entity.plan.rule.SuppressCondition;
import com.xwguan.autofund.service.api.DateTimeService;

/**
 * 抑制条件检查者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
@Unimplement
@Component
@Scope("prototype")
public class SuppressConditionHandler extends AbstractConditionHandler<SuppressCondition> {

    @Autowired
    private DateTimeService dateTimeService;

    public SuppressConditionHandler() {
        super();
    }

    @Override
    public SuppressConditionHandler handle(SuppressCondition entity) {
        super.handle(entity);
        return this;
    }

    /**
     * 某日期是否不在抑制范围内
     * 
     * @param date 待判断日期, 非null
     * @return
     */
    public boolean isNotSuppress(LocalDate date) {
        if (needNotHandle()) {
            return true;
        }
        return getConditionOptional()
            .map(SuppressCondition::getSuppressBeforeDate)
            .map(suppressBefore -> date.isAfter(suppressBefore))
            .orElse(true);
    }

    /**
     * 计算SuppressCondition, 将activatingDate设为lastActivatedDate, 计算suppressTradeDays个交易日后的日期并设为suppressBeforeDate.
     * 若Condition对象本身为null或SuppressTradeDays为null时, 则返回Optional.Empty
     * 
     * @param activatingDate 发生条件激活的日期, 非null
     * @return Optional包装的SuppressCondition对象
     */
    public Optional<SuppressCondition> calcSuppressCondition(LocalDate activatingDate) {
        if (needNotHandle()) {
            return Optional.empty();
        }
        getCondition().setLastActivatedDate(activatingDate);
        Integer suppressTradeDays = getCondition().getSuppressTradeDays();
        LocalDate suppressBeforeDate = dateTimeService.tradeDayOfTradeDaysAfterDate(activatingDate, suppressTradeDays);
        getCondition().setSuppressBeforeDate(suppressBeforeDate);
        return Optional.of(getCondition());
    }

    /**
     * Condition对象本身为null或SuppressTradeDays为null时返回true
     */
    @Override
    public boolean needNotHandle() {
        return isEntityNull() || null == getCondition().getSuppressTradeDays();
    }

}
