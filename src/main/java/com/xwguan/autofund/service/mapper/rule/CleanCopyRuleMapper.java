package com.xwguan.autofund.service.mapper.rule;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.xwguan.autofund.entity.plan.rule.Rule;

@Mapper(uses = { CleanCopyRangeConditionMapper.class, CleanCopyOperationMapper.class, CleanCopySuppressConditionMapper.class })
public interface CleanCopyRuleMapper {

    @Mappings({
        @Mapping(target = "id", constant = "-1"),
        @Mapping(target = "tacticId", constant = "-1"),

    })
    Rule cleanCopy(Rule source);

    List<Rule> cleanCopyList(List<Rule> source);
}
