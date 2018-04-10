package com.xwguan.autofund.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.service.api.InitService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-*.xml" })
public class InitServiceTest {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private InitService initService;
    
    @Test
    public void testInitStockTable() {
        int success = initService.initStockTable();
        logger.info("initStockTable success={}", success);
    }

}
