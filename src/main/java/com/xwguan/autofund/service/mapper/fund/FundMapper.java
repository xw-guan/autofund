package com.xwguan.autofund.service.mapper.fund;

import java.util.List;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.dto.fund.LatestFundDto;
import com.xwguan.autofund.entity.fund.Fund;

@Mapper
@DecoratedWith(FundMapperDecorator.class)
public interface FundMapper {
    @Mappings({
        @Mapping(target = "id", source = "fund.id"),
        @Mapping(target = "latestFundHistory", ignore = true),
    })
    LatestFundDto toLatestFundDto(Fund fund);
    
    List<LatestFundDto> toLatestFundDtos(List<Fund> funds);
}
