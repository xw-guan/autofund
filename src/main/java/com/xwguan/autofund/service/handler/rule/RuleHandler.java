package com.xwguan.autofund.service.handler.rule;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.mapper.rule.CleanCopyRuleMapper;

/**
 * 规则处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
@Component
@Scope("prototype")
public class RuleHandler extends AbstractEntityHandler<Rule> implements CleanlyCopyable<Rule> {

    @Autowired
    private RangeConditionHandler rangeConditionHandler;

    @Autowired
    private SuppressConditionHandler suppressConditionHandler;
    
    @Autowired
    private CleanCopyRuleMapper mapper;

    public RuleHandler() {
        super();
    }

    @Override
    public RuleHandler handle(Rule entity) {
        super.handle(entity);
        return this;
    }

    /**
     * 是否满足该规则的条件, 条件为null视为满足
     * 
     * @param date 日期
     * @param value 信号值, 可为null
     * @return 是否满足该规则的条件, 条件为null视为满足
     */
    public boolean isConditionsMet(LocalDate date, Double value) {
        rangeConditionHandler.handle(getRule().getRangeCondition());
        suppressConditionHandler.handle(getRule().getSuppressCondition());
        return rangeConditionHandler.isInRange(value) && suppressConditionHandler.isNotSuppress(date);
    }

    public Rule getRule() {
        return getEntity();
    }

    @Override
    public Rule cleanCopy() {
        return mapper.cleanCopy(getRule());
    }

}
