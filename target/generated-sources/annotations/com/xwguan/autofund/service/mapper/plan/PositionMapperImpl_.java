package com.xwguan.autofund.service.mapper.plan;

import com.xwguan.autofund.dto.plan.LatestPositionDto;
import com.xwguan.autofund.dto.plan.PositionDto;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:01+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class PositionMapperImpl_ implements PositionMapper {

    @Override
    public PositionDto toPositionDto(Position position) {
        if ( position == null ) {
            return null;
        }

        PositionDto positionDto = new PositionDto();

        positionDto.setId( position.getId() );
        positionDto.setPlanId( position.getPlanId() );
        positionDto.setFundId( position.getFundId() );
        positionDto.setRefIndexId( position.getRefIndexId() );

        return positionDto;
    }

    @Override
    public List<PositionDto> toPositionDtoList(List<Position> positionList) {
        if ( positionList == null ) {
            return null;
        }

        List<PositionDto> list = new ArrayList<PositionDto>( positionList.size() );
        for ( Position position : positionList ) {
            list.add( toPositionDto( position ) );
        }

        return list;
    }

    @Override
    public Position toPosition(PositionDto positionDto) {
        if ( positionDto == null ) {
            return null;
        }

        Position position = new Position();

        position.setId( positionDto.getId() );
        position.setPlanId( positionDto.getPlanId() );
        position.setFundId( positionDto.getFundId() );
        position.setRefIndexId( positionDto.getRefIndexId() );

        return position;
    }

    @Override
    public List<Position> toPositionList(List<PositionDto> positionDtoList) {
        if ( positionDtoList == null ) {
            return null;
        }

        List<Position> list = new ArrayList<Position>( positionDtoList.size() );
        for ( PositionDto positionDto : positionDtoList ) {
            list.add( toPosition( positionDto ) );
        }

        return list;
    }

    @Override
    public LatestPositionDto toLatestPositionDto(Position position) {
        if ( position == null ) {
            return null;
        }

        LatestPositionDto latestPositionDto = new LatestPositionDto();

        latestPositionDto.setId( position.getId() );
        latestPositionDto.setPlanId( position.getPlanId() );

        return latestPositionDto;
    }

    @Override
    public List<LatestPositionDto> toLatestPositionDtoList(List<Position> positionList) {
        if ( positionList == null ) {
            return null;
        }

        List<LatestPositionDto> list = new ArrayList<LatestPositionDto>( positionList.size() );
        for ( Position position : positionList ) {
            list.add( toLatestPositionDto( position ) );
        }

        return list;
    }
}
