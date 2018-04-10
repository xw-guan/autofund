package com.xwguan.autofund.dao.plan.rule;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.enums.TacticTypeEnum;
import com.xwguan.autofund.service.template.plan.RuleTemplate;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class RuleDaoTest {

    @Autowired
    private RuleDao dao;

    @Autowired
    private RuleTemplate template;

     @Test // passed, current data may cause assert fail
    public void testListByTacticId() {
        List<Rule> listByTacticId = dao.listByTacticIdAndType(666L, TacticTypeEnum.GAIN_LOSS_TACTIC, null);
        List<Rule> listByTacticId2 = dao.listByTacticIdAndType(667L, TacticTypeEnum.GAIN_LOSS_TACTIC, null);
        assertTrue(listByTacticId.size() == 16);
        assertTrue(listByTacticId2.size() == 1);
    }

//     @Test
    public void testDeleteByTacticId() {
        fail("Not yet implemented"); // TODO
    }

//     @Test // passed
    public void testGetById() {
         Rule e = dao.getById(792);
         System.out.println(e);
         assertNotNull(e);
    }

//     @Test // passed
    public void testCUD() {
        Rule rule = template.ignore(TacticTypeEnum.GAIN_LOSS_TACTIC);
        rule.setTacticId(1L);
        int insertAndSetId = dao.insertAndSetId(rule);
        assertTrue(insertAndSetId == 1 && rule.getId() > 0);

        rule.setTacticId(100L);
        int update = dao.update(rule);
        assertTrue(update == 1);

        int deleteById = dao.deleteById(rule.getId());
        assertTrue(deleteById == 1);
    }

//     @Test // passed
    public void testCUDBatch() {
        List<Rule> list = new ArrayList<>();
//        for (TacticTypeEnum tt : TacticTypeEnum.values()) {
//            List<Rule> rules = template.defaultRuleList(tt);
//            rules.stream().forEach(r -> r.setTacticId(666L));
//            list.addAll(rules);
//            Rule rule = template.of(tt, null, null, null);
//            rule.setTacticId(667L);
//            list.add(rule);
//        }
        list.addAll(template.defaultRuleList(TacticTypeEnum.GAIN_LOSS_TACTIC));
        int size = list.size();
        
        int insertAndSetIdBatch = dao.insertAndSetIdBatch(list);
        assertTrue(insertAndSetIdBatch == size);
        list.parallelStream().forEach(e -> assertTrue(e.getId() > 0));

        List<Long> ids = list.stream().map(Rule::getId).collect(Collectors.toList());
        int deleteBatch = dao.deleteByIdListBatch(ids);
        assertTrue(deleteBatch == size);

    }

}
