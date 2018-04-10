package com.xwguan.autofund.service.mapper.plan;

import com.xwguan.autofund.dto.plan.LatestPlanDto;
import com.xwguan.autofund.dto.plan.PlanDto;
import com.xwguan.autofund.dto.plan.PlanInfoDto;
import com.xwguan.autofund.entity.plan.Plan;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class PlanMapperImpl_ implements PlanMapper {

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public PlanDto toPlanDto(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanDto planDto = new PlanDto();

        planDto.setPositions( positionMapper.toPositionDtoList( plan.getPositionList() ) );

        return planDto;
    }

    @Override
    public Plan toPlan(PlanDto planDto) {
        if ( planDto == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setPositionList( positionMapper.toPositionList( planDto.getPositions() ) );

        return plan;
    }

    @Override
    public PlanInfoDto toPlanInfoDto(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanInfoDto planInfoDto = new PlanInfoDto();

        planInfoDto.setId( plan.getId() );
        planInfoDto.setUserId( plan.getUserId() );
        planInfoDto.setName( plan.getName() );
        planInfoDto.setExecutionMode( plan.getExecutionMode() );
        planInfoDto.setActivated( plan.getActivated() );

        return planInfoDto;
    }

    @Override
    public Plan toPlan(PlanInfoDto planInfoDto) {
        if ( planInfoDto == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setId( planInfoDto.getId() );
        plan.setUserId( planInfoDto.getUserId() );
        plan.setName( planInfoDto.getName() );
        plan.setExecutionMode( planInfoDto.getExecutionMode() );
        plan.setActivated( planInfoDto.getActivated() );

        return plan;
    }

    @Override
    public void updatePlan(Plan plan, PlanInfoDto planInfoDto) {
        if ( planInfoDto == null ) {
            return;
        }

        plan.setId( planInfoDto.getId() );
        plan.setUserId( planInfoDto.getUserId() );
        plan.setName( planInfoDto.getName() );
        plan.setExecutionMode( planInfoDto.getExecutionMode() );
        plan.setActivated( planInfoDto.getActivated() );
    }

    @Override
    public LatestPlanDto toLatestPlanDto(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        LatestPlanDto latestPlanDto = new LatestPlanDto();

        latestPlanDto.setLatestPositions( positionMapper.toLatestPositionDtoList( plan.getPositionList() ) );

        return latestPlanDto;
    }
}
