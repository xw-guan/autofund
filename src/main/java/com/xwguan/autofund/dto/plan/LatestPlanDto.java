package com.xwguan.autofund.dto.plan;

import java.util.List;

import com.xwguan.autofund.dto.account.LatestAccountDto;
import com.xwguan.autofund.dto.plan.tactic.FlatTacticsDto;

public class LatestPlanDto {

    private PlanInfoDto planInfo;

    private LatestAccountDto latestAccount;

    private FlatTacticsDto tactics;

    private List<LatestPositionDto> latestPositions;

    public PlanInfoDto getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(PlanInfoDto planInfo) {
        this.planInfo = planInfo;
    }

    public LatestAccountDto getLatestAccount() {
        return latestAccount;
    }

    public void setLatestAccount(LatestAccountDto latestAccount) {
        this.latestAccount = latestAccount;
    }

    public FlatTacticsDto getTactics() {
        return tactics;
    }

    public void setTactics(FlatTacticsDto tactics) {
        this.tactics = tactics;
    }

    public List<LatestPositionDto> getLatestPositions() {
        return latestPositions;
    }

    public void setLatestPositions(List<LatestPositionDto> latestPositions) {
        this.latestPositions = latestPositions;
    }

    @Override
    public String toString() {
        return "LatestPlanDto [planInfo=" + planInfo + ", latestAccount=" + latestAccount + ", tactics=" + tactics
            + ", latestPositions=" + latestPositions + "]";
    }

}
