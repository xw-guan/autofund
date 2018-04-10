package com.xwguan.autofund.service.mapper.scheme;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PlanTradeSchemeMapper {
    PlanTradeScheme toPlanTradeScheme(Plan plan, List<ActivatedTactic> activatedPlanTacticList,
        List<PositionTradeScheme> positionTradeSchemeList, LocalDate date, Double totalBuy);
}
