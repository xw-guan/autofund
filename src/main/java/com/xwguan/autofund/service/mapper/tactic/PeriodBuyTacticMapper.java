package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;

import com.xwguan.autofund.dto.plan.tactic.PeriodBuyTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedPeriodBuyTactic;
import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;

@Mapper
public interface PeriodBuyTacticMapper extends TacticMapper<PeriodBuyTactic, PeriodBuyTacticDto> {
    ActivatedPeriodBuyTactic toActivatedTactic(PeriodBuyTactic periodBuyTactic, Rule rule);
}
