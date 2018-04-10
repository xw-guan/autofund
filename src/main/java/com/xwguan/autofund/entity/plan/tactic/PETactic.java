package com.xwguan.autofund.entity.plan.tactic;

import com.xwguan.autofund.annotation.Unimplement;
import com.xwguan.autofund.service.handler.EntityHandler;
import com.xwguan.autofund.service.handler.Handleable;
import com.xwguan.autofund.service.handler.tactic.PositionTacticHandler;

/**
 * 市盈率策略, 应用于投资组合中的每一个持仓, 一般为主要策略
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-16
 */
@Unimplement // 暂时没找到获取历史PE数据的方法, 没法进行历史百分位计算
public class PETactic extends PositionTactic implements Handleable {

    /**
     * 指数的id
     */
    private Integer indexId;

    public Integer getIndexId() {
        return indexId;
    }

    public void setIndexId(Integer indexId) {
        this.indexId = indexId;
    }

    @Override
    public Class<? extends EntityHandler<? extends Handleable>> handlerClass() {
        return null;
    }

    @Override
    public PositionTacticHandler handler() {
        // TODO Auto-generated method stub
        return null;
    }

}
