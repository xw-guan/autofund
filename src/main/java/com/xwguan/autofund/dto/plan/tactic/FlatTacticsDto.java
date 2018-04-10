package com.xwguan.autofund.dto.plan.tactic;

public class FlatTacticsDto {

    private GainLossTacticDto gainLossTactic;

    private IndexChangeTacticDto indexChangeTactic;

    private MATacticDto maTactic;

    private NavChangeTacticDto navChangeTactic;

    private PeriodBuyTacticDto periodBuyTactic;

    private ProfitTakingTacticDto profitTakingTactic;

    public GainLossTacticDto getGainLossTactic() {
        return gainLossTactic;
    }

    public void setGainLossTactic(GainLossTacticDto gainLossTactic) {
        this.gainLossTactic = gainLossTactic;
    }

    public IndexChangeTacticDto getIndexChangeTactic() {
        return indexChangeTactic;
    }

    public void setIndexChangeTactic(IndexChangeTacticDto indexChangeTactic) {
        this.indexChangeTactic = indexChangeTactic;
    }

    public MATacticDto getMaTactic() {
        return maTactic;
    }

    public void setMaTactic(MATacticDto maTactic) {
        this.maTactic = maTactic;
    }

    public NavChangeTacticDto getNavChangeTactic() {
        return navChangeTactic;
    }

    public void setNavChangeTactic(NavChangeTacticDto navChangeTactic) {
        this.navChangeTactic = navChangeTactic;
    }

    public PeriodBuyTacticDto getPeriodBuyTactic() {
        return periodBuyTactic;
    }

    public void setPeriodBuyTactic(PeriodBuyTacticDto periodBuyTactic) {
        this.periodBuyTactic = periodBuyTactic;
    }

    public ProfitTakingTacticDto getProfitTakingTactic() {
        return profitTakingTactic;
    }

    public void setProfitTakingTactic(ProfitTakingTacticDto profitTakingTactic) {
        this.profitTakingTactic = profitTakingTactic;
    }

    @Override
    public String toString() {
        return "FlatTacticsDto [gainLossTactic=" + gainLossTactic + ", indexChangeTactic=" + indexChangeTactic
            + ", maTactic=" + maTactic + ", navChangeTactic=" + navChangeTactic + ", periodBuyTactic=" + periodBuyTactic
            + ", profitTakingTactic=" + profitTakingTactic + "]";
    }

}
