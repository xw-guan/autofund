package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.PeriodBuyTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedPeriodBuyTactic;
import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:03+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class PeriodBuyTacticMapperImpl implements PeriodBuyTacticMapper {

    @Override
    public PeriodBuyTacticDto toTacticDto(PeriodBuyTactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        PeriodBuyTacticDto periodBuyTacticDto = new PeriodBuyTacticDto();

        if ( tactic != null ) {
            periodBuyTacticDto.setId( tactic.getId() );
            periodBuyTacticDto.setPlanId( tactic.getPlanId() );
            periodBuyTacticDto.setPeriodCondition( tactic.getPeriodCondition() );
            periodBuyTacticDto.setActivated( tactic.getActivated() );
            List<Rule> list = tactic.getRuleList();
            if ( list != null ) {
                periodBuyTacticDto.setRuleList( new ArrayList<Rule>( list ) );
            }
            else {
                periodBuyTacticDto.setRuleList( null );
            }
        }
        if ( tacticType != null ) {
            periodBuyTacticDto.setName( tacticType.getTacticName() );
            periodBuyTacticDto.setTypeCode( tacticType.getTypeCode() );
            periodBuyTacticDto.setPlanTactic( tacticType.isPlanTactic() );
        }

        return periodBuyTacticDto;
    }

    @Override
    public PeriodBuyTactic toTactic(PeriodBuyTacticDto tacticDto) {
        if ( tacticDto == null ) {
            return null;
        }

        PeriodBuyTactic periodBuyTactic = new PeriodBuyTactic();

        periodBuyTactic.setId( tacticDto.getId() );
        periodBuyTactic.setPlanId( tacticDto.getPlanId() );
        periodBuyTactic.setPeriodCondition( tacticDto.getPeriodCondition() );
        periodBuyTactic.setActivated( tacticDto.getActivated() );
        List<Rule> list = tacticDto.getRuleList();
        if ( list != null ) {
            periodBuyTactic.setRuleList( new ArrayList<Rule>( list ) );
        }
        else {
            periodBuyTactic.setRuleList( null );
        }

        return periodBuyTactic;
    }

    @Override
    public ActivatedPeriodBuyTactic toActivatedTactic(PeriodBuyTactic periodBuyTactic, Rule rule) {
        if ( periodBuyTactic == null && rule == null ) {
            return null;
        }

        ActivatedPeriodBuyTactic activatedPeriodBuyTactic = new ActivatedPeriodBuyTactic();

        if ( periodBuyTactic != null ) {
            activatedPeriodBuyTactic.setPeriodCondition( periodBuyTactic.getPeriodCondition() );
        }
        if ( rule != null ) {
            activatedPeriodBuyTactic.setRangeCondition( rule.getRangeCondition() );
            activatedPeriodBuyTactic.setOperation( rule.getOperation() );
        }

        return activatedPeriodBuyTactic;
    }
}
