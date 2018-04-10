package com.xwguan.autofund.enums;

/**
 * 计划模板枚举
 * <ul>
 * <li>PERIOD_BUY_PLAN("普通定投计划", "PB")
 * <li>MA_PLAN("均线计划", "MA")
 * <li>GAIN_LOSS_PLAN("盈亏计划", "GL")
 * <li>MA_WITH_PROFIT_TAKING("止盈均线计划", "MAPT")
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-03-29
 */
public enum PlanTemplateEnum {

    PERIOD_BUY_PLAN("普通定投计划", "PB"),

    MA_PLAN("均线计划", "MA"),

    GAIN_LOSS_PLAN("盈亏计划", "GL"),

    MA_WITH_PROFIT_TAKING("止盈均线计划", "MAPT")

    ;

    /**
     * 模板名称
     */
    private String planName;

    /**
     * 模板代号
     */
    private String templateCode;

    private PlanTemplateEnum(String planName, String templateCode) {
        this.planName = planName;
        this.templateCode = templateCode;
    }

    public String getPlanName() {
        return planName;
    }

    public String getCode() {
        return templateCode;
    }

    public static PlanTemplateEnum of(String code) {
        for (PlanTemplateEnum pt : values()) {
            if (pt.getCode().equals(code)) {
                return pt;
            }
        }
        return null;
    }

}
