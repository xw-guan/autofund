package com.xwguan.autofund.service.handler.tactic;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.handler.EntityHandler;

/**
 * 策略处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-16
 */
public interface TacticHandler<T extends Tactic> extends EntityHandler<T>, CleanlyCopyable<T> {

    /**
     * 获取满足条件的规则, 若不存在满足条件的规则或策略为非激活状态, 则返回Optional.EMPTY.
     * <p>若Condition和Operation为null, 则设为对应的默认值,
     * 如RangeConditionTemplate::alwaysMet, OperationTemplate::ignore
     * 
     * @param date 日期
     * @return
     * @throws IOException 对于需要获取实时值的可能有URL异常
     */
    Optional<Rule> getRuleMetCondition(LocalDate date) throws IOException;

    /**
     * 获取对应的ActivatedTactic, 若没有被激活的规则或策略为非激活状态, 则返回Optional.EMPTY.
     * <p>若Condition和Operation为null, 则设为对应的默认值,
     * 如RangeConditionTemplate::alwaysMet, OperationTemplate::ignore
     * 
     * @param date 日期
     * @return
     * @throws IOException 对于需要获取实时值的可能有URL异常
     */
    Optional<ActivatedTactic> getActivatedTactic(LocalDate date) throws IOException;

    /**
     * 策略是否为激活状态, 值为null或false视为未激活
     * 
     * @return
     */
    boolean isActivated();

    /**
     * 是否满足周期条件, 周期条件为null视为交易日满足条件
     * 
     * @param date 日期
     * @return
     */
    boolean isPeriodConditionMet(LocalDate date);

    /**
     * 策略在某日是否不需要处理, 非激活状态或不满足周期条件时不需要处理(true), date为null时为true
     * 
     * @param date
     * @return
     */
    boolean needNotHandle(LocalDate date);

    /**
     * 获取被处理的策略, 可能为null
     * 
     * @return 被处理的策略
     */
    Tactic getTactic();
}
