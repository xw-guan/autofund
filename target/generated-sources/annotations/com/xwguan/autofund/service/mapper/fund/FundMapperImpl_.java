package com.xwguan.autofund.service.mapper.fund;

import com.xwguan.autofund.dto.fund.LatestFundDto;
import com.xwguan.autofund.entity.fund.Fund;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T02:55:36+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class FundMapperImpl_ implements FundMapper {

    @Override
    public LatestFundDto toLatestFundDto(Fund fund) {
        if ( fund == null ) {
            return null;
        }

        LatestFundDto latestFundDto = new LatestFundDto();

        latestFundDto.setId( fund.getId() );
        latestFundDto.setCode( fund.getCode() );
        latestFundDto.setName( fund.getName() );

        return latestFundDto;
    }

    @Override
    public List<LatestFundDto> toLatestFundDtos(List<Fund> funds) {
        if ( funds == null ) {
            return null;
        }

        List<LatestFundDto> list = new ArrayList<LatestFundDto>( funds.size() );
        for ( Fund fund : funds ) {
            list.add( toLatestFundDto( fund ) );
        }

        return list;
    }
}
