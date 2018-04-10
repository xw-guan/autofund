package com.xwguan.autofund.enums;

/**
 * 计划执行模式枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-15
 */
public enum PlanExecutionModeEnum {

    MSG("短信提醒"), AUTO_TRADE("自动交易"), EMAIL("邮件提醒"), IN_APP("应用内提醒");

    private String info;

    private PlanExecutionModeEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
