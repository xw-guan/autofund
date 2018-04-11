package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.MATacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedMATactic;
import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:02+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class MATacticMapperImpl implements MATacticMapper {

    @Override
    public MATacticDto toTacticDto(MATactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        MATacticDto mATacticDto = new MATacticDto();

        if ( tactic != null ) {
            mATacticDto.setId( tactic.getId() );
            mATacticDto.setPlanId( tactic.getPlanId() );
            mATacticDto.setPeriodCondition( tactic.getPeriodCondition() );
            mATacticDto.setActivated( tactic.getActivated() );
            List<Rule> list = tactic.getRuleList();
            if ( list != null ) {
                mATacticDto.setRuleList( new ArrayList<Rule>( list ) );
            }
            else {
                mATacticDto.setRuleList( null );
            }
            mATacticDto.setMa( tactic.getMa() );
        }
        if ( tacticType != null ) {
            mATacticDto.setName( tacticType.getTacticName() );
            mATacticDto.setTypeCode( tacticType.getTypeCode() );
            mATacticDto.setPlanTactic( tacticType.isPlanTactic() );
        }

        return mATacticDto;
    }

    @Override
    public MATactic toTactic(MATacticDto tacticDto) {
        if ( tacticDto == null ) {
            return null;
        }

        MATactic mATactic = new MATactic();

        mATactic.setId( tacticDto.getId() );
        mATactic.setPlanId( tacticDto.getPlanId() );
        mATactic.setPeriodCondition( tacticDto.getPeriodCondition() );
        mATactic.setActivated( tacticDto.getActivated() );
        List<Rule> list = tacticDto.getRuleList();
        if ( list != null ) {
            mATactic.setRuleList( new ArrayList<Rule>( list ) );
        }
        else {
            mATactic.setRuleList( null );
        }
        mATactic.setMa( tacticDto.getMa() );

        return mATactic;
    }

    @Override
    public ActivatedMATactic toActivatedMATactic(MATactic tactic, Rule rule, Double indexValue, Double maValue, Double deviationPct) {
        if ( tactic == null && rule == null && indexValue == null && maValue == null && deviationPct == null ) {
            return null;
        }

        ActivatedMATactic activatedMATactic = new ActivatedMATactic();

        if ( tactic != null ) {
            activatedMATactic.setPeriodCondition( tactic.getPeriodCondition() );
            activatedMATactic.setMa( tactic.getMa() );
        }
        if ( rule != null ) {
            activatedMATactic.setRangeCondition( rule.getRangeCondition() );
            activatedMATactic.setOperation( rule.getOperation() );
        }
        if ( indexValue != null ) {
            activatedMATactic.setIndexValue( indexValue );
        }
        if ( maValue != null ) {
            activatedMATactic.setMaValue( maValue );
        }
        if ( deviationPct != null ) {
            activatedMATactic.setDeviationPct( deviationPct );
        }

        return activatedMATactic;
    }
}
