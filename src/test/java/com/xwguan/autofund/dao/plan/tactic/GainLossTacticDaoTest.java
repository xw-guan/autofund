package com.xwguan.autofund.dao.plan.tactic;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.tactic.GainLossTactic;
import com.xwguan.autofund.service.template.plan.TacticTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class GainLossTacticDaoTest {

    @Autowired
    private GainLossTacticDao dao;

    @Autowired
    private TacticTemplate template;

    // @Test // passed
    public void testListByPlanId() {
        GainLossTactic e = dao.getByPlanId(666L);
        System.out.println(e);
        assertNotNull(e);
        assertTrue(e.getRuleList().size() == 16);
    }

    // @Test
    public void testDeleteByPlanId() {
        int deleteByPlanId = dao.deleteByPlanId(-1L);
        System.out.println(deleteByPlanId);
    }

    @Test // passed
    public void testGetById() {
        GainLossTactic e = dao.getById(666);
        System.out.println(e);
        assertNotNull(e);
        assertTrue(e.getRuleList().size() == 16);
    }

    // @Test // passed
    public void testCUD() {
        int year = 2018;
        int month = 1;
        int dayOfMonth = 1;
        GainLossTactic gainLossTactic = template.gainLossTactic(LocalDate.of(year, month, dayOfMonth));
        gainLossTactic.setPlanId(112L);
        int insertAndSetId = dao.insertAndSetId(gainLossTactic);
        assertTrue(insertAndSetId == 1 && gainLossTactic.getId() > 0);

        gainLossTactic.setPlanId(100L);
        int update = dao.update(gainLossTactic);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(gainLossTactic.getId());
        assertTrue(deleteById == 1);
    }

}
