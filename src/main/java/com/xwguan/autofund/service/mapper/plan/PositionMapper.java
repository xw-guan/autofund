package com.xwguan.autofund.service.mapper.plan;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.plan.LatestPositionDto;
import com.xwguan.autofund.dto.plan.PositionDto;
import com.xwguan.autofund.entity.plan.portfolio.Position;

@Mapper
@DecoratedWith(PositionMapperDecorator.class)
public interface PositionMapper {
    @Mappings({
        @Mapping(target = "id", source = "position.id"),
        @Mapping(target = "fundCode", ignore = true),
        @Mapping(target = "fundName", ignore = true),
        @Mapping(target = "refIndexCode", ignore = true),
        @Mapping(target = "refIndexName", ignore = true),
    })
    PositionDto toPositionDto(Position position);
    
    List<PositionDto> toPositionDtoList(List<Position> positionList);
    
    /**
     * 返回的Position中的account在Decorator中实现, 用Account模板方法赋值, ownerId为PositionDto的id
     */
    @Mappings({
        @Mapping(target = "account", ignore = true),
    })
    Position toPosition(PositionDto positionDto);
    
    List<Position> toPositionList(List<PositionDto> positionDtoList); 

    @Mappings({
        @Mapping(target = "id", source = "position.id"),
        @Mapping(target = "latestAccount", ignore = true),
        @Mapping(target = "latestFund", ignore = true),
        @Mapping(target = "latestRefIndex", ignore = true),
    })
    LatestPositionDto toLatestPositionDto(Position position);

    List<LatestPositionDto> toLatestPositionDtoList(List<Position> positionList);
    
}
