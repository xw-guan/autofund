package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;

import com.xwguan.autofund.dto.plan.tactic.NavChangeTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedNavChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.NavChangeTactic;

@Mapper
public interface NavChangeTacticMapper extends TacticMapper<NavChangeTactic, NavChangeTacticDto> {
    ActivatedNavChangeTactic toActivatedNavChangeTactic(NavChangeTactic tactic, Rule rule, Double startValue,
        Double endValue, Double changePct);
}
