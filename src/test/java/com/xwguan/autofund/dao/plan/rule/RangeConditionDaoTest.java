package com.xwguan.autofund.dao.plan.rule;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.rule.RangeCondition;
import com.xwguan.autofund.enums.ConditionUnitEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.template.plan.RangeConditionTemplate;
import com.xwguan.autofund.service.template.plan.RuleTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class RangeConditionDaoTest {

    @Autowired
    private RangeConditionDao dao;

    @Autowired
    private RangeConditionTemplate template;

    @Autowired
    private RuleTemplate ruleTemplate;

    @Test // passed
    public void testGetByRuleId() {
        RangeCondition byRuleId = dao.getByRuleId(1L, null);
        System.out.println(byRuleId);
        assertNotNull(byRuleId);
    }

    // @Test
    public void testDeleteByRuleId() {
        fail("Not yet implemented"); // TODO
    }

    @Test // passed
    public void testGetById() {
        RangeCondition e = dao.getById(1);
        System.out.println(e);
        assertNotNull(e);
    }

    // @Test // passed
    public void testCUD() {
        RangeCondition e = template.pct(0, 10);
        int insertAndSetId = dao.insertAndSetId(e);
        assertTrue(insertAndSetId == 1 && e.getId() > 0);

        e.setBoundaryRight(20D);
        int update = dao.update(e);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(e.getId());
        assertTrue(deleteById == 1);
    }

    // @Test // passed
    public void testCUDBatch() {
        List<RangeCondition> list = ruleTemplate.defaultRuleList(TacticTypeEnum.GAIN_LOSS_TACTIC).stream()
            .map(r -> r.getRangeCondition())
            .collect(Collectors.toList());
        long i = 100; // 每次测试必须改
        for (RangeCondition rc : list) {
            rc.setRuleId(i++);
        }
        int size = list.size();

        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == size);
        list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

        list.parallelStream().forEach(e -> e.setRangeUnit(ConditionUnitEnum.YUAN));
        int updateBatch = dao.updateBatch(list);
        assertTrue(updateBatch == size * 2);

        List<Long> ids = list.stream().map(RangeCondition::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == size);

    }

}
