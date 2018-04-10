package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;

import com.xwguan.autofund.dto.plan.tactic.GainLossTacticDto;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedGainLossTactic;
import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;

@Mapper
public interface GainLossTacticMapper extends TacticMapper<GainLossTactic, GainLossTacticDto> {

    ActivatedGainLossTactic toActivatedGainLossTactic(GainLossTactic gainLossTactic, Rule rule,
        AssetHistory assetHistory);

}
