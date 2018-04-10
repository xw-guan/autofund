package com.xwguan.autofund.service.mapper.scheme;

import java.time.LocalDate;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;

import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.entity.plan.scheme.PositionTradeScheme;
import com.xwguan.autofund.entity.plan.scheme.activatedTactic.ActivatedTactic;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface PositionTradeSchemeMapper {
    PositionTradeScheme toPositionTradeScheme(Position position, Double tradeValue,
        List<ActivatedTactic> activatedTacticList, LocalDate date);
}
