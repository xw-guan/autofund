package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.entity.plan.tactic.PeriodBuyTactic;
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
public class CleanCopyPeriodBuyTacticMapperImpl implements CleanCopyPeriodBuyTacticMapper {

    @Autowired
    private CleanCopyRuleMapper cleanCopyRuleMapper;
    @Autowired
    private CleanCopyPeriodConditionMapper cleanCopyPeriodConditionMapper;

    @Override
    public PeriodBuyTactic cleanCopy(PeriodBuyTactic source) {
        if ( source == null ) {
            return null;
        }

        PeriodBuyTactic periodBuyTactic = new PeriodBuyTactic();

        periodBuyTactic.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( source.getPeriodCondition() ) );
        periodBuyTactic.setRuleList( cleanCopyRuleMapper.cleanCopyList( source.getRuleList() ) );

        periodBuyTactic.setPlanId( Long.parseLong( "-1" ) );
        periodBuyTactic.setId( Long.parseLong( "-1" ) );
        periodBuyTactic.setActivated( Boolean.parseBoolean( "true" ) );

        return periodBuyTactic;
    }
}
