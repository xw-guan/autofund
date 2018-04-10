package com.xwguan.autofund.service.template.plan;

import org.springframework.stereotype.Component;

import com.xwguan.autofund.entity.plan.rule.SuppressCondition;

/**
 * 抑制条件模板
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-17
 */
@Component
public class SuppressConditionTemplate {

    /**
     * 不考虑节假日, 每周5个交易日
     */
    public SuppressCondition week() {
        return of(5);
    }

    /**
     * 每月平均约22个交易日
     */
    public SuppressCondition month() {
        return of(22);
    }

    public SuppressCondition tradeDays10() {
        return of(10);
    }

    public SuppressCondition notSuppress() {
        return of(0);
    }
    
    public SuppressCondition of(int tradeDays) {
        return new SuppressCondition(-1L, -1L, tradeDays);
    }
}
