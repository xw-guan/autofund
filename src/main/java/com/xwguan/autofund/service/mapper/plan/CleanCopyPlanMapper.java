package com.xwguan.autofund.service.mapper.plan;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.service.mapper.account.CleanCopyAccountMapper;

@Mapper(uses = { CleanCopyPositionMapper.class, CleanCopyAccountMapper.class})
public interface CleanCopyPlanMapper {

    /**
     * 不复制历史, 不复制策略(用MapStruct实现复杂, handler中实现), 所有id设为-1, 激活状态设为true
     * 
     * @param plan
     * @return
     */
    @Mappings({
        @Mapping(target = "id", constant = "-1"),
        @Mapping(target = "userId", constant = "-1"),
        @Mapping(target = "activated", constant = "true"),
        @Mapping(target = "planHistoryList", ignore = true),
        @Mapping(target = "planTacticList", ignore = true),
        @Mapping(target = "positionTacticList", ignore = true),
    })
    Plan cleanCopy(Plan plan);

}
