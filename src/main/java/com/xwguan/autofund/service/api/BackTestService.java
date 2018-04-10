package com.xwguan.autofund.service.api;

import java.time.LocalDate;

import com.xwguan.autofund.entity.plan.Plan;
import com.xwguan.autofund.entity.plan.backtest.PlanBackTestResult;
import com.xwguan.autofund.exception.plan.BackTestServiceException;

/**
 * 回测服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public interface BackTestService {
    /**
     * 回测
     * 
     * @param plan 计划
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 回测结果
     * @throws BackTestServiceException 1. 参数为null. 2. plan不完整: account为null, 各list为null
     */
    PlanBackTestResult backTest(Plan plan, LocalDate startDate, LocalDate endDate) throws BackTestServiceException;
    
    /**
     * 回测
     * 
     * @param planId 计划id
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 回测结果
     * @throws BackTestServiceException 1. 参数为null. 2. plan不完整: account为null, 各list为null
     */
    PlanBackTestResult backTest(Long planId, LocalDate startDate, LocalDate endDate) throws BackTestServiceException;
}
