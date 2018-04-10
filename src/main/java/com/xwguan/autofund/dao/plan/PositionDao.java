package com.xwguan.autofund.dao.plan;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.portfolio.Position;

/**
 * 
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-11
 */
public interface PositionDao extends BaseDao<Position>, CUDBatchDao<Position> {

    /**
     * 获取id对应的Position.
     * includeLatestAccountHistory为true时Account对象包含最新历史数据(没有对应历史数据时也可能为空列表),
     * includeLatestAccountHistory为false时Account对象为null
     * 
     * @param id
     * @param includeLatestAccountHistory 是否包含最新的历史数据
     * @return Position对象
     */
    Position getById(@Param("id") Long id, @Param("includeLatestAccountHistory") Boolean includeLatestAccountHistory);

    /**
     * 获取计划id对应的Position, 其中的Account只有最新历史数据或为空列表
     * includeLatestAccountHistory为true时Account对象包含最新历史数据(没有对应历史数据时也可能为空列表),
     * includeLatestAccountHistory为false时Account对象为null
     * 
     * @param planId
     * @param includeLatestAccountHistory 是否包含最新的历史数据
     * @param page 分页对象
     * @return Position对象列表
     */
    List<Position> listByPlanId(@Param("planId") Long planId,
        @Param("includeLatestAccountHistory") Boolean includeLatestAccountHistory, @Param("page") Page page);

    List<Long> listIdByPlanId(@Param("planId") Long planId);

    int deleteByPlanId(@Param("planId") Long planId);

    /**
     * 更新参考指数id(业务上来说, 其他字段不应该被更新, 只能删除)
     * 
     * @param id
     * @param refIndexId
     * @return
     */
    int updateRefIndexId(@Param("id") Long id, @Param("refIndexId") Integer refIndexId);

}
