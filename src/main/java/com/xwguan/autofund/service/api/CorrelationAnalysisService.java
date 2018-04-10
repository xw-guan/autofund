package com.xwguan.autofund.service.api;

import com.xwguan.autofund.annotation.Unimplement;

/**
 * 基金与指数的相关性分析服务
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-30
 */
@Unimplement
public interface CorrelationAnalysisService {
    /**
     * 分析相关性
     * 
     * @param fundId
     * @param indexId
     * @return 相关系数
     */
    double analyseCorrelation(int fundId, int indexId);
}