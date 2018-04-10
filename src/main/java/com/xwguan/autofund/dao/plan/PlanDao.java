package com.xwguan.autofund.dao.plan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.Plan;

/**
 * 计划dao
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-16
 */
public interface PlanDao extends BaseDao<Plan>, CUDBatchDao<Plan> {

    /**
     * 其中, Account和各List均为空
     */
    List<Plan> listByUserId(@Param("userId") Integer userId, @Param("page") Page page);
    
    /**
     * 根据用户id列出所属的计划id
     */
    List<Long> listIdByUserId(@Param("userId") Integer userId);

    int deleteByUserId(@Param("userId") Integer userId);
    
}
