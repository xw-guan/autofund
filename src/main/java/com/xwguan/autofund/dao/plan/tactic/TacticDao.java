package com.xwguan.autofund.dao.plan.tactic;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.entity.plan.tactic.Tactic;

/**
 * 策略Dao
 * 
 * @param <T> 策略子类
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-12
 */
public interface TacticDao<T extends Tactic> extends BaseDao<Tactic> {

    /**
     * 根据id获取, 包含Rule, Condition, Operation
     */
    @Override
    T getById(long id);

    /**
     * 根据计划id获取, 包含Rule, Condition, Operation
     * 
     * @param planId
     * @return
     */
    T getByPlanId(@Param("planId") Long planId);

    /**
     * 根据计划id获取策略id
     * 
     * @param planId
     * @return
     */
    Long getIdByPlanId(@Param("planId") Long planId);

    int deleteByPlanId(@Param("planId") Long planId);

    int setActivated(@Param("id") Long id, @Param("activated") Boolean activated);
}
