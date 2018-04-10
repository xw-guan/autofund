package com.xwguan.autofund.dao.plan.rule;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.rule.Rule;
import com.xwguan.autofund.enums.TacticTypeEnum;

/**
 * 
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-11
 */
public interface RuleDao extends BaseDao<Rule>, CDBatchDao<Rule> {

    List<Long> listIdByTacticIdAndType(@Param("tacticId") Long tacticId,
        @Param("tacticType") TacticTypeEnum tacticType);

    List<Rule> listByTacticIdAndType(@Param("tacticId") Long tacticId,
        @Param("tacticType") TacticTypeEnum tacticType, @Param("page") Page page);

    int deleteByTacticIdAndType(@Param("tacticId") Long tacticId, @Param("tacticType") TacticTypeEnum tacticType);

}
