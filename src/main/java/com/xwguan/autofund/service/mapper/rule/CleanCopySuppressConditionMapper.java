package com.xwguan.autofund.service.mapper.rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.rule.SuppressCondition;

@Mapper
public interface CleanCopySuppressConditionMapper {
    
    @Mappings({
        @Mapping(target="id", constant="-1"),
        @Mapping(target="ruleId", constant="-1"),
    })
    SuppressCondition cleanCopy(SuppressCondition source);
    
}
