package com.xwguan.autofund.dao.plan;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.PlanHistory;

/**
 * 计划dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-16
 */
public interface PlanHistoryDao extends BaseDao<PlanHistory>, CUDBatchDao<PlanHistory> {

    PlanHistory getByPlanIdAndDate(@Param("planId") Long planId, @Param("date") LocalDate date);
    
    PlanHistory getLatestByPlanId(@Param("planId") Long planId);
    
    List<PlanHistory> listByPlanId(@Param("planId") Long planId, @Param("page") Page page);
    
    int deleteByPlanId(@Param("planId") Long planId);
}
