package com.xwguan.autofund.enums;

/**
 * 条件单位
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2017-11-15
 */
public enum ConditionUnitEnum {

    PERCENT("%"), YUAN("元");

    private String info;

    private ConditionUnitEnum(String desc) {
        this.info = desc;
    }

    public String getInfo() {
        return info;
    }

}
