package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;

import com.xwguan.autofund.dto.plan.tactic.IndexChangeTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedIndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;

@Mapper
public interface IndexChangeTacticMapper extends TacticMapper<IndexChangeTactic, IndexChangeTacticDto> {
    ActivatedIndexChangeTactic toActivatedIndexChangeTactic(IndexChangeTactic tactic, Rule rule, Double startValue,
        Double endValue, Double changePct);
    
}
