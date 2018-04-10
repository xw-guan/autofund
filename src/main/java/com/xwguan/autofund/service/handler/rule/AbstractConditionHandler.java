package com.xwguan.autofund.service.handler.rule;

import java.util.Optional;

import com.xwguan.autofund.entity.plan.rule.Condition;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.EntityHandler;

/**
 * 抽象条件处理者
 * 
 * @param <T> Condition的子类
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-18
 */
public abstract class AbstractConditionHandler<T extends Condition> extends AbstractEntityHandler<T> {

    private Optional<T> conditionOptional = Optional.empty();
    
    /**
     * 处理实体对象, Entity可以为null, null时不抛出异常
     * 
     * @param entity 待处理的实体对象, 可以为null
     * @return 本对象
     */
    @Override
    public EntityHandler<T> handle(T entity) {
        conditionOptional = Optional.ofNullable(entity);
        return super.handle(entity);
    }

    public T getCondition() {
        return getEntity();
    }

    protected Optional<T> getConditionOptional() {
        return conditionOptional;
    }
    
}
