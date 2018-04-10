package com.xwguan.autofund.dao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xwguan.autofund.dao.date.NonWeekendHolidayDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/spring-dao.xml" })
public class NonWeekendHolidayDaoTest {

    @Autowired
    private NonWeekendHolidayDao nonWeekendHolidayDao;

    private List<LocalDate> testData = Arrays.asList(
        LocalDate.of(2017, 1, 1)
        , LocalDate.of(2017, 2, 14)
        , LocalDate.of(2017, 5, 1)
        , LocalDate.of(2017, 10, 1)
        , LocalDate.of(2017, 10, 2)
        , LocalDate.of(2018, 1, 1)
    );

    @Test
    public void testListHolidayOfYearAndMonth() {
        System.out.println(nonWeekendHolidayDao.listNonWeekendHolidayOfMonth(2017, 10));
    }

    @Test
    public void testListHolidayOfYear() {
        System.out.println(nonWeekendHolidayDao.listNonWeekendHolidayOfYear(2017));
    }

    @Test
    public void testInsertHoliday() {
        System.out.println(nonWeekendHolidayDao.insertNonWeekendHoliday(testData));
    }

}
