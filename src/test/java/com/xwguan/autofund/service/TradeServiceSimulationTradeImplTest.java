package com.xwguan.autofund.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.portfolio.Position;
import com.xwguan.autofund.exception.io.InvalidFundCodeException;
import com.xwguan.autofund.exception.io.InvalidTickerSymbolException;
import com.xwguan.autofund.service.api.TradeService;
import com.xwguan.autofund.service.template.plan.PlanTemplate;
import com.xwguan.autofund.service.template.plan.PositionTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class TradeServiceSimulationTradeImplTest {

    @Autowired
    @Qualifier("tradeServiceSimulationImpl")
    private TradeService tradeService;

    @Autowired
    private PlanTemplate planTemplate;

    @Autowired
    private PositionTemplate positionTemplate;

    @Test
    public void testExecuteTradePlanTradeScheme() throws InvalidFundCodeException, InvalidTickerSymbolException {
        LocalDate date = LocalDate.of(2017, 12, 15);
        Plan plan = planTemplate.maPlan(date);
        Position position = positionTemplate.of("000001", "000001.SH");
        List<Position> positionList = new ArrayList<>();
        positionList.add(position);
        plan.setPositionList(positionList);
        try {
            tradeService.executeTrade(plan.handler().generateTradeScheme(date).get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testExecuteTradePositionTradeScheme() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testExecuteAfterTrade() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCancelTrade() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetRealTradeDetail() {
        fail("Not yet implemented"); // TODO
    }

    
}
