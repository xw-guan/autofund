package com.xwguan.autofund.dao.plan.rule;

import org.apache.ibatis.annotations.Param;

import com.xwguan.autofund.dao.common.BaseDao;
import com.xwguan.autofund.dao.common.CUDBatchDao;
import com.xwguan.autofund.dto.common.Page;
import com.xwguan.autofund.entity.plan.rule.Operation;

/**
 * 
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-11
 */
public interface OperationDao extends BaseDao<Operation>, CUDBatchDao<Operation> {
    
    Operation getByRuleId(@Param("ruleId") Long ruleId, @Param("page") Page page);
    
    int deleteByRuleId(@Param("ruleId") Long ruleId);
    
}
