package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;

import com.xwguan.autofund.dto.plan.tactic.MATacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedMATactic;
import com.xwguan.autofund.entity.plan.tactic.MATactic;

@Mapper
public interface MATacticMapper extends TacticMapper<MATactic, MATacticDto> {
    
    ActivatedMATactic toActivatedMATactic(MATactic tactic, Rule rule, Double indexValue, Double maValue,
        Double deviationPct);

}
