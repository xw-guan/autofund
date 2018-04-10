package com.xwguan.autofund.entity.plan.portfolio;

import java.util.List;

/**
 * 证券投资组合
 * TODO 删除? 没有太大意义的多一层结构
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-03
 */
public class Portfolio {
    /**
     * 组合id
     */
    private Long id;

    /**
     * 从属计划的id;
     */
    private Long planId;

    /**
     * 持仓列表
     */
    private List<Position> positionList;

    public Portfolio() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<Position> positionList) {
        this.positionList = positionList;
    }

}
