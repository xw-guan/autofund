package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.IndexChangeTacticDto;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedIndexChangeTactic;
import com.xwguan.autofund.entity.plan.tactic.IndexChangeTactic;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.mapper.rule.CleanCopyPeriodConditionMapper;
import com.xwguan.autofund.service.mapper.rule.CleanCopyRuleMapper;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:02+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyIndexChangeTacticMapperImpl implements CleanCopyIndexChangeTacticMapper {

    @Autowired
    private CleanCopyRuleMapper cleanCopyRuleMapper;
    @Autowired
    private CleanCopyPeriodConditionMapper cleanCopyPeriodConditionMapper;

    @Override
    public IndexChangeTactic cleanCopy(IndexChangeTactic source) {
        if ( source == null ) {
            return null;
        }

        IndexChangeTactic indexChangeTactic = new IndexChangeTactic();

        indexChangeTactic.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( source.getPeriodCondition() ) );
        indexChangeTactic.setRuleList( cleanCopyRuleMapper.cleanCopyList( source.getRuleList() ) );
        indexChangeTactic.setInTradeDays( source.getInTradeDays() );

        indexChangeTactic.setPlanId( Long.parseLong( "-1" ) );
        indexChangeTactic.setId( Long.parseLong( "-1" ) );
        indexChangeTactic.setActivated( Boolean.parseBoolean( "true" ) );

        return indexChangeTactic;
    }

    @Override
    public ActivatedIndexChangeTactic toActivatedIndexChangeTactic(IndexChangeTactic tactic, Rule rule, Double startValue, Double endValue, Double changePct) {
        if ( tactic == null && rule == null && startValue == null && endValue == null && changePct == null ) {
            return null;
        }

        ActivatedIndexChangeTactic activatedIndexChangeTactic = new ActivatedIndexChangeTactic();

        if ( tactic != null ) {
            activatedIndexChangeTactic.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( tactic.getPeriodCondition() ) );
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

    @Override
    public IndexChangeTacticDto toTacticDto(IndexChangeTactic tactic, TacticTypeEnum tacticType) {
        if ( tactic == null && tacticType == null ) {
            return null;
        }

        IndexChangeTacticDto indexChangeTacticDto = new IndexChangeTacticDto();

        if ( tactic != null ) {
            indexChangeTacticDto.setId( tactic.getId() );
            indexChangeTacticDto.setPlanId( tactic.getPlanId() );
            indexChangeTacticDto.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( tactic.getPeriodCondition() ) );
            indexChangeTacticDto.setActivated( tactic.getActivated() );
            indexChangeTacticDto.setRuleList( cleanCopyRuleMapper.cleanCopyList( tactic.getRuleList() ) );
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
        indexChangeTactic.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( tacticDto.getPeriodCondition() ) );
        indexChangeTactic.setActivated( tacticDto.getActivated() );
        indexChangeTactic.setRuleList( cleanCopyRuleMapper.cleanCopyList( tacticDto.getRuleList() ) );
        indexChangeTactic.setInTradeDays( tacticDto.getInTradeDays() );

        return indexChangeTactic;
    }
}
