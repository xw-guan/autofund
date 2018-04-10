package com.xwguan.autofund.dto.plan;

import java.util.List;

import com.xwguan.autofund.dto.plan.tactic.FlatTacticsDto;

/**
 * 用于创建和修改
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-04-03
 */
public class PlanDto {
    
    private PlanInfoDto planInfo;
    
    private FlatTacticsDto tactics;
    
    private List<PositionDto> positions;

    public PlanInfoDto getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(PlanInfoDto planInfo) {
        this.planInfo = planInfo;
    }

    public FlatTacticsDto getTactics() {
        return tactics;
    }

    public void setTactics(FlatTacticsDto tactics) {
        this.tactics = tactics;
    }

    public List<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionDto> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "PlanDto [planInfo=" + planInfo + ", tactics=" + tactics + ", positions=" + positions + "]";
    }
    
    
    
}
