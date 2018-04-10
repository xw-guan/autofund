package com.xwguan.autofund.dao.stock;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.entity.stock.MA;
import com.xwguan.autofund.enums.MAEnum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class MADaoTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MADao maDao;

//    @Test
    public void testListNDayMAByDateRange() {
        List<MA> maList = maDao.listNDayMAByDateRange(1, LocalDate.of(2017, 10, 25), LocalDate.now(), MAEnum.MA250, null);
        logger.info("malList={}", maList);
    }

//    @Test
    public void testGetLatestMA() {
        MA ma = maDao.getLatestMA(1, MAEnum.MA250);
        logger.info("malList={}", ma);
    }
    
    @Test
    public void testGetMAofDate() {
        MA ma = maDao.getMAByStockIdAndDate(1, LocalDate.of(2017, 11, 14), MAEnum.MA250);
        logger.info("malList={}", ma);
    }
}
