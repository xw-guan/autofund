package com.xwguan.autofund.util;

import java.time.LocalDate;
import java.util.TreeMap;

import org.junit.Test;

public class FinanceUtilsTest {

    @Test
    public void testXirr() {
        TreeMap<LocalDate, Double> cashFlow = new TreeMap<LocalDate, Double>();
        cashFlow.put(LocalDate.of(2008, 1, 1), -10000D);
        cashFlow.put(LocalDate.of(2008, 4, 1), 2750D);
        cashFlow.put(LocalDate.of(2008, 10, 30), 4250D);
        cashFlow.put(LocalDate.of(2009, 2, 15), 3250D);
        cashFlow.put(LocalDate.of(2009, 4, 1), 2750D);
        try {
            System.out.println(FinanceUtils.xirr(cashFlow));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
