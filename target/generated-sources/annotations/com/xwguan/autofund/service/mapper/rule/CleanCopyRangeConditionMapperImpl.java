package com.xwguan.autofund.service.mapper.rule;

import com.xwguan.autofund.entity.plan.rule.RangeCondition;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyRangeConditionMapperImpl implements CleanCopyRangeConditionMapper {

    @Override
    public RangeCondition cleanCopy(RangeCondition source) {
        if ( source == null ) {
            return null;
        }

        RangeCondition rangeCondition = new RangeCondition();

        rangeCondition.setBoundaryLeft( source.getBoundaryLeft() );
        rangeCondition.setBoundaryRight( source.getBoundaryRight() );
        rangeCondition.setRangeUnit( source.getRangeUnit() );

        rangeCondition.setRuleId( Long.parseLong( "-1" ) );
        rangeCondition.setId( Long.parseLong( "-1" ) );

        return rangeCondition;
    }
}
