package com.xwguan.autofund.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 计划模板枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-29
 */
public enum TacticTemplateEnum {

    PERIOD_BUY_TACTIC("普通定投策略", "PB", TacticTypeEnum.PERIOD_BUY_TACTIC),

    MA_TACTIC_250("均线策略", "MA250", TacticTypeEnum.MA_TACTIC),

    GAIN_LOSS_TACTIC("盈亏策略", "GL", TacticTypeEnum.GAIN_LOSS_TACTIC),

    INDEX_CHANGE_TACTIC_22("指数变化策略", "IC22", TacticTypeEnum.INDEX_CHANGE_TACTIC),
    
    NAV_CHANGE_TACTIC_22("净值变化策略", "NC22", TacticTypeEnum.NAV_CHANGE_TACTIC),

    PLAN_PROFIT_TAKING_TACTIC("计划止盈策略", "PTPL", TacticTypeEnum.PROFIT_TAKING_TACTIC),

    POSITION_PROFIT_TAKING_TACTIC("持仓止盈策略", "PTPST", TacticTypeEnum.GAIN_LOSS_TACTIC);

    /**
     * 模板名称
     */
    private String planName;

    /**
     * 模板代号
     */
    private String templateCode;

    /**
     * 策略类型, 主要用于标识以保证每个计划的策略唯一性
     */
    private TacticTypeEnum tacticType;

    private TacticTemplateEnum(String planName, String templateCode, TacticTypeEnum tacticType) {
        this.planName = planName;
        this.templateCode = templateCode;
        this.tacticType = tacticType;
    }

    public String getPlanName() {
        return planName;
    }

    public String getCode() {
        return templateCode;
    }

    public TacticTypeEnum getTacticType() {
        return tacticType;
    }

    public static TacticTemplateEnum of(String code) {
        for (TacticTemplateEnum tt : values()) {
            if (tt.getCode().equals(code)) {
                return tt;
            }
        }
        return null;
    }

    public static List<TacticTemplateEnum> of(TacticTypeEnum tacticType) {
        return Stream.of(values())
            .filter(tt -> tt.getTacticType().equals(tacticType))
            .collect(Collectors.toList());

        // List<TacticTemplateEnum> templateList = new ArrayList<>();
        // for (TacticTemplateEnum tt : values()) {
        // if (tt.getTacticType().equals(tacticType)) {
        // templateList.add(tt);
        // }
        // }
        // return templateList;
    }

}
