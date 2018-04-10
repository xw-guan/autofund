package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
import com.xwguan.autofund.service.mapper.rule.CleanCopyPeriodConditionMapper;
import com.xwguan.autofund.service.mapper.rule.CleanCopyRuleMapper;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyGainLossTacticMapperImpl implements CleanCopyGainLossTacticMapper {

    @Autowired
    private CleanCopyRuleMapper cleanCopyRuleMapper;
    @Autowired
    private CleanCopyPeriodConditionMapper cleanCopyPeriodConditionMapper;

    @Override
    public GainLossTactic cleanCopy(GainLossTactic source) {
        if ( source == null ) {
            return null;
        }

        GainLossTactic gainLossTactic = new GainLossTactic();

        gainLossTactic.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( source.getPeriodCondition() ) );
        gainLossTactic.setRuleList( cleanCopyRuleMapper.cleanCopyList( source.getRuleList() ) );

        gainLossTactic.setPlanId( Long.parseLong( "-1" ) );
        gainLossTactic.setId( Long.parseLong( "-1" ) );
        gainLossTactic.setActivated( Boolean.parseBoolean( "true" ) );

        return gainLossTactic;
    }
}
