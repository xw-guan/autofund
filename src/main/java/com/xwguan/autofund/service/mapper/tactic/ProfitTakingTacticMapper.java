package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;

import com.xwguan.autofund.dto.plan.tactic.ProfitTakingTacticDto;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedProfitTakingTactic;
import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;

@Mapper
public interface ProfitTakingTacticMapper extends TacticMapper<ProfitTakingTactic, ProfitTakingTacticDto> {
    ActivatedProfitTakingTactic toActivatedProfitTakingTactic(ProfitTakingTactic profitTakingTactic, Rule rule,
        AssetHistory assetHistory);
    
}
