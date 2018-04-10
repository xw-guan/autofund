package com.xwguan.autofund.service.mapper.rule;

import com.xwguan.autofund.entity.plan.rule.Operation;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyOperationMapperImpl implements CleanCopyOperationMapper {

    @Override
    public Operation cleanCopy(Operation source) {
        if ( source == null ) {
            return null;
        }

        Operation operation = new Operation();

        operation.setTradeValue( source.getTradeValue() );
        operation.setUnit( source.getUnit() );

        operation.setRuleId( Long.parseLong( "-1" ) );
        operation.setId( Long.parseLong( "-1" ) );

        return operation;
    }
}
