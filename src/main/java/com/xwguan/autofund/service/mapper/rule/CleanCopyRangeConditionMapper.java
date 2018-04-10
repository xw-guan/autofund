package com.xwguan.autofund.service.mapper.rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.rule.RangeCondition;

@Mapper
public interface CleanCopyRangeConditionMapper {
    
    @Mappings({
        @Mapping(target="id", constant="-1"),
        @Mapping(target="ruleId", constant="-1"),
    })
    RangeCondition cleanCopy(RangeCondition source);
    
}
