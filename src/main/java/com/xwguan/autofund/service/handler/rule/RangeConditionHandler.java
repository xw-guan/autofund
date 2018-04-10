package com.xwguan.autofund.service.handler.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.RangeCondition;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.mapper.rule.CleanCopyRangeConditionMapper;

/**
 * 范围条件检查者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
@Component
@Scope("prototype")
public class RangeConditionHandler extends AbstractConditionHandler<RangeCondition>
    implements CleanlyCopyable<RangeCondition> {

//    @Autowired
//    private RangeConditionTemplate template;
    
    @Autowired
    private CleanCopyRangeConditionMapper mapper;

    public RangeConditionHandler() {
        super();
    }

    @Override
    public RangeConditionHandler handle(RangeCondition entity) {
        super.handle(entity);
        return this;
    }

    /**
     * 信号值是否在范围内, Condition为null时视为满足条件, boundary值为null时视为infinity, value为null时返回false
     * 
     * @param value 信号值, 可为null
     * @return 信号值是否满足条件, Condition为null时视为满足条件
     */
    public boolean isInRange(Double value) {
        if (isEntityNull()) {
            return true;
        }
        if (value == null) {
            return false;
        }
        boolean gtBoundaryLeft = getConditionOptional()
            .map(RangeCondition::getBoundaryLeft)
            .map(bl -> bl <= value)
            .orElse(true);
        boolean ltBoundaryRight = getConditionOptional()
            .map(RangeCondition::getBoundaryRight)
            .map(br -> br >= value)
            .orElse(true);
        return gtBoundaryLeft && ltBoundaryRight;
    }

    @Override
    public RangeCondition cleanCopy() {
        return mapper.cleanCopy(getCondition());
    }

}
