package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.NavChangeTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedNavChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.NavChangeTactic;
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
public class NavChangeTacticMapperImpl implements NavChangeTacticMapper {

    @Override
    public NavChangeTacticDto toTacticDto(NavChangeTactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        NavChangeTacticDto navChangeTacticDto = new NavChangeTacticDto();

        if ( tactic != null ) {
            navChangeTacticDto.setId( tactic.getId() );
            navChangeTacticDto.setPlanId( tactic.getPlanId() );
            navChangeTacticDto.setPeriodCondition( tactic.getPeriodCondition() );
            navChangeTacticDto.setActivated( tactic.getActivated() );
            List<Rule> list = tactic.getRuleList();
            if ( list != null ) {
                navChangeTacticDto.setRuleList( new ArrayList<Rule>( list ) );
            }
            else {
                navChangeTacticDto.setRuleList( null );
            }
            navChangeTacticDto.setInTradeDays( tactic.getInTradeDays() );
        }
        if ( tacticType != null ) {
            navChangeTacticDto.setName( tacticType.getTacticName() );
            navChangeTacticDto.setTypeCode( tacticType.getTypeCode() );
            navChangeTacticDto.setPlanTactic( tacticType.isPlanTactic() );
        }

        return navChangeTacticDto;
    }

    @Override
    public NavChangeTactic toTactic(NavChangeTacticDto tacticDto) {
        if ( tacticDto == null ) {
            return null;
        }

        NavChangeTactic navChangeTactic = new NavChangeTactic();

        navChangeTactic.setId( tacticDto.getId() );
        navChangeTactic.setPlanId( tacticDto.getPlanId() );
        navChangeTactic.setPeriodCondition( tacticDto.getPeriodCondition() );
        navChangeTactic.setActivated( tacticDto.getActivated() );
        List<Rule> list = tacticDto.getRuleList();
        if ( list != null ) {
            navChangeTactic.setRuleList( new ArrayList<Rule>( list ) );
        }
        else {
            navChangeTactic.setRuleList( null );
        }
        navChangeTactic.setInTradeDays( tacticDto.getInTradeDays() );

        return navChangeTactic;
    }

    @Override
    public ActivatedNavChangeTactic toActivatedNavChangeTactic(NavChangeTactic tactic, Rule rule, Double startValue, Double endValue, Double changePct) {
        if ( tactic == null && rule == null && startValue == null && endValue == null && changePct == null ) {
            return null;
        }

        ActivatedNavChangeTactic activatedNavChangeTactic = new ActivatedNavChangeTactic();

        if ( tactic != null ) {
            activatedNavChangeTactic.setPeriodCondition( tactic.getPeriodCondition() );
            activatedNavChangeTactic.setInTradeDays( tactic.getInTradeDays() );
        }
        if ( rule != null ) {
            activatedNavChangeTactic.setRangeCondition( rule.getRangeCondition() );
            activatedNavChangeTactic.setOperation( rule.getOperation() );
        }
        if ( startValue != null ) {
            activatedNavChangeTactic.setStartValue( startValue );
        }
        if ( endValue != null ) {
            activatedNavChangeTactic.setEndValue( endValue );
        }
        if ( changePct != null ) {
            activatedNavChangeTactic.setChangePct( changePct );
        }

        return activatedNavChangeTactic;
    }
}
