package com.xwguan.autofund.entity.plan.portfolio;

/**
 * 持仓占比
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-05
 */
public class PositionPropotion {

    /**
     * 持仓比例id
     */
    private Long id;

    /**
     * 持仓id
     */
    private Long positionId;

    /**
     * 比例百分比
     */
    private Double propotionPct;

    public PositionPropotion() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Double getPropotionPct() {
        return propotionPct;
    }

    public void setPropotionPct(Double propotionPct) {
        this.propotionPct = propotionPct;
    }

    @Override
    public String toString() {
        return "PositionPropotion [id=" + id + ", positionId=" + positionId + ", propotionPct=" + propotionPct + "]";
    }

}
