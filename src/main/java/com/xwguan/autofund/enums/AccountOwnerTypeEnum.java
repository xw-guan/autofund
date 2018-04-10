package com.xwguan.autofund.enums;

/**
 * 账户所有者枚举
 * 
 * @author XWGuan
 * @version 1.0.0
 * @date 2018-01-07
 */
public enum AccountOwnerTypeEnum {

    /**
     * 每个用户拥有的账户
     */
    USER("用户"),

    /**
     * 每个计划拥有的账户
     */
    PLAN("计划"),

    /**
     * 每个持仓拥有独立的账户对象
     */
    POSITION("持仓");

    private String info;

    private AccountOwnerTypeEnum(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
