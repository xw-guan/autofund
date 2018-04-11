package com.xwguan.autofund.service.mapper.tactic;

import com.xwguan.autofund.dto.plan.tactic.TacticDto;
import com.xwguan.autofund.entity.plan.tactic.Tactic;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T23:39:44+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class TacticsMapperImpl implements TacticsMapper {

    @Override
    public List<TacticDto> toTacticDtoList(List<? extends Tactic> tacticList) {
        if ( tacticList == null ) {
            return null;
        }

        List<TacticDto> list = new ArrayList<TacticDto>( tacticList.size() );
        for ( Tactic tactic : tacticList ) {
            list.add( toTacticDto( tactic ) );
        }

        return list;
    }

    @Override
    public List<Tactic> toTacticList(List<TacticDto> tacticDtoList) {
        if ( tacticDtoList == null ) {
            return null;
        }

        List<Tactic> list = new ArrayList<Tactic>( tacticDtoList.size() );
        for ( TacticDto tacticDto : tacticDtoList ) {
            list.add( toTactic( tacticDto ) );
        }

        return list;
    }
}
