package com.xwguan.autofund.service.mapper.tactic;

import org.mapstruct.Mapper;
import com.xwguan.autofund.entity.plan.tactic.MATactic;
import com.xwguan.autofund.service.mapper.rule.CleanCopyPeriodConditionMapper;
import com.xwguan.autofund.service.mapper.rule.CleanCopyRuleMapper;

@Mapper(uses = { CleanCopyRuleMapper.class, CleanCopyPeriodConditionMapper.class })
public interface CleanCopyMATacticMapper extends CleanCopyTacticMapper<MATactic> {
}
