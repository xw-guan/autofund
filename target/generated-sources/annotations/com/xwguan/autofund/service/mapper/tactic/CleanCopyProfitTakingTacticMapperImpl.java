package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.entity.plan.tactic.ProfitTakingTactic;
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
public class CleanCopyProfitTakingTacticMapperImpl implements CleanCopyProfitTakingTacticMapper {

    @Autowired
    private CleanCopyRuleMapper cleanCopyRuleMapper;
    @Autowired
    private CleanCopyPeriodConditionMapper cleanCopyPeriodConditionMapper;

    @Override
    public ProfitTakingTactic cleanCopy(ProfitTakingTactic source) {
        if ( source == null ) {
            return null;
        }

        ProfitTakingTactic profitTakingTactic = new ProfitTakingTactic();

        profitTakingTactic.setPeriodCondition( cleanCopyPeriodConditionMapper.cleanCopy( source.getPeriodCondition() ) );
        profitTakingTactic.setRuleList( cleanCopyRuleMapper.cleanCopyList( source.getRuleList() ) );

        profitTakingTactic.setPlanId( Long.parseLong( "-1" ) );
        profitTakingTactic.setId( Long.parseLong( "-1" ) );
        profitTakingTactic.setActivated( Boolean.parseBoolean( "true" ) );

        return profitTakingTactic;
    }
}
