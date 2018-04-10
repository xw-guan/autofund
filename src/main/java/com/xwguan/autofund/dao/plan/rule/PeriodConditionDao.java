package com.xwguan.autofund.dao.plan.rule;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.rule.PeriodCondition;
import com.xwguan.autofund.enums.TacticTypeEnum;

/**
 * 
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-11
 */
public interface PeriodConditionDao extends BaseDao<PeriodCondition>, CUDBatchDao<PeriodCondition> {

    PeriodCondition getByTacticIdAndType(@Param("tacticId") Long tacticId,
        @Param("tacticType") TacticTypeEnum tacticType, @Param("page") Page page);

    int deleteByTacticIdAndType(@Param("tacticId") Long tacticId, @Param("tacticType") TacticTypeEnum tacticType);

}
