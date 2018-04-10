package com.xwguan.autofund.dao.plan.rule;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.enums.TradeUnitEnum;
import com.xwguan.autofund.service.template.plan.OperationTemplate;
import com.xwguan.autofund.service.template.plan.RuleTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class OperationDaoTest {

    @Autowired
    private OperationDao dao;

    @Autowired
    private OperationTemplate template;

    @Autowired
    private RuleTemplate ruleTemplate;

    @Test // passed
    public void testGetByRuleId() {
        Operation byRuleId = dao.getByRuleId(1L, null);
        System.out.println(byRuleId);
        assertNotNull(byRuleId);
    }

    // @Test
    public void testDeleteByRuleId() {
        fail("Not yet implemented"); // TODO
    }

    @Test // passed
    public void testGetById() {
        Operation e = dao.getById(1);
        System.out.println(e);
        assertNotNull(e);
    }

    // @Test // passed
    public void testCUD() {
        Operation e = template.ignore();
        int insertAndSetId = dao.insertAndSetId(e);
        assertTrue(insertAndSetId == 1 && e.getId() > 0);

        e.setUnit(TradeUnitEnum.PERCENT);
        int update = dao.update(e);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(e.getId());
        assertTrue(deleteById == 1);
    }

    // @Test // passed
    public void testCUDBatch() {
        List<Operation> list = ruleTemplate.defaultRuleList(TacticTypeEnum.GAIN_LOSS_TACTIC).stream()
            .map(r -> r.getOperation())
            .collect(Collectors.toList());
        long i = 100; // 每次测试必须改
        for (Operation o : list) {
            o.setRuleId(i++);
        }
        int size = list.size();

        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == size);
        list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

        list.parallelStream().forEach(e -> e.setUnit(TradeUnitEnum.PERCENT));
        int updateBatch = dao.updateBatch(list);
        assertTrue(updateBatch == size * 2);

        List<Long> ids = list.stream().map(Operation::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == size);

    }

}
