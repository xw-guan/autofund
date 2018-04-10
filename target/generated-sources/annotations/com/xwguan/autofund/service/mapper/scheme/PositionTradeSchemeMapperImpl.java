package com.xwguan.autofund.service.mapper.scheme;

import com.xwguan.autofund.entity.plan.portfolio.Position;
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
public class PositionTradeSchemeMapperImpl implements PositionTradeSchemeMapper {

    @Override
    public PositionTradeScheme toPositionTradeScheme(Position position, Double tradeValue, List<ActivatedTactic> activatedTacticList, LocalDate date) {

        PositionTradeScheme positionTradeScheme = new PositionTradeScheme();

        if ( position != null ) {
            positionTradeScheme.setPosition( position );
        }
        if ( tradeValue != null ) {
            positionTradeScheme.setTradeValue( tradeValue );
        }
        if ( activatedTacticList != null ) {
            List<ActivatedTactic> list = activatedTacticList;
            if ( list != null ) {
                positionTradeScheme.setActivatedTacticList( new ArrayList<ActivatedTactic>( list ) );
            }
            else {
                positionTradeScheme.setActivatedTacticList( new ArrayList<ActivatedTactic>() );
            }
        }
        if ( date != null ) {
            positionTradeScheme.setDate( date );
        }

        return positionTradeScheme;
    }
}
