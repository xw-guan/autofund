package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.IndexChangeTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedIndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
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
public class IndexChangeTacticMapperImpl implements IndexChangeTacticMapper {

    @Override
    public IndexChangeTacticDto toTacticDto(IndexChangeTactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        IndexChangeTacticDto indexChangeTacticDto = new IndexChangeTacticDto();

        if ( tactic != null ) {
            indexChangeTacticDto.setId( tactic.getId() );
            indexChangeTacticDto.setPlanId( tactic.getPlanId() );
            indexChangeTacticDto.setPeriodCondition( tactic.getPeriodCondition() );
            indexChangeTacticDto.setActivated( tactic.getActivated() );
            List<Rule> list = tactic.getRuleList();
            if ( list != null ) {
                indexChangeTacticDto.setRuleList( new ArrayList<Rule>( list ) );
            }
            else {
                indexChangeTacticDto.setRuleList( null );
            }
            indexChangeTacticDto.setInTradeDays( tactic.getInTradeDays() );
        }
        if ( tacticType != null ) {
            indexChangeTacticDto.setName( tacticType.getTacticName() );
            indexChangeTacticDto.setTypeCode( tacticType.getTypeCode() );
            indexChangeTacticDto.setPlanTactic( tacticType.isPlanTactic() );
        }

        return indexChangeTacticDto;
    }

    @Override
    public IndexChangeTactic toTactic(IndexChangeTacticDto tacticDto) {
        if ( tacticDto == null ) {
            return null;
        }

        IndexChangeTactic indexChangeTactic = new IndexChangeTactic();

        indexChangeTactic.setId( tacticDto.getId() );
        indexChangeTactic.setPlanId( tacticDto.getPlanId() );
        indexChangeTactic.setPeriodCondition( tacticDto.getPeriodCondition() );
        indexChangeTactic.setActivated( tacticDto.getActivated() );
        List<Rule> list = tacticDto.getRuleList();
        if ( list != null ) {
            indexChangeTactic.setRuleList( new ArrayList<Rule>( list ) );
        }
        else {
            indexChangeTactic.setRuleList( null );
        }
        indexChangeTactic.setInTradeDays( tacticDto.getInTradeDays() );

        return indexChangeTactic;
    }

    @Override
    public ActivatedIndexChangeTactic toActivatedIndexChangeTactic(IndexChangeTactic tactic, Rule rule, Double startValue, Double endValue, Double changePct) {
        if ( tactic == null && rule == null && startValue == null && endValue == null && changePct == null ) {
            return null;
        }

        ActivatedIndexChangeTactic activatedIndexChangeTactic = new ActivatedIndexChangeTactic();

        if ( tactic != null ) {
            activatedIndexChangeTactic.setPeriodCondition( tactic.getPeriodCondition() );
            activatedIndexChangeTactic.setInTradeDays( tactic.getInTradeDays() );
        }
        if ( rule != null ) {
            activatedIndexChangeTactic.setRangeCondition( rule.getRangeCondition() );
            activatedIndexChangeTactic.setOperation( rule.getOperation() );
        }
        if ( startValue != null ) {
            activatedIndexChangeTactic.setStartValue( startValue );
        }
        if ( endValue != null ) {
            activatedIndexChangeTactic.setEndValue( endValue );
        }
        if ( changePct != null ) {
            activatedIndexChangeTactic.setChangePct( changePct );
        }

        return activatedIndexChangeTactic;
    }
}
