package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.plan.tactic.TacticDto;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import com.xwguan.autofund.enums.TacticTypeEnum;

public interface TacticMapper<T extends Tactic, U extends TacticDto> {
    @Mappings({
        @Mapping(target = "name", source = "tacticType.tacticName"),
    })
    U toTacticDto(T tactic, TacticTypeEnum tacticType);

    T toTactic(U tacticDto);
}
