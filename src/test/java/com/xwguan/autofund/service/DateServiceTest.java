package com.xwguan.autofund.service;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.service.api.DateTimeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class DateServiceTest {

    @Autowired
    private DateTimeService dateTimeService;
    
//    @Test
    public void testListTradeDay() {
        System.out.println(dateTimeService.listTradeDay(2017, 10));
        System.out.println(dateTimeService.listTradeDay(2017));
    }

//    @Test
    public void testIsTradeDay() {
        System.out.println(dateTimeService.isTradeDay(LocalDate.now()));
    }

//    @Test
    public void testContainTradeDay() {
        
    }

    @Test
    public void testCountTradeDay() {
        System.out.println(dateTimeService.countTradeDay(LocalDate.now().minusDays(5), LocalDate.now()));
    }
    
//    @Test
    public void testUpdateTradeDay() {
        try {
            System.out.println(dateTimeService.updateTradeDay());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
