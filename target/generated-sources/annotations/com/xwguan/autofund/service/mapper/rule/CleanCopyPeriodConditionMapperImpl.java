package com.xwguan.autofund.service.mapper.rule;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:02+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyPeriodConditionMapperImpl implements CleanCopyPeriodConditionMapper {

    @Override
    public PeriodCondition cleanCopy(PeriodCondition source) {
        if ( source == null ) {
            return null;
        }

        PeriodCondition periodCondition = new PeriodCondition();

        periodCondition.setTacticType( source.getTacticType() );
        periodCondition.setPeriod( source.getPeriod() );
        periodCondition.setDayOfPeriod( source.getDayOfPeriod() );

        periodCondition.setId( Long.parseLong( "-1" ) );
        periodCondition.setTacticId( Long.parseLong( "-1" ) );

        return periodCondition;
    }
}
