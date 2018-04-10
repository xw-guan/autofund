package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.ProfitTakingTacticDto;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedProfitTakingTactic;
import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class ProfitTakingTacticMapperImpl implements ProfitTakingTacticMapper {

    @Override
    public ProfitTakingTacticDto toTacticDto(ProfitTakingTactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        ProfitTakingTacticDto profitTakingTacticDto = new ProfitTakingTacticDto();

        if ( tactic != null ) {
            profitTakingTacticDto.setId( tactic.getId() );
            profitTakingTacticDto.setPlanId( tactic.getPlanId() );
            profitTakingTacticDto.setPeriodCondition( tactic.getPeriodCondition() );
            profitTakingTacticDto.setActivated( tactic.getActivated() );
            List<Rule> list = tactic.getRuleList();
            if ( list != null ) {
                profitTakingTacticDto.setRuleList( new ArrayList<Rule>( list ) );
            }
            else {
                profitTakingTacticDto.setRuleList( null );
            }
        }
        if ( tacticType != null ) {
            profitTakingTacticDto.setName( tacticType.getTacticName() );
            profitTakingTacticDto.setTypeCode( tacticType.getTypeCode() );
            profitTakingTacticDto.setPlanTactic( tacticType.isPlanTactic() );
        }

        return profitTakingTacticDto;
    }

    @Override
    public ProfitTakingTactic toTactic(ProfitTakingTacticDto tacticDto) {
        if ( tacticDto == null ) {
            return null;
        }

        ProfitTakingTactic profitTakingTactic = new ProfitTakingTactic();

        profitTakingTactic.setId( tacticDto.getId() );
        profitTakingTactic.setPlanId( tacticDto.getPlanId() );
        profitTakingTactic.setPeriodCondition( tacticDto.getPeriodCondition() );
        profitTakingTactic.setActivated( tacticDto.getActivated() );
        List<Rule> list = tacticDto.getRuleList();
        if ( list != null ) {
            profitTakingTactic.setRuleList( new ArrayList<Rule>( list ) );
        }
        else {
            profitTakingTactic.setRuleList( null );
        }

        return profitTakingTactic;
    }

    @Override
    public ActivatedProfitTakingTactic toActivatedProfitTakingTactic(ProfitTakingTactic profitTakingTactic, Rule rule, AssetHistory assetHistory) {
        if ( profitTakingTactic == null && rule == null && assetHistory == null ) {
            return null;
        }

        ActivatedProfitTakingTactic activatedProfitTakingTactic = new ActivatedProfitTakingTactic();

        if ( profitTakingTactic != null ) {
            activatedProfitTakingTactic.setPeriodCondition( profitTakingTactic.getPeriodCondition() );
        }
        if ( rule != null ) {
            activatedProfitTakingTactic.setRangeCondition( rule.getRangeCondition() );
            activatedProfitTakingTactic.setOperation( rule.getOperation() );
        }
        if ( assetHistory != null ) {
            activatedProfitTakingTactic.setPosIncomeRatePct( assetHistory.getPosIncomeRatePct() );
        }

        return activatedProfitTakingTactic;
    }
}
