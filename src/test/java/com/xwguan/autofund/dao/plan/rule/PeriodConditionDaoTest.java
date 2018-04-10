package com.xwguan.autofund.dao.plan.rule;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.enums.AutoInvestPeriodEnum;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.template.plan.PeriodConditionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class PeriodConditionDaoTest {

    @Autowired
    private PeriodConditionDao dao;

    @Autowired
    private PeriodConditionTemplate template;

    @Test // passed
    public void testGetByRuleId() {
        PeriodCondition e = dao.getByTacticIdAndType(666l, TacticTypeEnum.GAIN_LOSS_TACTIC, null);
        System.out.println(e);
        assertNotNull(e);
    }

    // @Test
    public void testDeleteByRuleId() {
        fail("Not yet implemented"); // TODO
    }

     @Test // passed
    public void testGetById() {
        PeriodCondition e = dao.getById(61);
        System.out.println(e);
        assertNotNull(e);
    }

     @Test // passed
    public void testCUD() {
        PeriodCondition e = template.monthly(TacticTypeEnum.GAIN_LOSS_TACTIC, LocalDate.now());
        int insertAndSetId = dao.insertAndSetId(e);
        assertTrue(insertAndSetId == 1 && e.getId() > 0);

        int update = dao.update(e);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(e.getId());
        assertTrue(deleteById == 1);
    }

     @Test // passed
    public void testCUDBatch() {

        int sizePerTactic = 5;
        int year = 2018;
        int month = 1;
        int dayOfMonth = 1;
        List<PeriodCondition> list = new ArrayList<>();
        for (TacticTypeEnum tt : TacticTypeEnum.values()) {
            long tacticId = 10;
            for (int i = 0; i < sizePerTactic; i++) {
                PeriodCondition item = template.bimonthly(tt, LocalDate.of(year, month, dayOfMonth++));
                item.setTacticId(tacticId++);
                list.add(item);
            }
        }
        int size = sizePerTactic * TacticTypeEnum.values().length;

        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == size);
        list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

        list.parallelStream().forEach(e -> e.setPeriod(AutoInvestPeriodEnum.WEEKLY));
        int updateBatch = dao.updateBatch(list);
        assertTrue(updateBatch == size);

        list.parallelStream().forEach(e -> e.setPeriod(AutoInvestPeriodEnum.BIMONTHLY));
        int updateOrInsertBatch = dao.updateOrInsertBatch(list);
        assertTrue(updateOrInsertBatch == size * 2);

        List<Long> ids = list.stream().map(PeriodCondition::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == size);

    }

}
