package com.xwguan.autofund.service.mapper.plan;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.plan.LatestPlanDto;
import com.xwguan.autofund.dto.plan.PlanDto;
import com.xwguan.autofund.dto.plan.PlanInfoDto;
import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.service.mapper.tactic.TacticsMapper;

@Mapper(uses = { PositionMapper.class, TacticsMapper.class })
@DecoratedWith(PlanMapperDecorator.class)
public interface PlanMapper {

    @Mappings({
        @Mapping(target = "planInfo", ignore = true),
        @Mapping(target = "positions", source = "plan.positionList"),
        @Mapping(target = "tactics", ignore = true),
    })
    PlanDto toPlanDto(Plan plan);
    
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "name", ignore = true),
        @Mapping(target = "userId", ignore = true),
        @Mapping(target = "activated", ignore = true),
        @Mapping(target = "executionMode", ignore = true),
        @Mapping(target = "positionList", source = "planDto.positions"),
        @Mapping(target = "planHistoryList", ignore = true),
        @Mapping(target = "planTacticList", ignore = true),
        @Mapping(target = "positionTacticList", ignore = true),
        @Mapping(target = "account", ignore = true),
    })
    Plan toPlan(PlanDto planDto);

    PlanInfoDto toPlanInfoDto(Plan plan);

    @Mappings({
        @Mapping(target = "positionList", ignore = true),
        @Mapping(target = "planHistoryList", ignore = true),
        @Mapping(target = "planTacticList", ignore = true),
        @Mapping(target = "positionTacticList", ignore = true),
        @Mapping(target = "account", ignore = true),
    })
    Plan toPlan(PlanInfoDto planInfoDto);
    
    @Mappings({
        @Mapping(target = "positionList", ignore = true),
        @Mapping(target = "planHistoryList", ignore = true),
        @Mapping(target = "planTacticList", ignore = true),
        @Mapping(target = "positionTacticList", ignore = true),
        @Mapping(target = "account", ignore = true),
    })
    void updatePlan(@MappingTarget Plan plan, PlanInfoDto planInfoDto);
    
    @Mappings({
        @Mapping(target = "planInfo", ignore = true),
        @Mapping(target = "latestPositions", source = "plan.positionList"),
        @Mapping(target = "latestAccount", ignore = true),
        @Mapping(target = "tactics", ignore = true),
    })
    LatestPlanDto toLatestPlanDto(Plan plan);
}
