package com.xwguan.autofund.service.handler.rule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.Operation;
import com.xwguan.autofund.service.handler.AbstractEntityHandler;
import com.xwguan.autofund.service.handler.CleanlyCopyable;
import com.xwguan.autofund.service.mapper.rule.CleanCopyOperationMapper;

/**
 * 操作处理者
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
@Component
public class OperationHandler extends AbstractEntityHandler<Operation> implements CleanlyCopyable<Operation>{

    @Autowired
    private CleanCopyOperationMapper mapper;
    
    @Override
    public OperationHandler handle(Operation entity) {
        super.handle(entity);
        return this;
    }

    @Override
    public Operation cleanCopy() {
        return mapper.cleanCopy(getOperation());
    }
    
    public Operation getOperation() {
        return getEntity();
    }
    
}
