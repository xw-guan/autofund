package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.plan.tactic.IndexChangeTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedIndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.mapper.rule.CleanCopyPeriodConditionMapper;
import com.xwguan.autofund.service.mapper.rule.CleanCopyRuleMapper;

@Mapper(uses = { CleanCopyRuleMapper.class, CleanCopyPeriodConditionMapper.class })
public interface CleanCopyIndexChangeTacticMapper extends CleanCopyTacticMapper<IndexChangeTactic> {
    ActivatedIndexChangeTactic toActivatedIndexChangeTactic(IndexChangeTactic tactic, Rule rule, Double startValue,
        Double endValue, Double changePct);
    
    @Mappings({
        @Mapping(target = "name", source = "tacticType.tacticName"),
    })
    IndexChangeTacticDto toTacticDto(IndexChangeTactic tactic, TacticTypeEnum tacticType);

    
    IndexChangeTactic toTactic(IndexChangeTacticDto tacticDto);
}
