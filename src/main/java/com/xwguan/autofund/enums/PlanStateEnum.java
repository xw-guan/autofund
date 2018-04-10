package com.xwguan.autofund.enums;

/**
 * 计划状态枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-12-05
 */
public enum PlanStateEnum {

    RUNNING("运行中"), USER_PAUSE("用户暂停"), USER_END("用户中止"), PROFIT_TAKING_END("止盈中止");

    private String info;

    private PlanStateEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

}
