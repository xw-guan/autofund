package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.GainLossTacticDto;
import com.xwguan.autofund.entity.account.AssetHistory;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedGainLossTactic;
import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
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
public class GainLossTacticMapperImpl implements GainLossTacticMapper {

    @Override
    public GainLossTacticDto toTacticDto(GainLossTactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        GainLossTacticDto gainLossTacticDto = new GainLossTacticDto();

        if ( tactic != null ) {
            gainLossTacticDto.setId( tactic.getId() );
            gainLossTacticDto.setPlanId( tactic.getPlanId() );
            gainLossTacticDto.setPeriodCondition( tactic.getPeriodCondition() );
            gainLossTacticDto.setActivated( tactic.getActivated() );
            List<Rule> list = tactic.getRuleList();
            if ( list != null ) {
                gainLossTacticDto.setRuleList( new ArrayList<Rule>( list ) );
            }
            else {
                gainLossTacticDto.setRuleList( null );
            }
        }
        if ( tacticType != null ) {
            gainLossTacticDto.setName( tacticType.getTacticName() );
            gainLossTacticDto.setTypeCode( tacticType.getTypeCode() );
            gainLossTacticDto.setPlanTactic( tacticType.isPlanTactic() );
        }

        return gainLossTacticDto;
    }

    @Override
    public GainLossTactic toTactic(GainLossTacticDto tacticDto) {
        if ( tacticDto == null ) {
            return null;
        }

        GainLossTactic gainLossTactic = new GainLossTactic();

        gainLossTactic.setId( tacticDto.getId() );
        gainLossTactic.setPlanId( tacticDto.getPlanId() );
        gainLossTactic.setPeriodCondition( tacticDto.getPeriodCondition() );
        gainLossTactic.setActivated( tacticDto.getActivated() );
        List<Rule> list = tacticDto.getRuleList();
        if ( list != null ) {
            gainLossTactic.setRuleList( new ArrayList<Rule>( list ) );
        }
        else {
            gainLossTactic.setRuleList( null );
        }

        return gainLossTactic;
    }

    @Override
    public ActivatedGainLossTactic toActivatedGainLossTactic(GainLossTactic gainLossTactic, Rule rule, AssetHistory assetHistory) {
        if ( gainLossTactic == null && rule == null && assetHistory == null ) {
            return null;
        }

        ActivatedGainLossTactic activatedGainLossTactic = new ActivatedGainLossTactic();

        if ( gainLossTactic != null ) {
            activatedGainLossTactic.setPeriodCondition( gainLossTactic.getPeriodCondition() );
        }
        if ( rule != null ) {
            activatedGainLossTactic.setRangeCondition( rule.getRangeCondition() );
            activatedGainLossTactic.setOperation( rule.getOperation() );
        }
        if ( assetHistory != null ) {
            activatedGainLossTactic.setPosIncomeRatePct( assetHistory.getPosIncomeRatePct() );
        }

        return activatedGainLossTactic;
    }
}
