package com.xwguan.autofund.service.mapper.scheme;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class PlanTradeSchemeMapperImpl implements PlanTradeSchemeMapper {

    @Override
    public PlanTradeScheme toPlanTradeScheme(Plan plan, List<ActivatedTactic> activatedPlanTacticList, List<PositionTradeScheme> positionTradeSchemeList, LocalDate date, Double totalBuy) {

        PlanTradeScheme planTradeScheme = new PlanTradeScheme();

        if ( plan != null ) {
            planTradeScheme.setPlan( plan );
        }
        if ( activatedPlanTacticList != null ) {
            List<ActivatedTactic> list = activatedPlanTacticList;
            if ( list != null ) {
                planTradeScheme.setActivatedPlanTacticList( new ArrayList<ActivatedTactic>( list ) );
            }
            else {
                planTradeScheme.setActivatedPlanTacticList( new ArrayList<ActivatedTactic>() );
            }
        }
        if ( positionTradeSchemeList != null ) {
            List<PositionTradeScheme> list1 = positionTradeSchemeList;
            if ( list1 != null ) {
                planTradeScheme.setPositionTradeSchemeList( new ArrayList<PositionTradeScheme>( list1 ) );
            }
            else {
                planTradeScheme.setPositionTradeSchemeList( new ArrayList<PositionTradeScheme>() );
            }
        }
        if ( date != null ) {
            planTradeScheme.setDate( date );
        }
        if ( totalBuy != null ) {
            planTradeScheme.setTotalBuy( totalBuy );
        }

        return planTradeScheme;
    }
}
