package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.tactic.Tactic;

public interface CleanCopyTacticMapper<T extends Tactic> {
    @Mappings({
        @Mapping(target = "id", constant = "-1"),
        @Mapping(target = "planId", constant = "-1"),
        @Mapping(target = "activated", constant = "true"),
    })
    T cleanCopy(T source);

}
