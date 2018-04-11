package com.xwguan.autofund.service.mapper.rule;

import com.xwguan.autofund.entity.plan.rule.Rule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-11T13:56:02+0800",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.13.0.v20170516-1929, environment: Java 1.8.0_151 (Oracle Corporation)"
)
@Component
public class CleanCopyRuleMapperImpl implements CleanCopyRuleMapper {

    @Autowired
    private CleanCopyRangeConditionMapper cleanCopyRangeConditionMapper;
    @Autowired
    private CleanCopyOperationMapper cleanCopyOperationMapper;
    @Autowired
    private CleanCopySuppressConditionMapper cleanCopySuppressConditionMapper;

    @Override
    public Rule cleanCopy(Rule source) {
        if ( source == null ) {
            return null;
        }

        Rule rule = new Rule();

        rule.setTacticType( source.getTacticType() );
        rule.setRangeCondition( cleanCopyRangeConditionMapper.cleanCopy( source.getRangeCondition() ) );
        rule.setSuppressCondition( cleanCopySuppressConditionMapper.cleanCopy( source.getSuppressCondition() ) );
        rule.setOperation( cleanCopyOperationMapper.cleanCopy( source.getOperation() ) );

        rule.setId( Long.parseLong( "-1" ) );
        rule.setTacticId( Long.parseLong( "-1" ) );

        return rule;
    }

    @Override
    public List<Rule> cleanCopyList(List<Rule> source) {
        if ( source == null ) {
            return null;
        }

        List<Rule> list = new ArrayList<Rule>( source.size() );
        for ( Rule rule : source ) {
            list.add( cleanCopy( rule ) );
        }

        return list;
    }
}
