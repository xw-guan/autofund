package com.xwguan.autofund.service.mapper.plan;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.service.mapper.account.CleanCopyAccountMapper;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:01+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyPlanMapperImpl implements CleanCopyPlanMapper {

    @Autowired
    private CleanCopyPositionMapper cleanCopyPositionMapper;
    @Autowired
    private CleanCopyAccountMapper cleanCopyAccountMapper;

    @Override
    public Plan cleanCopy(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        Plan plan1 = new Plan();

        plan1.setName( plan.getName() );
        plan1.setAccount( cleanCopyAccountMapper.cleanCopy( plan.getAccount() ) );
        plan1.setPositionList( cleanCopyPositionMapper.cleanCopyList( plan.getPositionList() ) );
        plan1.setExecutionMode( plan.getExecutionMode() );

        plan1.setId( Long.parseLong( "-1" ) );
        plan1.setUserId( Long.parseLong( "-1" ) );
        plan1.setActivated( Boolean.parseBoolean( "true" ) );

        return plan1;
    }
}
