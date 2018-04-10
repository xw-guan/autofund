package com.xwguan.autofund.service.mapper.plan;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.service.mapper.account.CleanCopyAccountMapper;

@Mapper(uses = CleanCopyAccountMapper.class)
public interface CleanCopyPositionMapper {
    @Mappings({
        @Mapping(target = "id", constant = "-1"),
        @Mapping(target = "planId", constant = "-1"),
    })
    Position cleanCopy(Position source);

    List<Position> cleanCopyList(List<Position> source);

}
