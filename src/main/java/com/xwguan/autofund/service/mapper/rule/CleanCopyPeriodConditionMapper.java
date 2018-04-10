package com.xwguan.autofund.service.mapper.rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;

@Mapper
public interface CleanCopyPeriodConditionMapper {
    
    @Mappings({
        @Mapping(target="id", constant="-1"),
        @Mapping(target="tacticId", constant="-1"),
        @Mapping(target="lastInvestDate", ignore = true),
        @Mapping(target="nextInvestDate", ignore = true),
        @Mapping(target="nextInvestDateInTheory", ignore = true),
    })
    PeriodCondition cleanCopy(PeriodCondition source);
    
}
