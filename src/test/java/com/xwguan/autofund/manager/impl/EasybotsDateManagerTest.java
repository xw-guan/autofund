package com.xwguan.autofund.manager.impl;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.manager.api.DateManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class EasybotsDateManagerTest {

    @Autowired
    private DateManager dateManager;
    
    @Test
    public void testListNonWeekendHoliday() throws IOException {
        System.out.println(dateManager.listNonWeekendHoliday(2017));
    }
    
}
