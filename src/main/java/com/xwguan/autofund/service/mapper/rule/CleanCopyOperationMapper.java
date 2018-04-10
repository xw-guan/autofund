package com.xwguan.autofund.service.mapper.rule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.rule.Operation;

@Mapper
public interface CleanCopyOperationMapper {
    
    @Mappings({
        @Mapping(target="id", constant="-1"),
        @Mapping(target="ruleId", constant="-1"),
    })
    Operation cleanCopy(Operation source);
    
}
