package com.xwguan.autofund.service.api;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.entity.plan.scheme.PlanTradeScheme;
import com.xwguan.autofund.enums.PlanExecutionModeEnum;

/**
 * 推送服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-02-15
 */
@Unimplement
public interface PushService {

    /**
     * 推送交易策略
     * 
     * @param planTradeScheme 计划交易策略
     * @param pushMode 推送模式, 值为AUTO_TRADE时不推送, 其他按照具体模式推送
     * @return 是否推送成功
     */
    boolean pushTradeScheme(PlanTradeScheme planTradeScheme, PlanExecutionModeEnum pushMode);

}
